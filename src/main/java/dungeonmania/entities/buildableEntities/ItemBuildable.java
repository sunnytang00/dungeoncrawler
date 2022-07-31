package dungeonmania.entities.buildableEntities;

import dungeonmania.DungeonMap;
import dungeonmania.entities.Item;
import dungeonmania.entities.movingEntity.player.Player;
import dungeonmania.exceptions.InvalidActionException;

import java.util.List;

public interface ItemBuildable {

    /**
     * The bow type name
     */
    String BUILDABLE_TYPE_BOW = "bow";
    /**
     * The shield type name
     */
    String BUILDABLE_TYPE_SHIELD = "shield";
    /**
     * Sceptre name
     */
    String BUILDABLE_TYPE_SCEPTRE = "sceptre";
    /**
     * Armour name
     */
    String BUILDABLE_TYPE_MIDNIGHT_ARMOUR = "midnight_armour";


    /**
     * Build the complex items by the passing simple items
     * @param inventory the items storing many items which could
     *                  be used to craft the buildable item
     * @return return a complex item if it craft successfully,
     * otherwise null
     */
    boolean build(List<Item> inventory, Player player, DungeonMap map) throws InvalidActionException;
}


