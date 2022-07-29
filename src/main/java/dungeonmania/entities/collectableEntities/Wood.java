package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Item;
import dungeonmania.util.Position;

public class Wood extends Item {
    public Wood(String type, Position position) {
        super(type, position);
        // the wood just could be used one time
        setDurability(1);
    }
}

