package dungeonmania.goals;

import dungeonmania.DungeonMap;
import static dungeonmania.util.GoalsConstants.*;

import java.util.List;

public class CompositeGoal extends LeafGoal implements Goals {

    private List<Goals> subGoals;

    public CompositeGoal(DungeonMap map) {
        super(map);
    }

    @Override
    public boolean isAchieve() {
        return getGoalsFromLogic(this);
    }

    private boolean getGoalsFromLogic(Goals goal) {
        String goalName = goal.getGoal();
        if (LOGIC_AND.equals(goalName)) {
            for (Goals g : goal.getSubGoals()) {
                if (!g.isAchieve()) {
                    return false;
                }
            }
            return true;
        } else if (LOGIC_OR.equals(goalName)) {
            for (Goals g : goal.getSubGoals()) {
                if (g.isAchieve()) {
                    return true;
                }
            }
            return false;
        } else {
            return getGoalsFromName(goalName).isAchieve();
        }
    }

    private Goals getGoalsFromName(String goal) {
        Goals goals = null;
        if (GOALS_EXIT.equals(goal)) {
            goals = new GetExit(map);
        } else if (GOALS_TREASURE.equals(goal)) {
            goals = new CollectTreasure(map);
        } else if (GOALS_BOULDERS.equals(goal)) {
            goals = new BoulderOnSwitch(map);
        } else if (GOALS_ENEMY.equals(goal)) {
            goals = new DestroyEnemy(map);
        }

        return goals;
    }

    @Override
    public List<Goals> getSubGoals() {
        return subGoals;
    }

    public void setSubGoals(List<Goals> subGoals) {
        this.subGoals = subGoals;
    }
}

