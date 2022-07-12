package dungeonmania.entities.collectableEntities;

import dungeonmania.DungeonMap;
import dungeonmania.Entity;
import dungeonmania.entities.Player;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Weapon {

    private int bombRadius;

    public Bomb(String type, Position position) {
        super(type, position);
    }

    public int getBombRadius() {
        return bombRadius;
    }

    public void setBombRadius(int bombRadius) {
        this.bombRadius = bombRadius;
    }

    public void explode(DungeonMap map) {
        List<Entity> mapEntities = map.getMapEntities();
        List<Integer> removeListIndex = new ArrayList<>();
        int currentX = getPosition().getX();
        int currentY = getPosition().getY();
        // find all the position index of all the entities which are
        // in the destroying range of the bomb in the map
        for (int i = 0; i < mapEntities.size(); ++i) {
            Entity entity = mapEntities.get(i);
            int x = entity.getXCoordinate();
            int y = entity.getYCoordinate();
            if (Math.abs(currentX - x) <= 0 && Math.abs(currentY - y) <= 0 && !(entity instanceof Player)) {
                removeListIndex.add(i);
            }
        }
        // remove all the entities destroyed by bomb in the range
        // of the bomb attacking radius
        for (Integer removeIndex : removeListIndex) {
            mapEntities.remove(removeIndex);
        }
    }
}

