package dungeonmania.goals;

import dungeonmania.DungeonMap;
import dungeonmania.entities.Entity;
import dungeonmania.entities.StaticEntities.FloorSwitch;

import java.util.List;

import org.json.JSONObject;

public class BoulderOnSwitch extends LeafGoal {

    private boolean prevIsAchieved = false;

    public BoulderOnSwitch(DungeonMap map) {
        map.setRemainingConditions(1);
    }

    @Override
    public boolean isAchieved(DungeonMap map) {
        List<Entity> switches = map.getEntitiesFromType(map.getMapEntities(), "switch");
        if (switches.stream().allMatch(s -> ((FloorSwitch) s).isActivated())) {
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
        return ":boulders ";
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("goal", "boulders");
        return obj;
    }
    
}
