package dungeonmania.goals;

import dungeonmania.Entity;
import dungeonmania.entities.Enemy;
import dungeonmania.entities.Player;

import java.util.List;

public class DestroyEnemy extends LeafGoal {

    public DestroyEnemy(Player player) {
        super(player);
    }

    @Override
    public boolean isAchieve() {
        boolean flag = true;
        List<Entity> entities = player.getEntities();
        for (Entity entity : entities) {
            if (entity instanceof Enemy) {
                flag = false;
                break;
            }
        }
        return flag;
    }
}

