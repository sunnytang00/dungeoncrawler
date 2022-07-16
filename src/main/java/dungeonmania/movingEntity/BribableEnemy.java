package dungeonmania.movingEntity;

import dungeonmania.util.JSONConfig;
import dungeonmania.util.Position;

public abstract class BribableEnemy extends Enemy {

    // private int bribeRadius;
    private static final int DEFAULT_BRIBE_RADIUS = JSONConfig.getConfig("bribe_radius");

    public BribableEnemy(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        // this.bribeRadius = DEFAULT_BRIBE_RADIUS;
        setMovingStrategy(new RandomSpawn());
    }

    public int getBribeRadius() {
        // return bribeRadius;
        return DEFAULT_BRIBE_RADIUS;
    }

    // public void setBribeRadius(int bribeRadius) {
    //     this.bribeRadius = bribeRadius;
    // }

    
    
}
