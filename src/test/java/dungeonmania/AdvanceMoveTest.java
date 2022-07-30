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

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class AdvanceMoveTest {
    @Test
    @DisplayName("Test enemy stuck for 2 ticks")
    public void testStuckfor2Ticks() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse intialResponse = dmc.newGame("d_testSwamp", "M3_config");

        DungeonResponse move = dmc.tick(Direction.RIGHT);
        Position playerPos = getEntities(move, "player").get(0).getPosition();
        Position mercPos = getEntities(move, "mercenary").get(0).getPosition();
        
        assertEquals(playerPos.getX(), 1);
        assertEquals(playerPos.getY(), 1);
        assertEquals(mercPos.getX(), 2);
        assertEquals(mercPos.getY(), 1);

        move = dmc.tick(Direction.LEFT);
        playerPos = getEntities(move, "player").get(0).getPosition();
        mercPos = getEntities(move, "mercenary").get(0).getPosition();

        assertEquals(playerPos.getX(), 0);
        assertEquals(playerPos.getY(), 1);
        assertEquals(mercPos.getX(), 2);
        assertEquals(mercPos.getY(), 1);

        move = dmc.tick(Direction.RIGHT);
        playerPos = getEntities(move, "player").get(0).getPosition();
        mercPos = getEntities(move, "mercenary").get(0).getPosition();

        assertEquals(playerPos.getX(), 1);
        assertEquals(playerPos.getY(), 1);
        assertEquals(mercPos.getX(), 2);
        assertEquals(mercPos.getY(), 1);

        move = dmc.tick(Direction.LEFT);
        playerPos = getEntities(move, "player").get(0).getPosition();
        mercPos = getEntities(move, "mercenary").get(0).getPosition();

        assertEquals(playerPos.getX(), 0);
        assertEquals(playerPos.getY(), 1);
        assertEquals(mercPos.getX(), 1);
        assertEquals(mercPos.getY(), 1);



    }

    @Test
    @DisplayName("Test enemy stuck for 0 ticks")
    public void testStuckfor0Ticks() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse intialResponse = dmc.newGame("d_testSwamp0", "M3_config");

        DungeonResponse move = dmc.tick(Direction.RIGHT);
        Position playerPos = getEntities(move, "player").get(0).getPosition();
        Position mercPos = getEntities(move, "mercenary").get(0).getPosition();
        
        assertEquals(playerPos.getX(), 1);
        assertEquals(playerPos.getY(), 1);
        assertEquals(mercPos.getX(), 2);
        assertEquals(mercPos.getY(), 1);

        move = dmc.tick(Direction.LEFT);
        playerPos = getEntities(move, "player").get(0).getPosition();
        mercPos = getEntities(move, "mercenary").get(0).getPosition();

        assertEquals(playerPos.getX(), 0);
        assertEquals(playerPos.getY(), 1);
        assertEquals(mercPos.getX(), 1);
        assertEquals(mercPos.getY(), 1);



    }

    @Test
    @DisplayName("Test enemy stuck for 4 ticks")
    public void testStuckfor4Ticks() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse intialResponse = dmc.newGame("d_testSwampFactor4", "M3_config");

        DungeonResponse move = dmc.tick(Direction.RIGHT);
        Position playerPos = getEntities(move, "player").get(0).getPosition();
        Position mercPos = getEntities(move, "assassin").get(0).getPosition();
        
        assertEquals(playerPos.getX(), 1);
        assertEquals(playerPos.getY(), 1);
        assertEquals(mercPos.getX(), 2);
        assertEquals(mercPos.getY(), 1);

        move = dmc.tick(Direction.LEFT);
        playerPos = getEntities(move, "player").get(0).getPosition();
        mercPos = getEntities(move, "assassin").get(0).getPosition();

        assertEquals(playerPos.getX(), 0);
        assertEquals(playerPos.getY(), 1);
        assertEquals(mercPos.getX(), 2);
        assertEquals(mercPos.getY(), 1);

        move = dmc.tick(Direction.RIGHT);
        playerPos = getEntities(move, "player").get(0).getPosition();
        mercPos = getEntities(move, "assassin").get(0).getPosition();

        assertEquals(playerPos.getX(), 1);
        assertEquals(playerPos.getY(), 1);
        assertEquals(mercPos.getX(), 2);
        assertEquals(mercPos.getY(), 1);

        move = dmc.tick(Direction.LEFT);
        playerPos = getEntities(move, "player").get(0).getPosition();
        mercPos = getEntities(move, "assassin").get(0).getPosition();

        assertEquals(playerPos.getX(), 0);
        assertEquals(playerPos.getY(), 1);
        assertEquals(mercPos.getX(), 2);
        assertEquals(mercPos.getY(), 1);

        move = dmc.tick(Direction.RIGHT);
        playerPos = getEntities(move, "player").get(0).getPosition();
        mercPos = getEntities(move, "assassin").get(0).getPosition();

        assertEquals(playerPos.getX(), 1);
        assertEquals(playerPos.getY(), 1);
        assertEquals(mercPos.getX(), 2);
        assertEquals(mercPos.getY(), 1);

        move = dmc.tick(Direction.LEFT);
        playerPos = getEntities(move, "player").get(0).getPosition();
        mercPos = getEntities(move, "assassin").get(0).getPosition();

        assertEquals(playerPos.getX(), 0);
        assertEquals(playerPos.getY(), 1);
        assertEquals(mercPos.getX(), 1);
        assertEquals(mercPos.getY(), 1);

    }

    @Test
    @DisplayName("Test avoid swamp tile with large movement factor")
    public void testAvoidTile() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse intialResponse = dmc.newGame("dijkstra_swamp_largeFactor", "M3_config");

        DungeonResponse move = dmc.tick(Direction.RIGHT);
        Position playerPos = getEntities(move, "player").get(0).getPosition();
        Position mercPos = getEntities(move, "assassin").get(0).getPosition();
        
        assertEquals(playerPos.getX(), 1);
        assertEquals(playerPos.getY(), 0);
        assertEquals(mercPos.getX(), 7);
        assertEquals(mercPos.getY(), 6);

        move = dmc.tick(Direction.LEFT);
        mercPos = getEntities(move, "assassin").get(0).getPosition();

        assertEquals(mercPos.getX(), 7);
        assertEquals(mercPos.getY(), 5);

        move = dmc.tick(Direction.RIGHT);
        mercPos = getEntities(move, "assassin").get(0).getPosition();

        assertEquals(mercPos.getX(), 7);
        assertEquals(mercPos.getY(), 4);

        move = dmc.tick(Direction.LEFT);
        mercPos = getEntities(move, "assassin").get(0).getPosition();

        assertEquals(mercPos.getX(), 7);
        assertEquals(mercPos.getY(), 3);

        move = dmc.tick(Direction.RIGHT);
        mercPos = getEntities(move, "assassin").get(0).getPosition();

        assertEquals(mercPos.getX(), 7);
        assertEquals(mercPos.getY(), 2);

        move = dmc.tick(Direction.LEFT);
        mercPos = getEntities(move, "assassin").get(0).getPosition();

        assertEquals(mercPos.getX(), 7);
        assertEquals(mercPos.getY(), 1);

        move = dmc.tick(Direction.RIGHT);
        mercPos = getEntities(move, "assassin").get(0).getPosition();

        assertEquals(mercPos.getX(), 6);
        assertEquals(mercPos.getY(), 1);

        move = dmc.tick(Direction.LEFT);
        mercPos = getEntities(move, "assassin").get(0).getPosition();

        assertEquals(mercPos.getX(), 5);
        assertEquals(mercPos.getY(), 1);

        move = dmc.tick(Direction.RIGHT);
        mercPos = getEntities(move, "assassin").get(0).getPosition();

        assertEquals(mercPos.getX(), 4);
        assertEquals(mercPos.getY(), 1);

        move = dmc.tick(Direction.RIGHT);
        mercPos = getEntities(move, "assassin").get(0).getPosition();

        assertEquals(mercPos.getX(), 3);
        assertEquals(mercPos.getY(), 1);



    }

}
