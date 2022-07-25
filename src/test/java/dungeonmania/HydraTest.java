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

public class HydraTest {
    private static DungeonResponse genericMercenarySequence(DungeonManiaController controller, String configFile) {
        
        DungeonResponse initialResponse = controller.newGame("d_battleTest_basicHydra", configFile);
        int sCount = countEntityOfType(initialResponse, "hydra");

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
    @DisplayName("Test basic battle calculations - hydra - player loses")
    public void testHealthBelowZeroHydra() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericMercenarySequence(controller, "c_battleTests_basicHydraPlayerDies");
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        assertBattleCalculations("hydra", battle, false, "c_battleTests_basicHydraPlayerDies");
    }


    @Test
    @DisplayName("Test basic battle calculations - hydra - player wins")
    // I set the hydra health increase rate to be very low here -> 0.01
    public void testRoundCalculationsHydra() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericMercenarySequence(controller, "c_battleTests_basicHydraHydraDies");
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        assertBattleCalculations("hydra", battle, true, "c_battleTests_basicHydraHydraDies");
    }
}
