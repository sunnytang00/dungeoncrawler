package dungeonmania.goals;

import dungeonmania.Entity;
import dungeonmania.movingEntity.Enemy;
import dungeonmania.movingEntity.Player;

import java.util.List;

public class DestroyEnemy extends LeafGoal {

    public DestroyEnemy(Player player) {
        super(player);
    }

    @Override
    public boolean isAchieve() {
        boolean flag = true;
        List<Entity> entities = player.getInventory().get;
        for (Entity entity : entities) {
            if (entity instanceof Enemy) {
                flag = false;
                break;
            }
        }
        return flag;
    }
}
