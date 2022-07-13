package dungeonmania.goals;

import dungeonmania.Entity;
import dungeonmania.StaticEntities.Boulder;
import dungeonmania.entities.Player;

import java.util.List;

public class BoulderOnSwitch extends LeafGoal {

    public BoulderOnSwitch(Player player) {
        super(player);
    }

    @Override
    public boolean isAchieve() {
        boolean flag = true;
        List<Entity> entities = player.getEntities();
        for (Entity entity : entities) {
            if (entity instanceof Boulder) {
                Boulder boulder = (Boulder)entity;
                if (!boulder.isOnSwitch()) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }
}

