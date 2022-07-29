package dungeonmania.util;

import dungeonmania.DungeonMap;
import dungeonmania.Entity;
import dungeonmania.StaticEntities.*;
import dungeonmania.movingEntity.*;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.entities.logicSwitches.*;
import dungeonmania.goals.*;

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
        // JSONgoals = object.getJSONArray("goal-condition");

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
            case "old_player":
                entity = new OlderPlayer(type, position, false); break;
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
                entity = new Bomb(type, position); break;
            case "sword":
                entity = new Sword(type, position); break;
            case "swamp_tile":
                entity = new SwampTile(type, position, obj.getInt("movement_factor")); break;
            case "time_turner":
                // entity = new TimeTurner(type, position); break;
            case "time_travelling_portal":
                entity = new TimeTravellingPortal(type, position); break;
            case "light_bulb_off":
                entity = new LightBulb(type, position, getLogic(obj.getString("logic"))); break;
            case "wire":
                entity = new Wire(type, position, getLogic(obj.getString("logic"))); break;
            case "switch_door":
                entity = new SwitchDoor(type, position, getLogic(obj.getString("logic")), obj.getInt("key")); break;

        }
        initialMapEntities.add(entity);
    }

    public List<Entity> getInitialMapEntities() {
        return initialMapEntities;
    }

    public JSONObject getGoals() {
        return JSONgoals;
    }

    private LogicEnum getLogic(String logic) {
        switch(logic) {
            case "and":
                return LogicEnum.AND;
            case "or":
                return LogicEnum.OR;
            case "xor":
                return LogicEnum.XOR;
            case "co_and":
                return LogicEnum.CO_AND;
        }
        return null;
    }

}
