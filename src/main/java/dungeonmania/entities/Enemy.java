package dungeonmania.entities;

import dungeonmania.Entity;
import dungeonmania.util.Position;

public class Enemy extends Entity {
    public Enemy(String type, Position position) {
        super(type, position);
    }

    public Enemy(String type, Position position, String colour) {
        super(type, position, colour);
    }
}

