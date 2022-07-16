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
        System.out.println("FINAL GOALS" + getGoals(res));
        assertTrue(getGoals(res).equals(""));

    }
}
