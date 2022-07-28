package dungeonmania.util;

import dungeonmania.Entity;
import dungeonmania.StaticEntities.*;
import dungeonmania.movingEntity.*;
import dungeonmania.entities.Item;
import dungeonmania.entities.buildableEntities.*;
import dungeonmania.entities.collectableEntities.*;
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
 *      "entities": [
 *          {
 *              "type": <player / old_player / merc>,
 *              "id": <String>,
 *              "x": <double>,
 *              "y": <double>,
 *              "interactable": <boolean>,
 *              "state": <String>,
 *              "health": 
 *          }, {
 *              "type": <other moving entities - enemies>,
 *              "id": <String>,
 *              "x": <double>,
 *              "y": <double>,
 *              "interactable": <boolean>,
 *              "health": 
 *          },  {
 *              "type": <collectables / static entities / time travel portal>,
 *              "id": ,
 *              "x": ,
 *              "y":
 *          }, {
 *              "type": <key / door>,
 *              "id": ,
 *              "x": ,
 *              "y": ,
 *              "key": ,
 *          },{ 
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
 *              "movement_factor": 
 *          },
 *      ], 
 *      "goal-condition": {
 *          "goal": 
 *          "subgoals": [
 *                          {"goal": },
 *                          {"goal": }
 *                      ]
 *       }, 
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
 *              "duration": ,
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
 *          <not too sure if we have to store previous battles> 
 *      ],
 *      
 *      "config-file": {"file_name": },
 *      "remaining-goal-conditions": {"remains": <int>},
 *      "slayed-enemies": {"number": <int>}
 * }
 */


public class JSONReloadGame {

    private Player player;
    private List<Entity> mapEntities = new ArrayList<Entity>();
    private List<Item> inventory = new ArrayList<Item>();
    private PotionQueue potions = new PotionQueue();
    private JSONObject JSONgoals;
    private Goals goals; 

    public List<Entity> getMapEntities() {
        return mapEntities;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public JSONObject getGoals() {
        return JSONgoals;
    }

    public JSONReloadGame(InputStream is) {

        JSONTokener tokener = new JSONTokener(is);
        JSONObject object = new JSONObject(tokener);

        JSONgoals = object.getJSONObject("goal-condition");
        JSONArray entitiesJSON = object.getJSONArray("entities");
        JSONArray inventoryJSON = object.getJSONArray("inventory");
        JSONArray potionsJSON = object.getJSONArray("potion-queue");
        // JSONArray battlesJSON = object.getJSONArray("battle-queue");
        String configFile = object.getString("config-file");
        
        JSONConfig.setConfig(configFile);
        
        // initialise entities 
        for (int i = 0; i < entitiesJSON.length(); i++) {
            JSONObject obj = entitiesJSON.getJSONObject(i);
            String type = obj.getString("type");
            initialiseMapEntities(type, obj);
        }   
        // restore inventory 
        for (int i = 0; i < inventoryJSON.length(); i++) {
            JSONObject obj = inventoryJSON.getJSONObject(i);
            setInventory(obj);
        }
        Player player = (Player) mapEntities.stream().filter(e -> e.getType().equals("player")).findAny().orElse(null);
        player.setInventory(inventory);

        // restore potion queue
        for (int i = 0; i < potionsJSON.length(); i++) {
            JSONObject obj = potionsJSON.getJSONObject(i);
            setPotionQueue(obj);
        }
        player.setPotionQueue(potions);
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
                entity = player;
                initialiseState(obj.getString("state"), entity);
                ((Player) entity).setHealth(obj.getInt("health")); break;
            case "older_player": // do not know how old player is defined
                // entity = new Player(type, position, obj.getBoolean("interactable")); 
                // ((Player) entity).setHealth(obj.getInt("health")); 
                // break;
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
            case "portal":
                entity = new Portal(type, position, obj.getString("colour")); break;
            case "zombie_toast_spawner":
                entity = new ZombieToastSpawner(type, position, obj.getBoolean("interactable")); break;
            case "spider":
                entity = new Spider(type, position, obj.getBoolean("interactable"));
                ((Spider) entity).setHealth(obj.getInt("health")); break;
            case "zombie_toast":
                entity = new ZombieToast(type, position, obj.getBoolean("interactable")); 
                initialiseState(obj.getString("state"), entity);
                ((Player) entity).setHealth(obj.getInt("health"));  break;
            case "mercenary":
                entity = new Mercenary(type, position, obj.getBoolean("interactable")); 
                initialiseState(obj.getString("state"), entity);
                ((Mercenary) entity).setHealth(obj.getInt("health"));  break;
            case "assassin":
                entity = new Assassin(type, position, obj.getBoolean("interactable")); 
                initialiseState(obj.getString("state"), entity);
                ((Assassin) entity).setHealth(obj.getInt("health"));  break;
            case "hydra":
                entity = new Hydra(type, position, obj.getBoolean("interactable")); 
                initialiseState(obj.getString("state"), entity);
                ((Hydra) entity).setHealth(obj.getInt("health"));  break;
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
                entity = new Bomb(type, position); break;
            case "sword":
                entity = new Sword(type, position); break;
            case "swamp_tile":
                // entity = new SwampTile(type, position, obj.getInt("movement_factor")); break;
            case "time_turner":
                // entity = new TimeTurner(type, position); break;
            case "time_travelling_portal":
                // entity = new TimeTravellingPortal(type, position); break;
            case "light_bulb_off":
                // entity = new LightBulb(type, position); break;
            case "wire":
                // entity = new Wire(type, position); break;
            case "switch_door":
                // entity = new SwitchDoor(type, position); break;
        }
        mapEntities.add(entity);
    }

    private void initialiseState(String state, Entity e) {
        switch(state) {
            case "player_default_state":
                ((Player) e).setState(new PlayerDefaultState());
            case "invisible_state":
                ((Player) e).setState(new InvisibleState());
            case "invincible_state":
                ((Player) e).setState(new InvisibleState());
            case "merc_bribed_state":
                ((Mercenary) e).setState(new MercBribedState());
            case "merc_vicious_state":
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
                ((InvincibilityPotion) entity).setPotionDuration(durability); break;
            case "invisibility_potion":
                inventory.add((InvisibilityPotion) entity);  
                ((InvisibilityPotion) entity).setPotionDuration(durability); break;
            case "wood":
                inventory.add((Wood) entity); break;
            case "arrow":
                inventory.add((Arrows) entity);  
                ((Arrows) entity).setUsedTimes(durability); break;
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
                // MidnightArmour arnour = new MidnightArmour(type);
                // inventory.add(armour);
                // (armour).setDurability(durability); break;
            case "scepture":
                // Scepture scepture = new Scepture(type);
                // inventory.add(scepture);
                // (scepture).setDurability(durability); break;
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
                ((InvincibilityPotion) entity).setPotionDuration(durability); break;
            case "invisibility_potion":
                potions.addPotionToQueue((InvisibilityPotion) entity);  
                ((InvisibilityPotion) entity).setPotionDuration(durability); break;
        }
    }

}
