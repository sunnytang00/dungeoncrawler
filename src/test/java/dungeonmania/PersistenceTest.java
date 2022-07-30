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
public class PersistenceTest {
    @Test
    @DisplayName("Simple persistence test with maze map")
    public void simpleGoalTestWithNoConditions() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_maze", "c_simpleGoalTest_noGoalConditions");

        res = dmc.tick(Direction.DOWN);
        EntityResponse initPlayer = getPlayer(res).get();

        res = dmc.saveGame("mazeMoveDownBy1");
        EntityResponse savedPlayer = getPlayer(res).get();
        assertEquals(initPlayer, savedPlayer);

        DungeonManiaController dmc1 = new DungeonManiaController();
        DungeonResponse reloadRes = dmc1.loadGame("mazeMoveDownBy1");
        EntityResponse reloadPlayer = getPlayer(res).get();
        assertEquals(initPlayer, reloadPlayer);
        assertEquals(savedPlayer, reloadPlayer);

        assertEquals(res.getDungeonName() , reloadRes.getDungeonName());
        assertEquals(res.getDungeonId() , reloadRes.getDungeonId());
        assertTrue(reloadRes.getBattles().size() == 0);
        assertTrue(reloadRes.getInventory().size() == 0);
        assertTrue(reloadRes.getEntities().size() == res.getEntities().size());

    }
}
