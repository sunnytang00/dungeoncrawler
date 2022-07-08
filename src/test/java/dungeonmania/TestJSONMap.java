package dungeonmania;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.JSONMap;
import static dungeonmania.TestUtils.getGoals;;

public class TestJSONMap {
    
    @Test
    public void testReturnMap() throws IOException{
        DungeonManiaController dmc = new DungeonManiaController();
        JSONMap map = dmc.getMap("d_movementTest_testMovementDown");
  
        assertEquals(map.getEntityList(), new ArrayList<>(List.of("{\"x\":1,\"y\":3,\"type\":\"exit\"}","{\"x\":1,\"y\":1,\"type\":\"player\"}")));
        assertEquals(map.getGoals(), "{\"goal\":\"exit\"}");
        
    }

    @Test
    public void testMapWithComplexGoals() throws IOException{
        DungeonManiaController dmc = new DungeonManiaController();
        JSONMap map = dmc.getMap("d_complexGoalsTest_andAll");
  
        assertEquals(map.getEntityList(), new ArrayList<>(List.of(
            "{\"x\":1,\"y\":1,\"type\":\"player\"}", 
            "{\"x\":2,\"y\":2,\"type\":\"spider\"}", 
            "{\"x\":3,\"y\":1,\"type\":\"boulder\"}", 
            "{\"x\":4,\"y\":1,\"type\":\"switch\"}", 
            "{\"x\":3,\"y\":2,\"type\":\"treasure\"}", 
            "{\"x\":3,\"y\":3,\"type\":\"exit\"}")));
        assertEquals(map.getGoals(), 
        "{\"goal\":\"AND\"," + 
        "\"subgoals\":" + 
        "[{\"goal\":\"AND\",\"subgoals\":[{\"goal\":\"exit\"},{\"goal\":\"treasure\"}]}," +
        "{\"goal\":\"AND\",\"subgoals\":[{\"goal\":\"boulders\"},{\"goal\":\"enemies\"}]}]}");
        
    }
}
