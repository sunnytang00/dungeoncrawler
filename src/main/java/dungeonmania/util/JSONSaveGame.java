package dungeonmania.util;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.DungeonGame;
import dungeonmania.DungeonMap;
import dungeonmania.goals.Goals;

public class JSONSaveGame {
    // save files to "./bin/saved_games/" + <fileName> + ".json" 
    // save files to "/dungeons/" + <fileName> + ".json" 

    public static JSONObject saveGame(DungeonMap map, Goals goals, DungeonGame game) {
        JSONObject timeTravel = new JSONObject();
        JSONObject config = new JSONObject();
        JSONObject tick = new JSONObject();
        JSONArray entities = map.mapEntitiesToJSON();
        JSONArray inventory = map.getPlayer().inventoryToJSON();
        JSONArray potions = map.getPlayer().potionQueueToJSON();
        JSONArray battles = new JSONArray();
        JSONObject dungeonJSON = new JSONObject();
        JSONObject goalsJSON = goals.toJSON();

        config.put("file_name", JSONConfig.getConfigName());
        int currTick = game.getCurrentTick();
        System.out.println("currTick: " + currTick);
        tick.put("current_tick", currTick);

        // dungeon info 
        dungeonJSON.put("dungeon_id", game.getDungeonId());
        dungeonJSON.put("dungeon_name", map.getDungeonName());

        // time travel 
        List<JSONObject> histories = game.getTickHistory();
        JSONArray historyJSON = new JSONArray();
        for (JSONObject hist : histories) {
            historyJSON.put(hist);
        }
        int timeTravelTick = game.getTimeTravelTick();
        timeTravel.put("time_travel_tick", timeTravelTick);
        timeTravel.put("tick_histories", historyJSON);

        // battle queue to be confirmed 
        List<Battle> battleList = game.getBattles();
        for (Battle b : battleList) {
            battles.put(b.toJSON());
        }

        // combining
        JSONObject gameJSON = new JSONObject();
        gameJSON.put("tick", tick);
        gameJSON.put("entities", entities);
        gameJSON.put("goal-condition", goalsJSON);
        gameJSON.put("inventory", inventory);
        gameJSON.put("potion-queue", potions);
        gameJSON.put("time-travel", timeTravel);
        gameJSON.put("battle-queue", battles);
        gameJSON.put("dungeon", dungeonJSON);
        gameJSON.put("config-file", config);
        gameJSON.put("remaining-goal-conditions", map.getRemainingConditions());

        return gameJSON; 
    }
}
