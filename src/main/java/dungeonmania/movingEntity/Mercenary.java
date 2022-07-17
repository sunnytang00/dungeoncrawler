package dungeonmania.movingEntity;

import java.util.Arrays;
import java.util.List;

import dungeonmania.DungeonMap;
import dungeonmania.util.Position;

public class Mercenary extends BribableEnemy {
    

    private MercenaryState state = new MercViciousState();
    private boolean isBribed;


    public Mercenary(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        this.setBribed(false);
        setMovingStrategy(new MoveTowardsPlayer());
        setState(new MercViciousState());
        getState().currentState(this);
        this.setNonTraversibles(Arrays.asList("wall", "door"));
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
    public void move(Enemy movingEntity, DungeonMap map) {
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


    public boolean isInRad(DungeonMap map) {
        boolean inRadius = false;

        Player player = map.getPlayer();
        Position playerPos = player.getPosition();
        Position mercPos = this.getPosition();
        if (mercPos.getDistanceBetween(playerPos) <= this.getBribeRadius()) {
            inRadius = true;
        }

        return inRadius;
    }

    @Override
    public boolean becomeAlly() {
        if (isBribed) {
            return true;
        }
        return false;
    }

}
