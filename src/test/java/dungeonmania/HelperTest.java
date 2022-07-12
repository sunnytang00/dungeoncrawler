package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import dungeonmania.StaticEntities.Portal;
import dungeonmania.StaticEntities.Wall;
import dungeonmania.util.Helper;
import dungeonmania.util.Position;

public class HelperTest {
    @Test
    public void CheckIfTraversableTest() {
        
        ArrayList<Entity> entities = new ArrayList<Entity>();

        Portal portal1 = new Portal("portal", new Position(0, 0), "blue");
        Portal portal2 = new Portal("portal", new Position(3, 3), "blue");
        Wall wall = new Wall("wall", new Position(1,2));

        entities.add(portal1);
        entities.add(portal2);
        entities.add(wall);

        assertEquals(false, Helper.CheckIfTraversable(new Position(1,2), entities));
        assertEquals(true, Helper.CheckIfTraversable(new Position(0,0), entities));
        assertEquals(true, Helper.CheckIfTraversable(new Position(3,3), entities));


    }
}
