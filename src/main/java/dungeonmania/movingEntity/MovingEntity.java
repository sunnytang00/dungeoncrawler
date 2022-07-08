package dungeonmania.movingEntity;

import dungeonmania.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class MovingEntity extends Entity  {

    private Position initialPosition;
    private Direction direction;
    private int health;
    private int attack;
    private List<String> nontraversibles = new ArrayList<String>();

    public MovingEntity(String type, Position position, boolean isInteractable) {
        super(type, position);
        this.initialPosition = position;
    }
    

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Position getInitialPosition() {
        return initialPosition;
    }
    
    public boolean blockedBy(Entity atAdj) {
        String type = atAdj.getType();
        if (nontraversibles.contains(type)) {
            // if (type.equals("door")) {
            //     Door door = (Door) atAdj;
            //     if (door.isOpen()) {
            //         return true;
            //     }
            // }
            return false;
        }
        return true;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }


    public List<String> getNonTraversibles() {
        return nontraversibles;
    }

    public void setNonTraversibles(List<String> nontraversibles) {
        this.nontraversibles = nontraversibles;
    }
    
    
}
