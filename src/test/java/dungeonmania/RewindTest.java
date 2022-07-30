package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.reflections.vfs.Vfs.Dir;

import dungeonmania.exceptions.InvalidActionException;
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

        DungeonResponse intialResponse = dmc.newGame("BasicRewind", "c_battleTests_basicMercenaryMercenaryDies");// state
                                                                                                                 // 0

        // dmc.tick(Direction.RIGHT);
        // dmc.tick(Direction.RIGHT);

        for (int i = 0; i < 5; i++) {// 0 1 2 3 4 5
            dmc.tick(Direction.RIGHT);
        }

        DungeonResponse rewindRes = dmc.rewind(5);

        assertEquals(1, countEntityOfType(rewindRes, "player"));
        assertEquals(1, countEntityOfType(rewindRes, "older_player"));

        List<EntityResponse> entityList = getEntities(rewindRes, "older_player");
        assertEquals(new Position(0, 0), entityList.get(0).getPosition());

    }

    @Test
    public void PortalRewindTest() {
        // n = new game, f = where player finishes (x coord & tick)
        // n f
        // 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28
        // 29 30 31
        // above are the ticks and also the x coordinate the player should be on
        // time travelling portal is on coordinate 30 and so when we enter it we should
        // have time travelled
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("BasicRewind", "c_battleTests_basicMercenaryMercenaryDies");// state 0

        for (int i = 0; i < 30; i++) {
            res = dmc.tick(Direction.RIGHT);
        }

        assertEquals(1, countEntityOfType(res, "player"));
        assertEquals(1, countEntityOfType(res, "older_player"));

        List<EntityResponse> player = getEntities(res, "older_player");
        assertEquals(new Position(30, 0), player.get(0).getPosition());

        List<EntityResponse> older_player = getEntities(res, "older_player");
        assertEquals(new Position(0, 0), older_player.get(0).getPosition());

    }

    @Test
    public void PortalRewindTestLessThan30Ticks() {
        // n f
        // 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28
        // 29 30 31
        // above are the ticks and also the x coordinate the player should be on
        // time travelling portal is on coordinate 30 and so when we enter it we should
        // have time travelled
        // we should go to the initial state as we dont have enough ticks to rewind
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("BasicRewind", "c_battleTests_basicMercenaryMercenaryDies");// state 0

        for (int i = 0; i < 29; i++) {
            res = dmc.tick(Direction.RIGHT);
        }

        assertEquals(1, countEntityOfType(res, "player"));
        assertEquals(1, countEntityOfType(res, "older_player"));

        List<EntityResponse> player = getEntities(res, "older_player");
        assertEquals(new Position(29, 0), player.get(0).getPosition());

        List<EntityResponse> older_player = getEntities(res, "older_player");
        assertEquals(new Position(0, 0), older_player.get(0).getPosition());

    }

    @Test
    public void PortalRewindTestMoreThan30Ticks() {
        // n f
        // 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28
        // 29 30 31
        // above are the ticks and also the x coordinate the player should be on
        // time travelling portal is on coordinate 30 and so when we enter it we should
        // have time travelled
        // we should go to tick 1
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("BasicRewind", "c_battleTests_basicMercenaryMercenaryDies");// state 0

        for (int i = 0; i < 31; i++) {
            res = dmc.tick(Direction.RIGHT);
        }

        assertEquals(1, countEntityOfType(res, "player"));
        assertEquals(1, countEntityOfType(res, "older_player"));

        List<EntityResponse> player = getEntities(res, "older_player");
        assertEquals(new Position(31, 0), player.get(0).getPosition());

        List<EntityResponse> older_player = getEntities(res, "older_player");
        assertEquals(new Position(1, 0), older_player.get(0).getPosition());

    }

    @Test
    public void RewindThrowsExceptionNumberOfTicksHasNotOccured() {
        // n f
        // 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28
        // 29 30 31
        // above are the ticks and also the x coordinate the player should be on
        // time travelling portal is on coordinate 30 and so when we enter it we should
        // have time travelled
        // we should go to tick 1
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("BasicRewind", "c_battleTests_basicMercenaryMercenaryDies");// state 0

        res = dmc.tick(Direction.RIGHT);

        assertThrows(IllegalArgumentException.class, () -> {
            dmc.rewind(5);
        });
    }

    @Test
    public void RewindThrowsException() {
        // n f
        // 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28
        // 29 30 31
        // above are the ticks and also the x coordinate the player should be on
        // time travelling portal is on coordinate 30 and so when we enter it we should
        // have time travelled
        // we should go to tick 1
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("BasicRewind", "c_battleTests_basicMercenaryMercenaryDies");// state 0

        res = dmc.tick(Direction.RIGHT);

        assertThrows(IllegalArgumentException.class, () -> {
            dmc.rewind(-1);
        });
    }


}
