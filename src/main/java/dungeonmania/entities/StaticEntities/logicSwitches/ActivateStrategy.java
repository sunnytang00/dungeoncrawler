package dungeonmania.entities.StaticEntities.logicSwitches;

import dungeonmania.DungeonGame;
import dungeonmania.DungeonMap;

public interface ActivateStrategy {
    /**
     *
     * @param map
     */
    void activate(DungeonGame game, LogicItem logicItem);
}
