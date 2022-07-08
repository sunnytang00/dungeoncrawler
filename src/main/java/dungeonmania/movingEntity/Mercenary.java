package dungeonmania.movingEntity;

import java.util.Arrays;

import dungeonmania.DungeonMap;
import dungeonmania.util.JSONConfig;
import dungeonmania.util.Position;

public class Mercenary extends Enemy {
    private static final int DEFAULT_MERC_HEALTH = JSONConfig.mercenary_health;
    private static final int DEFAULT_MERC_ATTACK = JSONConfig.mercenary_attack;
    private static final int DEFAULT_BRIBE_RADIUS = JSONConfig.bribe_radius;

    private boolean isInRadius;


    public Mercenary(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        setMovingStrategy(new CirclingSpawn());
        setHealth(DEFAULT_MERC_HEALTH);
        setAttack(DEFAULT_MERC_ATTACK);
        setNonTraversibles(Arrays.asList("boulder", "wall", "door"));
    }

    @Override
    public void move(MovingEntity movingEntity, DungeonMap map) {
        if (map.getPlayer().isInvincible()) {
            setMovingStrategy(new RunAway());
        }  else {
            setMovingStrategy(new MoveTowardsPlayer());
        }   
        getMovingStrategy().move(this, map);  
    }

    public boolean isInRadius() {
        return isInRadius;
    }

    public void calculateInRadius(DungeonMap map) {

        boolean inRadius = false;

        Player player = map.getPlayer();
        Position playerPos = player.getPosition();
        Position mercPos = this.getPosition();
        if (mercPos.getDistanceBetween(playerPos) <= DEFAULT_BRIBE_RADIUS) {
            inRadius = true;
        }

        this.isInRadius = inRadius;
    }
    

}
