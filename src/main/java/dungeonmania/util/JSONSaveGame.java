package dungeonmania.util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;

import dungeonmania.DungeonMap;
import dungeonmania.Entity;
import dungeonmania.entities.Item;
import dungeonmania.entities.collectableEntities.Potion;
import dungeonmania.goals.Goals;

public class JSONSaveGame {
    // save files to "./bin/saved_games/" + <fileName> + ".json" 
    // save files to "/dungeons/" + <fileName> + ".json" 

    public static JSONObject saveGame(DungeonMap map, JSONObject goalsJSON) {
        JSONArray entities = new JSONArray();
        JSONArray inventory = new JSONArray();
        JSONArray potions = new JSONArray();
        JSONArray timeTravel = new JSONArray();
        JSONArray battles = new JSONArray();
        JSONObject config = new JSONObject();
        // JSONObject goalsJSON = map.getJSONGoals();

        for (Entity e : map.getMapEntities()) {
            JSONObject obj = e.toJSON();
            entities.put(obj);
        }
        for (Item i : map.getPlayer().getInventory()) {
            JSONObject obj = i.toJSON("inventory");
            inventory.put(obj);
        }
        potions = map.getPlayer().potionQueueToJSON();
        config.put("file_name", JSONConfig.getConfigName());

        // time travel not done 
        // battle queue to be confirmed 

        // combining
        JSONObject gameJSON = new JSONObject();
        gameJSON.put("entities", entities);
        gameJSON.put("goal-condition", goalsJSON);
        gameJSON.put("inventory", inventory);
        gameJSON.put("potion-queue", potions);
        gameJSON.put("time-travel", timeTravel);
        gameJSON.put("battle-queue", battles);
        gameJSON.put("config-file", config);

        return gameJSON; 
    }
   
}
