package dungeonmania.entities.StaticEntities;

import java.util.List;

import org.json.JSONObject;

import dungeonmania.DungeonMap;
import dungeonmania.entities.Item;
import dungeonmania.entities.StaticEntity;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.entities.movingEntity.player.Player;
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
    
    @Override
    public boolean interact(DungeonMap map, Player player) {
        Key currKey = player.getCurrKey();
        List<Item> inventory = player.getInventory();
        int totalSunstone = (int) inventory.stream().filter(i -> i instanceof SunStone).count();

        // assume sunstone always used first when it comes to open doors
        if (totalSunstone > 0) {
            this.unlockDoorThroughSunstone();
        } else if (currKey != null && (currKey.getDoorKeyId() == keyID)) {
            this.unlockDoor(currKey);
            inventory.remove(currKey);
            player.setInventory(inventory);
        } 
        return false;
    }
}
