package dungeonmania.logicSwitches;

import dungeonmania.DungeonMap;

public interface ActivateStrategy {
    /**
     *
     * @param map
     */
    void activate(DungeonMap map, LogicItem logicItem);
}
