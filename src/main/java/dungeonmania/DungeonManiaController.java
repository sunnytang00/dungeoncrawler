package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;
import dungeonmania.Entity;
import dungeonmania.util.JSONConfig;
import dungeonmania.util.JSONMap;
import dungeonmania.entities.buildableEntities.*;
import dungeonmania.movingEntity.*;

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

        Player player = getCurrentMap().getPlayer();
        //game.getBattleResponse()
        return new DungeonResponse(game.getDungeonId(), map.getDungeonName(), map.getEntityResponses(), player.getInventoryResponses(), new ArrayList<BattleResponse>() , player.getBuildables(), game.getGoals());
    }

    /**
     * /game/tick/item
     */
    public DungeonResponse tick(String itemUsedId) throws IllegalArgumentException, InvalidActionException {
        return null;
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
    
        Player player = getCurrentMap().getPlayer();

        switch(buildable) {
            case "bow":
                Bow bow = new Bow(buildable);
                bow.build(player.getInventory(), player);
                break;

            case "shield":
                Shield shield = new Shield(buildable);
                shield.build(player.getInventory(), player);
                break;
        }

        return getDungeonResponseModel();

    }

    /**
     * /game/interact
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        return null;
    }

    //HELPERS DOWN HERE

    
    // public DungeonMap currentDungeonMap(List<Entity> entities, String dungeonName) {
    //     return new DungeonMap(entities, dungeonName);
    // }

    public DungeonMap getCurrentMap() {
        return map;
    }


}
