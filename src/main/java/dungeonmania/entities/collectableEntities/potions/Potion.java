package dungeonmania.entities.collectableEntities.potions;


import dungeonmania.DungeonGame;
import dungeonmania.entities.Item;
import dungeonmania.entities.movingEntity.player.Player;
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
    
}

