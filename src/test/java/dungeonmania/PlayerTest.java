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


public class PlayerTest {
    @Test
    @DisplayName("Test the player can move UP")
    public void testMovementUp() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testMovementDown", "c_movementTest_testMovementDown");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 0), false);

        // move player downward
        DungeonResponse actualDungonRes = dmc.tick(Direction.UP);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
    }

    @Test
    @DisplayName("Test the player can move down")
    public void testMovementDown() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testMovementDown", "c_movementTest_testMovementDown");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 2), false);

        // move player downward
        DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
    }

    @Test
    @DisplayName("Test the player can move left")
    public void testMovementLeft() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testMovementDown", "c_movementTest_testMovementDown");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(0, 1), false);

        // move player downward
        DungeonResponse actualDungonRes = dmc.tick(Direction.LEFT);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
    }

    @Test
    @DisplayName("Test the player can move right")
    public void testMovementRight() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testMovementDown", "c_movementTest_testMovementDown");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 1), false);

        // move player downward
        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
    }

    @Test
    @DisplayName("Test the player block by wall")
    public void testMovementBlockbyWall() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("maze", "simple");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 1), false);

        // move player up
        DungeonResponse actualDungonRes = dmc.tick(Direction.UP);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);

        
        // move player left
        actualDungonRes = dmc.tick(Direction.LEFT);
        actualPlayer = getPlayer(actualDungonRes).get();
        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);

        // move player right
        actualDungonRes = dmc.tick(Direction.RIGHT);
        actualPlayer = getPlayer(actualDungonRes).get();
        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);

        expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 2), false);
        // move player down
        actualDungonRes = dmc.tick(Direction.DOWN);
        actualPlayer = getPlayer(actualDungonRes).get();
        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);

        // move player left
        actualDungonRes = dmc.tick(Direction.LEFT);
        actualPlayer = getPlayer(actualDungonRes).get();
        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);

        // move player right
        actualDungonRes = dmc.tick(Direction.RIGHT);
        actualPlayer = getPlayer(actualDungonRes).get();
        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);

        expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 1), false);

        // move player up
        actualDungonRes = dmc.tick(Direction.UP);
        actualPlayer = getPlayer(actualDungonRes).get();
        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);

        
    }

    @Test
    @DisplayName("Test the player block by unopen door")
    public void testMovementBlockbyDoor() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("2_doors", "simple");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(3,2), false);

        // move player right
        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        List<ItemResponse> inventory = actualDungonRes.getInventory();
        assertTrue(inventory.get(0).getType().equals("key"));
        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
        dmc.tick(Direction.RIGHT);
        actualDungonRes = dmc.tick(Direction.DOWN);

        expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(4,2), false);
        actualPlayer = getPlayer(actualDungonRes).get();
        assertEquals(expectedPlayer, actualPlayer);
        dmc.tick(Direction.LEFT);
        dmc.tick(Direction.DOWN);
        actualDungonRes = dmc.tick(Direction.DOWN);
        
        expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(3,4), false);
        actualPlayer = getPlayer(actualDungonRes).get();
        
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(0, getInventory(actualDungonRes, "key").size());

    }

    @Test
    @DisplayName("Test the player can teleport through one portal")
    public void teleportSuccess() {
        
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("portals", "simple");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        // move player leftwards
        dmc.tick(Direction.LEFT);
        DungeonResponse actualDungonRes = dmc.tick(Direction.LEFT);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(0, 2), false);


        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
    }
    
    @Test
    @DisplayName("Test the player can teleport through one portal")
    public void teleportChainSuccess() {
        
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("portals_advanced", "simple");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        // move player rightwards to red portal
        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);;
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(6, 1), false);


        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
    }
    
    @Test
    @DisplayName("Test the player cannot move with two boulders in a line")
    public void testCannotMoveTwoBoulder() {

        DungeonManiaController dmc = new DungeonManiaController();

        //PLAYER    BOULDER     BOULDER

        DungeonResponse intialResponse = dmc.newGame("d_testTwoBoulderBlocked", "c_battleTests_basicMercenaryMercenaryDies");

        assertEquals(countEntityOfType(intialResponse, "player"), 1);
        assertEquals(countEntityOfType(intialResponse, "exit"), 1);
        assertEquals(countEntityOfType(intialResponse, "boulder"), 2);

        DungeonResponse move = dmc.tick(Direction.RIGHT);


        EntityResponse expectedBoulder = new EntityResponse("1", "boulder", new Position (2,1), false);
        EntityResponse expectedPlayer = new EntityResponse("2", "player", new Position (1,1), false);

        //PLAYER    BOULDER     BOULDER

        EntityResponse actualBoulder = getEntities(move, "boulder").get(0);

        assertEquals(expectedBoulder.getPosition(), actualBoulder.getPosition());
        assertEquals(expectedPlayer.getPosition(), getPlayer(move).get().getPosition());


    }

    @Test
    @DisplayName("Test the player cannot move with one boulder one wall in a line")
    public void testCannotMoveOneBoulderOneWall() {

        DungeonManiaController dmc = new DungeonManiaController();

        //PLAYER    BOULDER     WALL

        DungeonResponse intialResponse = dmc.newGame("d_testBoulderWallBlocked", "c_battleTests_basicMercenaryMercenaryDies");


        DungeonResponse move = dmc.tick(Direction.RIGHT);


        EntityResponse expectedBoulder = new EntityResponse("1", "boulder", new Position (2,1), false);
        EntityResponse expectedPlayer = new EntityResponse("2", "player", new Position (1,1), false);

        EntityResponse actualBoulder = getEntities(move, "boulder").get(0);

        assertEquals(expectedBoulder.getPosition(), actualBoulder.getPosition());
        assertEquals(expectedPlayer.getPosition(), getPlayer(move).get().getPosition());


    }

    @Test
    @DisplayName("Test the player cannot move with one boulder one locked door in a line")
    public void testCannotMoveOneBoulderOneLockedDoor() {

        DungeonManiaController dmc = new DungeonManiaController();

        //PLAYER    BOULDER     DOOR

        DungeonResponse intialResponse = dmc.newGame("d_testBoulderDoorBlocked", "c_battleTests_basicMercenaryMercenaryDies");

        DungeonResponse move = dmc.tick(Direction.RIGHT);


        EntityResponse expectedBoulder = new EntityResponse("1", "boulder", new Position (2,1), false);
        EntityResponse expectedPlayer = new EntityResponse("2", "player", new Position (1,1), false);

        EntityResponse actualBoulder = getEntities(move, "boulder").get(0);

        assertEquals(expectedBoulder.getPosition(), actualBoulder.getPosition());
        assertEquals(expectedPlayer.getPosition(), getPlayer(move).get().getPosition());


    }

    @Test
    @DisplayName("Test player blocked by wall")
    public void testWallBlocksPlayer() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_battleTest_basicMercenary", "c_movementTest_testMovementDown");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
 
        // create the expected result
        // player does not move as it is blocked by a wall 
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(0, 1), false);
 
        // move player downward
        DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
 
        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
    }



    @Test
    @DisplayName("Test player not in radius for destroy spawner")
    public void testplayerDestroySpawnerFail() throws IllegalArgumentException, InvalidActionException {

        DungeonManiaController dmc = new DungeonManiaController();
        // bribe radius and amount are 1
        DungeonResponse intialResponse = dmc.newGame("d_testDestroySpawner", "simple");

        DungeonResponse move = dmc.tick(Direction.DOWN);
        assertEquals(1, getInventory(move, "sword").size());
        move = dmc.tick(Direction.DOWN);
        move = dmc.tick(Direction.DOWN);
        move = dmc.tick(Direction.DOWN);
        move = dmc.tick(Direction.DOWN);
        move = dmc.tick(Direction.DOWN);
        String sId = getEntities(move, "zombie_toast_spawner").get(0).getId();
        assertThrows(InvalidActionException.class, () -> dmc.interact(sId));

    }

    @Test
    @DisplayName("Test player not have sword for destroy spawner")
    public void testplayerDestroySpawnerFailWithoutWeapon() throws IllegalArgumentException, InvalidActionException {

        DungeonManiaController dmc = new DungeonManiaController();
        // bribe radius and amount are 1
        DungeonResponse intialResponse = dmc.newGame("d_testDestroySpawner", "simple");

        DungeonResponse move = dmc.tick(Direction.RIGHT);
        move = dmc.tick(Direction.DOWN);
        move = dmc.tick(Direction.DOWN);

        assertEquals(0, getInventory(move, "sword").size());
        String sId = getEntities(move, "zombie_toast_spawner").get(0).getId();
        assertThrows(InvalidActionException.class, () -> dmc.interact(sId));


    }

    @Test
    @DisplayName("Test player success destroy spawner")
    public void testplayerDestroySpawnerSuccess() throws IllegalArgumentException, InvalidActionException {

        DungeonManiaController dmc = new DungeonManiaController();
        // bribe radius and amount are 1
        DungeonResponse intialResponse = dmc.newGame("d_testDestroySpawner", "simple");

        DungeonResponse move = dmc.tick(Direction.DOWN);

        assertEquals(1, getInventory(move, "sword").size());
        String sId = getEntities(move, "zombie_toast_spawner").get(0).getId();
        assertDoesNotThrow(() -> dmc.interact(sId));

    }
    
    @Test
    @DisplayName("Test player build exception")
    public void testplayerbuildableInvalid() throws IllegalArgumentException, InvalidActionException {

        DungeonManiaController dmc = new DungeonManiaController();
        // bribe radius and amount are 1
        DungeonResponse intialResponse = dmc.newGame("d_testDestroySpawner", "simple");

        DungeonResponse move = dmc.tick(Direction.DOWN);

        assertEquals(1, getInventory(move, "sword").size());
        String sId = getEntities(move, "zombie_toast_spawner").get(0).getId();
        assertThrows(IllegalArgumentException.class, () -> dmc.build(sId));

    }

    @Test
    @DisplayName("Test player build exception")
    public void testplayerbuildableItemNotEnough() throws IllegalArgumentException, InvalidActionException {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse initialResponse = dmc.newGame("build_shield", "c_battleTests_basicMercenaryMercenaryDies");

        EntityResponse initPlayer = getPlayer(initialResponse).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 1), false);

        // move player downward
        DungeonResponse actualDungeonRes = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = getPlayer(actualDungeonRes).get();

    
        assertThrows(InvalidActionException.class, () -> dmc.build("shield"));

    }

}


