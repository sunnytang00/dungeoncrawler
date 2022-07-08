package dungeonmania.movingEntity;

import dungeonmania.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public abstract class MovingEntity extends Entity implements MovingStrategy {

    private MovingStrategy movingStrategy;
    private Position initialPosition;
    private Direction direction;
    //isClockwiseMove put into spider subclass later?
    private boolean isClockwiseMove;

    public MovingEntity(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        this.initialPosition = position;
        this.isClockwiseMove = true; // initially true for spider
        this.movingStrategy = null;
    }
    
    public MovingStrategy getMovingStrategy() {
        return movingStrategy;
    }
    public void setMovingStrategy(MovingStrategy movingStrategy) {
        this.movingStrategy = movingStrategy;
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
   
    
}
