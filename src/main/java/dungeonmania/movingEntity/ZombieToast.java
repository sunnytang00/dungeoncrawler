package dungeonmania.movingEntity;

import java.util.Arrays;

import dungeonmania.DungeonMap;
import dungeonmania.util.JSONConfig;
import dungeonmania.util.Position;

public class ZombieToast extends Enemy {

    private static final int DEFAULT_ZOMBIE_HEALTH = JSONConfig.zombie_health;
    private static final int DEFAULT_ZOMBIE_ATTACK = JSONConfig.zombie_attack;
    private static final int DEFAULT_ZOMBIE_SPAWN_RATE = JSONConfig.zombie_spawn_rate;

    private int spawnRate;

    public ZombieToast(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        setHealth(DEFAULT_ZOMBIE_HEALTH);
        setAttack(DEFAULT_ZOMBIE_ATTACK);
        setMovingStrategy(new RandomSpawn());
        // assume enemy could not push boulder here, can pass through exit but no effect to goal, can pass through open door
        // assume player, thus zombie, are not blocked by zombie toast spawner
        setNonTraversibles(Arrays.asList("boulder", "wall", "door"));
        this.spawnRate = DEFAULT_ZOMBIE_SPAWN_RATE;
    }

    @Override
    public void move(MovingEntity movingEntity, DungeonMap map) {
        if (map.getPlayer().isInvincible()) {
            setMovingStrategy(new RunAway());
        }  else {
            setMovingStrategy(new RandomSpawn());
        }   
        getMovingStrategy().move(this, map);  
    }

    public int getSpawnRate() {
        return spawnRate;
    }
    
}
