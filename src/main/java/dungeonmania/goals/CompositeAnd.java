package dungeonmania.goals;

import dungeonmania.DungeonMap;

public class CompositeAnd extends CompositeGoal{
    
    public CompositeAnd(Goals subgoal1, Goals subgoal2) {
        super(subgoal1, subgoal2);
    }

    @Override
    public boolean isAchieved(DungeonMap map) {
        // List<Goals> goals = getGoals();
        
        // return goals.stream().allMatch(goal -> goal.isAchieved(map));
        boolean achieved1 = getSubgoal1().isAchieved(map);
        boolean achieved2 = getSubgoal2().isAchieved(map);
        return (achieved1 && achieved2);
    }

    @Override
    public String getGoalsAsString(DungeonMap map) {
        // if (isAchieved(map)) { return ""; }

        // List<Goals> goals = getGoals();
        // String str = goals.stream()
        //                   .filter(goal -> !(goal.isAchieved(map))
        //                   .map(goal -> goal.getGoalsAsString(map)).toList());
        if (isAchieved(map)) {return ""; }

        String subgoal1 = getSubgoal1().getGoalsAsString(map);
        String subgoal2 = getSubgoal2().getGoalsAsString(map);
        String str = "(" + subgoal1 + "AND" + subgoal2 + ")";
        return str;
    }
}
