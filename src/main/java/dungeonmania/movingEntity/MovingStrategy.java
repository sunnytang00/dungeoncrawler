package dungeonmania.movingEntity;

import dungeonmania.DungeonMap;

public interface MovingStrategy {
    public void move(MovingEntity movingEntity, DungeonMap map);
}


