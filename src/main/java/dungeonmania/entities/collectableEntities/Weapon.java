package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Item;
import dungeonmania.util.Position;

/**
 * The weapon base class
 */
public abstract class Weapon extends Item {

    private int defence;
    private int damageValue;

    public Weapon(String type) {
        super(type);
    }

    public Weapon(String type, Position position) {
        super(type, position);
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getDamageValue() {
        return damageValue;
    }

    public void setDamageValue(int damageValue) {
        this.damageValue = damageValue;
    }

    public boolean isUsable() {
        if (getDurability() > 0) {
            return true;
        } 
        return false;  
    }

    public void useWeapon() {
        
        if (this.isUsable()) {
            int newDurability = this.getDurability() - 1;
            this.setDurability(newDurability);
        }
    }

}


