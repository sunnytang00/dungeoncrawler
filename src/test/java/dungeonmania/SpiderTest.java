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


public class SpiderTest {

      
    @Test
    @DisplayName("Test circular basic movement of spiders")
    public void spiderMovementwithoutBoulder() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_basicMoveWithoutBoulder", "c_spiderTest_basicMovement");
        Position pos = getEntities(res, "spider").get(0).getPosition();

        List<Position> movementTrajectory = new ArrayList<Position>();
        int x = pos.getX();
        int y = pos.getY();
        int nextPositionElement = 0;
        movementTrajectory.add(new Position(x  , y-1));
        movementTrajectory.add(new Position(x+1, y-1));
        movementTrajectory.add(new Position(x+1, y));
        movementTrajectory.add(new Position(x+1, y+1));
        movementTrajectory.add(new Position(x  , y+1));
        movementTrajectory.add(new Position(x-1, y+1));
        movementTrajectory.add(new Position(x-1, y));
        movementTrajectory.add(new Position(x-1, y-1));

        // Assert Circular Movement of Spider
        for (int i = 0; i <= 20; ++i) {
            res = dmc.tick(Direction.UP);
            assertEquals(movementTrajectory.get(nextPositionElement), getEntities(res, "spider").get(0).getPosition());
            
            nextPositionElement++;
            if (nextPositionElement == 8){
                nextPositionElement = 0;
            }
        }
    }

    @Test
    @DisplayName("Test spider traverse through walls, doors, switches, portals, exits")
    public void spiderTraverse() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_Traverse", "c_spiderTest_basicMovement");
        Position pos = getEntities(res, "spider").get(0).getPosition();

        List<Position> movementTrajectory = new ArrayList<Position>();
        int x = pos.getX();
        int y = pos.getY();
        int nextPositionElement = 0;
        movementTrajectory.add(new Position(x  , y-1));
        movementTrajectory.add(new Position(x+1, y-1));
        movementTrajectory.add(new Position(x+1, y));
        movementTrajectory.add(new Position(x+1, y+1));
        movementTrajectory.add(new Position(x  , y+1));
        movementTrajectory.add(new Position(x-1, y+1));
        movementTrajectory.add(new Position(x-1, y));
        movementTrajectory.add(new Position(x-1, y-1));

        // Assert Circular Movement of Spider
        for (int i = 0; i <= 20; ++i) {
            res = dmc.tick(Direction.UP);
            assertEquals(movementTrajectory.get(nextPositionElement), getEntities(res, "spider").get(0).getPosition());
            
            nextPositionElement++;
            if (nextPositionElement == 8){
                nextPositionElement = 0;
            }
        }
    }


 
    @Test
    @DisplayName("Test reverse movement direction of spiders")
    public void spiderReverseMovementDirection() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_reverseDirection", "c_spiderTest_basicMovement");
        Position pos = getEntities(res, "spider").get(0).getPosition();
 
        List<Position> movementTrajectory = new ArrayList<Position>();
        int x = pos.getX();
        int y = pos.getY();
        int nextPositionElement = 0;
        movementTrajectory.add(new Position(x  , y-1));
        movementTrajectory.add(new Position(x+1, y-1));
        movementTrajectory.add(new Position(x+1, y));
        // reverse direction as spider encounters boulder
        // movementTrajectory.add(new Position(x+1, y));
        movementTrajectory.add(new Position(x+1, y-1));
        movementTrajectory.add(new Position(x  , y-1));
        movementTrajectory.add(new Position(x-1, y-1));
        movementTrajectory.add(new Position(x-1, y));
        movementTrajectory.add(new Position(x-1, y+1));
        movementTrajectory.add(new Position(x  , y+1));
        // movementTrajectory.add(new Position(x  , y+1));
        movementTrajectory.add(new Position(x-1, y+1));
        movementTrajectory.add(new Position(x-1 , y));
 
        // Assert Circular Movement of Spider
        for (int i = 0; i < movementTrajectory.size(); ++i) {
            res = dmc.tick(Direction.UP);
            assertEquals(movementTrajectory.get(nextPositionElement), getEntities(res, "spider").get(0).getPosition());
            
            nextPositionElement++;

        }
    }

    @Test
    @DisplayName("Test reverse movement direction of spiders - 2 boulder")
    public void spiderReverseMovementTwoBoulder() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_reverse_2_boulders", "c_spiderTest_basicMovement");
        Position pos = getEntities(res, "spider").get(0).getPosition();
 
        List<Position> movementTrajectory = new ArrayList<Position>();
        int x = pos.getX();
        int y = pos.getY();
        int nextPositionElement = 0;
        movementTrajectory.add(new Position(x  , y-1));
        movementTrajectory.add(new Position(x+1, y-1));
        movementTrajectory.add(new Position(x+1, y));
        // reverse direction as spider encounters boulder
        // movementTrajectory.add(new Position(x+1, y));
        movementTrajectory.add(new Position(x+1, y-1));
        movementTrajectory.add(new Position(x  , y-1));
        movementTrajectory.add(new Position(x-1, y-1));
        movementTrajectory.add(new Position(x-1, y));
        movementTrajectory.add(new Position(x-1, y-1));
        movementTrajectory.add(new Position(x  , y-1));
        movementTrajectory.add(new Position(x+1, y-1));
        movementTrajectory.add(new Position(x+1, y));
        movementTrajectory.add(new Position(x+1, y-1));
        movementTrajectory.add(new Position(x  , y-1));
        movementTrajectory.add(new Position(x-1, y-1));
        movementTrajectory.add(new Position(x-1, y));
        movementTrajectory.add(new Position(x-1, y-1));
 
        // Assert Circular Movement of Spider
        for (int i = 0; i < movementTrajectory.size(); ++i) {
            res = dmc.tick(Direction.UP);
            assertEquals(movementTrajectory.get(nextPositionElement), getEntities(res, "spider").get(0).getPosition());
        
            nextPositionElement++;

        }
    }

    @Test
    @DisplayName("Test reverse movement direction of spiders - 2 boulder")
    public void spiderStuckBetweenTwoBoulder() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_stuck_2_boulders", "c_spiderTest_basicMovement");
        Position pos = getEntities(res, "spider").get(0).getPosition();
       
        Position newPos = pos.translateBy(Direction.UP);
        
        for (int i = 0; i < 5; ++i) {
            res = dmc.tick(Direction.UP);
            assertEquals(newPos, getEntities(res, "spider").get(0).getPosition());

        }
    }

    @Test
    @DisplayName("Test spider stay still if first move is boulder")
    public void spiderFirstMoveBoulder() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_firstMove_Boulder", "c_spiderTest_basicMovement");
        Position pos = getEntities(res, "spider").get(0).getPosition();

        for (int i = 0; i < 5; ++i) {
            res = dmc.tick(Direction.UP);
            assertEquals(pos, getEntities(res, "spider").get(0).getPosition());

        }
    }

    @Test
    @DisplayName("Test spider spawns at rate 1")
    public void spawnSpiderRateOne() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse intialResponse = dmc.newGame("zombieSpiderSpawn", "c_testSpawning_1");

        DungeonResponse move = dmc.tick(Direction.UP); //Tick 1
        assertEquals(1, countEntityOfType(move, "spider"));

        move = dmc.tick(Direction.DOWN); //Tick 2
        assertEquals(2, countEntityOfType(move, "spider"));

        move = dmc.tick(Direction.UP);
        assertEquals(3, countEntityOfType(move, "spider"));

        move = dmc.tick(Direction.DOWN);
        assertEquals(4, countEntityOfType(move, "spider"));


    }

    @Test
    @DisplayName("Test spider spawns at rate 2")
    public void spawnSpiderRateTwo() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse intialResponse = dmc.newGame("zombieSpiderSpawn", "c_testSpawning");

        DungeonResponse move = dmc.tick(Direction.UP); //Tick 1
        assertEquals(0, countEntityOfType(move, "spider"));

        move = dmc.tick(Direction.DOWN); //Tick 2
        assertEquals(1, countEntityOfType(move, "spider"));

        move = dmc.tick(Direction.UP);
        assertEquals(1, countEntityOfType(move, "spider"));

        move = dmc.tick(Direction.DOWN);
        assertEquals(2, countEntityOfType(move, "spider"));

        move = dmc.tick(Direction.UP);
        assertEquals(2, countEntityOfType(move, "spider"));

        move = dmc.tick(Direction.DOWN);
        assertEquals(3, countEntityOfType(move, "spider"));
    }

    @Test
    @DisplayName("Test spider spawns at rate 10")
    public void spawnSpiderRateTen() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse intialResponse = dmc.newGame("zombieSpiderSpawn", "simple");

        DungeonResponse move = dmc.tick(Direction.UP); //Tick 1
        assertEquals(0, countEntityOfType(move, "spider"));

        for (int i = 0; i < 8; i++) {
            move = dmc.tick(Direction.DOWN); //Tick 2
            assertEquals(0, countEntityOfType(move, "spider"));
        }

        move = dmc.tick(Direction.UP);
        assertEquals(1, countEntityOfType(move, "spider"));

        
    }

    @Test
    @DisplayName("Test spider not affected by player invincible state")
    public void testSpiderUnaffectedbyInvincible() throws IllegalArgumentException, InvalidActionException  {

        DungeonManiaController dmc = new DungeonManiaController();

        //PLAYER    BOULDER

        DungeonResponse intialResponse = dmc.newGame("d_spiderTest_invincibleNoEffect", "c_testSpawning");

        DungeonResponse move = dmc.tick(Direction.RIGHT);
        ItemResponse res = getInventory(move, "invincibility_potion").get(0);
        DungeonResponse consume = dmc.tick(res.getId());

        assertEquals(0, getInventory(consume, "invincibility_potion").size());
        assertEquals(0, getEntities(consume, "invincibility_potion").size());

        assertEquals(1, countEntityOfType(consume, "spider"));

        move = dmc.tick(Direction.RIGHT);
        move = dmc.tick(Direction.RIGHT);
        Position pos = getEntities(move, "spider").get(1).getPosition();

        List<Position> movementTrajectory = new ArrayList<Position>();
        int x = pos.getX();
        int y = pos.getY();
        int nextPositionElement = 0;
        movementTrajectory.add(new Position(x  , y-1));
        movementTrajectory.add(new Position(x+1, y-1));
        movementTrajectory.add(new Position(x+1, y));
        movementTrajectory.add(new Position(x+1, y+1));
        movementTrajectory.add(new Position(x  , y+1));
        movementTrajectory.add(new Position(x-1, y+1));
        movementTrajectory.add(new Position(x-1, y));
        movementTrajectory.add(new Position(x-1, y-1));

        // Assert Circular Movement of Spider
        for (int i = 0; i <= 10; ++i) {
            if (getEntities(move, "player").size() == 1) {
                move = dmc.tick(Direction.UP);
                assertEquals(movementTrajectory.get(nextPositionElement), getEntities(move, "spider").get(1).getPosition());
                
                nextPositionElement++;
                if (nextPositionElement == 8){
                    nextPositionElement = 0;
                }
            }
        }


    }
    
}
