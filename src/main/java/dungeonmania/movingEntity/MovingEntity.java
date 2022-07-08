package dungeonmania.movingEntity;

import dungeonmania.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public abstract class MovingEntity extends Entity  {

    private Position initialPosition;
    private Direction direction;
    //isClockwiseMove put into spider subclass later?
    private boolean isClockwiseMove;
    private int health;
    private int attack;

    public MovingEntity(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        this.initialPosition = position;
        this.isClockwiseMove = true; // initially true for spider
    }
    

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    public boolean isClockwiseMove() {
        return isClockwiseMove;
    }
    public void setClockwiseMove(boolean clockwise) {
        this.isClockwiseMove = clockwise;
    }

    public Position getInitialPosition() {
        return initialPosition;
    }
    
    public boolean blockedBy(Entity atAdj) {
        return false;
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
    
    
}
