package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Item;
import dungeonmania.util.Position;

/**
 * The weapon base class
 */
public abstract class Weapon extends Item {


    private int durability;


    public Weapon(String type) {
        super(type);
    }

    public Weapon(String type, Position position) {
        super(type, position);
    }

    public int getDamageValue() {
        return 0;
    }

    public int getDefence(){
        return 0;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
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


