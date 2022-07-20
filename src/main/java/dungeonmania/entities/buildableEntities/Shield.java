package dungeonmania.entities.buildableEntities;

import dungeonmania.DungeonMap;
import dungeonmania.entities.Item;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.movingEntity.Player;
import dungeonmania.util.JSONConfig;

import java.util.ArrayList;
import java.util.List;

public class Shield extends Weapon implements ItemBuildable {


    public Shield(String type) {
        super(type);
        setDurability(JSONConfig.getConfig("shield_durability"));
        setDamageValue(0);
        setDefence(JSONConfig.getConfig("shield_defence"));
    }
    
    public void build(List<Item> inventory, Player player, DungeonMap map) throws InvalidActionException {
        // Record all the removing items
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
                player.collectToInventory(new Shield(BUILDABLE_TYPE_SHIELD), map);
            } else {
                throw new InvalidActionException("Player does not have sufficient items to build shield");
            }
        } else {
            throw new InvalidActionException("Player does not have sufficient items to build shield");
        }
    }

}

