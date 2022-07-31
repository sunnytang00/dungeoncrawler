package dungeonmania.goals;

import org.json.JSONObject;

import dungeonmania.DungeonMap;
import dungeonmania.entities.movingEntity.player.Player;
import dungeonmania.util.JSONConfig;

public class CollectTreasure extends LeafGoal {

    private boolean prevIsAchieved = false;

    public CollectTreasure(DungeonMap map) {
        map.setRemainingConditions(1);
    }

    @Override
    public boolean isAchieved(DungeonMap map) {
        Player player = map.getPlayer();
        if (player == null) {return false; }
        int numOfTreasure = player.getWealth();
        // System.out.println(numOfTreasure);
        if (numOfTreasure >= JSONConfig.getConfig("treasure_goal")) {
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
        return ":treasure ";
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("goal", "treasure");
        return obj;
    }

}
