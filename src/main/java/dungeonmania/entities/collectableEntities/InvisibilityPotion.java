package dungeonmania.entities.collectableEntities;

import dungeonmania.util.Position;
import dungeonmania.util.json.JSONConfig;

public class InvisibilityPotion extends Potion {

    private static final int INVISIBILITY_POTION_DURATION = JSONConfig.getConfig("invisibility_potion_duration");

    public InvisibilityPotion(String type, Position position) {
        super(type, position);
        this.setPotionDuration(INVISIBILITY_POTION_DURATION);
    }
}

