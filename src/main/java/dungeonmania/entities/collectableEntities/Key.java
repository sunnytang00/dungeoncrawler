package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Item;
import dungeonmania.util.Position;

public class Key extends Item {
    /**
     * The door key id recording the door the key could open
     */
    private String doorKeyId;

    public Key(String type, Position position) {
        super(type, position);
        // the key just could be used one time
        setUsedTimes(1);
    }

    public String getDoorKeyId() {
        return doorKeyId;
    }

    public void setDoorKeyId(String doorKeyId) {
        this.doorKeyId = doorKeyId;
    }
}

