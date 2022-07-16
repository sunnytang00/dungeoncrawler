package dungeonmania.entities.collectableEntities;

import dungeonmania.util.Position;
import dungeonmania.util.json.JSONConfig;

public class InvincibilityPotion extends Potion {

    private static final int INVINCIBILITY_POTION_DURATION = JSONConfig.getConfig("invincibility_potion_duration");

    public InvincibilityPotion(String type, Position position) {
        super(type, position);
        this.setPotionDuration(INVINCIBILITY_POTION_DURATION);
    }
}

