package dungeonmania.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.DungeonMap;
import dungeonmania.entities.Entity;
import dungeonmania.entities.StaticEntities.*;
import dungeonmania.entities.StaticEntities.logicSwitches.*;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.entities.collectableEntities.potions.*;
import dungeonmania.entities.movingEntity.enemies.*;
import dungeonmania.entities.movingEntity.player.*;
import dungeonmania.util.Position;

public class ResetGame {

    JSONObject object;
    DungeonMap map;
    private Player player;
    private List<Entity> mapEntities = new ArrayList<Entity>();

    public ResetGame(JSONObject object, DungeonMap map) {
        this.object = object;
        this.map = map;
    }

    public List<Entity> getMapEntities() {
        return mapEntities;
    }

    public DungeonMap reloadMap() {
        JSONArray entitiesJSON = object.getJSONArray("entities");
        
        // initialise entities 
        for (int i = 0; i < entitiesJSON.length(); i++) {
            JSONObject obj = entitiesJSON.getJSONObject(i);
            String type = obj.getString("type");
            initialiseMapEntities(type, obj);
        } 
        map.setMapEntities(mapEntities);

        // current key player is holding
        Player player = map.getPlayer();
        JSONObject currKeyJSON = object.getJSONObject("curr_key");
        if (player != null && currKeyJSON.getBoolean("hasKey")) {
            String type = currKeyJSON.getString("type");
            Position position = new Position(currKeyJSON.getInt("x"), currKeyJSON.getInt("y")); 
            Key currKey = new Key(type, position, currKeyJSON.getInt("key"));
            currKey.setId(currKeyJSON.getString("id"));
            player.setCurrKey(currKey);
        }

        return map;
    }

    private void initialiseMapEntities(String type, JSONObject obj) {
        int x = obj.getInt("x");
        int y = obj.getInt("y");
        String id = obj.getString("id");
        Position position = new Position(x,y);
        Entity entity = null;

        switch(type) {
            case "player":
                player = new Player(type, position, obj.getBoolean("interactable")); 
                player.setSlayedEnemy(obj.getInt("slayed-enemies"));
                player.setId(id);
                entity = player;
                initialiseState(obj.getString("state"), entity);
                ((Player) entity).setHealth(obj.getInt("health")); break;
            case "older_player": 
                JSONArray arr = obj.getJSONArray("remaining_movements");
                List<Position> movements = new ArrayList<Position>();
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject j = arr.getJSONObject(i);
                    int oldX = j.getInt("x");
                    int oldY = j.getInt("y");
                    Position pos = new Position(oldX, oldY);
                    movements.add(pos);
                }
                entity = new OlderPlayer(type, position, obj.getBoolean("interactable"), movements); 
                ((OlderPlayer) entity).setId(id);
                initialiseState(obj.getString("state"), entity);
                ((OlderPlayer) entity).setHealth(obj.getInt("health")); 
                break;
            case "wall":
                entity = new Wall(type, position); 
                ((Wall) entity).setId(id); break;
            case "exit":
                entity = new Exit(type, position); 
                ((Exit) entity).setId(id); break;
            case "boulder":
                entity = new Boulder(type, position);
                ((Boulder) entity).setId(id); break;
            case "switch":
                FloorSwitch floorSwitch = new FloorSwitch(type, position); 
                floorSwitch.setActivated(obj.getBoolean("is_activated"));
                floorSwitch.setId(id);
                entity = floorSwitch; break;
            case "door":
                entity = new Door(type, position, obj.getInt("key"));
                ((Door) entity).setId(id); break;
            case "door_open":
                entity = new Door(type, position, obj.getInt("key"));
                ((Door) entity).setId(id); break;
            case "portal":
                entity = new Portal(type, position, obj.getString("colour"));
                ((Portal) entity).setId(id); break;
            case "zombie_toast_spawner":
                entity = new ZombieToastSpawner(type, position, obj.getBoolean("interactable"));
                ((ZombieToastSpawner) entity).setId(id); break;
            case "spider":
                int initX = obj.getInt("initial_position_x");
                int initY = obj.getInt("initial_position_y");
                Position initPosition = new Position(initX, initY);
                Spider spider = new Spider(type, initPosition, obj.getBoolean("interactable"));
                boolean isClockwiseMove = obj.getBoolean("is_clockwise_move");
                spider.setId(id);
                spider.setRemainingStuckTicks(obj.getInt("remaining_stuck_tick"));
                spider.setHealth(obj.getInt("health")); 
                spider.setPosition(position);
                spider.setClockwiseMove(isClockwiseMove);
                entity = spider; break;
            case "zombie_toast":
                ZombieToast zt = new ZombieToast(type, position, obj.getBoolean("interactable"));
                zt.setId(id); 
                zt.setRemainingStuckTicks(obj.getInt("remaining_stuck_tick"));
                zt.setHealth(obj.getInt("health")); 
                entity = zt; break;
            case "mercenary":
                Mercenary merc = new Mercenary(type, position, obj.getBoolean("interactable")); 
                merc.setId(id);
                merc.setRemainingStuckTicks(obj.getInt("remaining_stuck_tick"));
                merc.setHealth(obj.getInt("health"));  
                entity = merc;
                initialiseState(obj.getString("state"), entity); break;
            case "assassin":
                Assassin assassin = new Assassin(type, position, obj.getBoolean("interactable")); 
                assassin.setId(id);
                assassin.setRemainingStuckTicks(obj.getInt("remaining_stuck_tick"));
                assassin.setHealth(obj.getInt("health"));  
                entity = assassin;
                initialiseState(obj.getString("state"), entity); break;
            case "hydra":
                Hydra hydra = new Hydra(type, position, obj.getBoolean("interactable")); 
                hydra.setId(id);
                hydra.setRemainingStuckTicks(obj.getInt("remaining_stuck_tick"));
                hydra.setHealth(obj.getInt("health")); 
                entity = hydra; break;
            case "treasure":
                entity = new Treasure(type, position);
                ((Treasure) entity).setId(id); break;
            case "sun_stone":
                entity = new SunStone(type, position);
                ((SunStone) entity).setId(id); break; 
            case "key":
                entity = new Key(type, position, obj.getInt("key"));
                ((Key) entity).setId(id); break;
            case "invincibility_potion":
                entity = new InvincibilityPotion(type, position);
                ((InvincibilityPotion) entity).setId(id); break;
            case "invisibility_potion":
                entity = new InvisibilityPotion(type, position);
                ((InvisibilityPotion) entity).setId(id); break;
            case "wood":
                entity = new Wood(type, position);
                ((Wood) entity).setId(id); break;
            case "arrow":
                entity = new Arrows(type, position);
                ((Arrows) entity).setId(id); break;
            case "bomb":
                Bomb bomb = new Bomb(type, position); 
                bomb.setId(id);
                bomb.setActivated(obj.getBoolean("is_active"));
                bomb.setPickable(obj.getBoolean("is_pickable"));
                entity = bomb; break;
            case "sword":
                entity = new Sword(type, position);
                ((Sword) entity).setId(id); break;
            case "swamp_tile":
                entity = new SwampTile(type, position, obj.getInt("movement_factor"));
                ((SwampTile) entity).setId(id); break;
            case "time_turner":
                entity = new TimeTurner(type, position);
                ((TimeTurner) entity).setId(id); break;
            case "time_travelling_portal":
                entity = new TimeTravellingPortal(type, position);
                ((TimeTravellingPortal) entity).setId(id); break;
            case "light_bulb_off":
                entity = new LightBulb(type, position, Helper.getLogic(obj.getString("logic")));
                ((LightBulb) entity).setId(id); break;
            case "light_bulb_on":
                entity = new LightBulb(type, position, Helper.getLogic(obj.getString("logic")));
                ((LightBulb) entity).setId(id); break;
            case "wire":
                entity = new Wire(type, position, Helper.getLogic(obj.getString("logic")));
                ((Wire) entity).setId(id); break;
            case "switch_door":
                entity = new SwitchDoor(type, position, Helper.getLogic(obj.getString("logic")), obj.getInt("key"));
                ((SwitchDoor) entity).setId(id); break;
        }
        mapEntities.add(entity);
    }

    private void initialiseState(String state, Entity e) {
        
        if (state.contains("PlayerDefaultDtate")) {
            if (e instanceof Player) {
                ((Player) e).setState(new PlayerDefaultState());
            } else if (e instanceof OlderPlayer) {
                ((OlderPlayer) e).setState(new PlayerDefaultState());
            }
        } else if (state.contains("InvisibleState")) {
            if (e instanceof Player) {
                ((Player) e).setState(new InvisibleState());
            } else if (e instanceof OlderPlayer) {
                ((OlderPlayer) e).setState(new InvisibleState());
            }
        } else if (state.contains("InvincibleState")) {
            if (e instanceof Player) {
                ((Player) e).setState(new InvisibleState());
            } else if (e instanceof OlderPlayer) {
                ((OlderPlayer) e).setState(new InvisibleState());
            }
        } else if (state.contains("MercBribedState")) {
            ((Mercenary) e).setState(new MercBribedState());
        } else if (state.contains("MercViciousState")) {
            ((Mercenary) e).setState(new MercViciousState());
        }
    }
    
}
