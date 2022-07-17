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

public class ZombieTest {

    @Test
    @DisplayName("Test random movement of zombie")
    public void zombieMovementBasic() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("zombies", "bribe_radius_1");
        DungeonResponse move = dmc.tick(Direction.LEFT);
        assertEquals(1, countEntityOfType(move, "zombie_toast"));
        Position pos = getEntities(move, "zombie_toast").get(0).getPosition();
        Position pos2 = getEntities(move, "zombie_toast_spawner").get(0).getPosition();

        List<Position> positions = pos2.getCardinallyAdjacentPositions();

        assertTrue(positions.contains(pos));
 
    }


    @Test
    public void testSpawnerBehaviour() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse intialResponse = dmc.newGame("zombieSpiderSpawn", "c_testSpawning_1");

        DungeonResponse move = dmc.tick(Direction.UP); //Tick 1

        assertEquals(0, countEntityOfType(move, "zombie_toast"));

        move = dmc.tick(Direction.DOWN); //Tick 2

        assertEquals(1, countEntityOfType(move, "zombie_toast"));


        move = dmc.tick(Direction.UP);

        assertEquals(1, countEntityOfType(move, "zombie_toast"));

        move = dmc.tick(Direction.DOWN);

        assertEquals(2, countEntityOfType(move, "zombie_toast"));

    }

    @Test
    @DisplayName("Test zombie blocked by wall")
    public void testzombieBlockedbyWall() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_testZombieTraversible", "c_testSpawning_1");
 
        
        DungeonResponse move = dmc.tick(Direction.DOWN); //Tick 1

        assertEquals(0, countEntityOfType(move, "zombie_toast"));

        move = dmc.tick(Direction.DOWN); //Tick 2

        assertEquals(1, countEntityOfType(move, "zombie_toast"));
        Position pos = getEntities(move, "zombie_toast").get(0).getPosition();
        Position pos2 = getEntities(move, "zombie_toast_spawner").get(0).getPosition();
        Position expectedPos = pos2.translateBy(Direction.LEFT);
        // assert after movement
        assertEquals(expectedPos.getX(), pos.getX());
        assertEquals(expectedPos.getY(), pos.getY());

        move = dmc.tick(Direction.DOWN); //Tick 3
        pos = getEntities(move, "zombie_toast").get(0).getPosition();
        assertEquals(expectedPos.getX() + 1, pos.getX());
        assertEquals(expectedPos.getY(), pos.getY());

    }

    @Test
    @DisplayName("Test zombie blocked by Door")
    public void testzombieBlockedbyDoor() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_testZombieTraversibleDoor", "c_testSpawning_1");
 
        
        DungeonResponse move = dmc.tick(Direction.DOWN); //Tick 1

        assertEquals(0, countEntityOfType(move, "zombie_toast"));

        move = dmc.tick(Direction.DOWN); //Tick 2

        assertEquals(1, countEntityOfType(move, "zombie_toast"));
        Position pos = getEntities(move, "zombie_toast").get(0).getPosition();
        Position pos2 = getEntities(move, "zombie_toast_spawner").get(0).getPosition();
        Position expectedPos = pos2.translateBy(Direction.LEFT);
        // assert after movement
        assertEquals(expectedPos.getX(), pos.getX());
        assertEquals(expectedPos.getY(), pos.getY());

        move = dmc.tick(Direction.DOWN); //Tick 3
        pos = getEntities(move, "zombie_toast").get(0).getPosition();
        assertEquals(expectedPos.getX() + 1, pos.getX());
        assertEquals(expectedPos.getY(), pos.getY());

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
        // assert after movement
        assertEquals(expectedPos.getX(), pos.getX());
        assertEquals(expectedPos.getY(), pos.getY());

        move = dmc.tick(Direction.DOWN); //Tick 3 move
        pos = getEntities(move, "zombie_toast").get(0).getPosition();
        assertEquals(99 , pos.getX());
        assertEquals(101, pos.getY());

    }

    @Test
    @DisplayName("Test zombie not spawn if all cardinally adjacent to spawner are walls")
    public void testzombieNotSpawn() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_testZombieNotSpawn", "c_testSpawning_1");
 
        
        DungeonResponse move = dmc.tick(Direction.DOWN); //Tick 1

        assertEquals(0, countEntityOfType(move, "zombie_toast"));

        move = dmc.tick(Direction.DOWN); //Tick 2

        assertEquals(0, countEntityOfType(move, "zombie_toast"));
        move = dmc.tick(Direction.DOWN); //Tick 3

        assertEquals(0, countEntityOfType(move, "zombie_toast"));
        move = dmc.tick(Direction.DOWN); //Tick 4

        assertEquals(0, countEntityOfType(move, "zombie_toast"));
    }

    @Test
    @DisplayName("Test zombie run away when player in invincible state")
    public void testZombieaffectedbyInvincible() throws IllegalArgumentException, InvalidActionException  {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse intialResponse = dmc.newGame("d_zombieTest_invincibleEffect ", "c_testSpawning");

        DungeonResponse move = dmc.tick(Direction.RIGHT);
        ItemResponse res = getInventory(move, "invincibility_potion").get(0);
        DungeonResponse consume = dmc.tick(res.getId());

        assertEquals(0, getInventory(consume, "invincibility_potion").size());
        assertEquals(0, getEntities(consume, "invincibility_potion").size());

        assertEquals(1, countEntityOfType(consume, "zombie_toast"));

        move = dmc.tick(Direction.DOWN);
        Position prevPos = getEntities(move, "zombie_toast").get(0).getPosition();
        
        // Assert Movement of zombie away from player

            
        move = dmc.tick(Direction.RIGHT);
        Position playerPosition = getEntities(move, "player").get(0).getPosition();
        Position pos = getEntities(move, "zombie_toast").get(0).getPosition();
        double diff = pos.getDistanceBetween(playerPosition) - prevPos.getDistanceBetween(playerPosition);
        assertTrue(diff >= 0);


    }
}
