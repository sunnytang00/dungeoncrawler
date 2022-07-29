package dungeonmania.logicSwitches;

import dungeonmania.DungeonMap;

public class XORActivateStrategy extends BaseActivateStrategy {

    @Override
    public void activate(DungeonMap map, LogicItem logicItem) {
        super.activate(map, logicItem);
        int activatedCount = countAdjacentActivatedEntities(map, logicItem);
        if (1 == activatedCount) {
            logicItem.setActivated(true);
        }
    }
}
