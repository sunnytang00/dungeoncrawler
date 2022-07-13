package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Item;
import dungeonmania.util.Position;

public abstract class Potion extends Item {
    /**
     * Record the duration ticks of the current potion
     */
    protected int ticks;

    public Potion(String type, Position position) {
        super(type, position);
    }
    
    // set initial potion duration from config file
    public void setPotionDuration(int duration) {
        this.ticks = duration;
    }

    // gets remaining duration of this potion
    public int getTicks() {
        return ticks;
    }

    // update potion duration of this potion
    public void updateTicks() {
        this.ticks -= 1;
    }
}

