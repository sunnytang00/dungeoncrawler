package dungeonmania.goals;

import dungeonmania.DungeonMap;
import dungeonmania.Entity;
import dungeonmania.util.Position;

import java.util.List;

public class GetExit extends LeafGoal {

    public GetExit(DungeonMap map) {
        map.setRemainingConditions(1);
    }

    @Override
    public boolean isAchieved(DungeonMap map) {
        if (map.getRemainingConditions() > 1) { 
            return false; 
        }
        Position playerPosition = map.getPlayer().getPosition();

        List<Entity> hasExit = map.getEntitiesFromType(map.getMapEntities(), "exit");
        if (hasExit.size() == 0) { return false; }
        
        Position exitPosition = hasExit.get(0).getPosition();                           
        if (playerPosition.equals(exitPosition)) {
            map.setRemainingConditions(-1);
            return true;
        }
        return false;
    }

    @Override
    public String getGoalsAsString(DungeonMap map) {
        if (isAchieved(map)) { return ""; }
        return ":exit";
    }

}
