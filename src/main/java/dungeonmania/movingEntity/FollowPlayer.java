package dungeonmania.movingEntity;

import dungeonmania.DungeonMap;
import dungeonmania.util.Position;

public class FollowPlayer implements MovingStrategy {
    
    // A maximum upper bound for calculating distance for mercenaries moving towards players can be assumed
    private static final int UPPER_BOUND = 60;

    // They constantly move towards the Player, stopping only if they cannot move any closer (
    // they are able to move around walls). Mercenaries are limited by the same movement constraints as the Player. 

    // As an ally, once it reaches the Player it simply follows the Player around, 
    // occupying the square the player was previously in.

    @Override
    public void move(MovingEntity movingEntity, DungeonMap map) {
        // TODO Auto-generated method stub
        
    }
    
}

// bfs
// dijkstra maze
