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

import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class PlayerTest {
    @Test
    @DisplayName("Test the player can move UP")
    public void testMovementDown() {
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
        System.out.println(actualPlayer.getPosition());
        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(6, 1), false);


        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
    }
    
    @Test
    @DisplayName("Test if player do not have current key, cound not open and walk through a door")
    public void NoKeyCannotWalkThroughOpenDoor() {

    }


    
  

}


