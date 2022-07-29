package dungeonmania.entities.buildableEntities;

import dungeonmania.DungeonMap;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.entities.movingEntity.player.Player;
import dungeonmania.entities.Item;
import dungeonmania.util.JSONConfig;
import dungeonmania.exceptions.InvalidActionException;

import java.util.ArrayList;
import java.util.List;

public class Bow extends Weapon implements ItemBuildable {


    public Bow(String type) {
        super(type);
        setDurability((int) JSONConfig.getConfig("bow_durability"));
        setDamageValue(0);
        setDefence(0);
    }

    public void build(List<Item> inventory, Player player, DungeonMap map) throws InvalidActionException {
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
                player.collectToInventory(new Bow(BUILDABLE_TYPE_BOW), map);
            } else {
                throw new InvalidActionException("Player does not have sufficient items to build bow");
            }
        } else {
            throw new InvalidActionException("Player does not have sufficient items to build bow");
        }
    }

}

