package dungeonmania.movingEntity;

import dungeonmania.DungeonMap;
import dungeonmania.util.Position;

public class RunAway implements MovingStrategy{

    // Mercenaries and Zombies will run away from the Player when they are invincible.

    // see forum post, basically observe player location (x,y)
    // m is a positive integer
    // cardinal (4 circumstances):
    // if enemy to the upper side of player m squares, (x,y-m), go further up, y-m -= 1
    // if enemy to the lower side of player m squares, (x,y+m), go further down, y+m += 1
    // if enemy to the left side of player m squares, (x-m,y), go further left, x-m -= 1
    // if enemy to the right side of player m squares, (x+m,y), go further right, x+m += 1
    // diagonal (2 circumstances):
    // if enemy to the up-left or up-right side of player m squares, (any x, y-m), go further up, y-m -= 1
    // if enemy to the down-left or down-right side of player m squares, (any x, y+m), go further down, y+m += 1
    // if enemy at the same position as player, (x,y), can run away in any directions, we just assume (can we) it will escape by running UP, y-=1

    @Override
    public void move(MovingEntity movingEntity, DungeonMap map) {
        // change to observer pattern later?
        Position playerPos = map.getPlayerPosition();
        Position currPos = movingEntity.getPosition();
        int currX = currPos.getX();
        int currY = currPos.getY();

        if (currY < playerPos.getY()) {
            currY -= 1;
        } else if (currY > playerPos.getY()) {
            currY += 1;
        } else if (currY == playerPos.getY() && currX <= playerPos.getX()) {
            currX -= 1;
        } else if (currY == playerPos.getY() && currX > playerPos.getX()) {
            currX += 1;
        } 

        Position newPos = new Position(currX, currY);
        movingEntity.setPosition(newPos);
 
    }
    
}
