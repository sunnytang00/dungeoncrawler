package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Item;
import dungeonmania.util.Position;

public class Arrows extends Item {
    public Arrows(String type, Position position) {
        super(type, position);
        // the arrow just could be used one time
        setUsedTimes(1);
    }
}

