package dungeonmania.entities.StaticEntities.logicSwitches;

import dungeonmania.DungeonMap;
import dungeonmania.entities.Entity;
import dungeonmania.entities.StaticEntities.Boulder;
import dungeonmania.util.Helper;
import dungeonmania.util.Position;

import java.util.List;

import org.json.JSONObject;

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
                        setType("light_bulb_on");
                        break;
                    }
                }
            }
        }
    }
}
