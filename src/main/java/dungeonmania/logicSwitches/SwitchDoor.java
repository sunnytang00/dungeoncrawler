package dungeonmania.logicSwitches;

import dungeonmania.DungeonMap;
import dungeonmania.Entity;
import dungeonmania.util.Position;

import java.util.List;

public class SwitchDoor extends LogicItem {

    private int keyID;

    public SwitchDoor(String type, Position position, LogicEnum logic,  int keyID) {
        super(type, position, logic);
        this.keyID = keyID;
    }

    public int getKeyID() {
        return keyID;
    }

    @Override
    public void updateStatus(DungeonMap map) {
        List<Position> adjacentPositions = getPosition().getCardinallyAdjacentPositions();
        for (Position position : adjacentPositions) {
            List<Entity> entities = map.getEntityFromPos(position);
            for (Entity entity : entities) {
                if (entity instanceof Wire) {
                    Wire wire = (Wire)entity;
                    if (wire.isActivated()) {
                        this.isActivated = true;
                        break;
                    }
                }
                if (entity instanceof LightBulb) {
                    LightBulb lightBulb = (LightBulb)entity;
                    if (lightBulb.isActivated()) {
                        this.isActivated = true;
                        break;
                    }
                }
            }
        }
        getActivateStrategy().activate(map, this);
    }
}
