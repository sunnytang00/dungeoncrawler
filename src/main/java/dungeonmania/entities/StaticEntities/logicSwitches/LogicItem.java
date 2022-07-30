package dungeonmania.entities.StaticEntities.logicSwitches;

import dungeonmania.DungeonMap;
import dungeonmania.entities.Item;
import dungeonmania.entities.StaticEntity;
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

}
