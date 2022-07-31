package dungeonmania.entities.StaticEntities.logicSwitches;

import dungeonmania.DungeonGame;
import dungeonmania.DungeonMap;
import dungeonmania.entities.Entity;
import dungeonmania.entities.StaticEntities.FloorSwitch;
import dungeonmania.entities.collectableEntities.Bomb;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivateStrategy implements ActivateStrategy {

    protected int countAdjacentActivatedEntities(DungeonMap map, LogicItem logicItem) {
        int activatedCount = 0;

        List<Position> adjacentPositions = logicItem.getPosition().getCardinallyAdjacentPositions();
        for (Position position : adjacentPositions) {
            List<Entity> entities = map.getEntityFromPos(position);
            for (Entity entity : entities) {
                if ((entity instanceof Bomb && ((Bomb)entity).isActivated()) 
                || (entity instanceof LogicItem && ((LogicItem)entity).isActivated()) 
                || (entity instanceof FloorSwitch && ((FloorSwitch)entity).isActivated())) {
                    activatedCount++;
                }
            }
        }

        return activatedCount;
    }

    protected boolean checkActivatedOnSameTick(DungeonMap map, LogicItem logicItem) {
        boolean activateSame = false;
        List<Integer> ticks = new ArrayList<Integer>();
        
        List<Position> adjacentPositions = logicItem.getPosition().getCardinallyAdjacentPositions();
        for (Position position : adjacentPositions) {
            List<Entity> entities = map.getEntityFromPos(position);
            for (Entity entity : entities) {
                if (entity instanceof Bomb && ((Bomb)entity).isActivated()) {
                    int tick = ((LogicBomb)entity).getActivationTick();
                    ticks.add(tick);
                }
                if (entity instanceof LogicItem && ((LogicItem)entity).isActivated()) {
                    int tick = ((LogicItem)entity).getActivationTick();
                    ticks.add(tick);
                }
                if (entity instanceof FloorSwitch && ((FloorSwitch)entity).isActivated()) {
                    int tick = ((FloorSwitch)entity).getActivationTick();
                    ticks.add(tick);
                }
            }
        }

        int size = ticks.size();
        if(size < 2) { return activateSame; }

        if (ticks.stream().distinct().count() == size) { 
            activateSame = false;
        } else {
            activateSame = true;
        }

        return activateSame;
    }
}
