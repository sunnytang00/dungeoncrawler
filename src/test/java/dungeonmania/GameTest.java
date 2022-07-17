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

import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class GameTest {
    @Test
    @DisplayName("Test newGame dungeonName exception")
    public void testDungoenName() throws IllegalArgumentException {
        DungeonManiaController dmc = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> dmc.newGame("nonexsit", "c_movementTest_testMovementDown"));
        
    }

    @Test
    @DisplayName("Test newGame dungeonName exception")
    public void testConfigName() throws IllegalArgumentException {
        DungeonManiaController dmc = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> dmc.newGame("d_DoorsKeysTest_useKeyWalkThroughOpenDoor", "whatever"));
        
    }
    
}