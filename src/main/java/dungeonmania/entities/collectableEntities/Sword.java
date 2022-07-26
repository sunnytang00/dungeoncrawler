package dungeonmania.entities.collectableEntities;

import dungeonmania.util.JSONConfig;
import dungeonmania.util.Position;

public class Sword extends Weapon {

    public Sword(String type, Position position) {
        super(type, position);
        setDurability((int) JSONConfig.getConfig("sword_durability"));
        setDamageValue((int) JSONConfig.getConfig("sword_attack"));
        setDefence(0);

    }

}

