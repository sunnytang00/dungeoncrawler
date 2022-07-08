package dungeonmania.movingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dungeonmania.DungeonMap;
import dungeonmania.Entity;
import dungeonmania.util.Position;

public class FollowPlayer implements MovingStrategy {


    // They constantly move towards the Player, stopping only if they cannot move any closer (
    // they are able to move around walls). Mercenaries are limited by the same movement constraints as the Player. 

    // for bribed mercenaries
    
    // As an ally, once it reaches the Player it simply follows the Player around, 
    // occupying the square the player was previously in.

    // https://edstem.org/au/courses/8675/discussion/931224 moves closer in either x or y direction
    @Override
    public void move(MovingEntity movingEntity, DungeonMap map) {
        
        // reach player if hasn't, once it reaches the Play
        // simply follows the Player around, occupying the square the player was previously in.
        // change to observer pattern later?
        Player player = map.getPlayer();
        // if player invisible, move randomly
        Position playerPrevPos = player.getPrevPosition();
        Position currPos = movingEntity.getPosition();
        // has followed player
        if (playerPrevPos.equals(currPos)) {
            return;
        }

        List<Position> adjPos = currPos.getCardinallyAdjacentPositions();
        List<Position> moveablePos = new ArrayList<Position>();
        for (Position pos : adjPos) {
            // always follow player but do not go into player position
            if (pos.getDistanceBetween(playerPrevPos) < currPos.getDistanceBetween(playerPrevPos)) {
                Entity atAdj = map.getEntityFromPos(pos);
                if (atAdj == null || !movingEntity.blockedBy(atAdj)) {
                    moveablePos.add(pos);
                }    
            }
        }
        if (moveablePos.size() == 0) {
            return;
        }
        movingEntity.setPosition(getRandomPosition(moveablePos));
        
    }

    // Randomly select items from a List in Java
    // Reference: https://www.geeksforgeeks.org/randomly-select-items-from-a-list-in-java/

    public Position getRandomPosition(List<Position> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
    
}
