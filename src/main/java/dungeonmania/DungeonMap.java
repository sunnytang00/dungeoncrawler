package dungeonmania;

import java.util.List;

import dungeonmania.movingEntity.Player;
import dungeonmania.util.Position;

public class DungeonMap {
    // assume mapEntities contain all current entities on the map
    private List<Entity> mapEntities;

    public DungeonMap(List<Entity> mapEntities) {
        this.mapEntities = mapEntities;
    }

    public List<Entity> getMapEntities() {
        return mapEntities;
    }

    public List<Entity> getEntityFromPos(Position position) {
        List<Entity> currEntity = mapEntities.stream()
                            .filter(entity -> position.equals(entity.getPosition())).toList();
        return currEntity;    
    }

    public boolean checkTypeEntityAtPos(String type, Position position) {
        List<Entity> entitiesAtPos = this.getEntityFromPos(position);
        boolean anyMatch = entitiesAtPos.stream().anyMatch(entity -> entity.getType().equals(type));
        return anyMatch;
    }
    
    public Player getPlayer() {
        Player player = (Player) mapEntities.stream()
                                    .filter(entity -> entity.getType().equals("player"))
                                    .findAny()
                                    .orElse(null);
        return player;
    }

}
