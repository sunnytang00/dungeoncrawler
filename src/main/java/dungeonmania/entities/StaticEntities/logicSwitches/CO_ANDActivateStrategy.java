package dungeonmania.entities.StaticEntities.logicSwitches;

import dungeonmania.DungeonGame;

public class CO_ANDActivateStrategy extends BaseActivateStrategy {
    @Override
    public void activate(DungeonGame game, LogicItem logicItem) {
        int activatedCount = countAdjacentActivatedEntities(game.getMap(), logicItem);
        // check activated on the same tick
        if (activatedCount >= 2 && checkActivatedOnSameTick(game.getMap(), logicItem)) {
            logicItem.setActivated(true);
            logicItem.setActivationTick(game.getCurrentTick());
        } else {
            logicItem.setActivated(false);
        }
    }
}
