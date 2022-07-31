package dungeonmania.util;

import dungeonmania.entities.Entity;
import dungeonmania.entities.StaticEntities.*;
import dungeonmania.entities.StaticEntities.logicSwitches.*;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.entities.collectableEntities.potions.InvincibilityPotion;
import dungeonmania.entities.collectableEntities.potions.InvisibilityPotion;
import dungeonmania.entities.movingEntity.enemies.*;
import dungeonmania.entities.movingEntity.player.*;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.InputStream;
import java.security.cert.LDAPCertStoreParameters;

public class JSONMap {

    private ArrayList<Entity> initialMapEntities = new ArrayList<Entity>();
    private JSONObject JSONgoals;

    public JSONMap(InputStream is) {

        JSONTokener tokener = new JSONTokener(is);
        JSONObject object = new JSONObject(tokener);

        // complex goals are not handled yet
        JSONgoals = object.getJSONObject("goal-condition");
        

        JSONArray entitiesJSON = object.getJSONArray("entities");
        for (int i = 0; i < entitiesJSON.length(); i++) {
            JSONObject obj = entitiesJSON.getJSONObject(i);
            String type = obj.getString("type");
            initialiseMapEntities(type, obj);
        }   
    }

    private void initialiseMapEntities(String type, JSONObject obj) {
        int x = obj.getInt("x");
        int y = obj.getInt("y");
        
        Position position = new Position(x,y);
        Entity entity = null;
        switch (type) {
            case "player":
                entity = new Player(type, position, false); break;
            case "wall":
                entity = new Wall(type, position); break;
            case "exit":
                entity = new Exit(type, position); break;
            case "boulder":
                entity = new Boulder(type, position); break;
            case "switch":
                if (obj.has("logic")) {
                    entity = new FloorSwitch(type, position, Helper.getLogic(obj.getString("logic"))); break;
                } else {
                    entity = new FloorSwitch(type, position); break;
                }
            case "door":
                entity = new Door(type, position, obj.getInt("key")); break;
            case "portal":
                entity = new Portal(type, position, obj.getString("colour")); break;
            case "zombie_toast_spawner":
                entity = new ZombieToastSpawner(type, position, true); break;
            case "spider":
                entity = new Spider(type, position, false); break;
            case "zombie_toast":
                entity = new ZombieToast(type, position, false); break;
            case "mercenary":
                entity = new Mercenary(type, position, true); break;
            case "assassin":
                entity = new Assassin(type, position, true); break;
            case "hydra":
                entity = new Hydra(type, position, false); break;
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
                if (obj.has("logic")) {
                    entity = new LogicBomb(type, position, Helper.getLogic(obj.getString("logic")));break;
                } else {
                    entity = new Bomb(type, position); break;
                }
            case "sword":
                entity = new Sword(type, position); break;
            case "swamp_tile":
                entity = new SwampTile(type, position, obj.getInt("movement_factor")); break;
            case "time_turner":
                entity = new TimeTurner(type, position); break;
            case "time_travelling_portal":
                entity = new TimeTravellingPortal(type, position); break;
            case "light_bulb_off":
                entity = new LightBulb(type, position, Helper.getLogic(obj.getString("logic"))); break;
            case "wire":
                entity = new Wire(type, position, LogicEnum.OR); break;
            case "switch_door":
                entity = new SwitchDoor(type, position, Helper.getLogic(obj.getString("logic")), obj.getInt("key")); break;

        }
        initialMapEntities.add(entity);
    }

    public List<Entity> getInitialMapEntities() {
        return initialMapEntities;
    }

    public JSONObject getGoals() {
        return JSONgoals;
    }

}
