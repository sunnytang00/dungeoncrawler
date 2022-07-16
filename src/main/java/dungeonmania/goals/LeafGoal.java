package dungeonmania.goals;


import dungeonmania.DungeonMap;

public abstract class LeafGoal implements Goals {

    // private static int remainingConditions;

    @Override
    public abstract boolean isAchieved(DungeonMap map);

    // @Override
    // public abstract String getGoalsAsString(DungeonMap map);

    // public abstract int remainingConditions(DungeonMap map);

    // public void setRemainingConditions(int num) {
    //     remainingConditions += num;
    // }

    // public int getRemainingConditions() {
    //     return remainingConditions;
    // }
    
}
