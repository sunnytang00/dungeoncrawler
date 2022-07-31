package dungeonmania.entities.StaticEntities.logicSwitches;

import dungeonmania.DungeonGame;

public class XORActivateStrategy extends BaseActivateStrategy {

    @Override
    public void activate(DungeonGame game, LogicItem logicItem) {
        int activatedCount = countAdjacentActivatedEntities(game.getMap(), logicItem);
        if (1 == activatedCount) {
            logicItem.setActivated(true);
            logicItem.setActivationTick(game.getCurrentTick());
        } else {
            logicItem.setActivated(false);
        }
    }
}
