package dungeonmania.entities.collectableEntities;

import dungeonmania.util.JSONConfig;
import dungeonmania.util.Position;

public class InvincibilityPotion extends Potion {

    private static final int INVINCIBILITY_POTION_DURATION = JSONConfig.getConfig("invincibility_potion_duration");

    public InvincibilityPotion(String type, Position position) {
        super(type, position);
        this.setPotionDuration(INVINCIBILITY_POTION_DURATION);
    }
}

