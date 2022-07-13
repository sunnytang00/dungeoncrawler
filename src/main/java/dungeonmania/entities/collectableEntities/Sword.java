package dungeonmania.entities.collectableEntities;

import dungeonmania.util.JSONConfig;
import dungeonmania.util.Position;

public class Sword extends Weapon {

    private static final int DEFAULT_SWORD_DURABILITY = JSONConfig.getConfig("sword_durability");
    private static final int DEFAULT_SWORD_ATTACK = JSONConfig.getConfig("sword_attack");


    public Sword(String type, Position position) {
        super(type, position);
        setDurability(DEFAULT_SWORD_DURABILITY);
    }

    @Override
    public int getDamageValue() {
        return DEFAULT_SWORD_ATTACK;
    }

}

