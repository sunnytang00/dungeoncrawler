package dungeonmania.entities.collectableEntities;

import dungeonmania.util.JSONConfig;
import dungeonmania.util.Position;

public class Sword extends Weapon {

    public Sword(String type, Position position) {
        super(type, position);
        setDurability(JSONConfig.getConfig("sword_durability"));
        setDamageValue(JSONConfig.getConfig("sword_attack"));
        setDefence(0);

    }

}

