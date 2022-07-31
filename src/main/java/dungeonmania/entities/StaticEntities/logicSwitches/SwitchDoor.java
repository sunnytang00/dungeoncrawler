package dungeonmania.entities.StaticEntities.logicSwitches;

import dungeonmania.DungeonMap;
import dungeonmania.entities.Item;
import dungeonmania.entities.collectableEntities.Key;
import dungeonmania.entities.collectableEntities.SunStone;
import dungeonmania.entities.movingEntity.player.Player;
import dungeonmania.util.Position;

import java.util.List;

public class SwitchDoor extends LogicItem {

    private int keyID;

    public SwitchDoor(String type, Position position, LogicEnum logic,  int keyID) {
        super(type, position, logic);
        this.keyID = keyID;
    }

    public int getKeyID() {
        return keyID;
    }

    public void unlockDoor(Key key) {  
        setTraversable(true);
        setType("door_open");
    }

    public void unlockDoorThroughSunstone() {
        setTraversable(true);
        setType("door_open");
    }

    @Override
    public void setActivated(boolean activated) {
        this.isActivated = activated;
        if (activated) {
            setType("door_open");
        } else {
            setType("switch_door");
        }
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
