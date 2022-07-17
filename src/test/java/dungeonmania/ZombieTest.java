package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class ZombieTest {

    @Test
    @DisplayName("Test random movement of zombie")
    public void zombieMovementBasic() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("zombies", "bribe_radius_1");
        DungeonResponse move = dmc.tick(Direction.LEFT);
        assertEquals(1, countEntityOfType(move, "zombie_toast"));
        Position pos = getEntities(move, "zombie_toast").get(0).getPosition();
        Position pos2 = getEntities(move, "zombie_toast_spawner").get(0).getPosition();

        List<Position> positions = pos2.getCardinallyAdjacentPositions();

        assertTrue(positions.contains(pos));
 
    }


    @Test
    public void testSpawnerBehaviour() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse intialResponse = dmc.newGame("zombieSpiderSpawn", "c_testSpawning_1");

        DungeonResponse move = dmc.tick(Direction.UP); //Tick 1

        assertEquals(0, countEntityOfType(move, "zombie_toast"));

        move = dmc.tick(Direction.DOWN); //Tick 2

        assertEquals(1, countEntityOfType(move, "zombie_toast"));


        move = dmc.tick(Direction.UP);

        assertEquals(1, countEntityOfType(move, "zombie_toast"));

        move = dmc.tick(Direction.DOWN);

        assertEquals(2, countEntityOfType(move, "zombie_toast"));

    }

    @Test
    @DisplayName("Test zombie blocked by wall")
    public void testzombieBlockedbyWall() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_testZombieTraversible", "c_testSpawning_1");
 
        
        DungeonResponse move = dmc.tick(Direction.DOWN); //Tick 1

        assertEquals(0, countEntityOfType(move, "zombie_toast"));

        move = dmc.tick(Direction.DOWN); //Tick 2

        assertEquals(1, countEntityOfType(move, "zombie_toast"));
        Position pos = getEntities(move, "zombie_toast").get(0).getPosition();
        Position pos2 = getEntities(move, "zombie_toast_spawner").get(0).getPosition();
        Position expectedPos = pos2.translateBy(Direction.LEFT);
        System.out.println("expected" + expectedPos.getX() + "x" + expectedPos.getY() + "Y");
        // assert after movement
        assertEquals(expectedPos.getX(), pos.getX());
        assertEquals(expectedPos.getY(), pos.getY());

        move = dmc.tick(Direction.DOWN); //Tick 2
        pos = getEntities(move, "zombie_toast").get(0).getPosition();
        assertEquals(expectedPos.getX() + 1, pos.getX());
        assertEquals(expectedPos.getY(), pos.getY());

    }

}
