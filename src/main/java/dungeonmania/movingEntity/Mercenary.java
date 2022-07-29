package dungeonmania.movingEntity;

import java.util.Arrays;

import org.json.JSONObject;

import dungeonmania.DungeonMap;
import dungeonmania.util.JSONConfig;
import dungeonmania.util.Position;

public class Mercenary extends BribableEnemy {
    

    private MercenaryState state = new MercViciousState();
    private boolean isBribed;
    private int mindControlTicks;
    private boolean bribedByTreasure;


    public Mercenary(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        this.setBribed(false);
        setMovingStrategy(new MoveTowardsPlayer());
        setState(new MercViciousState());
        getState().currentState(this);
        this.setBribeAmount((int) JSONConfig.getConfig("bribe_amount"));
        this.setNonTraversibles(Arrays.asList("wall", "door"));
        mindControlTicks = 0;
        bribedByTreasure = false;
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

    public boolean isBribedByTreasure() {
        return bribedByTreasure;
    }


    public void setBribedByTreasure(boolean bribedByTreasure) {
        this.bribedByTreasure = bribedByTreasure;
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
        this.stuckOnSwamp(map);
        if (getRemainingStuckTicks() == 0) {
            getMovingStrategy().move(this, map);  
        } 
    }


    @Override
    public boolean becomeAlly() {
        if (isBribed) {
            return true;
        }
        return false;
    }

    public void bribe() {
        this.setState(new MercBribedState());
        getState().currentState(this);
        setInteractable(false);
<<<<<<< HEAD
    }    

=======
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = super.toJSON();
        obj.put("state", getState());
        return obj;
    }
    
>>>>>>> 4dcaa0111579c7dc66f09dbe5454190cada123e8
    public int getMindControlTicks() {
        return mindControlTicks;
    }


    public void setMindControlTicks(int mindControlTicks) {
        this.mindControlTicks = mindControlTicks;
    }

    public void mindControl() {
        int mindControlDuration = (int) JSONConfig.getConfig("mind_control_duration");
        setMindControlTicks(mindControlDuration);
        bribe();
    }

    public void updateMindControl() {
        if (mindControlTicks > 0) {
            bribe();
            mindControlTicks -= 1;
        } else if (mindControlTicks == 0 && !isBribedByTreasure()) {
            this.setState(new MercViciousState());
            getState().currentState(this);
            setInteractable(true);
        }
    }

<<<<<<< HEAD
    @Override
    public JSONObject toJSON() {
        JSONObject obj = super.toJSON();
        obj.put("state", getState());
        return obj;
    }
    
=======

>>>>>>> 4dcaa0111579c7dc66f09dbe5454190cada123e8
}
