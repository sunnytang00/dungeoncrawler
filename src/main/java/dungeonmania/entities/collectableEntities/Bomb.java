package dungeonmania.entities.collectableEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.DungeonMap;
import dungeonmania.Entity;
import dungeonmania.StaticEntities.FloorSwitch;
import dungeonmania.entities.Item;
import dungeonmania.movingEntity.Player;
import dungeonmania.util.JSONConfig;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageTypeSpecifier;

import org.json.JSONObject;

public class Bomb extends Item {

    private boolean isActivated;
    private boolean pickable;


    public Bomb(String type, Position position) {
        super(type, position);
        pickable = true;
    }

    public int getBombRadius() {
        return (int) JSONConfig.getConfig("bomb_radius");
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
                if ((currX - getBombRadius()) <= x && x <= (currX + getBombRadius()) 
                     && (currY - getBombRadius()) <= y && y <= (currY + getBombRadius())) {
                    removable.add(entity);
                }
            }
        }
        
        List<Position> cardPosition = getPosition().getCardinallyAdjacentPositions();
        
        for (Position position : cardPosition) {
            //Loop through each cardinally adjacent position
            List<Entity> eList = map.getEntityFromPos(position);
            //For each cardinally adj position, loop through the list of entities at that position
            for (Entity entity : eList) {
                //If there is a floorswitch entity and it is triggered, explode
                if (entity instanceof FloorSwitch) {
                    FloorSwitch floorSwitch = (FloorSwitch) entity;
                    if (floorSwitch.isTriggered()) {
                        // remove all the entities destroyed by bomb in the range
                        // of the bomb attacking radius
                        removeEntityDestroyed(map, removable);
                    }
                }
            }

        }
        
    }

    public void removeEntityDestroyed(DungeonMap map, List<Entity> entities) {
        for (Entity entity : entities) {
            map.removeEntityFromMap(entity);
        }
    }
    @Override
    public JSONObject toJSON() {
        JSONObject obj = super.toJSON();
        // obj.put("is_active", isTriggered);
        // obj.put("is_pickable", isPickable);
        return obj;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public boolean isPickable() {
        return pickable;
    }

    public void setPickable(boolean pickable) {
        this.pickable = pickable;
    }
}

