package dungeonmania.entities.collectableEntities;

import org.json.JSONObject;

import dungeonmania.entities.Item;
import dungeonmania.util.Position;

public class Key extends Item {
    /**
     * The door key id recording the door the key could open
     */
    private int doorKeyId;

    public Key(String type, Position position, int doorKeyId) {
        super(type, position);
        this.doorKeyId = doorKeyId;
    }

    public int getDoorKeyId() {
        return doorKeyId;
    }

    public void setDoorKeyId(int doorKeyId) {
        this.doorKeyId = doorKeyId;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = super.toJSON();
        obj.put("key", getDoorKeyId());
        return obj;
    }
}

