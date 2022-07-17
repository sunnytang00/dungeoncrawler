package dungeonmania.movingEntity;

import dungeonmania.DungeonMap;
import dungeonmania.util.Position;

public abstract class Enemy extends MovingEntity {

    private MovingStrategy movingStrategy;

    public Enemy(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
    }

    public MovingStrategy getMovingStrategy() {
        return movingStrategy;
    }

    public void setMovingStrategy(MovingStrategy movingStrategy) {
        this.movingStrategy = movingStrategy;
    }

    public boolean becomeAlly() {
        return false;
    }

    public void move(Enemy enemy, DungeonMap map) {
    }

    
    
}
