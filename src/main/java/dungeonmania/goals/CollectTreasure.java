package dungeonmania.goals;

import dungeonmania.DungeonMap;
import dungeonmania.Entity;
import dungeonmania.entities.collectableEntities.Treasure;
import dungeonmania.movingEntity.Player;
import dungeonmania.util.JSONConfig;

import java.util.List;

public class CollectTreasure extends LeafGoal {

    private boolean prevIsAchieved = false;

    public CollectTreasure(DungeonMap map) {
        map.setRemainingConditions(1);
    }

    @Override
    public boolean isAchieved(DungeonMap map) {
        // if (map.getPlayer() == null) { return false; }

        int numOfTreasure = map.getPlayer().getWealth();     
        if (numOfTreasure >= JSONConfig.getConfig("treasure_goal")) {
            prevIsAchieved = true;
            map.setRemainingConditions(-1);
            return true;
        }              
        if (prevIsAchieved) { // if previously it is true but now it is false again
            prevIsAchieved = false;
            map.setRemainingConditions(1);
        }
        return false;
    }

    @Override
    public String getGoalsAsString(DungeonMap map) {
        if (isAchieved(map)) { return ""; }
        return ":treasure";
    }
}
