package dungeonmania.movingEntity;

import dungeonmania.util.Position;
import dungeonmania.util.json.JSONConfig;

public abstract class BribableEnemy extends Enemy {


    public BribableEnemy(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        // this.bribeRadius = DEFAULT_BRIBE_RADIUS;
        setMovingStrategy(new RandomSpawn());
        System.out.println(this.getMovingStrategy());
    }

    public int getBribeRadius() {
        // return bribeRadius;
        return JSONConfig.getConfig("bribe_radius");
    }

    // public void setBribeRadius(int bribeRadius) {
    //     this.bribeRadius = bribeRadius;
    // }

    
    
}
