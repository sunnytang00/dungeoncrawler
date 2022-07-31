package dungeonmania.entities.StaticEntities.logicSwitches;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.DungeonGame;
import dungeonmania.DungeonMap;
import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntity.player.Player;
import dungeonmania.util.JSONConfig;
import dungeonmania.util.Position;


import org.json.JSONObject;

public class LogicBomb extends LogicItem {

    private boolean pickable;
    private int activationTick;

    public LogicBomb(String type, Position position, LogicEnum logic) {
        super(type, position, logic);
    }

    public int getBombRadius() {
        return (int) JSONConfig.getConfig("bomb_radius");
    }

    @Override
    public void setActivated(boolean activated) {
        this.isActivated = activated;
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
        removeEntityDestroyed(map, removable);
        
    }

    public void removeEntityDestroyed(DungeonMap map, List<Entity> entities) {
        for (Entity entity : entities) {
            map.removeEntityFromMap(entity);
        }
    }


    @Override
    public void tick(DungeonGame game) {
        Player player = game.getPlayer();
        DungeonMap map = game.getMap();
        Position newPosition = player.getPosition();
        setPosition(newPosition);
        map.addEntityToMap(this);
        explode(map);

    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = super.toJSON();
        obj.put("is_active", isActivated);
        obj.put("is_pickable", pickable);
        return obj;
    }

}

