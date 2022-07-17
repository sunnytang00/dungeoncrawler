package dungeonmania.movingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dungeonmania.DungeonMap;
import dungeonmania.Entity;
import dungeonmania.util.Position;

public class MoveTowardsPlayer implements MovingStrategy {


    // They constantly move towards the Player, stopping only if they cannot move any closer (
    // they are able to move around walls). Mercenaries are limited by the same movement constraints as the Player.

    // https://edstem.org/au/courses/8675/discussion/931224 moves closer in either x or y direction
    @Override
    public void move(Enemy movingEntity, DungeonMap map) {
        // change to observer pattern later?
        Player player = map.getPlayer();
        // if player invisible, move randomly
        Position playerPos = player.getPosition();
        Position currPos = movingEntity.getPosition();

        // System.out.println("Merc" + currPos + "Player" + playerPos);
        // has reached player
        if (playerPos.equals(currPos)) {
            return;
        }
        List<Position> adjPos = currPos.getCardinallyAdjacentPositions();
        List<Position> moveablePos = new ArrayList<Position>();
        List<Position> moveablePasswall = new ArrayList<Position>();
        if (adjPos != null && adjPos.size() > 0) {
            for (Position pos : adjPos) {
                if (pos.getDistanceBetween(playerPos) < currPos.getDistanceBetween(playerPos)) {
                    List<Entity> atAdj = map.getEntityFromPos(pos);
                    if (atAdj == null || atAdj.size() == 0|| !movingEntity.blockedBy(atAdj)) {
                        moveablePos.add(pos);
                        //System.out.println("pos" + pos + "block" + movingEntity.blockedBy(atAdj) + "atadj" + atAdj);
                    } 
                } else {
                    // find the empty spot not blocked by wall
                    List<Entity> atAdj = map.getEntityFromPos(pos);
                    if (atAdj == null || atAdj.size() == 0|| !movingEntity.blockedBy(atAdj)) {
                        moveablePasswall.add(pos);
                        //System.out.println("pos" + pos + "block" + movingEntity.blockedBy(atAdj) + "atadj" + atAdj);
                    } 
                }
            }
        }
        if (moveablePos != null && moveablePos.size() > 0) {
            movingEntity.setPosition(getRandomPosition(moveablePos));

        } else if (moveablePasswall != null && moveablePasswall.size() > 0) {
            // find spot to pass around walls
            movingEntity.setPosition(getRandomPosition(moveablePasswall));
        }

        
    }

    // Randomly select items from a List in Java
    // Reference: https://www.geeksforgeeks.org/randomly-select-items-from-a-list-in-java/

    public Position getRandomPosition(List<Position> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
    
}
