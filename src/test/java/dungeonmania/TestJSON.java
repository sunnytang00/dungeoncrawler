package dungeonmania;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;
import dungeonmania.util.JSONMap;

import static dungeonmania.TestUtils.getGoals;
import static dungeonmania.TestHelpers.*;

public class TestJSON {
    
    @Test
    public void testReturnMap() throws IOException{
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse dResponse = dmc.newGame("d_movementTest_testMovementDown", "c_battleTests_basicMercenaryMercenaryDies");
  
        assertEquals(dResponse.getDungeonName(), "d_movementTest_testMovementDown");     
        assertEquals(dResponse.getGoals(), "{\"goal\":\"exit\"}");
        
        List<String> types = new ArrayList<>();
        dResponse.getEntities().stream().forEach(s -> types.add(s.getType()));
        dResponse.getEntities().stream().forEach(s -> System.out.println(s.getType()));
        assertListAreEqualIgnoringOrder(Arrays.asList("exit", "player"), types);

        List<Integer> posX = new ArrayList<>();
        dResponse.getEntities().stream().forEach(s -> posX.add(s.getPosition().getX()));
        assertListAreEqualIgnoringOrder(Arrays.asList(1, 1), posX);

        List<Integer> posY = new ArrayList<>();
        dResponse.getEntities().stream().forEach(s -> posY.add(s.getPosition().getY()));
        assertListAreEqualIgnoringOrder(Arrays.asList(1, 3), posY);
    }
}
