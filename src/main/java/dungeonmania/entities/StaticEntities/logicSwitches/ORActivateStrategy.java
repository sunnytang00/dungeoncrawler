package dungeonmania.entities.StaticEntities.logicSwitches;

import dungeonmania.DungeonMap;

public class ORActivateStrategy extends BaseActivateStrategy {
    @Override
    public void activate(DungeonMap map, LogicItem logicItem) {
        super.activate(map, logicItem);
        int activatedCount = countAdjacentActivatedEntities(map, logicItem);
        if (activatedCount >= 1) {
            logicItem.setActivated(true);
        }
    }
}