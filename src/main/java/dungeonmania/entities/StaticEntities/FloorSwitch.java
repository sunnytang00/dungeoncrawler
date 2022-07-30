package dungeonmania.entities.StaticEntities;

import dungeonmania.entities.StaticEntity;
import dungeonmania.entities.StaticEntities.logicSwitches.LogicEnum;
import dungeonmania.util.Position;

public class FloorSwitch extends StaticEntity {

    private boolean isTriggered;
    private LogicEnum logic;
    private boolean isActivated;

    public FloorSwitch(String type, Position position) {
        super(type, position);
        this.isTraversable = true;
        this.isTriggered = false;
    }

    public FloorSwitch(String type, Position position, LogicEnum logic) {
        super(type, position);
        this.isTraversable = true;
        this.isTriggered = false;
        this.logic = logic;
    }

    public void switchState() {
        this.isTriggered ^= true; 
    }

    public boolean isTriggered() {
        return isTriggered;
    }
    public void setTriggered(boolean state) {
        this.isTriggered = state;
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
}