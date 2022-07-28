package dungeonmania.goals;

import org.json.JSONObject;

import dungeonmania.DungeonMap;
import dungeonmania.util.JSONConfig;

public class DestroyEnemy extends LeafGoal {

    private boolean prevIsAchieved = false;

    public DestroyEnemy(DungeonMap map) {
        map.setRemainingConditions(1);
    }

    @Override
    public boolean isAchieved(DungeonMap map) {
        int numOfEnemies = map.getPlayer().getSlayedEnemy();  

        if (numOfEnemies >= JSONConfig.getConfig("enemy_goal")) {
            prevIsAchieved = true;
            map.setRemainingConditions(-1);
            return true;
        }              
        if (prevIsAchieved) {
            prevIsAchieved = false;
            map.setRemainingConditions(1);
        }
        return false;
    }

    @Override
    public String getGoalsAsString(DungeonMap map) {
        if (isAchieved(map)) { return ""; }
        return ":enemies ";
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("goal", "enemies");
        return obj;
    }
    
}
