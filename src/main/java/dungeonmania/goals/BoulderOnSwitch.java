package dungeonmania.goals;

import dungeonmania.DungeonMap;
import dungeonmania.Entity;
import dungeonmania.StaticEntities.Boulder;
import dungeonmania.StaticEntities.FloorSwitch;
import dungeonmania.movingEntity.Player;

import java.util.List;

public class BoulderOnSwitch extends LeafGoal {

    public BoulderOnSwitch(DungeonMap map) {
        map.setRemainingConditions(1);
    }

    @Override
    public boolean isAchieved(DungeonMap map) {
        List<Entity> switches = map.getEntitiesFromType(map.getMapEntities(), "switch");
        if (switches.stream().allMatch(s -> ((FloorSwitch) s).isTriggered())) {
            map.setRemainingConditions(-1);
            return true;
        }
        if (isAchieved(map)) { // if previously it is true but now it is false again
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
