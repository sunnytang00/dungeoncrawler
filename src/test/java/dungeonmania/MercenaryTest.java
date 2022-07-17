package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class MercenaryTest {

    @Test
    @DisplayName("Test vicious merc follow player")
    public void testMercFollowPlayer() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse intialResponse = dmc.newGame("mercenary", "simple");

        DungeonResponse move = dmc.tick(Direction.LEFT);
        Position playerPos = getEntities(move, "player").get(0).getPosition();
        Position mercPos = getEntities(move, "mercenary").get(0).getPosition();
        
        assertEquals(playerPos.getX(), 0);
        assertEquals(playerPos.getY(), 1);
        assertEquals(mercPos.getX(), 7);
        assertEquals(mercPos.getY(), 1);

        move = dmc.tick(Direction.RIGHT);
        playerPos = getEntities(move, "player").get(0).getPosition();
        mercPos = getEntities(move, "mercenary").get(0).getPosition();

        assertEquals(playerPos.getX(), 1);
        assertEquals(playerPos.getY(), 1);
        assertEquals(mercPos.getX(), 6);
        assertEquals(mercPos.getY(), 1);

        move = dmc.tick(Direction.UP);
        playerPos = getEntities(move, "player").get(0).getPosition();
        mercPos = getEntities(move, "mercenary").get(0).getPosition();
        assertEquals(playerPos.getX(), 1);
        assertEquals(playerPos.getY(), 0);
        assertEquals(mercPos.getX(), 5);
        assertEquals(mercPos.getY(), 1);

        move = dmc.tick(Direction.UP);
        playerPos = getEntities(move, "player").get(0).getPosition();
        mercPos = getEntities(move, "mercenary").get(0).getPosition();
        assertEquals(playerPos.getX(), 1);
        assertEquals(playerPos.getY(), -1);
        assertEquals(mercPos.getX(), 4);
        assertEquals(mercPos.getY(), 1);


    }

    @Test
    @DisplayName("Test merc blocked by wall")
    public void testmercBlockedbyWall() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse intialResponse = dmc.newGame("mercenary", "simple");

        DungeonResponse move = dmc.tick(Direction.DOWN);
        Position playerPos = getEntities(move, "player").get(0).getPosition();
        Position mercPos = getEntities(move, "mercenary").get(0).getPosition();
        
        assertEquals(playerPos.getX(), 1);
        assertEquals(playerPos.getY(), 2);
        assertEquals(mercPos.getX(), 7);
        assertEquals(mercPos.getY(), 1);

        move = dmc.tick(Direction.DOWN);
        playerPos = getEntities(move, "player").get(0).getPosition();
        mercPos = getEntities(move, "mercenary").get(0).getPosition();

        assertEquals(playerPos.getX(), 1);
        assertEquals(playerPos.getY(), 3);
        assertEquals(mercPos.getX(), 6);
        assertEquals(mercPos.getY(), 1);

        move = dmc.tick(Direction.RIGHT);
        playerPos = getEntities(move, "player").get(0).getPosition();
        mercPos = getEntities(move, "mercenary").get(0).getPosition();
        assertEquals(playerPos.getX(), 2);
        assertEquals(playerPos.getY(), 3);
        assertEquals(mercPos.getX(), 5);
        assertEquals(mercPos.getY(), 1);

        move = dmc.tick(Direction.DOWN);
        playerPos = getEntities(move, "player").get(0).getPosition();
        mercPos = getEntities(move, "mercenary").get(0).getPosition();
        assertEquals(playerPos.getX(), 2);
        assertEquals(playerPos.getY(), 4);
        assertEquals(mercPos.getX(), 4);
        assertEquals(mercPos.getY(), 1);

    }

    @Test
    @DisplayName("Test merc move in advance map")
    public void testmercAdvanceMap() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("advanced", "simple");
        Position prevPlayer = getEntities(initDungonRes, "player").get(0).getPosition();
        Position prevMerc = getEntities(initDungonRes, "mercenary").get(0).getPosition();
        double disPrev = prevMerc.getDistanceBetween(prevPlayer);
        DungeonResponse move = dmc.tick(Direction.RIGHT);
        Position playerPos = getEntities(move, "player").get(0).getPosition();
        Position mercPos = getEntities(move, "mercenary").get(0).getPosition();
        double currDis = mercPos.getDistanceBetween(playerPos);
        assertEquals(playerPos.getX(), 2);
        assertEquals(playerPos.getY(), 1);
        assertTrue(disPrev > currDis);

        //System.out.println("Merc" + mercPos + "Player" + playerPos);

        disPrev = mercPos.getDistanceBetween(playerPos);

        move = dmc.tick(Direction.RIGHT);
        playerPos = getEntities(move, "player").get(0).getPosition();
        mercPos = getEntities(move, "mercenary").get(0).getPosition();
        currDis = mercPos.getDistanceBetween(playerPos);
       

        System.out.println("Merc" + mercPos + "Player" + playerPos);

        disPrev = mercPos.getDistanceBetween(playerPos);

        move = dmc.tick(Direction.RIGHT);
        playerPos = getEntities(move, "player").get(0).getPosition();
        mercPos = getEntities(move, "mercenary").get(0).getPosition();
        currDis = mercPos.getDistanceBetween(playerPos);
        

        System.out.println("Merc" + mercPos + "Player" + playerPos);

        disPrev = mercPos.getDistanceBetween(playerPos);

        move = dmc.tick(Direction.DOWN);
        playerPos = getEntities(move, "player").get(0).getPosition();
        mercPos = getEntities(move, "mercenary").get(0).getPosition();
        currDis = mercPos.getDistanceBetween(playerPos);
        assertTrue(disPrev > currDis);

        System.out.println("Merc" + mercPos + "Player" + playerPos);
        
        boolean reached = false;
        for(int i = 0; i < 4; i++) {
            disPrev = mercPos.getDistanceBetween(playerPos);

            move = dmc.tick(Direction.RIGHT);
            playerPos = getEntities(move, "player").get(0).getPosition();
            
            if (getEntities(move, "mercenary") == null || getEntities(move, "mercenary").size() == 0) {
                reached = true;
                break;
            } else {
                mercPos = getEntities(move, "mercenary").get(0).getPosition();
            }
            currDis = mercPos.getDistanceBetween(playerPos);
            disPrev = mercPos.getDistanceBetween(playerPos);
        }

        assertTrue(reached);

    }

    @Test
    @DisplayName("Test zombie not blocked by Boulder and does not push boulder")
    public void testzombieNotBlockedbyBoulder() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_testZombieTraversibleBoulder", "c_testSpawning_1");
 
        
        DungeonResponse move = dmc.tick(Direction.DOWN); //Tick 1

        assertEquals(0, countEntityOfType(move, "zombie_toast"));

        move = dmc.tick(Direction.DOWN); //Tick 2

        assertEquals(1, countEntityOfType(move, "zombie_toast"));
        Position pos = getEntities(move, "zombie_toast").get(0).getPosition();
        Position pos2 = getEntities(move, "zombie_toast_spawner").get(0).getPosition();
        Position expectedPos = pos2.translateBy(Direction.LEFT);
        System.out.println("expected" + expectedPos.getX() + "x" + expectedPos.getY() + "Y");
        // assert after movement
        assertEquals(expectedPos.getX(), pos.getX());
        assertEquals(expectedPos.getY(), pos.getY());

        move = dmc.tick(Direction.DOWN); //Tick 3 move
        pos = getEntities(move, "zombie_toast").get(0).getPosition();
        assertEquals(99 , pos.getX());
        assertEquals(101, pos.getY());

    }

    @Test
    @DisplayName("Test zombie not blocked by Portal and does not teleport")
    public void testzombieWithPortal() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_testZombieTraversiblePortal", "c_testSpawning_1");
 
        
        DungeonResponse move = dmc.tick(Direction.DOWN); //Tick 1

        assertEquals(0, countEntityOfType(move, "zombie_toast"));

        move = dmc.tick(Direction.DOWN); //Tick 2

        assertEquals(1, countEntityOfType(move, "zombie_toast"));
        Position pos = getEntities(move, "zombie_toast").get(0).getPosition();
        Position pos2 = getEntities(move, "zombie_toast_spawner").get(0).getPosition();
        Position expectedPos = pos2.translateBy(Direction.LEFT);
        System.out.println("expected" + expectedPos.getX() + "x" + expectedPos.getY() + "Y");
        // assert after movement
        assertEquals(expectedPos.getX(), pos.getX());
        assertEquals(expectedPos.getY(), pos.getY());

        move = dmc.tick(Direction.DOWN); //Tick 3 move
        pos = getEntities(move, "zombie_toast").get(0).getPosition();
        assertEquals(99 , pos.getX());
        assertEquals(101, pos.getY());

    }
    
}
