package dungeonmania.movingEntity;

import java.util.Arrays;
import java.util.List;

import dungeonmania.DungeonMap;
import dungeonmania.Entity;
import dungeonmania.util.JSONConfig;
import dungeonmania.util.Position;

public class ZombieToast extends Enemy {

    private static final double DEFAULT_ZOMBIE_HEALTH = JSONConfig.getConfig("zombie_health");
    private static final double DEFAULT_ZOMBIE_ATTACK = JSONConfig.getConfig("zombie_attack");

    public ZombieToast(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        this.setHealth(DEFAULT_ZOMBIE_HEALTH);
        this.setAttack(DEFAULT_ZOMBIE_ATTACK);
        this.setMovingStrategy(new RandomSpawn());
        // assume enemy could not push boulder here but can go pass it, can pass through exit but no effect to goal, can pass through open door
        // assume player, thus zombie, are not blocked by zombie toast spawner
        this.setNonTraversibles(Arrays.asList("wall", "door"));
    }

    @Override
    public void move(Enemy movingEntity, DungeonMap map) {
        //System.out.println("Invincible");
        if (map.getPlayer().isInvincible()) {
            setMovingStrategy(new RunAway());
        }  else {
            setMovingStrategy(new RandomSpawn());
        }   
        getMovingStrategy().move(this, map);  
    }
    
}
