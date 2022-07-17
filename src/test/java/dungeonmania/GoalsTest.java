package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static dungeonmania.TestUtils.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.*;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.goals.*;

public class GoalsTest {
    @Test
    @DisplayName("Simple goal testing only exist with no conditions")
    public void simpleGoalTestWithNoConditions() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_movementTest_testMovementDown", "c_simpleGoalTest_noGoalConditions");

        assertTrue(getGoals(res).contains(":exit"));

        // move player downward - goal still exist
        res = dmc.tick(Direction.DOWN);
        assertTrue(getGoals(res).contains(":exit"));

        // move player downward again - goal achieved
        res = dmc.tick(Direction.DOWN);
        System.out.println("goals" + getGoals(res));
        assertTrue(getGoals(res).equals(""));
        assertFalse(getGoals(res).contains(":exit"));
    }

    @Test
    @DisplayName("Complex goal with AND and OR where goals become unachieved")
    public void goalBecomesUnachieved() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_complexGoalsTest_AndOr", "c_complexGoalsTest_andAll");

        assertTrue(getGoals(res).contains(":exit"));
        assertTrue(getGoals(res).contains(":boulders"));
        assertTrue(getGoals(res).contains(":enemies"));

        // System.out.println("=======================================");
        // System.out.println("Player: " + getEntities(res, "player").get(0).getPosition());
        // System.out.println("Boulder: " + getEntities(res, "boulder").get(0).getPosition());
        // System.out.println("Merc: " + getEntities(res, "mercenary").get(0).getPosition());

        // move boulder onto switch
        res = dmc.tick(Direction.RIGHT);
        assertTrue(getGoals(res).contains(":exit"));
        assertFalse(getGoals(res).contains(":boulders"));
        assertFalse(getGoals(res).contains(":enemies"));

        // move boulder off switch
        res = dmc.tick(Direction.RIGHT);
        assertTrue(getGoals(res).contains(":exit"));
        assertTrue(getGoals(res).contains(":boulders"));
        assertTrue(getGoals(res).contains(":enemies"));

        // kill mercenary
        res = dmc.tick(Direction.RIGHT);
        assertTrue(getGoals(res).contains(":exit"));
        assertFalse(getGoals(res).contains(":boulders"));
        assertFalse(getGoals(res).contains(":enemies"));
       
        // move second boulder onto switch
        res = dmc.tick(Direction.DOWN);
        assertTrue(getGoals(res).contains(":exit"));
        assertFalse(getGoals(res).contains(":boulders"));
        assertFalse(getGoals(res).contains(":enemies"));

        // move second boulder off switch
        res = dmc.tick(Direction.DOWN);
        assertTrue(getGoals(res).contains(":exit"));
        assertFalse(getGoals(res).contains(":boulders"));
        assertFalse(getGoals(res).contains(":enemies"));

        // exit successfully
        res = dmc.tick(Direction.RIGHT);
        assertFalse(getGoals(res).contains(":exit"));
        assertFalse(getGoals(res).contains(":boulders"));
        assertFalse(getGoals(res).contains(":enemies"));
    }

    @Test
    @DisplayName("Test exit must be last")
    public void testExitLast() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_testExitLast", "c_complexGoalsTest_andAll");

        assertTrue(getGoals(res).contains(":exit"));
        assertTrue(getGoals(res).contains(":boulders"));
        assertTrue(getGoals(res).contains(":enemies"));

        for (int i = 0; i < 2; ++i) {
            res = dmc.tick(Direction.DOWN);
            assertTrue(getGoals(res).contains(":exit"));
            assertTrue(getGoals(res).contains(":boulders"));
            assertTrue(getGoals(res).contains(":enemies"));
        }
        
        for (int i = 0; i < 3; ++i) {
            res = dmc.tick(Direction.RIGHT);
            assertTrue(getGoals(res).contains(":exit"));
            assertTrue(getGoals(res).contains(":boulders"));
            assertTrue(getGoals(res).contains(":enemies"));
        }

        // player at exit but does not meet exit condition
        res = dmc.tick(Direction.RIGHT);
        assertTrue(getGoals(res).contains(":exit"));
        assertTrue(getGoals(res).contains(":boulders"));
        assertTrue(getGoals(res).contains(":enemies"));

        // treate exit as an empty box if condition not met 
        res = dmc.tick(Direction.RIGHT);
        assertTrue(getGoals(res).contains(":exit"));
        assertTrue(getGoals(res).contains(":boulders"));
        assertTrue(getGoals(res).contains(":enemies"));
        res = dmc.tick(Direction.LEFT);
        assertTrue(getGoals(res).contains(":exit"));
        assertTrue(getGoals(res).contains(":boulders"));
        assertTrue(getGoals(res).contains(":enemies"));

    }

    @Test
    @DisplayName("Test collect three treasures to win")
    public void testOnlyTreasure() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_testOnlyTreasure", "c_goalTest");

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

    @Test
    @DisplayName("Test kill two enemy to win")
    public void testEnemiesSimpleGoal() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_killTwoEnemiesGoal", "c_goalTest");

        assertTrue(getGoals(res).contains(":enemies"));

        // player kills 1st mercenary
        res = dmc.tick(Direction.RIGHT);
        assertTrue(getGoals(res).contains(":enemies"));

        // player kills 2nd mercenary
        res = dmc.tick(Direction.RIGHT);
        assertFalse(getGoals(res).contains(":enemies"));
        assertTrue(getGoals(res).equals(""));
    }

    // @Test
    // @DisplayName("Test treasure goal unachieved")
    // public void testTreasureUnachieved() {
    //     DungeonManiaController dmc;
    //     dmc = new DungeonManiaController();
    //     DungeonResponse res = dmc.newGame("d_testTreasureGoalUnachieved", "c_complexGoalsTest_andAll");

    //     assertTrue(getGoals(res).contains(":exit"));
    //     assertTrue(getGoals(res).contains(":treasure"));

    //     // player collect treasure
    //     res = dmc.tick(Direction.RIGHT);
    //     System.out.println("Player: " + getEntities(res, "player").get(0).getPosition());
    //     System.out.println("Merc: " + getEntities(res, "mercenary").get(0).getPosition());
    //     assertTrue(getGoals(res).contains(":exit"));
    //     assertFalse(getGoals(res).contains(":treasure"));

    //     // player use treasure to bribe mercenary - treasure unachieved
    //     res = dmc.tick(Direction.RIGHT);
    //     System.out.println("Player: " + getEntities(res, "player").get(0).getPosition());
    //     System.out.println("Merc: " + getEntities(res, "mercenary").get(0).getPosition());
    //     assertTrue(getGoals(res).contains(":exit"));
    //     assertTrue(getGoals(res).contains(":treasure"));

    //     // player collect treasure again
    //     res = dmc.tick(Direction.RIGHT);
    //     System.out.println("Player: " + getEntities(res, "player").get(0).getPosition());
    //     System.out.println("Merc: " + getEntities(res, "mercenary").get(0).getPosition());
    //     assertTrue(getGoals(res).contains(":exit"));
    //     assertFalse(getGoals(res).contains(":treasure"));

    //     // player exit successfully
    //     res = dmc.tick(Direction.RIGHT);
    //     System.out.println("Player: " + getEntities(res, "player").get(0).getPosition());
    //     System.out.println("Merc: " + getEntities(res, "mercenary").get(0).getPosition());
    //     assertTrue(getGoals(res).equals(""));
    //     assertFalse(getGoals(res).contains(":exit"));
    //     assertFalse(getGoals(res).contains(":treasure"));

    // }

    @Test
    @DisplayName("Test merc can be bribed")
    public void testMercBribeSuccess() throws IllegalArgumentException, InvalidActionException {

        DungeonManiaController dmc = new DungeonManiaController();
        // bribe radius and amount are 1
        DungeonResponse intialResponse = dmc.newGame("d_treasureUnachieved", "c_goalTest");

        assertTrue(getGoals(intialResponse).contains(":exit"));
        assertTrue(getGoals(intialResponse).contains(":treasure"));

        DungeonResponse move = dmc.tick(Direction.RIGHT);
        move = dmc.tick(Direction.RIGHT);
        move = dmc.tick(Direction.RIGHT);
        assertEquals(3, getInventory(move, "treasure").size());
        assertTrue(getGoals(intialResponse).contains(":exit"));
        // assertFalse(getGoals(intialResponse).contains(":treasure"));
        
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
        assertTrue(getGoals(intialResponse).contains(":exit"));
        assertTrue(getGoals(intialResponse).contains(":treasure"));

        move = dmc.tick(Direction.UP);
        mercP = getEntities(move, "mercenary").get(0).getPosition();
        assertEquals(mercP, newP);
        assertTrue(getGoals(intialResponse).contains(":exit"));
        assertTrue(getGoals(intialResponse).contains(":treasure"));
    }

}
