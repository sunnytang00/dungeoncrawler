package dungeonmania.entities.collectableEntities;

import dungeonmania.util.JSONConfig;
import dungeonmania.util.Position;

public class InvincibilityPotion extends Potion {

    public InvincibilityPotion(String type, Position position) {
        super(type, position);
        this.setPotionDuration(JSONConfig.getConfig("invincibility_potion_duration"));
    }
}

