package dungeonmania.entities.logicSwitches;

import dungeonmania.DungeonMap;
import dungeonmania.Entity;
import dungeonmania.StaticEntities.Boulder;
import dungeonmania.util.Position;

import java.util.List;

public class LightBulb extends LogicItem {

    public LightBulb(String type, Position position, LogicEnum logicEnum) {
        super(type, position, logicEnum);
    }

    public void litUp(DungeonMap map) {
        Position currentPosition = getPosition();
        List<Position> cardinallyAdjacentPositions = currentPosition.getCardinallyAdjacentPositions();
        if (null != cardinallyAdjacentPositions) {
            for (Position position : cardinallyAdjacentPositions) {
                List<Entity> entityFromPos = map.getEntityFromPos(position);
                for (Entity entity : entityFromPos) {
                    if (entity instanceof Boulder) {
                        this.isActivated = true;
                        break;
                    }
                }
            }
        }
    }
}
