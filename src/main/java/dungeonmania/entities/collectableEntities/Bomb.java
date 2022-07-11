package dungeonmania.entities.collectableEntities;

import dungeonmania.DungeonMap;
import dungeonmania.util.Position;

public class Bomb extends Weapon {
    public Bomb(String type, Position position) {
        super(type, position);
        // the bomb just could be used one time
        setUsedTimes(1);
    }
}

