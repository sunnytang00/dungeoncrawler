package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestUtils.getGoals;
import static dungeonmania.TestUtils.countEntityOfType;
import static dungeonmania.TestUtils.getValueFromConfigFile;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
public class PersistenceTest {
    @Test
    @DisplayName("Simple persistence test with maze map")
    public void simplePersistenceTestMazeMap() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_maze", "c_simpleGoalTest_noGoalConditions");

        res = dmc.tick(Direction.DOWN);
        EntityResponse initPlayer = getPlayer(res).get();

        res = dmc.saveGame("mazeMoveDownBy1");
        EntityResponse savedPlayer = getPlayer(res).get();
        assertEquals(initPlayer, savedPlayer);

        DungeonManiaController dmc1 = new DungeonManiaController();
        DungeonResponse reloadRes = dmc1.loadGame("mazeMoveDownBy1");
        EntityResponse reloadPlayer = getPlayer(res).get();
        assertEquals(initPlayer, reloadPlayer);
        assertEquals(savedPlayer, reloadPlayer);

        assertEquals(res.getDungeonName() , reloadRes.getDungeonName());
        assertEquals(res.getDungeonId() , reloadRes.getDungeonId());
        assertTrue(reloadRes.getBattles().size() == 0);
        assertTrue(reloadRes.getInventory().size() == 0);
        assertTrue(reloadRes.getEntities().size() == res.getEntities().size());
    }

    @Test
    @DisplayName("Test movement of spiders when reloaded")
    public void spiderMovementPersistenceTest() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_basicMovement", "c_spiderTest_basicMovement");
        EntityResponse initPlayer = getPlayer(res).get();
        Position pos = getEntities(res, "spider").get(0).getPosition();

        List<Position> movementTrajectory = new ArrayList<Position>();
        int x = pos.getX();
        int y = pos.getY();
        int nextPositionElement = 0;
        movementTrajectory.add(new Position(x  , y-1));
        movementTrajectory.add(new Position(x+1, y-1));
        movementTrajectory.add(new Position(x+1, y));
        movementTrajectory.add(new Position(x+1, y+1));
        movementTrajectory.add(new Position(x  , y+1));
        movementTrajectory.add(new Position(x-1, y+1));
        movementTrajectory.add(new Position(x-1, y));
        movementTrajectory.add(new Position(x-1, y-1));

        // Assert Circular Movement of Spider
        for (int i = 0; i <= 3; ++i) {
            res = dmc.tick(Direction.UP);
            assertEquals(movementTrajectory.get(nextPositionElement), getEntities(res, "spider").get(0).getPosition());
            nextPositionElement++;
        }

        // save game 
        res = dmc.saveGame("spiderMovementReload");
        EntityResponse savedPlayer = getPlayer(res).get();
        assertEquals(initPlayer, savedPlayer);

        // reload game 
        DungeonManiaController dmc1 = new DungeonManiaController();
        DungeonResponse reloadRes = dmc1.loadGame("spiderMovementReload");
        EntityResponse reloadPlayer = getPlayer(res).get();
        assertEquals(initPlayer, reloadPlayer);
        assertEquals(savedPlayer, reloadPlayer);
        assertEquals(movementTrajectory.get(3), getEntities(reloadRes, "spider").get(0).getPosition());

        reloadRes = dmc1.tick(Direction.UP);
        assertEquals(movementTrajectory.get(4), getEntities(reloadRes, "spider").get(0).getPosition());
        reloadRes = dmc1.tick(Direction.UP);
        assertEquals(movementTrajectory.get(5), getEntities(reloadRes, "spider").get(0).getPosition());
    }

    @Test
    @DisplayName("Test goals in persistence")
    public void persistenceGoalTest() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_complexGoalsTest_AndOr", "c_complexGoalsTest_andAll");

        assertTrue(getGoals(res).contains(":exit"));
        assertTrue(getGoals(res).contains(":boulders"));
        assertTrue(getGoals(res).contains(":enemies"));

        // move boulder onto switch
        res = dmc.tick(Direction.RIGHT);
        assertTrue(getGoals(res).contains(":exit"));
        assertFalse(getGoals(res).contains(":boulders"));
        assertFalse(getGoals(res).contains(":enemies"));

        // save game 
        res = dmc.saveGame("persistingGoals");

        // do something after saving game
        res = dmc.tick(Direction.RIGHT);
        assertTrue(getGoals(res).contains(":exit"));
        assertTrue(getGoals(res).contains(":boulders"));
        assertTrue(getGoals(res).contains(":enemies"));

        // reload game 
        DungeonManiaController dmc1 = new DungeonManiaController();
        DungeonResponse reloadRes = dmc1.loadGame("persistingGoals");
        assertTrue(getGoals(reloadRes).contains(":exit"));
        assertFalse(getGoals(reloadRes).contains(":boulders"));
        assertFalse(getGoals(reloadRes).contains(":enemies"));

        // go back to the saved game - move boulder off switch
        reloadRes = dmc1.tick(Direction.RIGHT);
        assertTrue(getGoals(reloadRes).contains(":exit"));
        assertTrue(getGoals(reloadRes).contains(":boulders"));
        assertTrue(getGoals(reloadRes).contains(":enemies"));

        // kill mercenary
        reloadRes = dmc1.tick(Direction.RIGHT);
        assertTrue(getGoals(reloadRes).contains(":exit"));
        assertFalse(getGoals(reloadRes).contains(":boulders"));
        assertFalse(getGoals(reloadRes).contains(":enemies"));
       
        // move second boulder onto switch
        reloadRes = dmc1.tick(Direction.DOWN);
        assertTrue(getGoals(reloadRes).contains(":exit"));
        assertFalse(getGoals(reloadRes).contains(":boulders"));
        assertFalse(getGoals(reloadRes).contains(":enemies"));

        // move second boulder off switch
        reloadRes = dmc1.tick(Direction.DOWN);
        assertTrue(getGoals(reloadRes).contains(":exit"));
        assertFalse(getGoals(reloadRes).contains(":boulders"));
        assertFalse(getGoals(reloadRes).contains(":enemies"));

        // exit successfully
        reloadRes = dmc1.tick(Direction.RIGHT);
        assertFalse(getGoals(reloadRes).contains(":exit"));
        assertFalse(getGoals(reloadRes).contains(":boulders"));
        assertFalse(getGoals(reloadRes).contains(":enemies"));
    }
}
