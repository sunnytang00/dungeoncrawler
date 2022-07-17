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

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BattleTest {

    private static DungeonResponse genericMercenarySequence(DungeonManiaController controller, String configFile) {
        
        DungeonResponse initialResponse = controller.newGame("d_battleTest_basicSpider", configFile);
        int sCount = countEntityOfType(initialResponse, "spider");
        

        assertEquals(1, countEntityOfType(initialResponse, "player"));
        assertEquals(1, sCount);
        return controller.tick(Direction.RIGHT);
    }

    private void assertBattleCalculations(String enemyType, BattleResponse battle, boolean enemyDies, String configFilePath) {
        List<RoundResponse> rounds = battle.getRounds();
        double playerHealth = Double.parseDouble(getValueFromConfigFile("player_health", configFilePath));
        double enemyHealth = Double.parseDouble(getValueFromConfigFile(enemyType + "_health", configFilePath));
        double playerAttack = Double.parseDouble(getValueFromConfigFile("player_attack", configFilePath));
        double enemyAttack = Double.parseDouble(getValueFromConfigFile(enemyType + "_attack", configFilePath));

        for (RoundResponse round : rounds) {
            assertEquals(-(enemyAttack / 10), round.getDeltaCharacterHealth(), 0.001);
            assertEquals(-(playerAttack / 5), round.getDeltaEnemyHealth(), 0.001);
            enemyHealth += round.getDeltaEnemyHealth();
            playerHealth += round.getDeltaCharacterHealth();
        }

        if (enemyDies) {
            assertTrue(enemyHealth <= 0);
        } else {
            assertTrue(playerHealth <= 0);
        }
    }

    @Test
    @DisplayName("Test basic battle calculations - spider - player loses")
    public void testHealthBelowZeroMercenary() {
       DungeonManiaController controller = new DungeonManiaController();
       DungeonResponse postBattleResponse = genericMercenarySequence(controller, "c_battleTests_basicSpiderPlayerDies copy");
       BattleResponse battle = postBattleResponse.getBattles().get(0);
       assertBattleCalculations("spider", battle, false, "c_battleTests_basicSpiderPlayerDies copy");
    }


    @Test
    @DisplayName("Test basic battle calculations - spider - player wins")
    public void testRoundCalculationsMercenary() {
       DungeonManiaController controller = new DungeonManiaController();
       DungeonResponse postBattleResponse = genericMercenarySequence(controller, "c_battleTests_basicSpiderSpiderDies copy");
       BattleResponse battle = postBattleResponse.getBattles().get(0);
       assertBattleCalculations("spider", battle, true, "c_battleTests_basicSpiderSpiderDies copy");
    }


    private static DungeonResponse genericMercenarySequence2(DungeonManiaController controller, String configFile) {
        
        DungeonResponse initialResponse = controller.newGame("d_battleTest_twoEnemy", configFile);
        int sCount = countEntityOfType(initialResponse, "spider");
        int mCount = countEntityOfType(initialResponse, "mercenary");
        

        assertEquals(1, countEntityOfType(initialResponse, "player"));
        assertEquals(1, sCount);
        assertEquals(1, mCount);
        return controller.tick(Direction.RIGHT);
    }

    private void assertBattleCalculations2(String enemyType, BattleResponse battle, boolean enemyDies, String configFilePath) {
        List<RoundResponse> rounds = battle.getRounds();
        double playerHealth = Double.parseDouble(getValueFromConfigFile("player_health", configFilePath));
        double enemyHealth = Double.parseDouble(getValueFromConfigFile(enemyType + "_health", configFilePath));
        double playerAttack = Double.parseDouble(getValueFromConfigFile("player_attack", configFilePath));
        double enemyAttack = Double.parseDouble(getValueFromConfigFile(enemyType + "_attack", configFilePath));

        for (RoundResponse round : rounds) {
            assertEquals(-(enemyAttack / 10), round.getDeltaCharacterHealth(), 0.001);
            assertEquals(-(playerAttack / 5), round.getDeltaEnemyHealth(), 0.001);
            enemyHealth += round.getDeltaEnemyHealth();
            playerHealth += round.getDeltaCharacterHealth();
        }

        if (enemyDies) {
            assertTrue(enemyHealth <= 0);
        } else {
            assertTrue(playerHealth <= 0);
        }
    }

    @Test
    @DisplayName("Test basic battle calculations - spider & merc - player loses")
    public void testMultipleEnemyPlayerLose() {
       DungeonManiaController controller = new DungeonManiaController();
       DungeonResponse postBattleResponse = genericMercenarySequence2(controller, "c_battleTests_basicSpiderPlayerDies copy");
       BattleResponse battle = postBattleResponse.getBattles().get(0);
       assertBattleCalculations2("spider", battle, false, "c_battleTests_basicSpiderPlayerDies copy");
    }


    @Test
    @DisplayName("Test basic battle calculations - spider & merc - player wins")
    public void testMultipleEnemyPlayerWin() {
       DungeonManiaController controller = new DungeonManiaController();
       DungeonResponse postBattleResponse = genericMercenarySequence2(controller, "c_battleTests_basicSpiderSpiderDies copy");
       BattleResponse battle = postBattleResponse.getBattles().get(0);
       assertBattleCalculations2("spider", battle, true, "c_battleTests_basicSpiderSpiderDies copy");
    }

    @Test
    @DisplayName("Test battle when player in invincible state")
    public void testBattleInvincible() throws IllegalArgumentException, InvalidActionException  {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse intialResponse = dmc.newGame("d_mercTest_invincibleEffect", "c_testSpawning");

        DungeonResponse move = dmc.tick(Direction.RIGHT);
        ItemResponse res = getInventory(move, "invincibility_potion").get(0);
        DungeonResponse consume = dmc.tick(res.getId());

        assertEquals(0, getInventory(consume, "invincibility_potion").size());
        assertEquals(0, getEntities(consume, "invincibility_potion").size());

        assertEquals(1, countEntityOfType(consume, "mercenary"));

        move = dmc.tick(Direction.DOWN);
        Position prevPos = getEntities(move, "mercenary").get(0).getPosition();
        Position prevPlayer = getEntities(move, "player").get(0).getPosition();
        // Assert Movement of zombie away from player

            
        move = dmc.tick(Direction.RIGHT);
        Position playerPosition = getEntities(move, "player").get(0).getPosition();
        Position pos = getEntities(move, "mercenary").get(0).getPosition();
        double diff = pos.getDistanceBetween(prevPlayer) - prevPos.getDistanceBetween(prevPlayer);
        assertTrue(diff >= 0);

        prevPos = pos;
        prevPlayer = playerPosition;

        move = dmc.tick(Direction.RIGHT);
        playerPosition = getEntities(move, "player").get(0).getPosition();
        pos = getEntities(move, "mercenary").get(0).getPosition();
        diff = pos.getDistanceBetween(prevPlayer) - prevPos.getDistanceBetween(prevPlayer);
        assertTrue(diff >= 0);

        prevPos = pos;
        prevPlayer = playerPosition;

        move = dmc.tick(Direction.RIGHT);
        playerPosition = getEntities(move, "player").get(0).getPosition();
        pos = getEntities(move, "mercenary").get(0).getPosition();
        diff = pos.getDistanceBetween(prevPlayer) - prevPos.getDistanceBetween(prevPlayer);
        assertTrue(diff >= 0);

        move = dmc.tick(Direction.UP);
        playerPosition = getEntities(move, "player").get(0).getPosition();
        pos = getEntities(move, "mercenary").get(0).getPosition();
        diff = pos.getDistanceBetween(prevPlayer) - prevPos.getDistanceBetween(prevPlayer);
        assertTrue(diff >= 0);
    }

    @Test
    @DisplayName("Test not battle with merc when player in invisible state")
    public void testBattleInvisible() throws IllegalArgumentException, InvalidActionException  {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse intialResponse = dmc.newGame("d_mercTest_invincibleEffect", "simple");

        DungeonResponse move = dmc.tick(Direction.RIGHT);
        ItemResponse res = getInventory(move, "invincibility_potion").get(0);
        DungeonResponse consume = dmc.tick(res.getId());

        assertEquals(0, getInventory(consume, "invincibility_potion").size());
        assertEquals(0, getEntities(consume, "invincibility_potion").size());

        assertEquals(1, countEntityOfType(consume, "mercenary"));

        move = dmc.tick(Direction.RIGHT);
        Position mercPrev = getEntities(move, "mercenary").get(0).getPosition();
        res = getInventory(move, "invisibility_potion").get(0);
        consume = dmc.tick(res.getId());

        assertEquals(0, getInventory(consume, "invincibility_potion").size());
        assertEquals(0, getEntities(consume, "invincibility_potion").size());

        Position playerP = getEntities(consume, "player").get(0).getPosition();
        Position mercP = getEntities(consume, "mercenary").get(0).getPosition();

        List<Position> positions = mercPrev.getCardinallyAdjacentPositions();
        assertTrue(positions.contains(mercP));

        
    }


    
}
