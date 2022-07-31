package dungeonmania.goals;


import dungeonmania.DungeonMap;

public abstract class LeafGoal implements Goals {
    private boolean prevIsAchieved;

    public LeafGoal(DungeonMap map, boolean prevIsAchieved) {
        this.prevIsAchieved = prevIsAchieved;
    }

    @Override
    public abstract boolean isAchieved(DungeonMap map);
    
    public boolean getPrevIsAchieved() {
        return prevIsAchieved;
    }

    public void setPrevIsAchieved(boolean prevIsAchieved) {
        this.prevIsAchieved = prevIsAchieved;
    }
}
