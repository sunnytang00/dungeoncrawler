package dungeonmania.entities.collectableEntities.potions;

import dungeonmania.util.JSONConfig;
import dungeonmania.util.Position;

public class InvincibilityPotion extends Potion {

    public InvincibilityPotion(String type, Position position) {
        super(type, position);
        setDurability((int) JSONConfig.getConfig("invincibility_potion_duration"));
    }

}

