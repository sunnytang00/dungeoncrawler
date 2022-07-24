package dungeonmania.entities.collectableEntities;

import dungeonmania.DungeonMap;
import dungeonmania.entities.Item;
import dungeonmania.util.Position;

public class SunStone extends Item {

    /**
     * The door key id recording the door the key could open
     */
    private int doorKeyId;

    public SunStone(String type, Position position) {
        super(type, position);
    }

    public SunStone(String type, Position position, int doorKeyId) {
        super(type, position);
        this.doorKeyId = doorKeyId;
    }

    public int getDoorKeyId() {
        return doorKeyId;
    }

    public void setDoorKeyId(int doorKeyId) {
        this.doorKeyId = doorKeyId;
    }

    /**
     * Exchange a treasure with a sun stone
     * @param map
     */
    public void exchangeTreasure(DungeonMap map) {
        Position position = getPosition();
        Treasure treasure = new Treasure("treasure", position);
        map.removeEntityFromMap(this);
        map.addEntityToMap(treasure);
    }
}

