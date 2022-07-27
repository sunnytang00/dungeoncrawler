package dungeonmania.entities.buildableEntities;

import dungeonmania.DungeonMap;
import dungeonmania.entities.Item;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.movingEntity.MercBribedState;
import dungeonmania.movingEntity.Mercenary;
import dungeonmania.movingEntity.MovingEntity;
import dungeonmania.movingEntity.Player;
import dungeonmania.util.JSONConfig;

import java.util.ArrayList;
import java.util.List;

public class Sceptre extends Item implements ItemBuildable {

    private int mindControlDuration;

    public Sceptre(String type) {
        super(type);
        this.mindControlDuration = JSONConfig.getConfig("mind_control_duration");
    }
    
    public void build(List<Item> inventory, Player player, DungeonMap map) throws InvalidActionException {
        // Record all the removing items
        List<Item> removingPosition = new ArrayList<>();
    
        if (!inventory.isEmpty()) {
            int woodNum;
            int arrowNum;
            int treasureOrKeyNum;
            int sunStone;
            
            for (Item item : inventory) {
                if (item instanceof Wood && woodNum < 1) {
                    woodNum++;
                    removingPosition.add(item);
                } else if (item instanceof Arrows && arrowNum < 2) {
                    arrowNum++;
                    removingPosition.add(item);
                }

                if ((item instanceof Treasure || item instanceof Key) && treasureOrKeyNum < 1) {
                    treasureOrKeyNum++;
                    removingPosition.add(item);
                }

                if (item instanceof SunStone && sunStone < 1) {
                    sunStone++;
                    removingPosition.add(item);
                }
            }
    
            if ((sunStone == 1) && (treasureOrKeyNum == 1) && ((arrowNum == 2)||(woodNum == 1))) {
                removingPosition.forEach(i -> inventory.remove(i));
                player.collectToInventory(new Sceptre(BUILDABLE_TYPE_SCEPTRE), map);
            } else {
                throw new InvalidActionException("Player does not have sufficient items to build sceptre");
            }
        } else {
            throw new InvalidActionException("Player does not have sufficient items to build sceptre");
        }
    }

    public void mindControl(Mercenary merc) {
        merc.setState(new MercBribedState());
    }

    public void mindControl(Assassin ass) {
        ass.setState(new MercBribedState());
    }

    

}

