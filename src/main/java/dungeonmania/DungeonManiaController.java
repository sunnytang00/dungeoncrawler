package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.goals.Goals;
import dungeonmania.response.models.*;
import dungeonmania.util.*;
import dungeonmania.util.JSONMap;
import dungeonmania.entities.buildableEntities.*;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.movingEntity.*;
import dungeonmania.StaticEntities.TimeTravellingPortal;
import dungeonmania.StaticEntities.ZombieToastSpawner;
import dungeonmania.entities.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class DungeonManiaController {

    private DungeonMap map;
    private DungeonGame game;
    private ArrayList<DungeonMap> mapList = new ArrayList<DungeonMap>();
    private ArrayList<DungeonGame> gameList = new ArrayList<DungeonGame>();
    private ArrayList<DungeonMap> mapsToPlayOut = new ArrayList<DungeonMap>();
    private ArrayList<DungeonGame> gamesToPlayOut = new ArrayList<DungeonGame>();
    private boolean timeTravelled = false;
    private Goals goals;
    private List<JSONObject> tickHistory = new ArrayList<JSONObject>();

    public String getSkin() {
        return "default";
    }

    public String getLocalisation() {
        return "en_US";
    }

    /**
     * /dungeons
     */
    public static List<String> dungeons() {
        return FileLoader.listFileNamesInResourceDirectory("dungeons");
    }

    /**
     * /configs
     */
    public static List<String> configs() {
        return FileLoader.listFileNamesInResourceDirectory("configs");
    }

    /**
     * /game/new
     */
    public DungeonResponse newGame(String dungeonName, String configName) throws IllegalArgumentException {

        JSONConfig.setConfig(configName);
        // get initial entities from json dungeon map, create a dungeon map instance of
        // the game and store all initial entities
        InputStream is = FileLoader.class.getResourceAsStream("/dungeons/" + dungeonName + ".json");
        if (is == null) {
            throw new IllegalArgumentException();
        }
        JSONMap jMap = new JSONMap(is);

        List<Entity> entities = jMap.getInitialMapEntities();
        map = new DungeonMap(entities, dungeonName);
        goals = JSONLoadGoals.getComposedGoals(jMap.getGoals(), map);
        map.setJSONGoals(jMap.getGoals());

        List<EntityResponse> entityResponses = map.getEntityResponses();
        List<Item> inventoryItems = new ArrayList<Item>();
        List<ItemResponse> inventoryResponses = new ArrayList<ItemResponse>();
        List<Battle> battles = new ArrayList<Battle>();
        List<BattleResponse> battleResponses = new ArrayList<BattleResponse>();
        List<String> buildableItems = new ArrayList<String>();

        game = new DungeonGame(goals.getGoalsAsString(map), inventoryItems, battles, buildableItems);
        
        gameList.add(game);
        mapList.add(map);


        return new DungeonResponse(game.getDungeonId(), dungeonName, entityResponses, inventoryResponses, battleResponses, buildableItems,
                goals.getGoalsAsString(map));
    }

    /**
     * /game/dungeonResponseModel
     */
    public DungeonResponse getDungeonResponseModel() {

        Player player = map.getPlayer();
        // System.out.println("dungeonresponse" + goals.getGoalsAsString(map));
        List<BattleResponse> battles = map.getBattleResponses(game.getBattles());
        if (player == null) {
            
            return new DungeonResponse(game.getDungeonId(), map.getDungeonName(), map.getEntityResponses(), null,
                    battles, null, goals.getGoalsAsString(map));
        }
        return new DungeonResponse(game.getDungeonId(), map.getDungeonName(), map.getEntityResponses(),
                player.getInventoryResponses(), battles, player.getBuildables(map), goals.getGoalsAsString(map));

    }

    /**
     * /game/tick/item
     */
    public DungeonResponse tick(String itemUsedId) throws IllegalArgumentException, InvalidActionException {
        if (null == itemUsedId || "".equals(itemUsedId)) {
            throw new InvalidActionException("Not found the item with the given id(" + itemUsedId + ")");
        }

        game.incrementTick();

        Player player = map.getPlayer();
        List<Item> inventory = player.getInventory();
        Item targetItem = null;
        List<ZombieToast> zombiesToAdd = new ArrayList<>();
        for (Item item : inventory) {
            if (itemUsedId.equals(item.getId())) {
                targetItem = item;
                break;
            }
        }

        if (targetItem == null) {
            throw new InvalidActionException("Not found the item with the given id(" + itemUsedId + ")");
        }

        if (!(targetItem instanceof Bomb)
                && !(targetItem instanceof InvincibilityPotion)
                && !(targetItem instanceof InvisibilityPotion)) {
            throw new IllegalArgumentException();
        }

        // remove the item from the player's inventory
        inventory.remove(targetItem);
        player.setInventory(inventory);

        if (targetItem instanceof Bomb) {
            Bomb bomb = (Bomb) targetItem;
            Position newPosition = player.getPosition();
            bomb.setPosition(newPosition);
            map.addEntityToMap(bomb);
            bomb.explode(map);
        }

        if (targetItem instanceof InvincibilityPotion) {
            InvincibilityPotion invincibilityPotion = (InvincibilityPotion) targetItem;
            player.consumePotion(invincibilityPotion);
        }

        if (targetItem instanceof InvisibilityPotion) {
            InvisibilityPotion invisibilityPotion = (InvisibilityPotion) targetItem;
            player.consumePotion(invisibilityPotion);
        }

        player.playerPotionQueueUpdateTick();

        // List<EntityResponse> entityResponses = map.getEntityResponses();
        // DungeonGame dDame = new DungeonGame(goals.getGoalsAsString(map), inventory, null, null);

        // List<ItemResponse> itemResponses = Helper.convertFromItem(inventory);
        List<Enemy> enemies = new ArrayList<>();
        for (Entity entity : map.getMapEntities()) {
            if (entity instanceof Enemy) {
                Enemy enemy = (Enemy) entity;
                enemies.add(enemy);
                enemy.move(enemy, map);
            }
            if (entity instanceof ZombieToastSpawner) {
                ZombieToastSpawner ZTSpawner = (ZombieToastSpawner) entity;
                ZombieToast zombie = ZTSpawner.spawnZombie(game.getCurrentTick(), map);
                if (zombie != null) {
                    zombiesToAdd.add(zombie);
                }
            }
        }

        for (Enemy enemy : enemies) {
            if (enemy instanceof Mercenary) {
                Mercenary mercenary = (Mercenary) enemy;
                mercenary.updateMindControl();
            }
            player.interactWithEnemies(enemy, map);
            player.battleWithEnemies(map, game);
        }

        map.addEntitiesToMap(zombiesToAdd);
        Spider spiderToAdd = map.spawnSpider(game.getCurrentTick(), map);

        if (spiderToAdd != null) {
            map.addEntityToMap(spiderToAdd);
        }
        map.BoulderSwitchOverlap();

        gameList.add(game);
        mapList.add(map);

        saveTickToHistory();
        return getDungeonResponseModel();
    }

    /**
     * /game/tick/movement
     */
    public DungeonResponse tick(Direction movementDirection) {
        game.incrementTick();
        Player player = map.getPlayer();
        // potion effect
        player.playerPotionQueueUpdateTick();
        
        Position nextPos = player.getPosition().translateBy(movementDirection);
        if (map.getEntityFromPos(nextPos).stream().anyMatch(x -> x instanceof TimeTravellingPortal)) {
            player.move(game, map, movementDirection);
            rewind(30);
        }


        player.move(game, map, movementDirection);
        List<Enemy> enemies = new ArrayList<>();
        List<ZombieToast> zombiesToAdd = new ArrayList<>();

        for (Entity entity : map.getMapEntities()) {
            if (entity instanceof Enemy) {
                Enemy enemy = (Enemy) entity;
                enemies.add(enemy);
                enemy.move(enemy, map);
            }

            if (entity instanceof ZombieToastSpawner) {
                ZombieToastSpawner ZTSpawner = (ZombieToastSpawner) entity;
                ZombieToast zombie = ZTSpawner.spawnZombie(game.getCurrentTick(), map);
                if (zombie != null) {
                    zombiesToAdd.add(zombie);
                }
            }

        }

        for (Enemy enemy : enemies) {
            if (enemy instanceof Mercenary) {
                Mercenary mercenary = (Mercenary) enemy;
                mercenary.updateMindControl();
            }
            player.interactWithEnemies(enemy, map);
            player.battleWithEnemies(map, game);

        }
        map.addEntitiesToMap(zombiesToAdd);

        Spider spiderToAdd = map.spawnSpider(game.getCurrentTick(), map);

        if (spiderToAdd != null) {
            map.addEntityToMap(spiderToAdd);
        }
        map.BoulderSwitchOverlap();

        gameList.add(game);
        mapList.add(map);

        saveTickToHistory();
        return getDungeonResponseModel();

    }

    /**
     * /game/build
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {

        Player player = map.getPlayer();

        switch (buildable) {
            case "bow":
                Bow bow = new Bow(buildable);
                bow.build(player.getInventory(), player, map);
                break;

            case "shield":
                Shield shield = new Shield(buildable);
                shield.build(player.getInventory(), player, map);
                break;

            case "sceptre":
                Sceptre sceptre = new Sceptre(buildable);
                sceptre.build(player.getInventory(), player, map);
                break;
            
            case "midnight_armour":
                MidnightArmour armour = new MidnightArmour(buildable);
                armour.build(player.getInventory(), player, map);
                break;


            default:
                throw new IllegalArgumentException(buildable + " is not one of bow, shield, midnight_armour or sceptre");

        }

        return getDungeonResponseModel();

    }

    /**
     * /game/interact
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        Player player = map.getPlayer();
        Entity interact = map.getEntityFromID(entityId);

        if (interact == null) {
            throw new InvalidActionException("Not found the item with the given id(" + entityId + ")");
        }

        if (!(interact instanceof ZombieToastSpawner)
                && !(interact instanceof Mercenary)) {
            throw new IllegalArgumentException();
        }

        if (interact instanceof ZombieToastSpawner) {
            player.interactWithSpawner((ZombieToastSpawner) interact, map);
        }

        if (interact instanceof Mercenary) {
            Mercenary mercenary = (Mercenary) interact;
            // mercenary has been bribed
            if (mercenary.isBribed()) {
                throw new IllegalArgumentException();
            }
            player.interactWithMercenary(mercenary, map);
        }

        return getDungeonResponseModel();
    }

    /**
     * /game/save
     */
    public DungeonResponse saveGame(String name) throws IllegalArgumentException {
        return null;
    }

    /**
     * /game/load
     */
    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        InputStream is = FileLoader.class.getResourceAsStream("bin/recourses/dungeons/" + name + ".json");
        if (is == null) {
            throw new IllegalArgumentException("Cannot find the game to load");
        }
        JSONReloadGame reloadGame = new JSONReloadGame(is, name);
        map = new DungeonMap(reloadGame.getMapEntities(), name);
        goals = JSONLoadGoals.getComposedGoals(reloadGame.getGoals(), map);
        map.setJSONGoals(reloadGame.getGoals());

        return getDungeonResponseModel();
    }


    /**
     * /games/all
     */
    public List<String> allGames() {
        return new ArrayList<>();
    }

    public void saveTickToHistory() {
        if (map.getPlayer() == null || goals.getGoalsAsString(map).equals("")) {
            // player wins or loses
            return;
        }
        JSONObject obj = JSONSaveGame.saveGame(map, map.getJSONGoals(), game.getCurrentTick());
        // System.out.println(obj);
        tickHistory.add(obj);
    }

    /**
     * Rewind
     * @param ticks<
     * @return
     * @throws IllegalArgumentException
     */

    public DungeonResponse rewind(int ticks) throws IllegalArgumentException {
        // timeTravelled = true;
        // int gameSize = gameList.size();
        // int idx;
        // Position playerPosition = map.getPlayer().getPosition();
        // //arraylist of dungresponse should look like 0, 1, 2, 3, 4 so size = 5
        // if (ticks <= 0) {
        //     throw new IllegalArgumentException("The number of ticks must be > 0");
        // }

        // if (ticks >= gameSize) {
        //     throw new IllegalArgumentException("The number of ticks has not occured yet");
        // }

        // for (DungeonMap map: mapList) {//Change all player entitys in previous states to type older_player
        //     map.changePlayerToOlder();
        // }
        
        // for (idx = gameSize - ticks; idx < gameSize; idx++) {//Add the games that need to be played out to a new list
        //     gamesToPlayOut.add(gameList.get(idx));
        //     mapsToPlayOut.add(mapList.get(idx));
        // } 

        // if (ticks == 30 && (gameList.size() < 30)) {//If we go through portal and we have been through < 30 ticks, we go back to initial state
        //     map = mapList.get(0);
        //     game = gameList.get(0);

        // } else if (gameSize > ticks) {//We should be in here if there are no problems
        //     game = gameList.get((gameSize - ticks) - 1);//Set the current game and map state to the rewinded one
        //     map = mapList.get((gameSize - ticks) - 1);
        // }

        // map.addEntityToMap(new Player("player", playerPosition, false));
        // return getDungeonResponseModel();
        return null;
    }

}
