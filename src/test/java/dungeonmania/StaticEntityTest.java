package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import dungeonmania.StaticEntities.*;
import dungeonmania.entities.collectableEntities.Key;
import dungeonmania.movingEntity.Player;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.countEntityOfType;

public class StaticEntityTest {

    //These tests will need to call the controller class and get the dungeonresponse from there, when we have implemented the jsonreader for dungeon.
    //Purely sanity tests for the time being

    @Test
    public void SimpleWallTest() {

        List<Entity> entities = new ArrayList<Entity>();
        entities.add(new Wall("wall", new Position(0, 1)));
        //Convert list to a list of entity responses and pass into dungeonresponse
        List<EntityResponse> actual = entities.stream().map(Entity::getEntityResponse).collect(Collectors.toList());
        DungeonResponse dunres = new DungeonResponse("dungeonTestId", "whatever", actual, null, null, null,null);
        //Create a dungeon response and check getentities returns the wall
        List<EntityResponse> expected = getEntities(dunres, "wall");
        assertEquals(expected, actual);

    }

    @Test
    public void WallAndExitTest() {

        List<Entity> entities = new ArrayList<Entity>();
        entities.add(new Wall("wall", new Position(0, 1)));
        entities.add(new Exit("exit", new Position(0, 1)));
        //Convert list to a list of entity responses and pass into dungeonresponse
        List<EntityResponse> actual = entities.stream().map(Entity::getEntityResponse).collect(Collectors.toList());
        DungeonResponse dunres = new DungeonResponse("dungeonTestId", "whatever", actual, null, null, null,null);
        //Create a dungeon response and check getentities returns the wall + exit
        List<EntityResponse> expected1 = getEntities(dunres, "wall");
        List<EntityResponse> expected2 = getEntities(dunres, "exit");
        List<EntityResponse> expected = Stream.concat(expected1.stream(), expected2.stream()).collect(Collectors.toList());
        assertEquals(expected, actual);

    }

    @Test
    public void CheckExitIsTraversable() {
        Exit exit = new Exit("exit", new Position(0, 0));
        assertEquals(true, exit.isTraversable());
    }

    @Test
    public void BoulderTest() {

        Boulder boulder = new Boulder("boulder", new Position(0, 0));

        boulder.move(Direction.UP);
        assertEquals(boulder.getPosition(), new Position(0, -1));
        boulder.move(Direction.LEFT);
        assertEquals(boulder.getPosition(), new Position(-1, -1));

    }
       
    @Test 
    public void FloorSwitchTest() {

        FloorSwitch floorswitch = new FloorSwitch("floorswitch", new Position(0, 0));

        assertEquals(floorswitch.isTriggered(), false);
        floorswitch.switchState();
        assertEquals(floorswitch.isTriggered(), true);
        floorswitch.switchState();
        assertEquals(floorswitch.isTriggered(), false);

    }

    @Test
    public void TestPortalLinking() {

        ArrayList<Entity> entities = new ArrayList<Entity>();

        Portal portal1 = new Portal("portal", new Position(0, 0), "blue");
        Portal portal2 = new Portal("portal", new Position(3, 3), "blue");
        Portal portal3 = new Portal("portal", new Position(-2, -2), "red");
        Portal portal4 = new Portal("portal", new Position(-5, -5), "red");

        entities.add(portal1);
        entities.add(portal2);
        entities.add(portal3);
        entities.add(portal4);

        //For each entity in the entities array
        for (Entity entity : entities) {
            
            if (entity instanceof Portal) {
                //Cast the entity to type portal
                Portal portal = (Portal) entity;
                //Link portal
                portal.linkPortals(entities);
            }
        }

        //Checking if linked correctly
        assertEquals(portal2, portal1.getPair());
        assertEquals(portal1, portal2.getPair());
        assertEquals(portal4, portal3.getPair());
        assertEquals(portal3, portal4.getPair());

        assertEquals("portal_blue", portal1.getType());
        assertEquals("portal_blue", portal2.getType());
        assertEquals("portal_red", portal3.getType());
        assertEquals("portal_red", portal4.getType());

    }
    
    //Begin testing with json and actual class
    @Test
    public void TestEntityCreation() {

        DungeonManiaController dmc = new DungeonManiaController();
        // List<EntityResponse> actual = new ArrayList<EntityResponse>();

        // Player player = new Player("player", new Position(1,1), false);
        // Key key = new Key("key", new Position(2,1), 1);
        // Door door = new Door("door", new Position(3,1), 1);
        // Exit exit = new Exit("exit", new Position(8,8));

        // EntityResponse playerRes = new EntityResponse(id, "player", new Position(1, 1), false);
        // EntityResponse keyRes = new EntityResponse(id, "key", new Position(2,1), false);
        // EntityResponse doorRes = new EntityResponse(id, "door", new Position(3,1), false);
        // EntityResponse exitRes = new EntityResponse(id, "exit", new Position(8,8), false);

        //List<EntityResponse> actual = new ArrayList<EntityResponse>();

        DungeonResponse initialResponse = dmc.newGame("d_bombTest_placeBombRadius2", "c_battleTests_basicMercenaryMercenaryDies");

        // actual.addAll(getEntities(intialResponse, "player"));
        // actual.addAll(getEntities(intialResponse, "wall"));
        // actual.addAll(getEntities(intialResponse, "door"));
        // actual.addAll(getEntities(intialResponse, "key"));

        assertEquals(null, initialResponse.getInventory());
        assertEquals("d_bombTest_placeBombRadius2", initialResponse.getDungeonName());
        assertEquals(countEntityOfType(initialResponse, "player"), 1);
        assertEquals(countEntityOfType(initialResponse, "treasure"), 2);
        assertEquals(countEntityOfType(initialResponse, "boulder"), 1);
        assertEquals(countEntityOfType(initialResponse, "switch"), 1);
        assertEquals(countEntityOfType(initialResponse, "wall"), 2);
        assertEquals(countEntityOfType(initialResponse, "exit"), 1);
        assertEquals(countEntityOfType(initialResponse, "bomb"), 1);

        //assertEquals(actual, intialResponse.getEntities());
        
    }


}
