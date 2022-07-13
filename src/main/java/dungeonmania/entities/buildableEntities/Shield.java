package dungeonmania.entities.buildableEntities;

import dungeonmania.entities.Item;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.movingEntity.Player;
import dungeonmania.util.JSONConfig;

import java.util.ArrayList;
import java.util.List;

public class Shield extends Weapon implements ItemBuildable {

    private static final int DEFAULT_SHILED_DURABILITY = JSONConfig.getConfig("shield_durability");
    private static final int DEFAULT_SHILED_DEFENCE = JSONConfig.getConfig("shield_defence");

    public Shield(String type) {
        super(type);
        setDurability(DEFAULT_SHILED_DURABILITY);
    }
    
    // @Override
    // public Shield build(List<Item> inventory) {
    //     /**
    //      * Record all the removing items
    //      */
    //     List<Item> removingPosition = new ArrayList<>();

    //     if (!inventory.isEmpty()) {
    //         int woodNumber = 0;
    //         int treasureOrKeyNumber = 0;
    //         for (Item item : inventory) {
    //             if (item instanceof Wood && woodNumber < 2) {
    //                 woodNumber++;
    //                 removingPosition.add(item);
    //             }
    //             if ((item instanceof Treasure || item instanceof Key) && treasureOrKeyNumber < 1) {
    //                 treasureOrKeyNumber++;
    //                 removingPosition.add(item);
    //             }
    //         }

    //         if (2 == woodNumber && 1 == treasureOrKeyNumber) {
    //             // remove all the items used to craft the buildable item
    //             // if it could be crafted by the items in the inventory
    //             removingPosition.forEach(i -> inventory.remove(i));
    //             return new Shield(BUILDABLE_TYPE_SHIELD);
    //         }
    //     }
    //     return null;
    // }


    @Override
    public int getDefence() {
        return DEFAULT_SHILED_DEFENCE;
    }

    @Override
    public void build(List<Item> inventory, Player player) {
        // TODO Auto-generated method stub
        
    }

}

