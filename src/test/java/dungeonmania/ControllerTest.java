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
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class ControllerTest {
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

    @Test
    @DisplayName("Test tick item id invalid") 
    public void testtickItem() throws InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testMovementDown", "c_movementTest_testMovementDown");
        assertThrows(InvalidActionException.class, () -> dmc.tick("wohoooo"));
        
    }

    @Test
    @DisplayName("Test tick item id empty") 
    public void testtickItemEmpty() throws InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testMovementDown", "c_movementTest_testMovementDown");
        assertThrows(InvalidActionException.class, () -> dmc.tick(""));
        
    }

    @Test
    @DisplayName("Test tick item incorrect type") 
    public void testtickItem2() throws IllegalArgumentException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testMovementDown", "c_movementTest_testMovementDown");
        String playerId = getEntities(initDungonRes, "player").get(0).getId(); 
        assertThrows(InvalidActionException.class, () -> dmc.tick(playerId));
        
    }

    @Test
    @DisplayName("Test interact incorrect type") 
    public void testtickInteract() throws IllegalArgumentException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testMovementDown", "c_movementTest_testMovementDown");
        String playerId = getEntities(initDungonRes, "player").get(0).getId(); 
        assertThrows(IllegalArgumentException.class, () -> dmc.interact(playerId));
        
    }

    @Test
    @DisplayName("Test interact id invalid") 
    public void testInteractInvalid() throws InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testMovementDown", "c_movementTest_testMovementDown");
        assertThrows(InvalidActionException.class, () -> dmc.interact("wohoooo"));
        
    }

    @Test
    @DisplayName("Test interact bribed") 
    public void testInteractBribed() throws InvalidActionException {
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
            
        }
        String mercId = getEntities(move, "mercenary").get(0).getId();
        assertThrows(IllegalArgumentException.class, () -> dmc.interact(mercId));
           
    }

    @Test
    @DisplayName("Test getskin") 
    public void testGetskin() {
        DungeonManiaController dmc = new DungeonManiaController();
        assertEquals(dmc.getSkin(), "default");

        
    }

    @Test
    @DisplayName("Test getLocalisation() ") 
    public void testgetLocalisation()  {
        DungeonManiaController dmc = new DungeonManiaController();
        assertEquals(dmc.getLocalisation() , "en_US");

        
    }

    @Test
    @DisplayName("Test getdungeons ") 
    public void testgetdungeons()  {
        DungeonManiaController dmc = new DungeonManiaController();
        assertTrue(dmc.dungeons() != null);

        
    }

    @Test
    @DisplayName("Test getdungeons ") 
    public void testgetconfigs() {
        DungeonManiaController dmc = new DungeonManiaController();
        assertTrue(dmc.configs() != null);

        
    }

    @Test
    @DisplayName("Test tick item incorrect type") 
    public void testtickItem3() throws IllegalArgumentException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testMovementDown", "c_movementTest_testMovementDown");
        String exitId = getEntities(initDungonRes, "exit").get(0).getId(); 
        assertThrows(InvalidActionException.class, () -> dmc.tick(exitId));
        
    }

    @Test
    @DisplayName("Generate dungeon")
    public void testgenerateDungeon() {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.generateDungeon(1, 1, 10, 10, "M3_config");
    }

    @Test
    @DisplayName("all game")
    public void testallGame() {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.allGames();
    }


    
}