package dungeonmania.util;

import java.util.List;

import dungeonmania.Entity;
import dungeonmania.StaticEntity;

public final class Helper {
    
    public static boolean CheckIfTraversable(Position position, List<Entity> entitiesList) {

        for (Entity entity : entitiesList) {
            if ((entity instanceof StaticEntity) && (entity.getPosition().equals(position))) {
                StaticEntity e = (StaticEntity) entity;
                return e.isTraversable();
            }
        }

        return false;

    }
}
