package dungeonmania.movingEntity;

import java.util.Arrays;

import dungeonmania.DungeonMap;
import dungeonmania.util.JSONConfig;
import dungeonmania.util.Position;

public class Mercenary extends BribableEnemy implements MercenaryState {

    private static final int DEFAULT_BRIBE_RADIUS = 0;//JSONConfig.bribe_radius

    private MercenaryState state;
    private boolean inRad;
    private boolean isBribed;


    public Mercenary(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        this.setBribeRadius(DEFAULT_BRIBE_RADIUS);
        this.setBribed(false);
        this.setState(new MercViciousState());
        this.setNonTraversibles(Arrays.asList("boulder", "wall", "door"));
    }
    

    public MercenaryState getState() {
        return state;
    }



    public void setState(MercenaryState state) {
        this.state = state;
    }

    

    public boolean isBribed() {
        return isBribed;
    }


    public void setBribed(boolean isBribed) {
        this.isBribed = isBribed;
    }


    @Override
    public void move(MovingEntity movingEntity, DungeonMap map) {
        if (!isBribed()) {
            if (map.getPlayer().isInvincible()) {
                setMovingStrategy(new RunAway());
            }  else if (map.getPlayer().isInvisible()) {
                setMovingStrategy(new RandomSpawn());
            } else {
                setMovingStrategy(new MoveTowardsPlayer());
            }   
        } else {
            if (map.getPlayer().isInvisible()) {
                setMovingStrategy(new RandomSpawn());
            } else {
                setMovingStrategy(new FollowPlayer());
            }   
        }
        getMovingStrategy().move(this, map);  
    }


    public void setInRadius(DungeonMap map) {

        boolean inRadius = false;

        Player player = map.getPlayer();
        Position playerPos = player.getPosition();
        Position mercPos = this.getPosition();
        if (mercPos.getDistanceBetween(playerPos) <= this.getBribeRadius()) {
            inRadius = true;
        }

        this.inRad = inRadius;
    }
    

    public boolean isInRad() {
        return inRad;
    }
    


    @Override
    public void currentState(Mercenary merc) {
        if (this.isInRad()) {
            setState(new MercBribedState());
        } else {
            setState(new MercViciousState());
        }
    }


}