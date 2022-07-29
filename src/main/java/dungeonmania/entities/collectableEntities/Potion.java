package dungeonmania.entities.collectableEntities;

import org.json.JSONObject;

import dungeonmania.DungeonGame;
import dungeonmania.DungeonMap;
import dungeonmania.entities.Item;
import dungeonmania.movingEntity.Player;
import dungeonmania.util.Position;

public abstract class Potion extends Item {
    /**
     * Record the duration ticks of the current potion
     */
    // protected int durability;

    public Potion(String type, Position position) {
        super(type, position);
    }
    
    @Override
    public void tick(DungeonGame game) {
        Player player = game.getPlayer();
        player.consumePotion(this);
    }
    
    // // set initial potion duration from config file
    // public void setDurability(int duration) {
    //     this.d = duration;
    // }

    // // gets remaining duration of this potion
    // public int getTicks() {
    //     return ticks;
    // }

    // // update potion duration of this potion
    // public void updateTicks() {
    //     this.ticks -= 1;
    // }

    // @Override
    // public JSONObject toJSON(String mode) {
    //     JSONObject obj = super.toJSON(mode);
    // }
}

