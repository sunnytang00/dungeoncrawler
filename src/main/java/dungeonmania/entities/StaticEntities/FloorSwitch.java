package dungeonmania.entities.StaticEntities;

import org.json.JSONObject;

import dungeonmania.entities.StaticEntity;
import dungeonmania.util.Position;

public class FloorSwitch extends StaticEntity {

    private boolean isTriggered;

    public FloorSwitch(String type, Position position) {
        super(type, position);
        this.isTraversable = true;
        this.isTriggered = false;
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

    @Override
    public JSONObject toJSON() {
        JSONObject obj = super.toJSON();
        obj.put("is_triggered", isTriggered);
        return obj;
    }
    
}
