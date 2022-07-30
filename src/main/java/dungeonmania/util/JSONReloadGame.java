package dungeonmania.util;

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
import dungeonmania.goals.Goals;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
 *              "initial_x": <int>,
 *              "initial_y": <int>,
 *              "is_clockwise": <boolean>,
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
 *              "type": <swamp-tile>,
 *              "id": ,
 *              "x": ,
 *              "y": ,
 *              "movement_factor": <int>
 *          },
 *      ], 
 *      "goal-condition": {
 *          "goal": 
 *          "subgoals": [
 *                          {"goal": },
 *                          {"goal": }
 *                      ]
 *      }, 
 *      "inventory": [
 *          {
 *              "type": , 
 *              "id": ,
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
 *      
 *      "config-file": {"file_name": },
 *      "remaining-goal-conditions": {"remains": <int>},
 *      
 * }
 */


public class JSONReloadGame {

    private Player player;
    private List<Entity> mapEntities = new ArrayList<Entity>();
    private List<Item> inventory = new ArrayList<Item>();
    private PotionQueue potions = new PotionQueue();
    private JSONObject JSONgoals;
    private Goals goals; 
    private DungeonMap newMap;

    public List<Entity> getMapEntities() {
        return mapEntities;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public JSONObject getGoals() {
        return JSONgoals;
    }

    public DungeonMap getReloadedMap() {
        return newMap;
    }

    public JSONReloadGame(InputStream is, String name) {
        JSONTokener tokener = new JSONTokener(is);
        JSONObject object = new JSONObject(tokener);

        newMap = new DungeonMap(mapEntities, name);
        newMap = resetGame(object, newMap);

        // restore inventory 
        JSONArray inventoryJSON = object.getJSONArray("inventory");
        for (int i = 0; i < inventoryJSON.length(); i++) {
            JSONObject obj = inventoryJSON.getJSONObject(i);
            setInventory(obj);
        }
        Player player = (Player) mapEntities.stream().filter(e -> e.getType().equals("player")).findAny().orElse(null);
        player.setInventory(inventory);
        
        // restore potion queue
        JSONArray potionsJSON = object.getJSONArray("potion-queue");
        for (int i = 0; i < potionsJSON.length(); i++) {
            JSONObject obj = potionsJSON.getJSONObject(i);
            setPotionQueue(obj);
        }
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

        // restore battle queue
        JSONArray battlesJSON = object.getJSONArray("battle-queue");

        // reset current tick
        int tickJSON = object.getJSONObject("tick").getInt("current_tick");
        // game.setCurrtick

        // restore time travel memories
        JSONObject timeTravelJSON = object.getJSONObject("time-travel");
        int timeTravelTick = timeTravelJSON.getInt("time_travel_tick");  
        JSONArray histories = timeTravelJSON.getJSONArray("tick_histories");

        // restore config file
        String configFile = object.getJSONObject("config-file").getString("file_name");
        JSONConfig.setConfig(configFile);
    }

    public DungeonMap resetGame(JSONObject object, DungeonMap map) {
        JSONArray entitiesJSON = object.getJSONArray("entities");
        JSONgoals = object.getJSONObject("goal-condition");
        int remaingingGoalConditions = object.getJSONObject("remaining-goal-conditions").getInt("remains");
        
        // initialise entities 
        for (int i = 0; i < entitiesJSON.length(); i++) {
            JSONObject obj = entitiesJSON.getJSONObject(i);
            String type = obj.getString("type");
            initialiseMapEntities(type, obj);
        } 
        // reset remaining goal conditions 
        map.setRemainingConditions(remaingingGoalConditions);
        // reset goals at current tick 
        map.resetGoals(JSONgoals, map);
        
        return map;
    }


    private void initialiseMapEntities(String type, JSONObject obj) {
        int x = obj.getInt("x");
        int y = obj.getInt("y");
        String id = obj.getString("id");
        Position position = new Position(x,y);
        Entity entity = null;

        switch(type) { // assumption: id of entities will change
            case "player":
                player = new Player(type, position, obj.getBoolean("interactable")); 
                player.setSlayedEnemy(obj.getInt("slayed-enemies"));
                entity = player;
                initialiseState(obj.getString("state"), entity);
                ((Player) entity).setHealth(obj.getInt("health")); break;
            case "older_player": 
                entity = new OlderPlayer(type, position, obj.getBoolean("interactable")); 
                initialiseState(obj.getString("state"), entity);
                ((Player) entity).setHealth(obj.getInt("health")); 
                break;
            case "wall":
                entity = new Wall(type, position); break;
            case "exit":
                entity = new Exit(type, position); break;
            case "boulder":
                entity = new Boulder(type, position); break;
            case "switch":
                entity = new FloorSwitch(type, position); break;
            case "door":
                entity = new Door(type, position, obj.getInt("key")); break;
            case "door_open":
                entity = new Door(type, position, obj.getInt("key")); break;
            case "portal":
                entity = new Portal(type, position, obj.getString("colour")); break;
            case "zombie_toast_spawner":
                entity = new ZombieToastSpawner(type, position, obj.getBoolean("interactable")); break;
            case "spider":
                Spider spider = new Spider(type, position, obj.getBoolean("interactable"));
                spider.setRemainingStuckTicks(obj.getInt("remaining_stuck_tick"));
                spider.setHealth(obj.getInt("health")); 
                entity = spider; break;
            case "zombie_toast":
                ZombieToast zt = new ZombieToast(type, position, obj.getBoolean("interactable")); 
                zt.setRemainingStuckTicks(obj.getInt("remaining_stuck_tick"));
                zt.setHealth(obj.getInt("health")); 
                entity = zt; break;
            case "mercenary":
                Mercenary merc = new Mercenary(type, position, obj.getBoolean("interactable")); 
                merc.setRemainingStuckTicks(obj.getInt("remaining_stuck_tick"));
                merc.setHealth(obj.getInt("health"));  
                entity = merc;
                initialiseState(obj.getString("state"), entity); break;
            case "assassin":
                Assassin assassin = new Assassin(type, position, obj.getBoolean("interactable")); 
                assassin.setHealth(obj.getInt("health"));  
                entity = assassin;
                initialiseState(obj.getString("state"), entity); break;
            case "hydra":
                Hydra hydra = new Hydra(type, position, obj.getBoolean("interactable")); 
                hydra.setHealth(obj.getInt("health")); 
                entity = hydra; break;
            case "treasure":
                entity = new Treasure(type, position); break;
            case "sun_stone":
                entity = new SunStone(type, position); break; 
            case "key":
                entity = new Key(type, position, obj.getInt("key")); break;
            case "invincibility_potion":
                entity = new InvincibilityPotion(type, position); break;
            case "invisibility_potion":
                entity = new InvisibilityPotion(type, position); break;
            case "wood":
                entity = new Wood(type, position); break;
            case "arrow":
                entity = new Arrows(type, position); break;
            case "bomb":
                Bomb bomb = new Bomb(type, position); 
                bomb.setActivated(obj.getBoolean("is_active"));
                bomb.setPickable(obj.getBoolean("is_pickable"));
                entity = bomb; break;
            case "sword":
                entity = new Sword(type, position); break;
            case "swamp_tile":
                entity = new SwampTile(type, position, obj.getInt("movement_factor")); break;
            case "time_turner":
                // entity = new TimeTurner(type, position); break;
            case "time_travelling_portal":
                entity = new TimeTravellingPortal(type, position); break;
            case "light_bulb_off":
                entity = new LightBulb(type, position, Helper.getLogic(obj.getString("logic"))); break;
            case "light_bulb_on":
                entity = new LightBulb(type, position, Helper.getLogic(obj.getString("logic"))); break;
            case "wire":
                entity = new Wire(type, position, Helper.getLogic(obj.getString("logic"))); break;
            case "switch_door":
                entity = new SwitchDoor(type, position, Helper.getLogic(obj.getString("logic")), obj.getInt("key")); break;
        }
        mapEntities.add(entity);
    }

    private void initialiseState(String state, Entity e) {
        switch(state) {
            case "PlayerDefaultDtate":
                ((Player) e).setState(new PlayerDefaultState());
            case "InvisibleState":
                ((Player) e).setState(new InvisibleState());
            case "InvincibleState":
                ((Player) e).setState(new InvisibleState());
            case "MercBribedState":
                ((Mercenary) e).setState(new MercBribedState());
            case "MercViciousState":
                ((Mercenary) e).setState(new MercViciousState());
        }
    }

    private void setInventory(JSONObject obj) {
        String type = obj.getString("type");
        String id = obj.getString("id");
        Integer durability = obj.getInt("durability");
        Entity entity = mapEntities.stream().filter(e -> e.getId().equals(id)).findAny().orElse(null);

        switch(type) {
            case "treasure":
                inventory.add((Treasure) entity); break;
            case "sun_stone":
                inventory.add((SunStone) entity); break; 
            case "key":
                inventory.add((Item) entity); break;
            case "invincibility_potion":
                inventory.add((InvincibilityPotion) entity); 
                ((InvincibilityPotion) entity).setDurability(durability); break;
            case "invisibility_potion":
                inventory.add((InvisibilityPotion) entity);  
                ((InvisibilityPotion) entity).setDurability(durability); break;
            case "wood":
                inventory.add((Wood) entity); break;
            case "arrow":
                inventory.add((Arrows) entity);  
                ((Arrows) entity).setDurability(durability); break;
            case "bomb":
                inventory.add((Bomb) entity); break;
            case "sword":
                inventory.add((Sword) entity);
                ((Sword) entity).setDurability(durability); break;
            case "shield":
                Shield shield = new Shield(type);
                inventory.add(shield);
                (shield).setDurability(durability); break;
            case "bow":
                Bow bow = new Bow(type);
                inventory.add(bow);
                (bow).setDurability(durability); break;
            case "midnight_armour":
                MidnightArmour armour = new MidnightArmour(type);
                inventory.add(armour);
                (armour).setDurability(durability); break;
            case "scepture":
                Sceptre sceptre = new Sceptre(type);
                inventory.add(sceptre);
                (sceptre).setDurability(durability); break;
            case "time_turner":
                // inventory.add((TimeTurner) entity); break;
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

}
