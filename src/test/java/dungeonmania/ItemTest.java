package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import dungeonmania.StaticEntities.*;
import dungeonmania.entities.collectableEntities.Key;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.movingEntity.Player;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.countEntityOfType;
import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestHelpers.assertListAreEqualIgnoringOrder;


public class ItemTest {

    @Test
    public void PickUpItemTest() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse initialResponse = dmc.newGame("d_testPickUpItems", "c_battleTests_basicMercenaryMercenaryDies");

        EntityResponse initPlayer = getPlayer(initialResponse).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(0, 1), false);

        // move player downward
        DungeonResponse actualDungeonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = getPlayer(actualDungeonRes).get();

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);

        expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(0, 2), false);

        // move player downward
        actualDungeonRes = dmc.tick(Direction.DOWN);
        actualPlayer = getPlayer(actualDungeonRes).get();

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
        
        expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(0, 3), false);

        // move player downward
        actualDungeonRes = dmc.tick(Direction.DOWN);
        actualPlayer = getPlayer(actualDungeonRes).get();

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
        //List<ItemResponse> itemList = new ArrayList<ItemResponse>();

        //Does this work? Am getting thing from the dungeon response, do I need to find another way to get the items
        // itemList.addAll(getInventory(actualDungeonRes, "treasure"));
        // itemList.addAll(getInventory(actualDungeonRes, "key"));
        // itemList.addAll(getInventory(actualDungeonRes, "invisibility_potion"));
        assertEquals(getInventory(actualDungeonRes, "key").size(), 1);
        assertEquals(getInventory(actualDungeonRes, "treasure").size(), 1);
        assertEquals(getInventory(actualDungeonRes, "invisibility_potion").size(), 1);
        // itemList.add(new ItemResponse(id, "treasure"));
        // itemList.add(new ItemResponse(id, "key"));
        // itemList.add(new ItemResponse(id, "invisibility_potion"));
        //assertEquals(itemList, actualDungeonRes.getInventory());
        //assertEquals(itemList, actualDungeonRes.getInventory());

        
    }

    @Test
    public void TestBuildShield() throws IllegalArgumentException, InvalidActionException {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse initialResponse = dmc.newGame("build_shield", "c_battleTests_basicMercenaryMercenaryDies");

        EntityResponse initPlayer = getPlayer(initialResponse).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 1), false);

        // move player downward
        DungeonResponse actualDungeonRes = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = getPlayer(actualDungeonRes).get();

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);

        expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(3, 1), false);

        // move player downward
        actualDungeonRes = dmc.tick(Direction.RIGHT);
        actualPlayer = getPlayer(actualDungeonRes).get();

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);

        expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(4, 1), false);

        // move player downward
        actualDungeonRes = dmc.tick(Direction.RIGHT);
        actualPlayer = getPlayer(actualDungeonRes).get();

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);

        List<String> buildables = new ArrayList<>();
        buildables.add("shield");
        assertListAreEqualIgnoringOrder(buildables, actualDungeonRes.getBuildables());


        actualDungeonRes = dmc.build("shield");
        ItemResponse shield = new ItemResponse("1", "shield");
        assertEquals(shield.getType(), actualDungeonRes.getInventory().get(0).getType());

    }

    @Test
    public void TestBuildBow() throws IllegalArgumentException, InvalidActionException {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse initialResponse = dmc.newGame("build_bow", "c_battleTests_basicMercenaryMercenaryDies");

        EntityResponse initPlayer = getPlayer(initialResponse).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 1), false);

        // move player right
        DungeonResponse actualDungeonRes = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = getPlayer(actualDungeonRes).get();

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);

        expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(3, 1), false);

        // move player right
        actualDungeonRes = dmc.tick(Direction.RIGHT);
        actualPlayer = getPlayer(actualDungeonRes).get();

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);

        expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(4, 1), false);

        // move player right
        actualDungeonRes = dmc.tick(Direction.RIGHT);
        actualPlayer = getPlayer(actualDungeonRes).get();

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);

        actualDungeonRes = dmc.tick(Direction.RIGHT);

        // assert after movement

        List<String> buildables = new ArrayList<>();
        buildables.add("bow");
        assertListAreEqualIgnoringOrder(buildables, actualDungeonRes.getBuildables());


        actualDungeonRes = dmc.build("bow");
        ItemResponse bow = new ItemResponse("1", "bow");
        assertEquals(bow.getType(), actualDungeonRes.getInventory().get(0).getType());

        //Check getBuildables does not return a bow as we don't have the materials
        actualDungeonRes = dmc.getDungeonResponseModel();
        buildables.remove(0);
        assertListAreEqualIgnoringOrder(buildables, actualDungeonRes.getBuildables());


    }

    @Test
    public void TestBuildBoth() throws IllegalArgumentException, InvalidActionException {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse initialResponse = dmc.newGame("testBuildBowShield", "c_battleTests_basicMercenaryMercenaryDies");

        DungeonResponse move = dmc.tick(Direction.RIGHT);
        for (int i = 0; i < 6; i++) {//move right 6 times, 0 1 2 3 4 5
            move = dmc.tick(Direction.RIGHT);
        }

        List<String> buildables = new ArrayList<>();
        buildables.add("bow");
        buildables.add("shield");
        assertListAreEqualIgnoringOrder(buildables, move.getBuildables());
        
        move = dmc.build("bow");
        buildables.remove(0);
        assertListAreEqualIgnoringOrder(buildables, move.getBuildables());

        move = dmc.build("shield");
        buildables.remove(0);
        assertListAreEqualIgnoringOrder(buildables, move.getBuildables());

    }




}

