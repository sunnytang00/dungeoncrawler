package dungeonmania.entities;

import dungeonmania.Entity;
import dungeonmania.util.Position;

public class Player extends Entity {
    public Player(String type, Position position) {
        super(type, position);
    }

    public Player(String type, Position position, String colour) {
        super(type, position, colour);
    }
}
