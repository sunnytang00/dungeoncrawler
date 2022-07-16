package dungeonmania.goals;

import dungeonmania.DungeonMap;
import dungeonmania.util.JSONConfig;

public class CollectTreasure extends LeafGoal {

    private boolean prevIsAchieved = false;

    public CollectTreasure(DungeonMap map) {
        map.setRemainingConditions(1);
    }

    @Override
    public boolean isAchieved(DungeonMap map) {
        int numOfTreasure = map.getPlayer().getWealth();     
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
}
