package dungeonmania;

import java.util.List;

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

    public Entity getEntityFromPos(Position position) {
        Entity currEntity = mapEntities.stream()
                            .filter(entity -> position.equals(entity.getPosition()))
                            .findAny()
                            .orElse(null);
        return currEntity;    
    }

}
