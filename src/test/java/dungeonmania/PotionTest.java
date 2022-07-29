package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.entities.Entity;
import dungeonmania.entities.StaticEntities.*;
import dungeonmania.entities.collectableEntities.Key;
import dungeonmania.entities.movingEntity.player.Player;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.countEntityOfType;
import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.*;

public class PotionTest {

    @Test
    @DisplayName("Test one invisibility_potion")
    public void SimpleInvisiblilityPotionTest() {

        List<Entity> entities = new ArrayList<Entity>();
        entities.add(new Wall("invisibility_potion", new Position(3, 1)));
        //Convert list to a list of entity responses and pass into dungeonresponse
        List<EntityResponse> actual = entities.stream().map(Entity::getEntityResponse).collect(Collectors.toList());
        DungeonResponse dunres = new DungeonResponse("dungeonTestId", "whatever", actual, null, null, null,null);
        //Create a dungeon response and check getentities returns the potion
        List<EntityResponse> expected = getEntities(dunres, "invisibility_potion");
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Test one invincibility_potion")
    public void SimpleInvincibilityPotionTest() {

        List<Entity> entities = new ArrayList<Entity>();
        entities.add(new Wall("invisibility_potion", new Position(2, 1)));
        //Convert list to a list of entity responses and pass into dungeonresponse
        List<EntityResponse> actual = entities.stream().map(Entity::getEntityResponse).collect(Collectors.toList());
        DungeonResponse dunres = new DungeonResponse("dungeonTestId", "whatever", actual, null, null, null,null);
        //Create a dungeon response and check getentities returns the potion
        List<EntityResponse> expected = getEntities(dunres, "invisibility_potion");
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Test pick up invincibility_potion")
    public void testPickupInvincibilityPotion() {

        DungeonManiaController dmc = new DungeonManiaController();

        //PLAYER    BOULDER

        DungeonResponse intialResponse = dmc.newGame("advanced", "simple");

        DungeonResponse move = dmc.tick(Direction.RIGHT);

        assertEquals(1, getInventory(move, "invincibility_potion").size());
        assertEquals(0, getEntities(move, "invincibility_potion").size());


    }

    @Test
    @DisplayName("Test pick up 2 potions to inventory")
    public void testPickupTwoPotion() {

        DungeonManiaController dmc = new DungeonManiaController();

        //PLAYER    BOULDER

        DungeonResponse intialResponse = dmc.newGame("advanced", "simple");

        DungeonResponse move = dmc.tick(Direction.RIGHT);

        assertEquals(1, getInventory(move, "invincibility_potion").size());
        assertEquals(0, getEntities(move, "invincibility_potion").size());

        DungeonResponse move2 = dmc.tick(Direction.RIGHT);

        assertEquals(1, getInventory(move2, "invisibility_potion").size());
        assertEquals(1, getInventory(move2, "invincibility_potion").size());
        assertEquals(0, getEntities(move2, "invisibility_potion").size());


    }

    @Test
    @DisplayName("Test pick up invincibility_potion and consume it")
    public void testConsumeInvincibilityPotion() throws IllegalArgumentException, InvalidActionException {

        DungeonManiaController dmc = new DungeonManiaController();

        //PLAYER    BOULDER

        DungeonResponse intialResponse = dmc.newGame("advanced", "simple");

        DungeonResponse move = dmc.tick(Direction.RIGHT);
        ItemResponse res = getInventory(move, "invincibility_potion").get(0);
        DungeonResponse consume = dmc.tick(res.getId());

        assertEquals(0, getInventory(consume, "invincibility_potion").size());
        assertEquals(0, getEntities(consume, "invincibility_potion").size());

    }

    @Test
    @DisplayName("Test pick up invincibility_potion and consume it at any tick")
    public void testConsumeInvincibilityPotionAnyTick() throws IllegalArgumentException, InvalidActionException {

        DungeonManiaController dmc = new DungeonManiaController();

        //PLAYER    BOULDER

        DungeonResponse intialResponse = dmc.newGame("advanced", "simple");

        DungeonResponse move = dmc.tick(Direction.RIGHT);
        ItemResponse res = getInventory(move, "invincibility_potion").get(0);
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.LEFT);
        DungeonResponse consume = dmc.tick(res.getId());

        assertEquals(0, getInventory(consume, "invincibility_potion").size());
        assertEquals(0, getEntities(consume, "invincibility_potion").size());

    }

    @Test
    @DisplayName("Test pick up 2 potions and consume them")
    public void testConsumetwoPotion() throws IllegalArgumentException, InvalidActionException {

        DungeonManiaController dmc = new DungeonManiaController();

        //PLAYER    BOULDER

        DungeonResponse intialResponse = dmc.newGame("d_twoPotion", "c_testPotion");

        DungeonResponse move = dmc.tick(Direction.RIGHT);
        DungeonResponse move2 = dmc.tick(Direction.RIGHT);
        ItemResponse res = getInventory(move, "invincibility_potion").get(0);
        DungeonResponse consume = dmc.tick(res.getId());
        ItemResponse res2 = getInventory(move2, "invisibility_potion").get(0);
        DungeonResponse consume2 = dmc.tick(res2.getId());

        assertEquals(0, getInventory(consume2, "invincibility_potion").size());
        assertEquals(0, getEntities(consume2, "invincibility_potion").size());
        assertEquals(0, getInventory(consume2, "invisibility_potion").size());
        assertEquals(0, getEntities(consume2, "invisibility_potion").size());

        move = dmc.tick(Direction.RIGHT);
        move = dmc.tick(Direction.RIGHT);
        move = dmc.tick(Direction.RIGHT);
        move = dmc.tick(Direction.RIGHT);


    }
    
    
}
