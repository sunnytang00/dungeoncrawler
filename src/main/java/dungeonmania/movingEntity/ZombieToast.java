package dungeonmania.movingEntity;

import java.util.Arrays;

import dungeonmania.DungeonMap;
import dungeonmania.util.JSONConfig;
import dungeonmania.util.Position;

public class ZombieToast extends Enemy {

    private static final int DEFAULT_ZOMBIE_HEALTH = 0; //JSONConfig.zombie_health
    private static final int DEFAULT_ZOMBIE_ATTACK = 0; //JSONConfig.zombie_attack

    private int spawnRate;

    public ZombieToast(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        this.setHealth(DEFAULT_ZOMBIE_HEALTH);
        this.setAttack(DEFAULT_ZOMBIE_ATTACK);
        this.setMovingStrategy(new RandomSpawn());
        // assume enemy could not push boulder here, can pass through exit but no effect to goal, can pass through open door
        // assume player, thus zombie, are not blocked by zombie toast spawner
        this.setNonTraversibles(Arrays.asList("boulder", "wall", "door"));
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
