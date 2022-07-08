package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import dungeonmania.StaticEntities.*;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import static dungeonmania.TestUtils.getEntities;

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
}
