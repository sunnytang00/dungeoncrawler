package dungeonmania.entities.buildableEntities;

import dungeonmania.DungeonMap;
import dungeonmania.entities.Item;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.movingEntity.Player;
import dungeonmania.util.JSONConfig;

import java.util.ArrayList;
import java.util.List;

public class MidnightArmour extends Weapon implements ItemBuildable {

    public MidnightArmour(String type) {
        super(type);
        setDurability(999999999);
        setDamageValue(JSONConfig.getConfig("midnight_armour_attack"));
        setDefence(JSONConfig.getConfig("midnight_armour_defence"));
    }
    
    public void build(List<Item> inventory, Player player, DungeonMap map) throws InvalidActionException {
        // Record all the removing items
        List<Item> removingPosition = new ArrayList<>();
    
        if (!inventory.isEmpty()) {
            int swordNum;
            int sunStone;
            
            for (Item item : inventory) {
                if (item instanceof Sword && swordNum < 1) {
                    swordNum++;
                    removingPosition.add(item);
                }

                if (item instanceof SunStone && sunStone < 1) {
                    sunStone++;
                    removingPosition.add(item);
                }
            }
    
            if ((sunStone == 1) && (swordNum == 1) && map.getEntitiesFromType(map.getMapEntities(), "zombie").isEmpty()) {
                removingPosition.forEach(i -> inventory.remove(i));
                player.collectToInventory(new MidnightArmour(BUILDABLE_TYPE_MIDNIGHT_ARMOUR), map);
            } else {
                throw new InvalidActionException("Player cannot build midnight armour");
            }
        } else {
            throw new InvalidActionException("Player cannot build midnight armour");
        }
    }

    @Override
    public boolean isUsable() {
        return true;
    }


}

