package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.entities.StaticEntities.*;
import dungeonmania.entities.collectableEntities.Key;
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
import static dungeonmania.TestHelpers.assertListAreEqualIgnoringOrder;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class LogicSwitchesTest {

    @Test
    public void testLitUpORBulb() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse initialResponse = dmc.newGame("logicSwitch", "M3_config");

        assertEquals(1, getEntities(initialResponse, "light_bulb_off").size());
        assertEquals(0, getEntities(initialResponse, "light_bulb_on").size());
        DungeonResponse move = dmc.tick(Direction.RIGHT);
        assertEquals(1, getEntities(move, "light_bulb_on").size());

        move = dmc.tick(Direction.UP);
        move = dmc.tick(Direction.RIGHT);
        move = dmc.tick(Direction.DOWN);
        assertEquals(1, getEntities(move, "light_bulb_off").size());
        assertEquals(0, getEntities(move, "light_bulb_on").size());

    }

    @Test
    @DisplayName("activating a wire")
    public void testLogic_activating(){
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse initialResponse = dmc.newGame("logicSwitchWire", "M3_config");

        assertEquals(1, getEntities(initialResponse, "light_bulb_off").size());
        assertEquals(0, getEntities(initialResponse, "light_bulb_on").size());
        DungeonResponse move = dmc.tick(Direction.RIGHT);
        assertEquals(1, getEntities(move, "light_bulb_on").size());

        move = dmc.tick(Direction.UP);
        move = dmc.tick(Direction.RIGHT);
        move = dmc.tick(Direction.DOWN);
        assertEquals(1, getEntities(move, "light_bulb_off").size());
        assertEquals(0, getEntities(move, "light_bulb_on").size());
    }

    @Test
    @DisplayName("open a switch door")
    public void testLogic_switchDoor(){
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse initialResponse = dmc.newGame("logicSwitchDoor", "M3_config");

        assertEquals(0, getEntities(initialResponse, "door_open").size());
        DungeonResponse move = dmc.tick(Direction.RIGHT);
        assertEquals(1, getEntities(move, "door_open").size());

    }

    @Test
    @DisplayName("test logic gate - spec example + explode bomb")
    public void testLogicGate(){
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse initialResponse = dmc.newGame("logicSwitchBomb", "M3_config");

        assertEquals(2, getEntities(initialResponse, "light_bulb_off").size());
        assertEquals(0, getEntities(initialResponse, "light_bulb_on").size());
        DungeonResponse move = dmc.tick(Direction.RIGHT);
        assertEquals(0, getEntities(move, "light_bulb_on").size());
        assertEquals(2, getEntities(move, "wire").size());
        assertEquals(0, getEntities(move, "bomb").size());

    }

    @Test
    @DisplayName("test a circuit with two switches one activated by another switch")
    public void testSwitchActivatebySwitch(){
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse initialResponse = dmc.newGame("logicSwitchCOAND", "M3_config");
        
        assertEquals(0, getEntities(initialResponse, "light_bulb_on").size());
        DungeonResponse move = dmc.tick(Direction.RIGHT);
        assertEquals(1, getEntities(move, "light_bulb_on").size());
        
    }

    @Test
    @DisplayName("test open a switch door")
    public void testOpenSwitchDoorKey() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_DoorsKeysTest_useKeyWalkThroughOpenDoorS", "M3_config");

        // pick up key
        res = dmc.tick(Direction.RIGHT);
        Position pos = getEntities(res, "player").get(0).getPosition();
        assertEquals(1, getInventory(res, "key").size());

        // walk through door and check key is gone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, getInventory(res, "key").size());
        assertNotEquals(pos, getEntities(res, "player").get(0).getPosition());
    }

    @Test
    @DisplayName("test open a switch door with sun stone")
    public void testOpenSwitchDoorSS() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_DoorsKeysTest_useKeyWalkThroughSwitchDoor2", "M3_config");

        // pick up key
        res = dmc.tick(Direction.RIGHT);
        Position pos = getEntities(res, "player").get(0).getPosition();
        assertEquals(1, getInventory(res, "sun_stone").size());

        // walk through door and check key is gone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, getInventory(res, "sun_stone").size());
        assertNotEquals(pos, getEntities(res, "player").get(0).getPosition());
    }

}


