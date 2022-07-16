package dungeonmania.movingEntity;

import java.util.Arrays;

import dungeonmania.DungeonMap;
import dungeonmania.util.Position;
import dungeonmania.util.json.JSONConfig;

public class ZombieToast extends Enemy {


    public ZombieToast(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        this.setHealth(JSONConfig.getConfig("zombie_health"));
        this.setAttack(JSONConfig.getConfig("zombie_attack"));
        this.setMovingStrategy(new RandomSpawn());
        // assume enemy could not push boulder here, can pass through exit but no effect to goal, can pass through open door
        // assume player, thus zombie, are not blocked by zombie toast spawner
        this.setNonTraversibles(Arrays.asList("boulder", "wall", "door"));
    }

    public void move(MovingEntity movingEntity, DungeonMap map) {
        if (map.getPlayer().isInvincible()) {
            setMovingStrategy(new RunAway());
        }  else {
            setMovingStrategy(new RandomSpawn());
        }   
        getMovingStrategy().move(this, map);  
    }
    
}
