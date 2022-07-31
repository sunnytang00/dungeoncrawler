package dungeonmania.goals;

import org.json.JSONObject;

import dungeonmania.DungeonMap;
import dungeonmania.entities.movingEntity.player.Player;
import dungeonmania.util.JSONConfig;

public class CollectTreasure extends LeafGoal {

    // private boolean prevIsAchieved = false;

    // public CollectTreasure(DungeonMap map) {
    //     map.setRemainingConditions(1);
    // }

    public CollectTreasure(DungeonMap map, boolean prevIsAchieved) {
        super(map, prevIsAchieved);
        map.setRemainingConditions(1);
    }

    public CollectTreasure() {
    }

    @Override
    public boolean isAchieved(DungeonMap map) {
        Player player = map.getPlayer();
        if (player == null) {return false; }
        int numOfTreasure = player.getWealth();
        if (numOfTreasure >= JSONConfig.getConfig("treasure_goal")) {
            setPrevIsAchieved(true);
            map.setRemainingConditions(-1);
            return true;
        }              
        if (getPrevIsAchieved()) {
            setPrevIsAchieved(false);
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
        obj.put("prev_is_achieved", getPrevIsAchieved());
        return obj;
    }

}
