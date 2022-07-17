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


public class MercenaryTest {

    @Test
    @DisplayName("Test vicious merc follow player")
    public void testMercFollowPlayer() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse intialResponse = dmc.newGame("mercenary", "simple");

        DungeonResponse move = dmc.tick(Direction.LEFT);
        Position playerPos = getEntities(move, "player").get(0).getPosition();
        Position mercPos = getEntities(move, "mercenary").get(0).getPosition();
        
        assertEquals(playerPos.getX(), 0);
        assertEquals(playerPos.getY(), 1);
        assertEquals(mercPos.getX(), 7);
        assertEquals(mercPos.getY(), 1);

        move = dmc.tick(Direction.RIGHT);
        playerPos = getEntities(move, "player").get(0).getPosition();
        mercPos = getEntities(move, "mercenary").get(0).getPosition();

        assertEquals(playerPos.getX(), 1);
        assertEquals(playerPos.getY(), 1);
        assertEquals(mercPos.getX(), 6);
        assertEquals(mercPos.getY(), 1);

        move = dmc.tick(Direction.UP);
        playerPos = getEntities(move, "player").get(0).getPosition();
        mercPos = getEntities(move, "mercenary").get(0).getPosition();
        assertEquals(playerPos.getX(), 1);
        assertEquals(playerPos.getY(), 0);
        assertEquals(mercPos.getX(), 5);
        assertEquals(mercPos.getY(), 1);

        move = dmc.tick(Direction.UP);
        playerPos = getEntities(move, "player").get(0).getPosition();
        mercPos = getEntities(move, "mercenary").get(0).getPosition();
        assertEquals(playerPos.getX(), 1);
        assertEquals(playerPos.getY(), -1);
        assertEquals(mercPos.getX(), 4);
        assertEquals(mercPos.getY(), 1);


    }

    @Test
    @DisplayName("Test merc blocked by wall")
    public void testmercBlockedbyWall() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse intialResponse = dmc.newGame("mercenary", "simple");

        DungeonResponse move = dmc.tick(Direction.DOWN);
        Position playerPos = getEntities(move, "player").get(0).getPosition();
        Position mercPos = getEntities(move, "mercenary").get(0).getPosition();
        
        assertEquals(playerPos.getX(), 1);
        assertEquals(playerPos.getY(), 2);
        assertEquals(mercPos.getX(), 7);
        assertEquals(mercPos.getY(), 1);

        move = dmc.tick(Direction.DOWN);
        playerPos = getEntities(move, "player").get(0).getPosition();
        mercPos = getEntities(move, "mercenary").get(0).getPosition();

        assertEquals(playerPos.getX(), 1);
        assertEquals(playerPos.getY(), 3);
        assertEquals(mercPos.getX(), 6);
        assertEquals(mercPos.getY(), 1);

        move = dmc.tick(Direction.RIGHT);
        playerPos = getEntities(move, "player").get(0).getPosition();
        mercPos = getEntities(move, "mercenary").get(0).getPosition();
        assertEquals(playerPos.getX(), 2);
        assertEquals(playerPos.getY(), 3);
        assertEquals(mercPos.getX(), 5);
        assertEquals(mercPos.getY(), 1);

        move = dmc.tick(Direction.DOWN);
        playerPos = getEntities(move, "player").get(0).getPosition();
        mercPos = getEntities(move, "mercenary").get(0).getPosition();
        assertEquals(playerPos.getX(), 2);
        assertEquals(playerPos.getY(), 4);
        assertEquals(mercPos.getX(), 4);
        assertEquals(mercPos.getY(), 1);

    }

    @Test
    @DisplayName("Test merc move in advance map")
    public void testmercAdvanceMap() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("advanced", "simple");
        Position prevPlayer = getEntities(initDungonRes, "player").get(0).getPosition();
        Position prevMerc = getEntities(initDungonRes, "mercenary").get(0).getPosition();
        double disPrev = prevMerc.getDistanceBetween(prevPlayer);
        DungeonResponse move = dmc.tick(Direction.RIGHT);
        Position playerPos = getEntities(move, "player").get(0).getPosition();
        Position mercPos = getEntities(move, "mercenary").get(0).getPosition();
        double currDis = mercPos.getDistanceBetween(playerPos);
        assertEquals(playerPos.getX(), 2);
        assertEquals(playerPos.getY(), 1);
        assertTrue(disPrev > currDis);

        //System.out.println("Merc" + mercPos + "Player" + playerPos);

        disPrev = mercPos.getDistanceBetween(playerPos);

        move = dmc.tick(Direction.RIGHT);
        playerPos = getEntities(move, "player").get(0).getPosition();
        mercPos = getEntities(move, "mercenary").get(0).getPosition();
        currDis = mercPos.getDistanceBetween(playerPos);

        disPrev = mercPos.getDistanceBetween(playerPos);

        move = dmc.tick(Direction.RIGHT);
        playerPos = getEntities(move, "player").get(0).getPosition();
        mercPos = getEntities(move, "mercenary").get(0).getPosition();
        currDis = mercPos.getDistanceBetween(playerPos);
        

        System.out.println("Merc" + mercPos + "Player" + playerPos);

        disPrev = mercPos.getDistanceBetween(playerPos);

        move = dmc.tick(Direction.DOWN);
        playerPos = getEntities(move, "player").get(0).getPosition();
        mercPos = getEntities(move, "mercenary").get(0).getPosition();
        currDis = mercPos.getDistanceBetween(playerPos);
        assertTrue(disPrev > currDis);

        System.out.println("Merc" + mercPos + "Player" + playerPos);
        
        boolean reached = false;
        for(int i = 0; i < 4; i++) {
            disPrev = mercPos.getDistanceBetween(playerPos);

            move = dmc.tick(Direction.RIGHT);
            playerPos = getEntities(move, "player").get(0).getPosition();
            
            if (getEntities(move, "mercenary") == null || getEntities(move, "mercenary").size() == 0) {
                reached = true;
                break;
            } else {
                mercPos = getEntities(move, "mercenary").get(0).getPosition();
            }
            currDis = mercPos.getDistanceBetween(playerPos);
            disPrev = mercPos.getDistanceBetween(playerPos);
        }

        // assertTrue(reached);

    }

    @Test
    @DisplayName("Test merc run away when player in invincible state")
    public void testMercenaryaffectedbyInvincible() throws IllegalArgumentException, InvalidActionException  {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse intialResponse = dmc.newGame("d_mercTest_invincibleEffect", "c_testSpawning");

        DungeonResponse move = dmc.tick(Direction.RIGHT);
        ItemResponse res = getInventory(move, "invincibility_potion").get(0);
        DungeonResponse consume = dmc.tick(res.getId());

        assertEquals(0, getInventory(consume, "invincibility_potion").size());
        assertEquals(0, getEntities(consume, "invincibility_potion").size());

        assertEquals(1, countEntityOfType(consume, "mercenary"));

        move = dmc.tick(Direction.DOWN);
        Position prevPos = getEntities(move, "mercenary").get(0).getPosition();
        Position prevPlayer = getEntities(move, "player").get(0).getPosition();
        // Assert Movement of zombie away from player

            
        move = dmc.tick(Direction.RIGHT);
        Position playerPosition = getEntities(move, "player").get(0).getPosition();
        Position pos = getEntities(move, "mercenary").get(0).getPosition();
        double diff = pos.getDistanceBetween(prevPlayer) - prevPos.getDistanceBetween(prevPlayer);
        assertTrue(diff >= 0);

        prevPos = pos;
        prevPlayer = playerPosition;

        move = dmc.tick(Direction.RIGHT);
        playerPosition = getEntities(move, "player").get(0).getPosition();
        pos = getEntities(move, "mercenary").get(0).getPosition();
        diff = pos.getDistanceBetween(prevPlayer) - prevPos.getDistanceBetween(prevPlayer);
        assertTrue(diff >= 0);

        prevPos = pos;
        prevPlayer = playerPosition;

        move = dmc.tick(Direction.RIGHT);
        playerPosition = getEntities(move, "player").get(0).getPosition();
        pos = getEntities(move, "mercenary").get(0).getPosition();
        diff = pos.getDistanceBetween(prevPlayer) - prevPos.getDistanceBetween(prevPlayer);
        assertTrue(diff >= 0);

        move = dmc.tick(Direction.UP);
        playerPosition = getEntities(move, "player").get(0).getPosition();
        pos = getEntities(move, "mercenary").get(0).getPosition();
        diff = pos.getDistanceBetween(prevPlayer) - prevPos.getDistanceBetween(prevPlayer);
        assertTrue(diff >= 0);
    }

    @Test
    @DisplayName("Test merc run away when player in invincible state")
    public void testMercenaryaffectedbyInvisible() throws IllegalArgumentException, InvalidActionException  {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse intialResponse = dmc.newGame("d_mercTest_invincibleEffect", "simple");

        DungeonResponse move = dmc.tick(Direction.RIGHT);
        ItemResponse res = getInventory(move, "invincibility_potion").get(0);
        DungeonResponse consume = dmc.tick(res.getId());

        assertEquals(0, getInventory(consume, "invincibility_potion").size());
        assertEquals(0, getEntities(consume, "invincibility_potion").size());

        assertEquals(1, countEntityOfType(consume, "mercenary"));

        move = dmc.tick(Direction.RIGHT);
        Position mercPrev = getEntities(move, "mercenary").get(0).getPosition();
        res = getInventory(move, "invisibility_potion").get(0);
        consume = dmc.tick(res.getId());

        assertEquals(0, getInventory(consume, "invincibility_potion").size());
        assertEquals(0, getEntities(consume, "invincibility_potion").size());

        Position playerP = getEntities(consume, "player").get(0).getPosition();
        Position mercP = getEntities(consume, "mercenary").get(0).getPosition();

        List<Position> positions = mercPrev.getCardinallyAdjacentPositions();
        assertTrue(positions.contains(mercP));

        
    }

    @Test
    @DisplayName("Test merc not in bribe radius")
    public void testMercNotInRadius() throws IllegalArgumentException, InvalidActionException {

        DungeonManiaController dmc = new DungeonManiaController();
        // bribe radius and amount are 1
        DungeonResponse intialResponse = dmc.newGame("mercenary", "simple");

        DungeonResponse move = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(move, "treasure").size());
        String mercId = getEntities(move, "mercenary").get(0).getId();
        assertThrows(InvalidActionException.class, () -> dmc.interact(mercId));
        


    }

    @Test
    @DisplayName("Test merc could not be bribed with not enough treasure")
    public void testMercBribeAmout() throws IllegalArgumentException, InvalidActionException {

        DungeonManiaController dmc = new DungeonManiaController();
        // bribe radius and amount are 1
        DungeonResponse intialResponse = dmc.newGame("mercenary", "simple");

        DungeonResponse move = dmc.tick(Direction.UP);
        move = dmc.tick(Direction.DOWN);
        move = dmc.tick(Direction.UP);
        move = dmc.tick(Direction.DOWN);
        move = dmc.tick(Direction.UP);
        move = dmc.tick(Direction.DOWN);
        move = dmc.tick(Direction.UP);
        move = dmc.tick(Direction.LEFT);
        
        
        Position mercP = getEntities(move, "mercenary").get(0).getPosition();
        Position playerP = getEntities(move, "player").get(0).getPosition();
        if (mercP.getDistanceBetween(playerP) == 1) {
            // System.out.println("P" + mercP + " " + playerP);
            assertEquals(0, getInventory(move, "treasure").size());
            String mercId = getEntities(move, "mercenary").get(0).getId();
            assertThrows(InvalidActionException.class, () -> dmc.interact(mercId));
        }

    }

    @Test
    @DisplayName("Test merc can be bribed")
    public void testMercBribeSuccess() throws IllegalArgumentException, InvalidActionException {

        DungeonManiaController dmc = new DungeonManiaController();
        // bribe radius and amount are 1
        DungeonResponse intialResponse = dmc.newGame("mercenary", "simple");

        DungeonResponse move = dmc.tick(Direction.RIGHT);
        move = dmc.tick(Direction.RIGHT);
        move = dmc.tick(Direction.RIGHT);
        assertEquals(3, getInventory(move, "treasure").size());
        
        Position mercP = getEntities(move, "mercenary").get(0).getPosition();
        Position playerP = getEntities(move, "player").get(0).getPosition();
        if (mercP.getDistanceBetween(playerP) == 1) {
            
            assertEquals(3, getInventory(move, "treasure").size());
            String mercId = getEntities(move, "mercenary").get(0).getId();
            assertDoesNotThrow(() -> dmc.interact(mercId));
            // System.out.println("P" + mercP + " " + playerP);
            
        }

        move = dmc.tick(Direction.UP);
        mercP = getEntities(move, "mercenary").get(0).getPosition();
        Position newP = getEntities(move, "player").get(0).getPosition();
        assertEquals(2, getInventory(move, "treasure").size());
        assertEquals(mercP, playerP);

        move = dmc.tick(Direction.UP);
        mercP = getEntities(move, "mercenary").get(0).getPosition();
        assertEquals(mercP, newP);
    }

    @Test
    @DisplayName("Test ally do not run away at invincible state")
    public void testBribedMercNotRunAway() throws IllegalArgumentException, InvalidActionException {

        DungeonManiaController dmc = new DungeonManiaController();
        // bribe radius and amount are 1
        DungeonResponse intialResponse = dmc.newGame("d_allyNotRunAway", "simple");

        DungeonResponse move = dmc.tick(Direction.RIGHT);
        move = dmc.tick(Direction.RIGHT);
        move = dmc.tick(Direction.RIGHT);
        assertEquals(3, getInventory(move, "treasure").size());
        assertEquals(1, getInventory(move, "invincibility_potion").size());
        
        Position mercP = getEntities(move, "mercenary").get(0).getPosition();
        Position playerP = getEntities(move, "player").get(0).getPosition();
        if (mercP.getDistanceBetween(playerP) == 1) {
            
            assertEquals(3, getInventory(move, "treasure").size());
            String mercId = getEntities(move, "mercenary").get(0).getId();
            assertDoesNotThrow(() -> dmc.interact(mercId));
            
        }

        move = dmc.tick(Direction.UP);
        mercP = getEntities(move, "mercenary").get(0).getPosition();
        Position newP = getEntities(move, "player").get(0).getPosition();
        assertEquals(2, getInventory(move, "treasure").size());
        assertEquals(mercP, playerP);

        
        ItemResponse res = getInventory(move, "invincibility_potion").get(0);
        DungeonResponse consume = dmc.tick(res.getId());

        assertEquals(0, getInventory(consume, "invincibility_potion").size());
        assertEquals(0, getEntities(consume, "invincibility_potion").size());
        newP = getEntities(consume, "player").get(0).getPosition();


        move = dmc.tick(Direction.UP);
        mercP = getEntities(move, "mercenary").get(0).getPosition();
        assertEquals(mercP, newP);
    }

}
