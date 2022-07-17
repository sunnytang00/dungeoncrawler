package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Item;
import dungeonmania.util.Position;

public class Treasure extends Item {

    /**
     * The wealth value of the treasure
     */
    // private int treasureValue;

    public Treasure(String type, Position position) {
        super(type, position);
    }

    // public int getTreasureValue() {
    //     return treasureValue;
    // }

    // public void setTreasureValue(int treasureValue) {
    //     this.treasureValue = treasureValue;
    // }
}

