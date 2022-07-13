package dungeonmania.goals;

import dungeonmania.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectableEntities.Treasure;

import java.util.List;

public class CollectTreasure extends LeafGoal {

    public CollectTreasure(Player player) {
        super(player);
    }

    @Override
    public boolean isAchieve() {
        boolean flag = true;
        List<Entity> entities = player.getEntities();
        for (Entity entity : entities) {
            if (entity instanceof Treasure) {
                flag = false;
                break;
            }
        }
        return flag;
    }
}

