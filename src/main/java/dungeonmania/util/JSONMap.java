package dungeonmania.util;

import java.util.ArrayList;

public class JSONMap {
    private ArrayList<String> entityList;
    private String goals;
    private String subGoals = null;
    
    public JSONMap(ArrayList<String> entityList, String goals) {
        this.entityList = entityList;
        this.goals = goals;
    }

    public JSONMap(ArrayList<String> entityList, String goals, String subGoals) {
        this(entityList, goals);
        this.subGoals = subGoals;
    }

    public ArrayList<String> getEntityList() {
        return entityList;
    }

    public void setEntityList(ArrayList<String> entityList) {
        this.entityList = entityList;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public String getSubGoals() {
        return subGoals;
    }

    public void setSubGoals(String subGoals) {
        this.subGoals = subGoals;
    }
 
}
