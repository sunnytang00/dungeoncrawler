package dungeonmania.goals;

import dungeonmania.DungeonMap;

import java.util.List;

public class CompositeGoal implements Goals {

    private DungeonMap map;
    private List<Goals> goals;

    public CompositeGoal(DungeonMap map) {
        this.map = map;
    }

    @Override
    public boolean isAchieve() {
        return false;
    }

    public DungeonMap getMap() {
        return map;
    }

    public void setMap(DungeonMap map) {
        this.map = map;
    }
}
