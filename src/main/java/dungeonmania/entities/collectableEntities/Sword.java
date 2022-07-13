package dungeonmania.entities.collectableEntities;

import dungeonmania.util.JSONConfig;
import dungeonmania.util.Position;

public class Sword extends Weapon {

    private static final int DEFAULT_SWORD_DURABILITY = JSONConfig.getConfig("sword_durability");
    private static final int DEFAULT_SWORD_ATTACK = JSONConfig.getConfig("sword_attack");

    private int sword_durability;

    public Sword(String type, Position position) {
        super(type, position);
        sword_durability = DEFAULT_SWORD_DURABILITY;
    }

    public int getSwordDurability() {
        return sword_durability;
    }

    public void useSword() {
        sword_durability -= 1;
    }

    public int getSwordAttack() {
        return DEFAULT_SWORD_ATTACK;
    }

}

