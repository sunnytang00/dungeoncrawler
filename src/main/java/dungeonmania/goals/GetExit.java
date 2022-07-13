package dungeonmania.goals;

import dungeonmania.Entity;
import dungeonmania.StaticEntities.Exit;
import dungeonmania.entities.Player;
import dungeonmania.util.Position;

import java.util.List;

public class GetExit extends LeafGoal {

    public GetExit(Player player) {
        super(player);
    }

    @Override
    public boolean isAchieve() {
        boolean flag = false;
        List<Entity> entities = player.getEntities();
        Position playerPosition = player.getPosition();
        for (Entity entity : entities) {
            if (entity instanceof Exit) {
                Exit exit = (Exit)entity;
                Position position = exit.getPosition();
                if (position.getX() == playerPosition.getX() && position.getY() == playerPosition.getY()) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }
}

