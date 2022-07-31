package dungeonmania.entities.movingEntity.enemies;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.DungeonMap;
import dungeonmania.entities.Entity;
import dungeonmania.util.*;

public class CirclingSpawn implements MovingStrategy {

    // assume we pick a box of size 3 x 3
    /* assume initial spawn position is (2,2) the spider moves like this:
        (2,2)-> (2,1) UP (if boulder at (2,1) then spider stay idle until the boulder is moved)
        
        (2,1)-> (3,1) RIGHT
        (3,1) -> (3,2) DOWN
        (3,2) -> (3,3) DOWN
        (3,3) -> (2,3) LEFT
        (2,3) -> (1,3) LEFT
        (1,3) -> (1,2) UP
        (1,2) -> (1,1) UP
        (1,1) -> (2,1) RIGHT

        (2,1)-> (3,1) RIGHT
        ... repeat above 8 steps

    if meet boulders, it will reverse direction

    */

    // Spiders spawn at random locations in the dungeon from the beginning of the game. 
    // When the spider spawns, they immediately move the 1 square upwards (towards the top of the screen) 
    // and then begin 'circling' their spawn spot (see a visual example here). Spiders are able to 
    // traverse through walls, doors, switches, portals, exits (which have no effect), but not boulders, 
    // in which case it will reverse direction (see a visual example here). 
    // When it comes to spawning spiders, since the map is technically infinite you can spawn them anywhere 
    // - however for better gameplay we suggest you make an assumption and pick a four co-ordinate box to 
    // spawn spiders in.


    @Override
    public void move(Enemy movingEntity, DungeonMap map) {

        Spider spider = (Spider) movingEntity;
        Position iniPos = spider.getInitialPosition();
        Position currPos = spider.getPosition();
        List<Position> adjPos = iniPos.getAdjacentPositions();
        // first move
        if (iniPos.equals(currPos)) {
            Position firstMovePos = iniPos.translateBy(Direction.UP);
            if (map.checkTypeEntityAtPos("boulder", firstMovePos)) {
                // if boulder at first move position, spider stay idle until boulder is removed
                return;
            }
            spider.setPosition(firstMovePos);

        // follow circular path
        } else {
            List<Position> bouldersPos = getAdjacentBoulderPositions(adjPos, map);
            // index of circular path position, from 0 to 7
            int circIndex = adjPos.indexOf(currPos);
            int newIndex = (circIndex + 1) % 8;
            Position newCircPos = adjPos.get(newIndex);
            int newAntiIndex = (circIndex - 1) % 8;
            if (newAntiIndex == -1) {newAntiIndex = 7;}
            Position newAntiCircPos = adjPos.get(newAntiIndex);
            // if no boulder, follow simple clockwise circular path
            if (bouldersPos == null || bouldersPos.size() == 0) {
                spider.setPosition(newCircPos);
            }
            // if encounters boulder, reverse direction
            else {

                if ((spider.isClockwiseMove() && bouldersPos.contains(newCircPos)) || 
                    (!spider.isClockwiseMove() && !bouldersPos.contains(newAntiCircPos))) {
                    if (bouldersPos.contains(newAntiCircPos)) {
                        return;
                    }
                    spider.setClockwiseMove(false);
                    spider.setPosition(newAntiCircPos);

                } else if ((!spider.isClockwiseMove() && bouldersPos.contains(newAntiCircPos)) ||
                            (spider.isClockwiseMove() && ! bouldersPos.contains(newCircPos))) {
                    if (bouldersPos.contains(newCircPos)) {
                        return;
                    }
                    spider.setClockwiseMove(true);
                    spider.setPosition(newCircPos);
                }
            }
        }

    }

    public boolean containsBoulder(List<Entity> adjEntities) {
        boolean found = adjEntities.stream().anyMatch(entity -> entity.getType().equals("boulder"));
        return found;    
    }

    public List<Position> getAdjacentBoulderPositions(List<Position> adjPos, DungeonMap map){
        List<Position> bouldersPosition = new ArrayList<Position>();
        for (Position pos : adjPos) {
            List<Entity> atAdj = map.getEntityFromPos(pos);
            if (atAdj != null && containsBoulder(atAdj)) {
                bouldersPosition.add(pos);
            }
        }
        return bouldersPosition;
    }
    
}
