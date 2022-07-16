package dungeonmania.entities.collectableEntities;

import dungeonmania.DungeonMap;
import dungeonmania.Entity;
import dungeonmania.entities.Item;
import dungeonmania.movingEntity.*;
import dungeonmania.util.JSONConfig;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Item {

    private static final int DEFAULT_BOMB_RADIUS = JSONConfig.getConfig("bomb_radius");


    public Bomb(String type, Position position) {
        super(type, position);
    }

    public int getBombRadius() {
        return DEFAULT_BOMB_RADIUS;
    }

    public void explode(DungeonMap map) {
        List<Entity> mapEntities = map.getMapEntities();
        List<Entity> removable = new ArrayList<>();
        int currX = getPosition().getX();
        int currY = getPosition().getY();
        
        for (Entity entity : mapEntities) {
            if (!(entity instanceof Player)) {
                int x = entity.getXCoordinate();
                int y = entity.getYCoordinate();
                System.out.println("OUTSIDE type: " + entity.getType() + " x: " + x + " y: " + y);
                System.out.println(currX + DEFAULT_BOMB_RADIUS);
                if ((currX - DEFAULT_BOMB_RADIUS) <= x && x <= (currX + DEFAULT_BOMB_RADIUS) 
                     && (currY - DEFAULT_BOMB_RADIUS) <= y && y <= (currY + DEFAULT_BOMB_RADIUS)) {
                    System.out.println("INSIDE type: " + entity.getType() + " x: " + x + " y: " + y);

                    removable.add(entity);
                }
            }
        }
        // remove all the entities destroyed by bomb in the range
        // of the bomb attacking radius
        removeEntityDestroyed(map, removable);
    }

    public void removeEntityDestroyed(DungeonMap map, List<Entity> entities) {
        for (Entity entity : entities) {
            map.removeEntityFromMap(entity);
        }
    }
}

