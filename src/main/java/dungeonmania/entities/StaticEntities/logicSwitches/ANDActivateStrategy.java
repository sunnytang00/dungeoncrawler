package dungeonmania.entities.StaticEntities.logicSwitches;

import dungeonmania.DungeonGame;

public class ANDActivateStrategy extends BaseActivateStrategy {

    @Override
    public void activate(DungeonGame game, LogicItem logicItem) {
        int activatedCount = countAdjacentActivatedEntities(game.getMap(), logicItem);
        if (activatedCount >= 2) {
            logicItem.setActivated(true);
            logicItem.setActivationTick(game.getCurrentTick());
        } else {
            logicItem.setActivated(false);
        }
    }
}
