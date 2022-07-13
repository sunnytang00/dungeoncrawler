package dungeonmania.entities;

import dungeonmania.Entity;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {
    public Player(String type, Position position) {
        super(type, position);
    }

    public Player(String type, Position position, String colour) {
        super(type, position, colour);
    }

    public List<Entity> getEntities() {
        List<Entity> entities = new ArrayList<>();
        return entities;
    }
}

