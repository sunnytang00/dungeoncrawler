package dungeonmania.goals;


import dungeonmania.DungeonMap;

import java.util.List;

public abstract class LeafGoal implements Goals {
    /**
     * Describe the goal name
     */
    protected String goal;

    protected DungeonMap map;

    public LeafGoal(DungeonMap map) {
        this.map = map;
    }

    public String getGoal() {
        return goal;
    }

    protected void setGoal(String goal) {
        this.goal = goal;
    }

    public List<Goals> getSubGoals() {
        return null;
    }
}

