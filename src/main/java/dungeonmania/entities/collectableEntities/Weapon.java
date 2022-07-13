package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Item;
import dungeonmania.util.Position;

/**
 * The weapon base class
 */
public abstract class Weapon extends Item {

    /**
     * The damage value the weapon is used
     */
    protected int damageValue;

    public Weapon(String type) {
        super(type);
    }

    public Weapon(String type, Position position) {
        super(type, position);
    }

    public int getDamageValue() {
        return damageValue;
    }

    public void setDamageValue(int damageValue) {
        this.damageValue = damageValue;
    }
}


