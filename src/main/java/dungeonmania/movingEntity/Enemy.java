package dungeonmania.movingEntity;

import dungeonmania.util.Position;

public abstract class Enemy extends MovingEntity implements MovingStrategy {

    private MovingStrategy movingStrategy;

    public Enemy(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        //TODO Auto-generated constructor stub
    }

    public MovingStrategy getMovingStrategy() {
        return movingStrategy;
    }
    public void setMovingStrategy(MovingStrategy movingStrategy) {
        this.movingStrategy = movingStrategy;
    }
    
}
