package dungeonmania.entities.collectableEntities;

import dungeonmania.util.Position;
import dungeonmania.util.json.JSONConfig;

public class Sword extends Weapon {

    public Sword(String type, Position position) {
        super(type, position);
        setDurability(JSONConfig.getConfig("sword_durability"));
        setDamageValue(JSONConfig.getConfig("sword_attack"));
        setDefence(0);

    }

}

