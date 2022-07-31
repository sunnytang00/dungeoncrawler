package dungeonmania.entities.StaticEntities;

import org.json.JSONObject;

import dungeonmania.entities.StaticEntity;
import dungeonmania.entities.StaticEntities.logicSwitches.LogicEnum;
import dungeonmania.util.Position;

public class FloorSwitch extends StaticEntity {

    private LogicEnum logic;
    private boolean isActivated;
    private int activationTick;

    public FloorSwitch(String type, Position position) {
        super(type, position);
        setTraversable(true);
        this.isActivated = false;
    }

    public FloorSwitch(String type, Position position, LogicEnum logic) {
        this(type, position);
        this.logic = logic;
    }

    public void switchState() {
        this.isActivated ^= true; 
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

    public int getActivationTick() {
        return activationTick;
    }

    public void setActivationTick(int activationTick) {
        this.activationTick = activationTick;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = super.toJSON();
        obj.put("is_activated", isActivated);
        return obj;
    }
    
}
