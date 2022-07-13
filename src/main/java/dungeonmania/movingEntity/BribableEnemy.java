package dungeonmania.movingEntity;

import dungeonmania.util.Position;

public abstract class BribableEnemy extends Enemy {

    private int bribeRadius;

    public BribableEnemy(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        this.bribeRadius = 0;
        
    }

    public int getBribeRadius() {
        return bribeRadius;
    }

    public void setBribeRadius(int bribeRadius) {
        this.bribeRadius = bribeRadius;
    }

    
    
}
