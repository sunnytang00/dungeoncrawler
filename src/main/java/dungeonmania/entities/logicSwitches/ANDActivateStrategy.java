package dungeonmania.entities.logicSwitches;

import dungeonmania.DungeonMap;

public class ANDActivateStrategy extends BaseActivateStrategy {

    @Override
    public void activate(DungeonMap map, LogicItem logicItem) {
        super.activate(map, logicItem);
        int activatedCount = countAdjacentActivatedEntities(map, logicItem);
        if (activatedCount >= 2) {
            logicItem.setActivated(true);
        }
    }
}
