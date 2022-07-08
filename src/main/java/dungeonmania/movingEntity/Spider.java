package dungeonmania.movingEntity;

import dungeonmania.DungeonMap;
import dungeonmania.util.Position;

public class Spider extends Enemy {

    // private static final int DEFAULT_SPIDER_HEALTH = JSONConfig.getSpider_health();

    public Spider(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        setMovingStrategy(new CirclingSpawn());
    }
    

    @Override
    public void move(MovingEntity movingEntity, DungeonMap map) {
        getMovingStrategy().move(this, map);
        
    }
}
