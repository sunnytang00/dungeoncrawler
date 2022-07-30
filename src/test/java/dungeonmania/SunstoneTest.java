package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.entities.movingEntity.player.Player;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.countEntityOfType;
import static dungeonmania.TestUtils.getInventory;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static dungeonmania.TestUtils.*;
import dungeonmania.goals.*;


public class SunstoneTest {

    @Test
    @DisplayName("Test sunstone can be picked up")
    public void PickUpItemTest() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse initialResponse = dmc.newGame("d_testPickUpSunstone", "M3_config");

        EntityResponse initPlayer = getPlayer(initialResponse).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(0, 1), false);

        // move player downward
        DungeonResponse actualDungeonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = getPlayer(actualDungeonRes).get();

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);

        expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(0, 2), false);

        // move player downward
        actualDungeonRes = dmc.tick(Direction.DOWN);
        actualPlayer = getPlayer(actualDungeonRes).get();

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
        
        expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(0, 3), false);

        // move player downward
        actualDungeonRes = dmc.tick(Direction.DOWN);
        actualPlayer = getPlayer(actualDungeonRes).get();

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);


        assertEquals(getInventory(actualDungeonRes, "key").size(), 1);
        assertEquals(getInventory(actualDungeonRes, "sun_stone").size(), 1);
        assertEquals(getInventory(actualDungeonRes, "invisibility_potion").size(), 1);


        
    }

    @Test
    @DisplayName("Test player can use a sunstone to open and walk through a door")
    public void useKeyWalkThroughOpenDoor() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_DoorsSunstoneTest_WalkThroughOpenDoor", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");

        // pick up sunstone
        res = dmc.tick(Direction.RIGHT);
        Position pos = getEntities(res, "player").get(0).getPosition();
        assertEquals(1, getInventory(res, "sun_stone").size());

        // walk through door and check sunstone still retained
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "sun_stone").size());
        assertNotEquals(pos, getEntities(res, "player").get(0).getPosition());
    }

    @Test
    public void TestBuildShield() throws IllegalArgumentException, InvalidActionException {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse initialResponse = dmc.newGame("build_shield_sunstone", "c_battleTests_basicMercenaryMercenaryDies");

        EntityResponse initPlayer = getPlayer(initialResponse).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 1), false);

        // move player downward
        DungeonResponse actualDungeonRes = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = getPlayer(actualDungeonRes).get();

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);

        expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(3, 1), false);

        // move player downward
        actualDungeonRes = dmc.tick(Direction.RIGHT);
        actualPlayer = getPlayer(actualDungeonRes).get();

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);

        expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(4, 1), false);

        // move player downward
        actualDungeonRes = dmc.tick(Direction.RIGHT);
        actualPlayer = getPlayer(actualDungeonRes).get();

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);


        actualDungeonRes = dmc.build("shield");
        assertEquals(1, getInventory(actualDungeonRes, "sun_stone").size());
        assertEquals(1, getInventory(actualDungeonRes, "shield").size());

    }

    @Test
    @DisplayName("Test collect three treasures to win")
    public void testOnlyTreasure() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_testTreasureSunstone", "c_goalTest");

        assertTrue(getGoals(res).contains(":treasure"));

        // player passes exit
        res = dmc.tick(Direction.RIGHT);
        assertTrue(getGoals(res).contains(":treasure"));
        // player collects 1st treasure
        res = dmc.tick(Direction.RIGHT);
        assertTrue(getGoals(res).contains(":treasure"));
        // player collects 2nd treasure
        res = dmc.tick(Direction.RIGHT);
        assertTrue(getGoals(res).contains(":treasure"));
        // player collects 3rd treasure
        res = dmc.tick(Direction.RIGHT);
        assertFalse(getGoals(res).contains(":treasure"));
        assertTrue(getGoals(res).equals(""));
    }



}
