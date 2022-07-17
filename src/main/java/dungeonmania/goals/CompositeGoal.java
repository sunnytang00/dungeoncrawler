package dungeonmania.goals;

import dungeonmania.DungeonMap;

public abstract class CompositeGoal implements Goals {

    private Goals subgoal1;
    private Goals subgoal2;

    public CompositeGoal(Goals subgoal1, Goals subgoal2) {
        this.subgoal1 = subgoal1;
        this.subgoal2 = subgoal2;
    }

    public Goals getSubgoal1() {
        return subgoal1;
    }

    public Goals getSubgoal2() {
        return subgoal2;
    }

    @Override
    public abstract boolean isAchieved(DungeonMap map);

    @Override
    public abstract String getGoalsAsString(DungeonMap map);

}
