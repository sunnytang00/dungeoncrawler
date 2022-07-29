package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.reflections.vfs.Vfs.Dir;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Helper;
import dungeonmania.util.Position;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.countEntityOfType;
import static dungeonmania.TestUtils.getPlayer;

public class RewindTest {

    @Test
    public void BasicRewind() {
        
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse intialResponse = dmc.newGame("BasicRewind", "c_battleTests_basicMercenaryMercenaryDies");//state 0
        
        // dmc.tick(Direction.RIGHT);
        // dmc.tick(Direction.RIGHT);

        for (int i = 0; i < 5; i++) {//0 1 2 3 4 5
            dmc.tick(Direction.RIGHT);
        }

        DungeonResponse rewindRes = dmc.rewind(5);

        assertEquals(1, countEntityOfType(rewindRes, "player"));
        assertEquals(1, countEntityOfType(rewindRes, "older_player"));

        List<EntityResponse> entityList = getEntities(rewindRes, "older_player");
        assertEquals(new Position(0, 0), entityList.get(0).getPosition());
        


    }
}
