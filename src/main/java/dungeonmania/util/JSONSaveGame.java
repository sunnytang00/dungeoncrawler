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

    public static JSONObject saveGame(DungeonMap map, JSONObject goalsJSON, int currTick) {

        JSONArray timeTravel = new JSONArray();
        JSONObject config = new JSONObject();
        JSONObject tick = new JSONObject();
        JSONArray entities = map.mapEntitiesToJSON();
        JSONArray inventory = map.getPlayer().inventoryToJSON();
        JSONArray potions = map.getPlayer().potionQueueToJSON();
        JSONArray battles = new JSONArray();
        // JSONObject goalsJSON = map.getJSONGoals();

        config.put("file_name", JSONConfig.getConfigName());
        tick.put("current_tick", currTick);

        // time travel not done 
        // battle queue to be confirmed 

        // combining
        JSONObject gameJSON = new JSONObject();
        gameJSON.put("tick", tick);
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
