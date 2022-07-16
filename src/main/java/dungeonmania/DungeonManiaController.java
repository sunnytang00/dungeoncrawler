package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.*;
import dungeonmania.util.*;
import dungeonmania.entities.buildableEntities.*;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.movingEntity.*;
import dungeonmania.StaticEntities.ZombieToastSpawner;
import dungeonmania.entities.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;


public class DungeonManiaController {

    private DungeonMap map;
    private DungeonGame game;

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
        // get initial entities from json dungeon map, create a dungeon map instance of the game and store all initial entities
        InputStream is = FileLoader.class.getResourceAsStream("/dungeons/" + dungeonName + ".json");
        if (is == null) { throw new IllegalArgumentException(); }
        JSONMap jMap = new JSONMap(is);
        
        List<Entity> entities = jMap.getInitialMapEntities();
        map = new DungeonMap(entities, dungeonName);
        
        List<EntityResponse> entityResponses = map.getEntityResponses();
        game = new DungeonGame(jMap.getGoals(), null, null, null);
        
        return new DungeonResponse(game.getDungeonId(), dungeonName, entityResponses, null, null, null, jMap.getGoals());
    }

    /**
     * /game/dungeonResponseModel
     */
    public DungeonResponse getDungeonResponseModel() {

        Player player = map.getPlayer();
        List<BattleResponse> battles = map.getBattleResponses(game.getBattles());
        System.out.println(map.getPlayer());
        return new DungeonResponse(game.getDungeonId(), map.getDungeonName(), map.getEntityResponses(), player.getInventoryResponses(), battles , player.getBuildables(), game.getGoals());
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
            Bomb bomb = (Bomb)targetItem;
            Position newPosition = player.getPosition();
            bomb.setPosition(newPosition);
            bomb.explode(map);
        }

        if (targetItem instanceof InvincibilityPotion) {
            InvincibilityPotion invincibilityPotion = (InvincibilityPotion)targetItem;
            player.consumePotion(invincibilityPotion);
        }

        if (targetItem instanceof InvisibilityPotion) {
            InvisibilityPotion invisibilityPotion = (InvisibilityPotion)targetItem;
            player.consumePotion(invisibilityPotion);
        }

        player.playerPotionQueueUpdateTick();

        List<EntityResponse> entityResponses = map.getEntityResponses();
        String goals = game.getGoals();
        DungeonGame dDame = new DungeonGame(goals, inventory, null, null);
        List<ItemResponse> itemResponses = Helper.convertFromItem(inventory);

        for (Entity entity : map.getMapEntities()) {
            if (entity instanceof Enemy) {
                Enemy enemy = (Enemy) entity;
                enemy.getMovingStrategy().move(enemy, map);
            }
            if (entity instanceof ZombieToastSpawner) {
                ZombieToastSpawner ZTSpawner = (ZombieToastSpawner) entity;
                ZombieToast zombie = ZTSpawner.spawnZombie(game.getCurrentTick(), map);
                if (zombie != null) {
                    zombiesToAdd.add(zombie);
                }
            }
        }
        map.addEntitiesToMap(zombiesToAdd);
        Spider spiderToAdd = map.spawnSpider(game.getCurrentTick(), map);

        if (spiderToAdd != null) { 
            map.addEntityToMap(spiderToAdd);
        }
        map.BoulderSwitchOverlap();
        //return new DungeonResponse(dDame.getDungeonId(), map.getDungeonName(), entityResponses, itemResponses, null, null, goals);
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
        player.move(game, map, movementDirection);
        // System.out.println("playerhere" + player.getPosition());
        List<Enemy> enemies = new ArrayList<>();
        List<ZombieToast> zombiesToAdd = new ArrayList<>();

        
        for (Entity entity : map.getMapEntities()) {
             if (entity instanceof Enemy) {
                Enemy enemy = (Enemy) entity;
                enemies.add(enemy);
                enemy.getMovingStrategy().move(enemy, map);
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
            //System.out.println("Merccccc" + enemy.getMovingStrategy() + enemy.getPosition() + enemy.getType() + "player" + player.getPosition());
            player.interactWithEnemies(enemy, map);
            player.battleWithEnemies(map, game);
        }
        map.addEntitiesToMap(zombiesToAdd);
        
        Spider spiderToAdd = map.spawnSpider(game.getCurrentTick(), map);

        if (spiderToAdd != null) { 
            map.addEntityToMap(spiderToAdd);
        }
        map.BoulderSwitchOverlap();
        return getDungeonResponseModel();
        
    }


    /**
     * /game/build
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
    
        Player player = map.getPlayer();

        switch(buildable) {
            case "bow":
                Bow bow = new Bow(buildable);
                bow.build(player.getInventory(), player, map);
                break;

            case "shield":
                Shield shield = new Shield(buildable);
                shield.build(player.getInventory(), player, map);
                break;

            default:
                throw new IllegalArgumentException(buildable + " is not one of bow, shield");

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
            player.interactWithMercenary((Mercenary) interact, map);
        }
        
        return getDungeonResponseModel();
    }

    //HELPERS DOWN HERE

    
    // public DungeonMap currentDungeonMap(List<Entity> entities, String dungeonName) {
    //     return new DungeonMap(entities, dungeonName);
    // }

    // public DungeonMap getCurrentMap() {
    //     return map;
    // }


}
