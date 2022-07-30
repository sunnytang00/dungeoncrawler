package dungeonmania.entities.collectableEntities.potions;

import dungeonmania.util.JSONConfig;
import dungeonmania.util.Position;

public class InvisibilityPotion extends Potion {

    public InvisibilityPotion(String type, Position position) {
        super(type, position);
        setDurability((int) JSONConfig.getConfig("invisibility_potion_duration"));
    }

}

