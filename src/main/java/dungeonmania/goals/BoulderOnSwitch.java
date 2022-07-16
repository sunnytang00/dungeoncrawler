package dungeonmania.goals;

import dungeonmania.DungeonMap;
import dungeonmania.Entity;
import dungeonmania.StaticEntities.Boulder;
import dungeonmania.StaticEntities.FloorSwitch;
import dungeonmania.movingEntity.Player;

import java.util.List;

public class BoulderOnSwitch extends LeafGoal {

    private boolean prevIsAchieved = false;

    public BoulderOnSwitch(DungeonMap map) {
        map.setRemainingConditions(1);
    }

    @Override
    public boolean isAchieved(DungeonMap map) {
        List<Entity> switches = map.getEntitiesFromType(map.getMapEntities(), "switch");
        if (switches.stream().allMatch(s -> ((FloorSwitch) s).isTriggered())) {
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
        return ":boulders";
    }
    
}
