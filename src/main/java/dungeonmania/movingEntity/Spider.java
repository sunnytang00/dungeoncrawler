package dungeonmania.movingEntity;

import java.util.Arrays;

import dungeonmania.DungeonMap;
import dungeonmania.util.Position;
import dungeonmania.util.json.JSONConfig;

public class Spider extends Enemy {


    private boolean isClockwiseMove;
    private int spawnRate;
    
    public Spider(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        this.setMovingStrategy(new CirclingSpawn());
        this.setHealth(JSONConfig.getConfig("spider_health"));
        this.setAttack(JSONConfig.getConfig("spider_attack"));
        this.setNonTraversibles(Arrays.asList("boulder"));
        this.spawnRate = JSONConfig.getConfig("spider_spawn_rate");
        this.isClockwiseMove = true; // initially true for spider
    }
    
    
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
