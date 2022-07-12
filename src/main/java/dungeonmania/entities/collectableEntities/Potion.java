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

    public int getTicks() {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }
}

