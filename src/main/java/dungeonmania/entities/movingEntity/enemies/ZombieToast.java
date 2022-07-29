package dungeonmania.entities.movingEntity.enemies;

import java.util.Arrays;
import java.util.List;

import dungeonmania.DungeonMap;
import dungeonmania.entities.Entity;
import dungeonmania.util.JSONConfig;
import dungeonmania.util.Position;

public class ZombieToast extends Enemy {

    public ZombieToast(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        this.setHealth(JSONConfig.getConfig("zombie_health"));
        this.setAttack(JSONConfig.getConfig("zombie_attack"));
        this.setMovingStrategy(new RandomSpawn());
        // assume enemy could not push boulder here but can go pass it, can pass through exit but no effect to goal, can pass through open door
        // assume player, thus zombie, are not blocked by zombie toast spawner
        this.setNonTraversibles(Arrays.asList("wall", "door"));
    }

    @Override
    public void move(Enemy movingEntity, DungeonMap map) {
        if (map.getPlayer().isInvincible()) {
            setMovingStrategy(new RunAway());
        }  else {
            setMovingStrategy(new RandomSpawn());
        }
        this.stuckOnSwamp(map);
        if (getRemainingStuckTicks() == 0) {
            getMovingStrategy().move(this, map);  
        }    
    }
    
}
