// package dungeonmania;

// import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertNotEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import static dungeonmania.TestUtils.getPlayer;
// import static dungeonmania.TestUtils.getEntities;


// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;

// import dungeonmania.response.models.DungeonResponse;
// import dungeonmania.response.models.EntityResponse;
// import dungeonmania.util.Position;

// public class EntityTest {
//     @Test
//     @DisplayName("Test Entity class")
//     public void testCreateAnEntity() {
//         DungeonManiaController dmc = new DungeonManiaController();
//         DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testMovementDown", "c_movementTest_testMovementDown");
//         EntityResponse initPlayer = getPlayer(initDungonRes).get();
//         EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), "player", new Position(1,1), true);
//         assertEquals(expectedPlayer, initPlayer);
//     }
// }
