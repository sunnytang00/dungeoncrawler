package dungeonmania.goals;


import dungeonmania.DungeonMap;

public abstract class LeafGoal implements Goals {

    @Override
    public abstract boolean isAchieved(DungeonMap map);
    
}
