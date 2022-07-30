package dungeonmania.entities.movingEntity.enemies;

import dungeonmania.util.Position;
import dungeonmania.DungeonMap;
import dungeonmania.util.JSONConfig;

public class Assassin extends Mercenary {


    public Assassin(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        setBribeAmount((int) JSONConfig.getConfig("assassin_bribe_amount"));
    }

    @Override
    public void move(Enemy movingEntity, DungeonMap map) {
        if (!isBribed()) {
            if (map.getPlayer().isInvincible()) {
                setMovingStrategy(new RunAway());
            }  else if (map.getPlayer().isInvisible()) {
                moveWhenInvisible(map);
            } else {
                setMovingStrategy(new MoveTowardsPlayer());
            }   
        } else {
            if (map.getPlayer().isInvisible()) {
                moveWhenInvisible(map);
            } else {
                setMovingStrategy(new FollowPlayer());
            }   
        }

        this.stuckOnSwamp(map);
        if (getRemainingStuckTicks() == 0) {
            getMovingStrategy().move(this, map);  
        } 
    }

    public void moveWhenInvisible(DungeonMap map) {

        int reconRadius = (int) JSONConfig.getConfig("assassin_recon_radius");

        if (this.isInRad(map, reconRadius))  {
            if (!isBribed()) {
                setMovingStrategy(new MoveTowardsPlayer());
            } else {
                setMovingStrategy(new FollowPlayer());
            }
        } else {
            setMovingStrategy(new RandomSpawn());
        }
    }

    @Override
    public void bribe() {
        if (Math.random() > JSONConfig.getConfig("assassin_bribe_fail_rate")) {
            this.setState(new MercBribedState());
            getState().currentState(this);
            setInteractable(false);
        }
    }

    
}
