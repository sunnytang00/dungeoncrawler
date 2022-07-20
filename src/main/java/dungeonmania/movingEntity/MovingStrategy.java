package dungeonmania.movingEntity;

import dungeonmania.DungeonMap;

public interface MovingStrategy {
    public void move(Enemy movingEntity, DungeonMap map);
}


