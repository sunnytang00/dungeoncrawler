package dungeonmania;

import dungeonmania.entities.Item;
import dungeonmania.entities.collectableEntities.Bomb;
import dungeonmania.entities.collectableEntities.InvincibilityPotion;
import dungeonmania.entities.collectableEntities.InvisibilityPotion;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.movingEntity.Player;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.*;
import dungeonmania.Entity;

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

    private DungeonMap dungeonMap;
    private JSONMap jMap;

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
        jMap = new JSONMap(is);
        
        List<Entity> entities = jMap.getInitialMapEntities();
        dungeonMap = currentDungeonMap(entities, dungeonName);
        
        List<EntityResponse> entityResponses = dungeonMap.getEntityResponses();
        DungeonGame dGame = new DungeonGame(jMap.getGoals(), null, null, null);
        return new DungeonResponse(dGame.getDungeonId(), dungeonName, entityResponses, null, null, null, jMap.getGoals());
    }

    /**
     * /game/dungeonResponseModel
     */
    public DungeonResponse getDungeonResponseModel() {
        return null;
    }

    /**
     * /game/tick/item
     */
    public DungeonResponse tick(String itemUsedId) throws IllegalArgumentException, InvalidActionException {
        if (null == itemUsedId || "".equals(itemUsedId)) {
            throw new InvalidActionException("Not found the item with the given id(" + itemUsedId + ")");
        }
        Player player = dungeonMap.getPlayer();
        List<Item> inventory = player.getInventory();
        Item targetItem = null;
        for (Item item : inventory) {
            if (itemUsedId.equals(item.getId())) {
                targetItem = item;
                break;
            }
        }

        if (null == targetItem) {
            throw new InvalidActionException("Not found the item with the given id(" + itemUsedId + ")");
        }

        if (!(targetItem instanceof Bomb)
                && !(targetItem instanceof InvincibilityPotion)
                && !(targetItem instanceof InvisibilityPotion)) {
            throw new IllegalArgumentException();
        }

        // firstly, remove the item from the player's inventory
        inventory.remove(targetItem);

        if (targetItem instanceof Bomb) {
            Bomb bomb = (Bomb)targetItem;
            bomb.explode(dungeonMap);
        }

        if (targetItem instanceof InvincibilityPotion) {
            InvincibilityPotion invincibilityPotion = (InvincibilityPotion)targetItem;
            if (invincibilityPotion.isTriggered()) {
                player.setInvincible(true);
                invincibilityPotion.updateTicks();
            }
        }

        if (targetItem instanceof InvisibilityPotion) {
            InvisibilityPotion invisibilityPotion = (InvisibilityPotion)targetItem;
            if (invisibilityPotion.isTriggered()) {
                player.setInvisible(true);
                invisibilityPotion.updateTicks();
            }
        }

        List<EntityResponse> entityResponses = dungeonMap.getEntityResponses();
        String goals = jMap.getGoals();
        DungeonGame dDame = new DungeonGame(goals, inventory, null, null);
        List<ItemResponse> itemResponses = Helper.convertFromItem(inventory);
        return new DungeonResponse(dDame.getDungeonId(), dungeonMap.getDungeonName(), entityResponses, itemResponses, null, null, goals);
    }

    /**
     * /game/tick/movement
     */
    public DungeonResponse tick(Direction movementDirection) {
        return null;
    }

    /**
     * /game/build
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        return null;
    }

    /**
     * /game/interact
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        return null;
    }

    //HELPERS DOWN HERE
    public DungeonMap currentDungeonMap(List<Entity> entities, String dungeonName) {
        return new DungeonMap(entities, dungeonName);
    }
}
