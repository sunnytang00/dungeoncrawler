package dungeonmania.entities.buildableEntities;

import dungeonmania.entities.Item;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;

public class Shield extends Weapon implements ItemBuildable {

    private int defence;

    public Shield(String type, Position position) {
        super(type, position);
    }

    @Override
    public Item build(List<Item> inventory) {
        Item buildableItem = null;
        /**
         * Record all the removing items
         */
        List<Item> removingPosition = new ArrayList<>();

        if (!inventory.isEmpty()) {
            int woodNumber = 0;
            int treasureOrKeyNumber = 0;
            for (Item item : inventory) {
                if (item instanceof Wood && woodNumber < 2) {
                    woodNumber++;
                    removingPosition.add(item);
                }
                if ((item instanceof Treasure || item instanceof Key) && treasureOrKeyNumber < 1) {
                    treasureOrKeyNumber++;
                    removingPosition.add(item);
                }
            }

            if (2 == woodNumber && 1 == treasureOrKeyNumber) {
                // remove all the items used to craft the buildable item
                // if it could be crafted by the items in the inventory
                removingPosition.forEach(i -> inventory.remove(i));
                buildableItem = new Shield(BUILDABLE_TYPE_SHIELD, new Position(0,0));
            }
        }

        return buildableItem;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }
}

