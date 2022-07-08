package dungeonmania.movingEntity;

import java.util.Arrays;

import dungeonmania.DungeonMap;
import dungeonmania.util.JSONConfig;
import dungeonmania.util.Position;

public class Spider extends Enemy {

    private static final int DEFAULT_SPIDER_HEALTH = JSONConfig.spider_health;
    private static final int DEFAULT_SPIDER_ATTACK = JSONConfig.spider_attack;
    private static final int DEFAULT_SPIDER_SPAWN_RATE = JSONConfig.spider_spawn_rate;


    private boolean isClockwiseMove;
    private int spawnRate;
    
    public Spider(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        setMovingStrategy(new CirclingSpawn());
        setHealth(DEFAULT_SPIDER_HEALTH);
        setAttack(DEFAULT_SPIDER_ATTACK);
        setNonTraversibles(Arrays.asList("boulder"));
        this.spawnRate = DEFAULT_SPIDER_SPAWN_RATE;
        this.isClockwiseMove = true; // initially true for spider
    }
    
    @Override
    public void move(MovingEntity movingEntity, DungeonMap map) {
        getMovingStrategy().move(this, map);  
    }

    public boolean isClockwiseMove() {
        return isClockwiseMove;
    }
    public void setClockwiseMove(boolean clockwise) {
        this.isClockwiseMove = clockwise;
    }

    public int getSpawnRate() {
        return spawnRate;
    }
    
}
