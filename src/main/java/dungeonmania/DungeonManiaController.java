package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;
import netscape.javascript.JSObject;
import dungeonmania.util.JSONConfig;
import dungeonmania.util.JSONMap;

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
import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class DungeonManiaController {
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
        // List<Entity> entities = new ArrayList<Entity>();
        // entities.add(new Entity("player", new Position(1, 1) , true));
        // List<EntityResponse> entityResponses = entities.stream().map(Entity::getEntityResponse).collect(Collectors.toList());
        // return new DungeonResponse("dungeonTestId", dungeonName, entityResponses, null, null, null,null);
        return null;
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
        return null;
    }

    /**
     * /game/interact
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        return null;
    }

    //HELPERS DOWN HERE

    public JSONConfig getConfig(String filename) throws IOException {

        Gson gson = new Gson();
        String content = FileLoader.loadResourceFile("/configs/" + filename + ".json");
        
        JSONConfig config = gson.fromJson(content, JSONConfig.class);

        return config;
    }

    public JSONMap getMap(String fileName) throws IOException {

        InputStream is = FileLoader.class.getResourceAsStream("/dungeons/" + fileName + ".json");
        if (is == null) { throw new IOException(); }

        JSONTokener tokener = new JSONTokener(is);
        JSONObject object = new JSONObject(tokener);

        String goals = object.getJSONObject("goal-condition").toString();

        JSONArray entitiesJSON = object.getJSONArray("entities");
        ArrayList<String> entityList = new ArrayList<String>();
        for (int i = 0; i < entitiesJSON.length(); i++) {
            entityList.add(entitiesJSON.get(i).toString());
        }
        
        JSONMap map = new JSONMap(entityList, goals);
        return map;
    }
}
