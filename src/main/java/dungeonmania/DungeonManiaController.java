package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.goals.Goals;
import dungeonmania.response.models.*;
import dungeonmania.util.*;
import dungeonmania.util.JSONMap;
import dungeonmania.entities.buildableEntities.*;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.entities.collectableEntities.potions.*;
import dungeonmania.entities.*;
import dungeonmania.entities.StaticEntities.TimeTravellingPortal;
import dungeonmania.entities.StaticEntities.ZombieToastSpawner;
import dungeonmania.entities.StaticEntities.logicSwitches.LogicItem;
import dungeonmania.entities.movingEntity.enemies.*;
import dungeonmania.entities.movingEntity.player.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;

public class DungeonManiaController {

    private DungeonMap map;
    private DungeonGame game;
    // private ArrayList<DungeonMap> mapList = new ArrayList<DungeonMap>();
    // private ArrayList<DungeonGame> gameList = new ArrayList<DungeonGame>();
    // private ArrayList<DungeonMap> mapsToPlayOut = new ArrayList<DungeonMap>();
    // private ArrayList<DungeonGame> gamesToPlayOut = new ArrayList<DungeonGame>();
    // private boolean timeTravelled = false;
    private Goals goals;

    public String getSkin() {
        return "default";
    }

    public String getLocalisation() {
        // chinese version available "CN"
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

        game = new DungeonGame(goals.getGoalsAsString(map), inventoryItems, battles, buildableItems, map);
        
        saveTickToHistory();

        return new DungeonResponse(game.getDungeonId(), dungeonName, entityResponses, inventoryResponses,
                battleResponses, buildableItems,
                goals.getGoalsAsString(map));
    }

    /**
     * /game/dungeonResponseModel
     */
    public DungeonResponse getDungeonResponseModel() {

        Player player = map.getPlayer();
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
        if (player == null) { return getDungeonResponseModel();}

        List<Item> inventory = player.getInventory();
        Item targetItem = null;

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

        targetItem.tick(game);
        player.playerPotionQueueUpdateTick();

        map.BoulderSwitchOverlap();
        game.updateLogicSwitches();
        for (Entity entity : map.getMapEntities()) {
            if (entity instanceof Enemy || entity instanceof ZombieToastSpawner) {
                entity.tick(game);
            }
        }

        player.battle(map, game);
        if (map.getPlayer() == null) { return getDungeonResponseModel();}
        map.spawnSpider(game);
        List<Entity> enemiesToSpawn = map.getEnemiesToSpawn();
        if (enemiesToSpawn != null && enemiesToSpawn.size() > 0) {
            map.addEntitiesToMap(enemiesToSpawn);
        }

        map.setEnemiesToSpawn(new ArrayList<Entity>());

        // OlderPlayer olderPlayer = (OlderPlayer) map.getEntitiesFromType(map.getMapEntities(), "older_player");
        // if (olderPlayer != null) {
        //     if (olderPlayer.remainingTimeTravel() == 0) {
        //         map.removeEntityFromMap(olderPlayer);
        //     }
        // }

        saveTickToHistory();
        return getDungeonResponseModel();
    }

    /**
     * /game/tick/movement
     */
    public DungeonResponse tick(Direction movementDirection) {
        game.incrementTick();
        Player player = map.getPlayer();
        if (player == null) { return getDungeonResponseModel();}
        // potion effect
        player.playerPotionQueueUpdateTick();

        Position nextPos = player.getPosition().translateBy(movementDirection);
        if (map.getEntityFromPos(nextPos).stream().anyMatch(x -> x instanceof TimeTravellingPortal)) {
            player.move(game, map, movementDirection);
            return rewind(30);
        }

        player.move(game, map, movementDirection);
        if (map.getPlayer() == null) { return getDungeonResponseModel();}
        
        map.BoulderSwitchOverlap();
        game.updateLogicSwitches();
        for (Entity entity : map.getMapEntities()) {
            if (entity instanceof Enemy || entity instanceof ZombieToastSpawner) {
                entity.tick(game);
            }
        }

        player.battle(map, game);
        if (map.getPlayer() == null) { return getDungeonResponseModel();}
        map.spawnSpider(game);
        List<Entity> enemiesToSpawn = map.getEnemiesToSpawn();
        if (enemiesToSpawn != null && enemiesToSpawn.size() > 0) {
            map.addEntitiesToMap(enemiesToSpawn);
        }

        map.setEnemiesToSpawn(new ArrayList<Entity>());

        // OlderPlayer olderPlayer = null;
        // if (map.getEntitiesFromType(map.getMapEntities(), "older_player").size() != 0) {
        //     olderPlayer = (OlderPlayer) map.getEntitiesFromType(map.getMapEntities(), "older_player").get(0);
        // }
        // if (olderPlayer != null) {
        //     if (olderPlayer.remainingTimeTravel() == 0) {
        //         map.removeEntityFromMap(olderPlayer);
        //     }
        // }

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
                throw new IllegalArgumentException(
                        buildable + " is not one of bow, shield, midnight_armour or sceptre");

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
        DungeonResponse res = getDungeonResponseModel();
        // save files to "./bin/saved_games/" + <fileName> + ".json" 
        if (name.length() == 0) {
            throw new IllegalArgumentException("please provide a name");
        }
        File pathAsFile = new File("./bin/");
        if (!pathAsFile.exists()) {
            pathAsFile.mkdir();
        }
        String path = "./bin/" + name + ".json";
        int currTick = game.getCurrentTick();
        JSONObject currGame = game.getGameFromTickHistory(currTick);
        try {
            PrintWriter out = new PrintWriter(new FileWriter(path));
            out.write(currGame.toString());
            out.close();
        } catch (Exception e) {
            return null;
        }
        return res;
    }

    /**
     * /game/load
     */
    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        if (name.length() == 0) {
            throw new IllegalArgumentException("please provide a name");
        }
        JSONReloadGame reloadGame = null;
        // File pathAsFile = new File("./bin/");
        // if (!pathAsFile.exists()) {
        //     pathAsFile.mkdir();
        // }
        // // add a stub file to ensure the path exist for reading 

        try (InputStream is = new FileInputStream("./bin/" + name + ".json")) {
            reloadGame = new JSONReloadGame(is, name);
            map = new DungeonMap(reloadGame.getMapEntities(), name);
            goals = reloadGame.getGoals();
            map.resetGoals(goals);
            map.setJSONGoals(reloadGame.getJSONGoals());
        } catch (IOException e) {
            throw new IllegalArgumentException("cannot find such file");
        }
        map = reloadGame.getReloadedMap();
        game = reloadGame.getReloadedGame();
        return getDungeonResponseModel();
    }

    /**
     * /games/all
     */
    public List<String> allGames() {
        File pathAsFile = new File("./bin/");
        if (!pathAsFile.exists()) {
            pathAsFile.mkdir();
        }
        Path files = Paths.get("bin/");
        try {
            return Files.walk(files)
                        .filter(Files::isRegularFile)
                        .map(x -> {String name = x.toFile().getName();
                                   int i = name.lastIndexOf('.');
                                   return name.substring(0, i > -1 ? i : name.length());
                            })
                        .collect(Collectors.toList());
        } catch (IOException e) {
            return null;
        }
    }

    public void saveTickToHistory() {
        if (map.getPlayer() == null || goals.getGoalsAsString(map).equals("")) {
            // player wins or loses
            return;
        }
        JSONObject obj = JSONSaveGame.saveGame(map, goals, game);
        game.addToTickHistory(obj, map.getPlayer().getPosition());
    }

    /**
     * Rewind
     * 
     * @param ticks
     * @return
     * @throws IllegalArgumentException
     */

    public DungeonResponse rewind(int ticks) throws IllegalArgumentException {
        
        Player currPlayer = map.getPlayer();

        if (ticks <= 0) {
            throw new IllegalArgumentException("The number of ticks must be > 0");
        }

        if (ticks > game.getTickHistorySize() && ticks != 30) {// not a time travelling portal
            throw new IllegalArgumentException("The number of ticks has not occured yet");
        }

        JSONObject gameJSON = null;
        int currTick = game.getCurrentTick();
        List<Position> olderPlayerMovements = new ArrayList<Position>();
        
        if (ticks == 30) {
            gameJSON = game.getGameFromTickHistory(0);
            olderPlayerMovements = game.getPlayerMovementsStartingFrom(0);
        } else {
            gameJSON = game.getGameFromTickHistory(currTick - ticks);
            olderPlayerMovements = game.getPlayerMovementsStartingFrom(currTick - ticks);
        }

        ResetGame resetGame = new ResetGame(gameJSON, map);
        map = resetGame.reloadMap();
        
        map.changePlayerToOlder(olderPlayerMovements);
        map.addEntityToMap(currPlayer);
        
        return getDungeonResponseModel();
    }

    public DungeonResponse generateDungeon(int xStart, int yStart, int xEnd, int yEnd, String configName) {
        return null;
    }

}
