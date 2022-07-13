package dungeonmania.StaticEntities;

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
        //if key id matches door id...
        System.out.println(key.getDoorKeyId());
        System.out.println(keyID);
        if (key.getDoorKeyId() == keyID) {
            setTraversable(true);
            setType("door_open");
        }
        
    }

    public int getKeyID() {
        return keyID;
    }

    
    
}
