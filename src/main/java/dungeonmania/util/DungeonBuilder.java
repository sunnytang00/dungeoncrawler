package dungeonmania.util;


import dungeonmania.DungeonMap;
import dungeonmania.Entity;
import dungeonmania.StaticEntities.Exit;
import dungeonmania.StaticEntities.Wall;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DungeonBuilder {

    private int width;
    private int height;
    private Position start;
    private Position end;
    private List<List<Boolean>> maze;

    public static final boolean MAZE_EMPTY = true;

    public DungeonBuilder(int width, int height, Position start, Position end) {
        this.width = width;
        this.height = height;
        this.start = start;
        this.end = end;
        this.maze = randomizedPrims();
    }

    public List<List<Boolean>> randomizedPrims() {

        // initialize one maze map
        List<List<Boolean>> maze = new ArrayList<>();

        if (width <=0 || height <= 0 || null == start || null == end) {
            return maze;
        }
        // initialize maze
        initMaze(maze);

        // add to options all neighbours of 'start' not on boundary that are of
        // distance 2 away and are walls
        List<Position> options = findSecondMovementsNeighbours(start)
                .stream()
                .filter(e -> !maze.get(e.getY()).get(e.getX()))
                .collect(Collectors.toList());

        while (options.size() > 0) {
            // let next = remove random from options
            Random random = new Random();
            Position next = options.remove(random.nextInt(options.size()));

            // let neighbours = each neighbour of distance 2 from next not on boundary
            // that are empty
            List<Position> neighbours = findSecondMovementsNeighbours(next)
                    .stream()
                    .filter(e -> maze.get(e.getY()).get(e.getX()))
                    .collect(Collectors.toList());
            if (neighbours.size() != 0) { // if neighbours is not empty
                Position neighbour = neighbours.get(random.nextInt(neighbours.size()));
                maze.get(next.getY()).set(next.getX(), true);
                // position in between next and neighbour = empty
                setBetweenNextAndNeighbour(maze, next, neighbour);
                maze.get(neighbour.getY()).set(neighbour.getX(), true);
            }

            // add to options all neighbours of 'next' not on boundary that are of
            // distance 2 away and are walls
            List<Position> nextNeighbours = findSecondMovementsNeighbours(next)
                    .stream()
                    .filter(e -> !maze.get(e.getY()).get(e.getX()))
                    .filter(e -> !options.contains(e))
                    .collect(Collectors.toList());
            options.addAll(nextNeighbours);
        }

        // incase the end position is not connected to the map
        if (!maze.get(end.getY()).get(end.getX())) {
            maze.get(end.getY()).set(end.getX(), MAZE_EMPTY);
            // let neighbours = neighbours not on boundary of distance 1 from maze[end]
            List<Position> firstNeighbours = findFirstMovementNeighbours(end);
            if (areAllWalls(firstNeighbours, maze)) { // no cells in neighbours that are empty
                Random random = new Random();
                //connect random neighbour
                Position neighbour = firstNeighbours.get(random.nextInt(firstNeighbours.size()));
                maze.get(neighbour.getY()).set(neighbour.getX(), MAZE_EMPTY);
            }
        }

        return maze;
    }

    private List<Position> findSecondMovementsNeighbours(Position start) {
        int x = start.getX();
        int y = start.getY();
        List<Position> positions = new ArrayList<>(
            Arrays.asList(
                new Position(x, y + 2),
                new Position(x - 2, y),
                new Position(x + 2, y),
                new Position(x, y - 2)
            )
        );

        // remove all the positions on the boundary
        positions.removeIf(pos -> isReachBoundary(pos));
        return positions;
    }

    private List<Position> findFirstMovementNeighbours(Position currentPosition) {
        int x = currentPosition.getX(), y = currentPosition.getY();
        // find the positions that could be the possible first neighbours of  currPos
        List<Position> positions = new ArrayList<Position>(
            Arrays.asList(
                new Position(x, y + 1),
                new Position(x - 1, y),
                new Position(x + 1, y),
                new Position(x, y - 1)
            )
        );
        // remove positions on the boundary
        positions.removeIf(pos -> isReachBoundary(pos));
        return positions;
    }

    private boolean isReachBoundary(Position position) {
        int x = position.getX(), y = position.getY();
        if (x <= 0 || x >= width - 1 || y <= 0 || y >= height - 1) {
            return true;
        }
        return false;
    }

    public boolean getPositionFromMaze(int x, int y) {
        return maze.get(y).get(x);
    }

    private void initMaze(List<List<Boolean>> maze) {
        for (int i = 0; i < height; i++) {
            List<Boolean> rowInMaze = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                rowInMaze.add(false);
            }
            maze.add(rowInMaze);
        }
        // set start as empty
        maze.get(start.getY()).set(start.getX(), MAZE_EMPTY);
    }

    private void setBetweenNextAndNeighbour(List<List<Boolean>> maze, Position start, Position neighbour) {
        int x1 = start.getX();
        int y1 = start.getY();
        int x2 = neighbour.getX();
        int y2 = neighbour.getY();
        if (y1 == y2 - 2) {
            maze.get(y1 + 1).set(x1, MAZE_EMPTY);
        } else if (y1 == y2 + 2) {
            maze.get(y1 - 1).set(x1, MAZE_EMPTY);
        } else if (x1 == x2 - 2) {
            maze.get(y1).set(x1 + 1, MAZE_EMPTY);
        } else if (x1 == x2 + 2) {
            maze.get(y1).set(x1 - 1, MAZE_EMPTY);
        }
    }

    private Boolean areAllWalls(List<Position> allPositions, List<List<Boolean>> maze) {
        for (Position position : allPositions) {
            int x = position.getX(), y = position.getY();
            if (maze.get(y).get(x)) {
                return false;
            }
        }
        return true;
    }

    private JSONObject createJSONEntity(int x, int y, String type) {
        JSONObject jsonEntity = new JSONObject();
        jsonEntity.put("x", x);
        jsonEntity.put("y", y);
        jsonEntity.put("type", type);
        return jsonEntity;
    }

    /**
     * Create all the entities for game map, return
     * the JSONObject by which it could get all the
     * entities by key/value format
     * @return
     */
    public JSONObject getJSONMazeInfo() {
        JSONObject mazeJSON = new JSONObject();
        JSONArray entities = new JSONArray();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (start.getX() == j && start.getY() == i) {
                    // player starts here
                    entities.put(createJSONEntity(j, i, "player"));
                } else if (end.getX() == j && end.getY() == i) {
                    // exit is here
                    entities.put(createJSONEntity(j, i, "exit"));
                } else if (!maze.get(i).get(j)) {
                    // place wall
                    entities.put(createJSONEntity(j, i, "wall"));
                }
            }
        }
        mazeJSON.put("entities", entities);

        // set the goal for the map
        JSONObject goal = new JSONObject();
        goal.put("goal", "exit");
        mazeJSON.put("goal-condition", goal);
        return mazeJSON;
    }
}

