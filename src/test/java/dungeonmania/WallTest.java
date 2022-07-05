package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dungeonmania.StaticEntities.Wall;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;
import static dungeonmania.TestUtils.getEntities;

public class WallTest {

    @Test
    public void SimpleWallTest() {

        List<Entity> entities = new ArrayList<Entity>();
        entities.add(new Wall("wall", new Position(0, 1)));
        //Convert list to a list of entity responses
        List<EntityResponse> actual = entities.stream().map(Entity::getEntityResponse).collect(Collectors.toList());
        DungeonResponse dunres = new DungeonResponse("dungeonTestId", "d_testOneWall", actual, null, null, null,null);
        //Create a dungeon response and check getentities returns the wall
        List<EntityResponse> expected = getEntities(dunres, "wall");
        assertEquals(expected, actual);

    }
       
}
