package dungeonmania.movingEntity;

import java.util.Arrays;

import dungeonmania.DungeonMap;
import dungeonmania.util.JSONConfig;
import dungeonmania.util.Position;

public class Mercenary extends Enemy {
    // private static final int DEFAULT_MERC_HEALTH = 0;//JSONConfig.mercenary_health
    // private static final int DEFAULT_MERC_ATTACK = 0;//JSONConfig.mercenary_attack
    // private static final int DEFAULT_BRIBE_RADIUS = 0;//JSONConfig.bribe_radius
    private static final int DEFAULT_MERC_HEALTH = JSONConfig.getConfig("mercenary_health");
    private static final int DEFAULT_MERC_ATTACK = JSONConfig.getConfig("mercenary_attack");
    private static final int DEFAULT_BRIBE_RADIUS = JSONConfig.getConfig("bribe_radius");

    private boolean isInRadius;


    public Mercenary(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        this.setMovingStrategy(new CirclingSpawn());
        this.setHealth(DEFAULT_MERC_HEALTH);
        this.setAttack(DEFAULT_MERC_ATTACK);
        this.setNonTraversibles(Arrays.asList("boulder", "wall", "door"));
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
