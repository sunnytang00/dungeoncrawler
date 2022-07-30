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

public class AssassinTest {

    @Test
    @DisplayName("Test vicious Assassin follow player")
    public void testAsnFollowPlayer() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse intialResponse = dmc.newGame("d_basicAssassin", "M3_config");

        DungeonResponse move = dmc.tick(Direction.LEFT);
        Position playerPos = getEntities(move, "player").get(0).getPosition();
        Position asnPos = getEntities(move, "assassin").get(0).getPosition();
        
        assertEquals(playerPos.getX(), 0);
        assertEquals(playerPos.getY(), 1);
        assertEquals(asnPos.getX(), 7);
        assertEquals(asnPos.getY(), 1);

        move = dmc.tick(Direction.RIGHT);
        playerPos = getEntities(move, "player").get(0).getPosition();
        asnPos = getEntities(move, "assassin").get(0).getPosition();

        assertEquals(playerPos.getX(), 1);
        assertEquals(playerPos.getY(), 1);
        assertEquals(asnPos.getX(), 6);
        assertEquals(asnPos.getY(), 1);

        move = dmc.tick(Direction.UP);
        playerPos = getEntities(move, "player").get(0).getPosition();
        asnPos = getEntities(move, "assassin").get(0).getPosition();
        assertEquals(playerPos.getX(), 1);
        assertEquals(playerPos.getY(), 0);
        assertEquals(asnPos.getX(), 5);
        assertEquals(asnPos.getY(), 1);

        move = dmc.tick(Direction.UP);
        playerPos = getEntities(move, "player").get(0).getPosition();
        asnPos = getEntities(move, "assassin").get(0).getPosition();
        assertEquals(playerPos.getX(), 1);
        assertEquals(playerPos.getY(), -1);
        assertEquals(asnPos.getX(), 4);
        assertEquals(asnPos.getY(), 1);


    }

    @Test
    @DisplayName("Test assassin blocked by wall")
    public void testassasinBlockedbyWall() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse intialResponse = dmc.newGame("d_basicAssassin", "M3_config");

        DungeonResponse move = dmc.tick(Direction.DOWN);
        Position playerPos = getEntities(move, "player").get(0).getPosition();
        Position asnPos = getEntities(move, "assassin").get(0).getPosition();
        
        assertEquals(playerPos.getX(), 1);
        assertEquals(playerPos.getY(), 2);
        assertEquals(asnPos.getX(), 7);
        assertEquals(asnPos.getY(), 1);

        move = dmc.tick(Direction.DOWN);
        playerPos = getEntities(move, "player").get(0).getPosition();
        asnPos = getEntities(move, "assassin").get(0).getPosition();

        assertEquals(playerPos.getX(), 1);
        assertEquals(playerPos.getY(), 3);
        assertEquals(asnPos.getX(), 6);
        assertEquals(asnPos.getY(), 1);

        move = dmc.tick(Direction.RIGHT);
        playerPos = getEntities(move, "player").get(0).getPosition();
        asnPos = getEntities(move, "assassin").get(0).getPosition();
        assertEquals(playerPos.getX(), 2);
        assertEquals(playerPos.getY(), 3);
        assertEquals(asnPos.getX(), 5);
        assertEquals(asnPos.getY(), 1);

        move = dmc.tick(Direction.DOWN);
        playerPos = getEntities(move, "player").get(0).getPosition();
        asnPos = getEntities(move, "assassin").get(0).getPosition();
        assertEquals(playerPos.getX(), 2);
        assertEquals(playerPos.getY(), 4);
        assertEquals(asnPos.getX(), 4);
        assertEquals(asnPos.getY(), 1);

    }

    @Test
    @DisplayName("Test assassin move in advance map")
    public void testmercAdvanceMap() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_basicAssassin", "M3_config");
        Position prevPlayer = getEntities(initDungonRes, "player").get(0).getPosition();
        Position prevMerc = getEntities(initDungonRes, "assassin").get(0).getPosition();
        double disPrev = prevMerc.getDistanceBetween(prevPlayer);
        DungeonResponse move = dmc.tick(Direction.RIGHT);
        Position playerPos = getEntities(move, "player").get(0).getPosition();
        Position asnPos = getEntities(move, "assassin").get(0).getPosition();
        double currDis = asnPos.getDistanceBetween(playerPos);
        assertEquals(playerPos.getX(), 2);
        assertEquals(playerPos.getY(), 1);
        assertTrue(disPrev > currDis);

        disPrev = asnPos.getDistanceBetween(playerPos);

        move = dmc.tick(Direction.RIGHT);
        playerPos = getEntities(move, "player").get(0).getPosition();
        asnPos = getEntities(move, "assassin").get(0).getPosition();
        currDis = asnPos.getDistanceBetween(playerPos);

        disPrev = asnPos.getDistanceBetween(playerPos);

        move = dmc.tick(Direction.RIGHT);
        playerPos = getEntities(move, "player").get(0).getPosition();
        asnPos = getEntities(move, "assassin").get(0).getPosition();
        currDis = asnPos.getDistanceBetween(playerPos);

        disPrev = asnPos.getDistanceBetween(playerPos);

        move = dmc.tick(Direction.DOWN);
        playerPos = getEntities(move, "player").get(0).getPosition();
        asnPos = getEntities(move, "assassin").get(0).getPosition();
        currDis = asnPos.getDistanceBetween(playerPos);
        assertTrue(disPrev >= currDis);
        
        boolean reached = false;
        for(int i = 0; i < 4; i++) {
            disPrev = asnPos.getDistanceBetween(playerPos);

            move = dmc.tick(Direction.RIGHT);
            playerPos = getEntities(move, "player").get(0).getPosition();
            
            if (getEntities(move, "assassin") == null || getEntities(move, "assassin").size() == 0) {
                reached = true;
                break;
            } else {
                asnPos = getEntities(move, "assassin").get(0).getPosition();
            }
            currDis = asnPos.getDistanceBetween(playerPos);
            disPrev = asnPos.getDistanceBetween(playerPos);
        }

        // assertTrue(reached);

    }

    @Test
    @DisplayName("Test assassin run away when player in invincible state")
    public void testAssassinaffectedbyInvincible() throws IllegalArgumentException, InvalidActionException  {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse intialResponse = dmc.newGame("d_asnTest_invincibleEffect", "c_testSpawning_1");

        DungeonResponse move = dmc.tick(Direction.RIGHT);
        ItemResponse res = getInventory(move, "invincibility_potion").get(0);
        DungeonResponse consume = dmc.tick(res.getId());

        assertEquals(0, getInventory(consume, "invincibility_potion").size());
        assertEquals(0, getEntities(consume, "invincibility_potion").size());

        assertEquals(1, countEntityOfType(consume, "assassin"));

        move = dmc.tick(Direction.DOWN);
        Position prevPos = getEntities(move, "assassin").get(0).getPosition();
        Position prevPlayer = getEntities(move, "player").get(0).getPosition();
        // Assert Movement of zombie away from player

            
        move = dmc.tick(Direction.RIGHT);
        Position playerPosition = getEntities(move, "player").get(0).getPosition();
        Position pos = getEntities(move, "assassin").get(0).getPosition();
        double diff = pos.getDistanceBetween(playerPosition) - prevPos.getDistanceBetween(playerPosition);

        assertTrue(diff >= 0);

        prevPos = pos;
        prevPlayer = playerPosition;

        move = dmc.tick(Direction.RIGHT);
        playerPosition = getEntities(move, "player").get(0).getPosition();
        pos = getEntities(move, "assassin").get(0).getPosition();
        diff = pos.getDistanceBetween(prevPlayer) - prevPos.getDistanceBetween(playerPosition);
        assertTrue(diff >= 0);

        prevPos = pos;
        prevPlayer = playerPosition;

        move = dmc.tick(Direction.RIGHT);
        playerPosition = getEntities(move, "player").get(0).getPosition();
        pos = getEntities(move, "assassin").get(0).getPosition();
        diff = pos.getDistanceBetween(prevPlayer) - prevPos.getDistanceBetween(playerPosition);
        assertTrue(diff >= 0);

        move = dmc.tick(Direction.UP);
        playerPosition = getEntities(move, "player").get(0).getPosition();
        pos = getEntities(move, "assassin").get(0).getPosition();
        diff = pos.getDistanceBetween(prevPlayer) - prevPos.getDistanceBetween(playerPosition);
        assertTrue(diff >= 0);
    }

    @Test
    @DisplayName("Test assassin behavior when player in invisible state - not in radius to see and move towards player")
    public void testasnaffectedbyInvisibleNotInRadius() throws IllegalArgumentException, InvalidActionException  {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse intialResponse = dmc.newGame("d_asnTest_invincibleEffect", "c_testSpawning_1");

        DungeonResponse move = dmc.tick(Direction.RIGHT);
        ItemResponse res = getInventory(move, "invincibility_potion").get(0);
        DungeonResponse consume = dmc.tick(res.getId());

        assertEquals(0, getInventory(consume, "invincibility_potion").size());
        assertEquals(0, getEntities(consume, "invincibility_potion").size());

        assertEquals(1, countEntityOfType(consume, "assassin"));

        move = dmc.tick(Direction.RIGHT);
        Position mercPrev = getEntities(move, "assassin").get(0).getPosition();
        res = getInventory(move, "invisibility_potion").get(0);
        consume = dmc.tick(res.getId());

        assertEquals(0, getInventory(consume, "invincibility_potion").size());
        assertEquals(0, getEntities(consume, "invincibility_potion").size());

        Position playerP = getEntities(consume, "player").get(0).getPosition();
        Position mercP = getEntities(consume, "assassin").get(0).getPosition();

        List<Position> positions = mercPrev.getCardinallyAdjacentPositions();
        assertTrue(positions.contains(mercP));
        
    }

    @Test
    @DisplayName("Test assassin behavior when player in invisible state - in radius to see and move towards player")
    public void testasnaffectedbyInvisibleInRadius() throws IllegalArgumentException, InvalidActionException  {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse intialResponse = dmc.newGame("d_asnTest_invincibleEffect", "c_testSpawning_2");

        DungeonResponse move = dmc.tick(Direction.RIGHT);
        ItemResponse res = getInventory(move, "invincibility_potion").get(0);
        DungeonResponse consume = dmc.tick(res.getId());

        assertEquals(0, getInventory(consume, "invincibility_potion").size());
        assertEquals(0, getEntities(consume, "invincibility_potion").size());

        assertEquals(1, countEntityOfType(consume, "assassin"));

        move = dmc.tick(Direction.RIGHT);
        // Position playerPrev = getEntities(consume, "player").get(0).getPosition();
        Position mercPrev = getEntities(move, "assassin").get(0).getPosition();
        res = getInventory(move, "invisibility_potion").get(0);
        consume = dmc.tick(res.getId());

        assertEquals(0, getInventory(consume, "invincibility_potion").size());
        assertEquals(0, getEntities(consume, "invincibility_potion").size());

        Position playerP = getEntities(consume, "player").get(0).getPosition();
        Position mercP = getEntities(consume, "assassin").get(0).getPosition();

        double currD = mercP.getDistanceBetween(playerP);
        double prevD = mercPrev.getDistanceBetween(playerP);
        assertTrue(currD <= prevD);
        
    }

    @Test
    @DisplayName("Test assassin not in bribe radius")
    public void testAsnNotInRadius() throws IllegalArgumentException, InvalidActionException {

        DungeonManiaController dmc = new DungeonManiaController();
        // bribe radius and amount are 1
        DungeonResponse intialResponse = dmc.newGame("d_basicAssassin", "M3_config");

        DungeonResponse move = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(move, "treasure").size());
        String asnId = getEntities(move, "assassin").get(0).getId();
        assertThrows(InvalidActionException.class, () -> dmc.interact(asnId));
        


    }

    @Test
    @DisplayName("Test assassin could not be bribed with not enough treasure")
    public void testAssassinBribeAmout() throws IllegalArgumentException, InvalidActionException {

        DungeonManiaController dmc = new DungeonManiaController();
        // bribe radius and amount are 1
        DungeonResponse intialResponse = dmc.newGame("d_basicAssassin", "M3_config");

        DungeonResponse move = dmc.tick(Direction.UP);
        move = dmc.tick(Direction.DOWN);
        move = dmc.tick(Direction.UP);
        move = dmc.tick(Direction.DOWN);
        move = dmc.tick(Direction.UP);
        move = dmc.tick(Direction.DOWN);
        move = dmc.tick(Direction.UP);
        move = dmc.tick(Direction.LEFT);
        
        
        Position mercP = getEntities(move, "assassin").get(0).getPosition();
        Position playerP = getEntities(move, "player").get(0).getPosition();
        if (mercP.getDistanceBetween(playerP) == 1) {
            assertEquals(0, getInventory(move, "treasure").size());
            String asnId = getEntities(move, "assassin").get(0).getId();
            assertThrows(InvalidActionException.class, () -> dmc.interact(asnId));
        }

    }

    @Test
    @DisplayName("Test assassin can be bribed with certain fail rate")
    public void testAssassinBribeSuccess() throws IllegalArgumentException, InvalidActionException {
        int total = 0;
        int fail = 0;

        for (int i = 0; i < 1000; i++) {
            
            DungeonManiaController dmc = new DungeonManiaController();
            // bribe radius and amount are 1
            DungeonResponse intialResponse = dmc.newGame("d_basicAssassin", "M3_config");

            DungeonResponse move = dmc.tick(Direction.RIGHT);
            move = dmc.tick(Direction.RIGHT);
            move = dmc.tick(Direction.RIGHT);
            assertEquals(3, getInventory(move, "treasure").size());
            
            Position mercP = getEntities(move, "assassin").get(0).getPosition();
            Position playerP = getEntities(move, "player").get(0).getPosition();
            if (mercP.getDistanceBetween(playerP) == 1) {
                
                assertEquals(3, getInventory(move, "treasure").size());
                String mercId = getEntities(move, "assassin").get(0).getId();
                assertDoesNotThrow(() -> dmc.interact(mercId));

                move = dmc.tick(Direction.UP);
                boolean curr = getEntities(move, "assassin").get(0).isInteractable();
                if (curr == true) {
                    fail += 1;
                }
                total += 1;
                    
            }

        }
        double ratio = (double) fail / total;

        assertEquals(0.3, ratio, 0.1);
    }

    @Test
    @DisplayName("Test ally do not run away at invincible state")
    public void testBribedAsnNotRunAway() throws IllegalArgumentException, InvalidActionException {

        DungeonManiaController dmc = new DungeonManiaController();
        // bribe radius and amount are 1
        DungeonResponse intialResponse = dmc.newGame("d_allyNotRunAwayAsn", "M3_config");

        DungeonResponse move = dmc.tick(Direction.RIGHT);
        move = dmc.tick(Direction.RIGHT);
        move = dmc.tick(Direction.RIGHT);
        assertEquals(3, getInventory(move, "treasure").size());
        assertEquals(1, getInventory(move, "invincibility_potion").size());
        
        Position mercP = getEntities(move, "assassin").get(0).getPosition();
        Position playerP = getEntities(move, "player").get(0).getPosition();
        if (mercP.getDistanceBetween(playerP) == 1) {
            
            assertEquals(3, getInventory(move, "treasure").size());
            String mercId = getEntities(move, "assassin").get(0).getId();
            assertDoesNotThrow(() -> dmc.interact(mercId));
            
        }

        move = dmc.tick(Direction.UP);
        boolean curr = getEntities(move, "assassin").get(0).isInteractable();
        
        if (curr == true) {
            return;
        }
        mercP = getEntities(move, "assassin").get(0).getPosition();
        Position newP = getEntities(move, "player").get(0).getPosition();
        assertEquals(2, getInventory(move, "treasure").size());
        assertEquals(mercP, playerP);

        
        ItemResponse res = getInventory(move, "invincibility_potion").get(0);
        DungeonResponse consume = dmc.tick(res.getId());

        assertEquals(0, getInventory(consume, "invincibility_potion").size());
        assertEquals(0, getEntities(consume, "invincibility_potion").size());
        newP = getEntities(consume, "player").get(0).getPosition();

        move = dmc.tick(Direction.UP);
        mercP = getEntities(move, "assassin").get(0).getPosition();
        
        assertEquals(mercP, newP);
    }

    @Test
    @DisplayName("Test mind control assassin")
    public void TestmindControlAssassin() throws IllegalArgumentException, InvalidActionException {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse initialResponse = dmc.newGame("test_build_sceptre_mindcontrolasn", "c_testConfigSceptre");
        
        DungeonResponse move = dmc.tick(Direction.RIGHT);
        for (int i = 0; i < 3; i++) {//move right 3 times, 0 1 2
            move = dmc.tick(Direction.RIGHT);
        }
        String mercId = getEntities(move, "assassin").get(0).getId();
        assertThrows(InvalidActionException.class, () -> dmc.interact(mercId));
        DungeonResponse build = dmc.build("sceptre");
        assertEquals(1, getInventory(build, "sceptre").size());
        assertEquals(0, getInventory(build, "sun_stone").size());

        assertTrue(getEntities(build, "assassin").get(0).isInteractable());

        DungeonResponse interact = dmc.interact(mercId);
        // assume sceptre can be consumed like potion and can only be used once
        assertEquals(0, getInventory(interact, "sceptre").size());

        for (int i = 0; i < 4; i++) {//move right 2 times
            move = dmc.tick(Direction.RIGHT);
            assertFalse(getEntities(move, "assassin").get(0).isInteractable());
        }

        move = dmc.tick(Direction.RIGHT);
        assertTrue(getEntities(move, "assassin").get(0).isInteractable());
        move = dmc.tick(Direction.RIGHT);
        assertTrue(getEntities(move, "assassin").get(0).isInteractable());
        
    }
    

    @Test
    @DisplayName("Test assassin bribe fail no refund")
    public void testAssassinBribeFailNoRefund() throws IllegalArgumentException, InvalidActionException {

        for (int i = 0; i < 50; i++) {
            
            DungeonManiaController dmc = new DungeonManiaController();
            // bribe radius and amount are 1
            DungeonResponse intialResponse = dmc.newGame("d_basicAssassin", "M3_config");

            DungeonResponse move = dmc.tick(Direction.RIGHT);
            move = dmc.tick(Direction.RIGHT);
            move = dmc.tick(Direction.RIGHT);
            assertEquals(3, getInventory(move, "treasure").size());
            
            Position mercP = getEntities(move, "assassin").get(0).getPosition();
            assertTrue(getEntities(move, "assassin").get(0).isInteractable());
            Position playerP = getEntities(move, "player").get(0).getPosition();
            if (mercP.getDistanceBetween(playerP) == 1) {
                
                assertEquals(3, getInventory(move, "treasure").size());
                String mercId = getEntities(move, "assassin").get(0).getId();
                assertDoesNotThrow(() -> dmc.interact(mercId));

                move = dmc.tick(Direction.UP);
                boolean curr = getEntities(move, "assassin").get(0).isInteractable();
                if (curr == true) {
                    System.out.println("failed to bribe");
                    assertEquals(2, getInventory(move, "treasure").size());
                }
                    
            }

        }
    }
}
