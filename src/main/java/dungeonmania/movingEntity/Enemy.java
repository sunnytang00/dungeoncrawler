package dungeonmania.movingEntity;

import org.json.JSONObject;

import dungeonmania.DungeonMap;
import dungeonmania.StaticEntities.SwampTile;
import dungeonmania.util.Position;

public abstract class Enemy extends MovingEntity {

    private MovingStrategy movingStrategy;
    private int remainingStuckTicks;

    public Enemy(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        remainingStuckTicks = 0;
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

    public void stuckOnSwamp(DungeonMap map) {
        if (!map.checkTypeEntityAtPos("swamp_tile", getPosition())) {
            return;
        }

        SwampTile swamp = (SwampTile) map.getSwampAtPos(getPosition());
        int stuckTicks = swamp.getMovementFactor();
        if (remainingStuckTicks == 0) {
            setRemainingStuckTicks(stuckTicks);
        } else if (remainingStuckTicks > 0){
            updateRemainingStuckTicks();
        }
    }

    public int getRemainingStuckTicks() {
        return remainingStuckTicks;
    }

    public void setRemainingStuckTicks(int remainingStuckTicks) {
        this.remainingStuckTicks = remainingStuckTicks;
    }

    public void updateRemainingStuckTicks() {
        remainingStuckTicks -= 1;
    }

    
}
