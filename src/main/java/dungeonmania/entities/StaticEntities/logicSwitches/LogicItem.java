package dungeonmania.entities.StaticEntities.logicSwitches;

import org.json.JSONObject;

import dungeonmania.DungeonMap;
import dungeonmania.entities.Item;
import dungeonmania.entities.StaticEntity;
import dungeonmania.util.Helper;
import dungeonmania.util.Position;

public abstract class LogicItem extends StaticEntity {

    /**
     * Each Entity in the logicSwitches package have
     * a field called logic
     */
    protected LogicEnum logic;

    /**
     * Indicating whether the logic item is activated
     */
    protected boolean isActivated;

    protected ActivateStrategy activateStrategy;

    public LogicItem(String type, Position position, LogicEnum logic) {
        super(type, position);
        this.logic = logic;
        this.activateStrategy = ActivateStrategyFactory.getActivateStrategyByLogic(logic);
    }

    public LogicEnum getLogic() {
        return logic;
    }

    public void setLogic(LogicEnum logic) {
        this.logic = logic;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public void updateStatus(DungeonMap map) {
        getActivateStrategy().activate(map, this);
    }

    public ActivateStrategy getActivateStrategy() {
        return activateStrategy;
    }

    public JSONObject toJSON() {
        JSONObject obj = super.toJSON();
        String logic = Helper.logicToJSON(getLogic());
        obj.put("logic", logic);
        return obj;
    }

}
