package dungeonmania.entities.buildableEntities;

import dungeonmania.entities.Item;

import java.util.List;

public interface ItemBuildable {

    /**
     * The bow type name
     */
    String BUILDABLE_TYPE_BOW = "BOW";
    /**
     * The shield type name
     */
    String BUILDABLE_TYPE_SHIELD = "SHIELD";

    /**
     * Build the complex items by the passing simple items
     * @param items the items used to craft the complex item
     * @return return a complex item if it craft successfully,
     * otherwise null
     */
    Item build(List<Item> items);
}

