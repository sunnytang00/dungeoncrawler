package dungeonmania.entities.StaticEntities.logicSwitches;

import dungeonmania.DungeonGame;

public interface ActivateStrategy {
    /**
     *
     * @param map
     */
    void activate(DungeonGame game, LogicItem logicItem);
}
