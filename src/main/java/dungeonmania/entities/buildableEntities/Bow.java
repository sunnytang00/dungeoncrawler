package dungeonmania.entities.buildableEntities;

import dungeonmania.entities.Item;
import dungeonmania.entities.collectableEntities.Arrows;
import dungeonmania.entities.collectableEntities.Weapon;
import dungeonmania.entities.collectableEntities.Wood;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;

public class Bow extends Weapon implements ItemBuildable {

    public Bow(String type, Position position) {
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
            int arrowsNumber = 0;
            for (Item item : inventory) {
                if (item instanceof Wood && woodNumber < 1) {
                    woodNumber++;
                    removingPosition.add(item);
                }
                if (item instanceof Arrows && arrowsNumber < 3) {
                    arrowsNumber++;
                    removingPosition.add(item);
                }
            }

            if (1 == woodNumber && 3 == arrowsNumber) {
                // remove all the items used to craft the buildable item
                // if it could be crafted by the items in the inventory
                removingPosition.forEach(i -> inventory.remove(i));
                buildableItem = new Bow(BUILDABLE_TYPE_BOW, new Position(0,0));
            }
        }

        return buildableItem;
    }
}

