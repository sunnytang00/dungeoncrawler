package dungeonmania.util;

import dungeonmania.DungeonMap;
import dungeonmania.goals.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONLoadGoals {

    public static Goals getComposedGoals(JSONObject goals, DungeonMap map) {
        switch(goals.getString("goal")) {
            case "AND":
                JSONArray subgoalsAnd = goals.getJSONArray("subgoals");
                CompositeGoal compositeAndGoal = new CompositeAnd(getComposedGoals(subgoalsAnd.getJSONObject(0), map),
                                                                  getComposedGoals(subgoalsAnd.getJSONObject(1), map));
                return compositeAndGoal;
            case "OR":
                JSONArray subgoalsOr = goals.getJSONArray("subgoals");
                CompositeGoal compositeOrGoal = new CompositeOr(getComposedGoals(subgoalsOr.getJSONObject(0), map),
                                                                 getComposedGoals(subgoalsOr.getJSONObject(1), map));
                return compositeOrGoal;
            case "exit":
                return new GetExit(map, false);
            case "enemies":
                return new DestroyEnemy(map, false);
            case "boulders":
                return new BoulderOnSwitch(map, false);
            case "treasure":
                return new CollectTreasure(map, false);
        }
        return null;
    }

}
