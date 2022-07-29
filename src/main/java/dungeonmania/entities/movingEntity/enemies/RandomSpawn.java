package dungeonmania.entities.movingEntity.enemies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dungeonmania.DungeonMap;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class RandomSpawn implements MovingStrategy {


    // Zombies spawn at zombie spawners and move in random directions. 
    // Zombies are limited by the same movement constraints as the Player, 
    // - can be moved up, down, left and right into cardinally adjacent squares, 
    // provided another entity doesn't stop them (e.g. a wall) -
    // except portals have no effect on them.
    @Override
    public void move(Enemy movingEntity, DungeonMap map) {
        Position currPos = movingEntity.getPosition();
        
        List<Position> adjPos = currPos.getCardinallyAdjacentPositions();
        List<Position> moveablePos = new ArrayList<Position>();
        if (adjPos != null && adjPos.size() > 0) {
            for (Position pos : adjPos) {
                List<Entity> atAdj = map.getEntityFromPos(pos);
                if (atAdj == null || atAdj.size() == 0 || !movingEntity.blockedBy(atAdj)) {
                    moveablePos.add(pos);
                    
                }      
            }
        }
        if (moveablePos != null && moveablePos.size() > 0) {
            movingEntity.setPosition(getRandomPosition(moveablePos));
        }
    }

    // Randomly select items from a List in Java
    // Reference: https://www.geeksforgeeks.org/randomly-select-items-from-a-list-in-java/

    public Position getRandomPosition(List<Position> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    
}
