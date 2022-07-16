package dungeonmania.StaticEntities;

import java.util.List;
import java.util.Random;

import dungeonmania.DungeonMap;
import dungeonmania.Entity;
import dungeonmania.StaticEntity;
import dungeonmania.movingEntity.ZombieToast;
import dungeonmania.util.Direction;
import dungeonmania.util.JSONConfig;
import dungeonmania.util.Position;

public class ZombieToastSpawner extends StaticEntity {

    public ZombieToastSpawner(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        this.isTraversable = true;
    }
    
    /**
     * Method to spawn zombie
     * @param currentTick curent tick 
     * @param spawnTick zombe_spawn_rate, read in from the config file
     */
    public ZombieToast spawnZombie(int currentTick, DungeonMap map) {
        int spawnrate = JSONConfig.getConfig("zombie_spawn_rate");
        if (spawnrate == 0 ) { return null;}

        List<Position> cardinallyAdj = this.getPosition().getCardinallyAdjacentPositions();
        for (Position pos : cardinallyAdj) {
            List<Entity> atAdj = map.getEntityFromPos(pos);
            if (atAdj != null && containsWall(atAdj)) {
                cardinallyAdj.remove(pos);
            }
        }

        if ((currentTick % spawnrate == 0) && cardinallyAdj != null) {
            
            return new ZombieToast("zombie_toast", getRandomPosition(cardinallyAdj), true);
        }

        return null;

    }

    public Position getRandomPosition(List<Position> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    public boolean containsWall(List<Entity> entities) {
        boolean found = entities.stream().anyMatch(entity -> entity.getType().equals("wall"));
        return found;    
    }
}
