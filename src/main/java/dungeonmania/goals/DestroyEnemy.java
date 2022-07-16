package dungeonmania.goals;

import dungeonmania.DungeonMap;
import dungeonmania.Entity;
import dungeonmania.movingEntity.Enemy;
import dungeonmania.movingEntity.Player;
import dungeonmania.util.JSONConfig;

import java.util.List;

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
        if (prevIsAchieved) { // if previously it is true but now it is false again
            prevIsAchieved = false;
            map.setRemainingConditions(1);
        }
        return false;
    }

    @Override
    public String getGoalsAsString(DungeonMap map) {
        if (isAchieved(map)) { return ""; }
        return ":enemies";
    }
    
}
