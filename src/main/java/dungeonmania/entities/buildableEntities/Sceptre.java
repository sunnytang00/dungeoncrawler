package dungeonmania.entities.buildableEntities;

import dungeonmania.DungeonMap;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.entities.movingEntity.player.Player;
import dungeonmania.entities.Item;
import dungeonmania.exceptions.InvalidActionException;

import java.util.ArrayList;
import java.util.List;

public class Sceptre extends Item implements ItemBuildable {

    public Sceptre(String type) {
        super(type);
    }

    public boolean build(List<Item> inventory, Player player, DungeonMap map) throws InvalidActionException {
        // Record all the removing items
        List<Item> removingPosition = new ArrayList<>();

        if (!inventory.isEmpty()) {
            int woodNum = 0;
            int arrowNum = 0;
            int treasureOrKeyNum = 0;
            int sunStone = 0;

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

            if ((sunStone == 1) && (treasureOrKeyNum == 1) && ((arrowNum == 2) || (woodNum == 1))) {
                removingPosition.forEach(i -> inventory.remove(i));
                player.addToInventory(new Sceptre(BUILDABLE_TYPE_SCEPTRE));
                return true;
            } else {
                throw new InvalidActionException("Player does not have sufficient items to build sceptre");
            }
        } else {
            throw new InvalidActionException("Player does not have sufficient items to build sceptre");
        }
    }

}
