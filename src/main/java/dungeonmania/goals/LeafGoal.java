package dungeonmania.goals;


import dungeonmania.movingEntity.Player;

public abstract class LeafGoal implements Goals {
    /**
     * Describe the goal name
     */
    protected String goal;

    protected Player player;

    public LeafGoal(Player player) {
        this.player = player;
    }

    protected String getGoal() {
        return goal;
    }

    protected void setGoal(String goal) {
        this.goal = goal;
    }

}

