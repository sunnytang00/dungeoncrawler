package dungeonmania.movingEntity;

import dungeonmania.util.JSONConfig;
import dungeonmania.util.Position;

public abstract class BribableEnemy extends Enemy {


    public BribableEnemy(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        setMovingStrategy(new RandomSpawn());
    }

    public int getBribeRadius() {
        // return bribeRadius;
        return JSONConfig.getConfig("bribe_radius");
    }

    // public void setBribeRadius(int bribeRadius) {
    //     this.bribeRadius = bribeRadius;
    // }

    
    
}
