package dungeonmania.util;

import dungeonmania.DungeonGame;
import dungeonmania.DungeonManiaController;
import dungeonmania.DungeonMap;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Item;
import dungeonmania.entities.StaticEntities.*;
import dungeonmania.entities.StaticEntities.logicSwitches.*;
import dungeonmania.entities.buildableEntities.*;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.entities.collectableEntities.potions.InvincibilityPotion;
import dungeonmania.entities.collectableEntities.potions.InvisibilityPotion;
import dungeonmania.entities.movingEntity.enemies.*;
import dungeonmania.entities.movingEntity.player.*;
import dungeonmania.goals.*;
import dungeonmania.goals.Goals;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.metal.MetalBorders.ScrollPaneBorder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Reload games from a list of saved json files 
 * File structure as below: 
 * {
 *      "tick": {"current_tick": <int>},
 *      "entities": [
 *          {
 *              "type": <player>,
 *              "id": <String>,
 *              "x": <double>,
 *              "y": <double>,
 *              "interactable": <boolean>,
 *              "state": <String>,
 *              "health": <int> , 
 *              "slayed-enemies": {"number": <int>}
 *          }, {
 *              "type": <old_player>,
 *              "id": <String>,
 *              "x": <double>,
 *              "y": <double>,
 *              "interactable": <boolean>,
 *              "state": <String>,
 *              "health": <int> 
 *          }, {"type": <merc>,
 *              "id": <String>,
 *              "x": <double>,
 *              "y": <double>,
 *              "interactable": <boolean>,
 *              "state": <String>,
 *              "health": <int>, 
 *              "remaining_stuck_tick": <int> 
 *          }, {
 *              "type": <spider>,
 *              "id": <String>,
 *              "x": <double>,
 *              "y": <double>,
 *              "interactable": <boolean>,
 *              "initial_position_x": <int>,
 *              "initial_potition_y": <int>,
 *              "is_clockwise_move": <boolean>,
 *              "health": ,
 *              "remaining_stuck_tick": <int>
 *          }, {
 *              "type": <other moving entities - enemies>,
 *              "id": <String>,
 *              "x": <double>,
 *              "y": <double>,
 *              "interactable": <boolean>,
 *              "health": ,
 *              "remaining_stuck_tick": <int>
 *          }, {
 *              "type": <bomb>,
 *              "id": ,
 *              "x": ,
 *              "y": , 
 *              "is_active": <boolean> , 
 *              "is_pickable": <boolean>,
 *          }, {
 *              "type": <collectables / static entities / time travel portal>,
 *              "id": ,
 *              "x": ,
 *              "y":
 *          }, {
 *              "type": <key / door / door_open>,
 *              "id": ,
 *              "x": ,
 *              "y": ,
 *              "key": ,
 *          }, { 
 *              "type": <portal>,
 *              "id": ,
 *              "x": ,
 *              "y": ,
 *              "colour":
 *          }, { 
 *              "type": <switch>,
 *              "id": ,
 *              "x": ,
 *              "y": ,
 *              "is_triggered":
 *          }, {
 *              "type": <swamp_tile>,
 *              "id": ,
 *              "x": ,
 *              "y": ,
 *              "movement_factor": <int>
 *          }, {
 *              "type": <light_bulb_on / light_bulb_off>,
 *              "id": ,
 *              "x": ,
 *              "y": ,
 *              "movement_factor": <int>
 *          }
 *      ], 
 *      "goal-condition": {
 *          "goal": 
 *          "subgoals": [
 *                          {"goal": 
 *                           "prev_is_achieved": },
 *                          {"goal": }
 *                      ]
 *      }, 
 *      "inventory": [
 *          {
 *              "type": , 
 *              "id": ,
 *              "x": ,
 *              "y": , 
 *              "durability": <shield/ sword/ bow/ sceptre>, 
 *          }, {
 *              
 *          }
 *      ], 
 *      "potion-queue": [
 *          {
 *              "type": <invisibility_potion / invincibility_potion>,
 *              "id": ,
 *              "durability": ,
 *          }, 
 *          {
 *          }
 *      ], 
 *      "time-travel": [
 *          "time_travel_tick": <an integer indicating where the time travel is up to,
 *                               if no time travel, set to 0>,
 *          "tick_histories": [ // list of games for previous ticks
 *              {
 *                  
 *              }, 
 *          ]
 *      ], 
 *      "battle-queue": [
 *          {
 *              "enemy": <String>, 
 *              "initial_player_health": <int>,
 *              "initial_enemy_health": <int>,
 *              "rounds": [
 *                  "delta_player_health": <int>,
 *                  "delta_enemy_health": <int>, 
 *                  "weaponry_used": [
 *                      "id": <String> // have to store the actual item instance from this given id
 *                  ]
 *               ]
 *          }, {
 *          }
 *      ],
 *      "dungeon": [
 *          {"dungeon_id": <String>}, 
 *          {"dungeon_name": <String>}
 *      ],
 *      "config-file": {"file_name": },
 *      "remaining-goal-conditions": <int>,
 *      "curr_key": {
 *                      "type": <key / door / door_open>,
 *                      "id": ,
 *                      "x": ,
 *                      "y": ,
 *                      "key": ,
 *                  }
 * }
 */


public class JSONReloadGame {

    private Player player;
    private List<Entity> mapEntities = new ArrayList<Entity>();
    private List<Battle> battles = new ArrayList<Battle>();
    private List<Item> inventory = new ArrayList<Item>();
    private PotionQueue potions = new PotionQueue();
    private JSONObject JSONgoals;
    private DungeonMap newMap;
    private DungeonGame newGame;
    private Goals goals;

    public List<Entity> getMapEntities() {
        return mapEntities;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public JSONObject getJSONGoals() {
        return JSONgoals;
    }

    public DungeonMap getReloadedMap() {
        return newMap;
    }

    public DungeonGame getReloadedGame() {
        return newGame;
    }

    public Goals getGoals() {
        return goals;
    }

    public JSONReloadGame(InputStream is, String name) {
        JSONTokener tokener = new JSONTokener(is);
        JSONObject object = new JSONObject(tokener);

        JSONObject dungeon = object.getJSONObject("dungeon");
        String dungeonName = dungeon.getString("dungeon_name");
        String dungeonId = dungeon.getString("dungeon_id");

        newMap = new DungeonMap(mapEntities, dungeonName);

        ResetGame resetGame = new ResetGame(object, newMap);
        newMap = resetGame.reloadMap();
        mapEntities = resetGame.getMapEntities();

        // reset remaining goal conditions 
        JSONgoals = object.getJSONObject("goal-condition");
        int remaingingGoalConditions = object.getInt("remaining-goal-conditions");
        newMap.setRemainingConditions(remaingingGoalConditions);
        // reset goals at current tick 
        goals = resetGoals(JSONgoals, newMap, object);
        newMap.setJSONGoals(JSONgoals);
        newMap.resetGoals(goals);

        // restore inventory 
        JSONArray inventoryJSON = object.getJSONArray("inventory");
        for (int i = 0; i < inventoryJSON.length(); i++) {
            JSONObject obj = inventoryJSON.getJSONObject(i);
            setInventory(obj);
        }
        player = newMap.getPlayer();
        player.setInventory(inventory);
        
        // restore potion queue
        JSONArray potionsJSON = object.getJSONArray("potion-queue");
        for (int i = 0; i < potionsJSON.length(); i++) {
            JSONObject obj = potionsJSON.getJSONObject(i);
            setPotionQueue(obj);
        }
        if (potions.queueSize() != 0) {
            player.setPotionQueue(potions);
            if (potions.potionInUse() == null) { // no potion in use right now
                player.setInvincible(false);
                player.setInvisible(false);
            } else if (potions.potionInUse() instanceof InvincibilityPotion) {
                player.setInvincible(true);
                player.setInvisible(false);
            } else {
                player.setInvincible(false);
                player.setInvisible(true);
            }
        }
        // reset battle responses
        JSONArray battlesJSON = object.getJSONArray("battle-queue");
        for (int i = 0; i < battlesJSON.length(); i++) {
            JSONObject obj = battlesJSON.getJSONObject(i);
            setBattles(obj);
        }
        // reset game 
        newGame = new DungeonGame(newMap.goalString(), inventory, battles, player.getBuildables(newMap), newMap);
        newGame.setDungeonId(dungeonId);

        // reset current tick
        int tick = object.getJSONObject("tick").getInt("current_tick");
        newGame.setCurrentTick(tick);

        // restore time travel memories
        JSONObject timeTravelJSON = object.getJSONObject("time-travel");
        int timeTravelTick = timeTravelJSON.getInt("time_travel_tick");  
        JSONArray historyJSON = timeTravelJSON.getJSONArray("tick_histories");
        newGame.setTimeTravelTick(timeTravelTick);
        List<JSONObject> historyList = new ArrayList<JSONObject>();
        for (int i = 0; i < historyJSON.length(); i++) {
            JSONObject obj = historyJSON.getJSONObject(i);
            historyList.add(obj);
        }
        newGame.resetTickHistory(historyList);

        // restore config file
        String configFile = object.getJSONObject("config-file").getString("file_name");
        JSONConfig.setConfig(configFile);
    }

    private void setInventory(JSONObject obj) {
        String type = obj.getString("type");
        String id = obj.getString("id");
        int x = obj.getInt("x");
        int y = obj.getInt("y");
        Position position = new Position(x, y);
        Integer durability = obj.getInt("durability");

        switch(type) {
            case "treasure":
                Treasure treasure = new Treasure(type, position);
                treasure.setId(id);
                inventory.add(treasure); break;
            case "sun_stone":
                SunStone sunStone = new SunStone(type, position);
                sunStone.setId(id);
                inventory.add(sunStone); break; 
            case "key":
                Key key = new Key(type, position, obj.getInt("key_id"));
                key.setId(id);
                inventory.add(key); break;
            case "invincibility_potion":
                InvincibilityPotion invincibilityPotion = new InvincibilityPotion(type, position);
                invincibilityPotion.setId(id);
                invincibilityPotion.setDurability(durability);
                inventory.add(invincibilityPotion); break;
            case "invisibility_potion":
                InvisibilityPotion invisibilityPotion = new InvisibilityPotion(type, position);
                invisibilityPotion.setId(id);
                invisibilityPotion.setDurability(durability);
                inventory.add(invisibilityPotion); break;
            case "wood":
                Wood wood = new Wood(type, position);
                wood.setId(id);
                inventory.add(wood); break;
            case "arrow":
                Arrows arrow = new Arrows(type, position);  
                arrow.setId(id);
                arrow.setDurability(durability); 
                inventory.add(arrow); break;
            case "bomb":
                Bomb bomb = new Bomb(type, position);
                bomb.setId(id);
                inventory.add(bomb); break;
            case "sword":
                Sword sword = new Sword(type, position);
                sword.setId(id);
                sword.setDurability(durability);
                inventory.add(sword);
                break;
            case "shield":
                Shield shield = new Shield(type);
                shield.setId(id);
                inventory.add(shield);
                (shield).setDurability(durability); break;
            case "bow":
                Bow bow = new Bow(type);
                bow.setId(id);
                inventory.add(bow);
                (bow).setDurability(durability); break;
            case "midnight_armour":
                MidnightArmour armour = new MidnightArmour(type);
                armour.setId(id);
                inventory.add(armour); break;
            case "scepture":
                Sceptre sceptre = new Sceptre(type);
                sceptre.setId(id);
                inventory.add(sceptre);
                (sceptre).setDurability(durability); break;
            case "time_turner":
                TimeTurner timeTurner = new TimeTurner(type, position);
                timeTurner.setId(id);
                inventory.add(timeTurner); break;
        }
    }

    private void setPotionQueue(JSONObject obj) {
        String type = obj.getString("type");
        String id = obj.getString("id");
        Integer durability = obj.getInt("durability");
        Entity entity = mapEntities.stream().filter(e -> e.getId().equals(id)).findAny().orElse(null);

        switch(type) {
            case "invincibility_potion":
                potions.addPotionToQueue((InvincibilityPotion) entity); 
                ((InvincibilityPotion) entity).setDurability(durability); break;
            case "invisibility_potion":
                potions.addPotionToQueue((InvisibilityPotion) entity);  
                ((InvisibilityPotion) entity).setDurability(durability); break;
        }
    }

    private void setBattles(JSONObject obj) {
        JSONArray roundsJSON = obj.getJSONArray("rounds");
        List<Round> rounds = new ArrayList<Round>();
        for (int i = 0; i < roundsJSON.length(); i++) {
            JSONObject r = roundsJSON.getJSONObject(i);
            JSONArray weapons = r.getJSONArray("weaponry_used");
            List<Item> weaponryUsed = getWeaponsUsed(weapons);
            Round round = new Round(r.getInt("get_delta_player_health"), r.getInt("get_delta_enemy_health"), weaponryUsed);
            rounds.add(round);
        }
        Battle b = new Battle(obj.getString("enemy"), rounds, obj.getInt("intial_player_health"), obj.getInt("initial_enemy_health"));
        battles.add(b);
    }

    private List<Item> getWeaponsUsed(JSONArray weapons) {
        List<Item> wUsed = new ArrayList<Item>();
        for (int i = 0; i < weapons.length(); i++) {
            JSONObject w = weapons.getJSONObject(i);
            String id = w.getString("id");
            Item item = getItemFromInventory(id);
            if (item != null) {
                wUsed.add((Item) item);
            }
        }
        return wUsed;
    }

    private Item getItemFromInventory(String id) {
        return inventory.stream().filter(e -> e.getId().equals(id)).findAny().orElse(null);
    }

    private Goals resetGoals(JSONObject goals, DungeonMap map, JSONObject obj) {
        switch(goals.getString("goal")) {
            case "AND":
                JSONArray subgoalsAnd = goals.getJSONArray("subgoals");
                CompositeGoal compositeAndGoal = new CompositeAnd(resetGoals(subgoalsAnd.getJSONObject(0), map, obj),
                                                                  resetGoals(subgoalsAnd.getJSONObject(1), map, obj));
                return compositeAndGoal;
            case "OR":
                JSONArray subgoalsOr = goals.getJSONArray("subgoals");
                CompositeGoal compositeOrGoal = new CompositeOr(resetGoals(subgoalsOr.getJSONObject(0), map, obj),
                                                                resetGoals(subgoalsOr.getJSONObject(1), map, obj));
                return compositeOrGoal;
            case "exit":
                return new GetExit(map, goals.getBoolean("prev_is_achieved"));
            case "enemies":
                return new DestroyEnemy(map, goals.getBoolean("prev_is_achieved"));
            case "boulders":
                return new BoulderOnSwitch(map, goals.getBoolean("prev_is_achieved"));
            case "treasure":
                return new CollectTreasure(map, goals.getBoolean("prev_is_achieved"));
        }
        return null;
    }
}
