package dungeonmania.goals;

import java.util.List;

/**
 * The goal interface
 */
public interface Goals {
    /**
     * Judge whether the goal is complete
     * @return
     */
    boolean isAchieve();

    /**
     * return the goal name
     * @return
     */
    String getGoal();

    List<Goals> getSubGoals();
}

