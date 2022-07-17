package dungeonmania.goals;

import dungeonmania.DungeonMap;
import dungeonmania.Entity;
import dungeonmania.entities.collectableEntities.Treasure;
import dungeonmania.movingEntity.Player;

import java.util.List;

public class CollectTreasure extends LeafGoal {

    public CollectTreasure(DungeonMap map) {
        super(map);
    }

    @Override
    public boolean isAchieve() {
        boolean flag = true;
        List<Entity> entities = map.getMapEntities();
        for (Entity entity : entities) {
            if (entity instanceof Treasure) {
                flag = false;
                break;
            }
        }
        return flag;
    }
}


