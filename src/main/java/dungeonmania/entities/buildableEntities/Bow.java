package dungeonmania.entities.buildableEntities;

import dungeonmania.entities.Item;
import dungeonmania.entities.collectableEntities.Arrows;
import dungeonmania.entities.collectableEntities.Weapon;
import dungeonmania.entities.collectableEntities.Wood;
import dungeonmania.util.JSONConfig;
import dungeonmania.movingEntity.*;

import java.util.ArrayList;
import java.util.List;

public class Bow extends Weapon implements ItemBuildable {

    private static final int DEFAULT_BOW_DURABILITY = JSONConfig.getConfig("bow_durability");

    private int bow_durability;

    public Bow(String type) {
        super(type);
        this.bow_durability = DEFAULT_BOW_DURABILITY;
    }

    @Override
    public void build(List<Item> inventory, Player player) {
        //Item buildableItem = null;
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
                player.collectToInventory(new Bow(BUILDABLE_TYPE_BOW));
            }
        }
    }

    public int getBowDurability() {
        return bow_durability;
    }

    public void useBow() {
        bow_durability -= 1;
    }

    
    
}

