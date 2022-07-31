package dungeonmania.goals;

import org.json.JSONObject;

import dungeonmania.DungeonMap;

/**
 * The goal interface
 */
public interface Goals {
    /**
     * Judge whether the goal is complete
     * @return
     */
    public boolean isAchieved(DungeonMap map);

    public String getGoalsAsString(DungeonMap map);

    public JSONObject toJSON();

}
