package dungeonmania.goals;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.DungeonMap;

public class CompositeAnd extends CompositeGoal{

    public CompositeAnd() {
        super(null, null);
    }
    
    public CompositeAnd(Goals subgoal1, Goals subgoal2) {
        super(subgoal1, subgoal2);
    }

    @Override
    public boolean isAchieved(DungeonMap map) {
        boolean achieved1 = getSubgoal1().isAchieved(map);
        boolean achieved2 = getSubgoal2().isAchieved(map);
        return (achieved1 && achieved2);
    }

    @Override
    public String getGoalsAsString(DungeonMap map) {
        if (isAchieved(map)) {return ""; }

        String subgoal1 = getSubgoal1().getGoalsAsString(map);
        String subgoal2 = getSubgoal2().getGoalsAsString(map);
        String str = "(" + subgoal1 + " AND " + subgoal2 + ")";

        return str;
    }
    
    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("goal", "AND");

        JSONArray arr = new JSONArray();
        JSONObject subgoal1 = getSubgoal1().toJSON();
        JSONObject subgoal2 = getSubgoal2().toJSON();
        arr.put(subgoal1);
        arr.put(subgoal2);
        
        obj.put("subgoals", arr);
        return obj;
    }

}
