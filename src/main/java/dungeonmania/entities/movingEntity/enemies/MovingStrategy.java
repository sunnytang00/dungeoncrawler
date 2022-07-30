package dungeonmania.entities.movingEntity.enemies;

import dungeonmania.DungeonMap;

public interface MovingStrategy {
    public void move(Enemy movingEntity, DungeonMap map);
}


