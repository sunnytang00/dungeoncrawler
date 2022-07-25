package dungeonmania.movingEntity;

import dungeonmania.util.Position;
import dungeonmania.DungeonMap;
import dungeonmania.util.JSONConfig;
import java.util.Random;

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
        getMovingStrategy().move(this, map);  
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
        Random random = new Random();
        int num = random.nextInt(100);
        double threshold = 100 * JSONConfig.getConfig("assassin_bribe_fail_rate");
        if (num > threshold) {
            this.setState(new MercBribedState());
            getState().currentState(this);
            setInteractable(false);
        }
    }

    
}
