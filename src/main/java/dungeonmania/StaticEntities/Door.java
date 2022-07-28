package dungeonmania.StaticEntities;

import org.json.JSONObject;

import dungeonmania.StaticEntity;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.util.Position;

public class Door extends StaticEntity {

    //By default StaticEntity isTraversable = false, so that means all doors cannot be traversed by default
    
    private int keyID;

    public Door(String type, Position position, int keyID) {
        super(type, position);
        this.keyID = keyID;
    }

    public void unlockDoor(Key key) {  
        setTraversable(true);
        setType("door_open");
    }

    public int getKeyID() {
        return keyID;
    }

    public void unlockDoorThroughSunstone() {
        setTraversable(true);
        setType("door_open");
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = super.toJSON();
        obj.put("key", getKeyID());
        return obj;
    }
    
}
