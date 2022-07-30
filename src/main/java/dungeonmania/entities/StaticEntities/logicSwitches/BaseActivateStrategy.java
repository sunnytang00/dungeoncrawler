package dungeonmania.entities.StaticEntities.logicSwitches;

import dungeonmania.DungeonMap;
import dungeonmania.entities.Entity;
import dungeonmania.entities.collectableEntities.Bomb;
import dungeonmania.util.Position;

import java.util.List;

public abstract class BaseActivateStrategy implements ActivateStrategy {
    @Override
    public void activate(DungeonMap map, LogicItem logicItem) {
        if (logicItem instanceof Wire) {
            Wire wire = (Wire)logicItem;
            List<Position> adjacentPositions = wire.getPosition().getCardinallyAdjacentPositions();
            if (hasActivatedEntities(map, adjacentPositions)) {
                for (Position position : adjacentPositions) {
                    List<Entity> entities = map.getEntityFromPos(position);
                    for (Entity entity : entities) {
                        if (entity instanceof Bomb) {
                            ((Bomb)entity).setActivated(true);
                        }
                        if (entity instanceof LogicItem) {
                            ((LogicItem)entity).setActivated(true);
                        }
                    }
                }
            }
        }
    }

    private boolean hasActivatedEntities(DungeonMap map, List<Position> positions) {
        if (null != positions) {
            for (Position position : positions) {
                List<Entity> entities = map.getEntityFromPos(position);
                for (Entity entity : entities) {
                    if (entity instanceof Bomb && ((Bomb)entity).isActivated()) {
                        return true;
                    }
                    if (entity instanceof LogicItem && ((LogicItem)entity).isActivated()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    protected int countAdjacentActivatedEntities(DungeonMap map, LogicItem logicItem) {
        int activatedCount = 0;

        List<Position> adjacentPositions = logicItem.getPosition().getCardinallyAdjacentPositions();
        for (Position position : adjacentPositions) {
            List<Entity> entities = map.getEntityFromPos(position);
            for (Entity entity : entities) {
                if (entity instanceof Bomb && ((Bomb)entity).isActivated()) {
                    activatedCount++;
                }
                if (entity instanceof LogicItem && ((LogicItem)entity).isActivated()) {
                    activatedCount++;
                }
            }
        }

        return activatedCount;
    }
}
