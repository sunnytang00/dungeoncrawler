package dungeonmania.entities.collectableEntities;

import java.util.List;

import org.json.JSONObject;

import dungeonmania.DungeonMap;
import dungeonmania.entities.Item;
import dungeonmania.entities.movingEntity.player.Player;
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

    @Override
    public boolean interact(DungeonMap map, Player player) {
        if (player.hasKey()) {
            return false;
        }
        player.setCurrKey(this);
        player.addToInventory(this);
        map.removeEntityFromMap(this);
        return false;
    }
}

