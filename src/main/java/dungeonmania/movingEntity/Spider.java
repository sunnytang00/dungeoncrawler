package dungeonmania.movingEntity;

import java.util.Arrays;

import dungeonmania.DungeonMap;
import dungeonmania.util.JSONConfig;
import dungeonmania.util.Position;

public class Spider extends Enemy {

    // private static final int DEFAULT_SPIDER_HEALTH = 0; //JSONConfig.spider_health
    // private static final int DEFAULT_SPIDER_ATTACK = 0; //JSONConfig.spider_attack
    // private static final int DEFAULT_SPIDER_SPAWN_RATE = 0; //JSONConfig.spider_spawn_rate

    private static final int DEFAULT_SPIDER_HEALTH = JSONConfig.getConfig("spider_health");
    private static final int DEFAULT_SPIDER_ATTACK = JSONConfig.getConfig("spider_attack");
    private static final int DEFAULT_SPIDER_SPAWN_RATE = JSONConfig.getConfig("spider_spawn_rate");

    private boolean isClockwiseMove;
    private int spawnRate;
    
    public Spider(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        this.setMovingStrategy(new CirclingSpawn());
        this.setHealth(DEFAULT_SPIDER_HEALTH);
        this.setAttack(DEFAULT_SPIDER_ATTACK);
        this.setNonTraversibles(Arrays.asList("boulder"));
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
