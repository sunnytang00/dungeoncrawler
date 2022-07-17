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

        // TODO

        // DungeonResponse res = dmc.newGame("d_movementTest_testMovementDown", "c_simpleGoalTest_noGoalConditions");

        // assertTrue(getGoals(res).contains(":exit"));

        // // move player downward - goal still exist
        // res = dmc.tick(Direction.DOWN);
        // assertTrue(getGoals(res).contains(":exit"));

        // // move player downward again - goal achieved
        // res = dmc.tick(Direction.DOWN);
        // assertTrue(getGoals(res).equals(""));
        // assertFalse(getGoals(res).contains(":exit"));
    }
}
