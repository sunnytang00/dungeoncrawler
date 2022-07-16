package dungeonmania.goals;

import dungeonmania.DungeonMap;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class CompositeGoal implements Goals {

    private List<Goals> goals;
    private Goals subgoal1;
    private Goals subgoal2;
    // private JSONObject subgoal1;
    // private JSONObject subgoal2;

    public CompositeGoal(Goals subgoal1, Goals subgoal2) {
        this.subgoal1 = subgoal1;
        this.subgoal2 = subgoal2;
    }

    // public void composeGoal(Goals goal) {
    //     goals.add(goal);
    // }

    // public List<Goals> getGoals() {
    //     return goals;
    // }

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
