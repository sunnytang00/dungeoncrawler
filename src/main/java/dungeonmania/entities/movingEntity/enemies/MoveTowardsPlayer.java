package dungeonmania.entities.movingEntity.enemies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import dungeonmania.DungeonMap;
import dungeonmania.entities.Entity;
import dungeonmania.entities.StaticEntities.Portal;
import dungeonmania.entities.StaticEntities.SwampTile;
import dungeonmania.entities.movingEntity.player.Player;
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
        Position nextPos = currPos;

        // has reached player
        if (playerPos.equals(currPos)) {
            return;
        }

        List<Position> grid = getGrid(playerPos, currPos);
        Map<Position,Position> prev = Dijkstras(grid, currPos, movingEntity, map);
        Position newPos = prev.get(playerPos);
        if (newPos != null && newPos.equals(nextPos)) {
            nextPos = playerPos;
        } else {
            while (newPos != null && !currPos.equals(newPos)) {
                nextPos = newPos;
                newPos = prev.get(newPos);
            }
        }

        movingEntity.setPosition(nextPos);
    }

    public List<Position> getGrid(Position playerPos, Position enemyPos) {
        int pX = playerPos.getX();
        int pY = playerPos.getY();
        int eX = enemyPos.getX();
        int eY = enemyPos.getY();
        List<Position> grid = new ArrayList<Position>();

        for (int x = Math.min(pX, eX); x <= Math.max(pX,eX); x++) {
            for (int y = Math.min(pY, eY); y <= Math.max(pY,eY); y++) {
                grid.add(new Position(x, y));
            }
        }

        return grid;
    }

    /*
    function Dijkstras(grid, source):
    let dist be a Map<Position, Double>
    let prev be a Map<Position, Position>

    for each Position p in grid:
        dist[p] := infinity
        previous[p] := null
    dist[source] := 0

    let queue be a Queue<Position> of every position in grid
    while queue is not empty:
        u := next node in queue with the smallest dist
        for each cardinal neighbour v of u:
            if dist[u] + cost(u, v) < dist[v]:
                dist[v] := dist[u] + cost(u, v)
                previous[v] := u
    return previous

    */

    //  swamp tile is taken into acount
    // mercenary and assassin can choose to move through protal if portal move is beneficial

    public Map<Position, Position> Dijkstras(List<Position> gridPositions, Position src, Enemy movingEntity, DungeonMap map){

        Map<Position, Double> dist = new HashMap<Position, Double>();
        Map<Position, Position> prev = new HashMap<Position, Position>();

        Queue<Position> queue = new LinkedList<Position>();

        for (Position p : gridPositions) {
            dist.put(p, Double.POSITIVE_INFINITY);
            prev.put(p, null);
        }

        dist.put(src,(double) 0);
        queue.add(src);

        while (!queue.isEmpty()) {
            Position u = queue.remove();
            List<Position> adjPos = u.getCardinallyAdjacentPositions();
            List<Position> moveableAdj = new ArrayList<Position>();
            for (Position pos : adjPos) {
                List<Entity> atAdj = map.getEntityFromPos(pos);
                if (atAdj == null || atAdj.size() == 0|| !movingEntity.blockedBy(atAdj)) {
                    moveableAdj.add(pos);
                }
                Portal portal = map.getPortalAtPos(pos);
                if (portal != null) {
                    List<Position> positions =portal.getTeleportPositions(portal, map);
                    moveableAdj.addAll(positions);
                }
            }

            for (Position v : moveableAdj) {
                if (dist.get(u) != null && dist.get(v) != null) {
                    int cost = 1;
                    if (map.getSwampAtPos(v) != null) {
                        SwampTile swamp = (SwampTile) map.getSwampAtPos(v);
                        cost = swamp.getMovementFactor();
                    }

                    if (dist.get(u) + cost < dist.get(v)) {
                        dist.put(v, dist.get(u) + cost);
                        prev.put(v, u);
                        queue.add(v);
                    }
                }
            }
        }
        return prev;
        
    }

}
