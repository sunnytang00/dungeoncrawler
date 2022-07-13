package dungeonmania.entities;

import dungeonmania.Entity;
import dungeonmania.util.Position;

public abstract class Item extends Entity {
    /**
     * Record the position of the item in the map
     */
    protected Position position;
    /**
     * Indicate whether the current potion is activated
     */
    protected boolean isTriggered;

    /**
     * Record the durability times of the sword
     */
    private int usedTimes;

    public Item(String type) {
        super(type);
    }

    public Item(String type, Position position) {
        super(type, position);
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }


    public int getUsedTimes() {
        return usedTimes;
    }

    public void setUsedTimes(int usedTimes) {
        this.usedTimes = usedTimes;
    }

    /**
     * Judge whether the weapon could still be used according
     * to the used times
     * @return
     */
    public boolean canBeStillUsed() {
        return usedTimes > 0;
    }
}

