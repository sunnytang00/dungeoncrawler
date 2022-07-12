package dungeonmania.util;

import dungeonmania.Entity;
import dungeonmania.StaticEntities.*;
import dungeonmania.movingEntity.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import netscape.javascript.JSObject;
import java.io.InputStream;

public class JSONMap {

    private static ArrayList<Entity> initialMapEntities = new ArrayList<Entity>();
    private static String goals;

    public JSONMap(InputStream is) {

        JSONTokener tokener = new JSONTokener(is);
        JSONObject object = new JSONObject(tokener);

        // complex goals are not handled yet
        JSONMap.goals = object.getJSONObject("goal-condition").toString();

        JSONArray entitiesJSON = object.getJSONArray("entities");
        for (int i = 0; i < entitiesJSON.length(); i++) {
            String type = entitiesJSON.getJSONObject(i).getString("type");
            int x = entitiesJSON.getJSONObject(i).getInt("x");
            int y = entitiesJSON.getJSONObject(i).getInt("y");
            Position pos = new Position(x,y);
            initialiseMapEntities(type, pos);
        }
        
    }

    private void initialiseMapEntities(String type, Position position) {
        Entity entity = null;
        switch (type) {
            case "player":
                entity = new Player(type, position, true);
            case "wall":
                entity = new Wall(type, position);
            case "exit":
                entity = new Exit(type, position);
            case "boulder":
                entity = new Boulder(type, position);
            case "switch":
                entity = new FloorSwitch(type, position);
            case "door":
                entity = new Door(type, position);
            case "portal":
                entity = new Portal(type, position);
            case "zombie_toast_spawner":
                entity = new ZombieToastSpawner(type, position);
            case "spider":
                entity = new Spider(type, position, true);
            case "zombie_toast":
                entity = new ZombieToast(type, position, true);
            case "mercenary":
                entity = new Mercenary(type, position, true);
            case "treasure":
                entity = new Treasure(type, position);
            case "key":
                entity = new Key(type, position);
            case "invincibility_potion":
                entity = new InvincibilityPotion(type, position);
            case "invisibility_potion":
                entity = new InvisibilityPotion(type, position);
            case "wood":
                entity = new Wood(type, position);
            case "arrow":
                entity = new Arrow(type, position);
            case "bomb":
                entity = new Bomb(type, position);
            case "sword":
                entity = new Sword(type, position);
            case "bow":
                entity = new Bow(type, position);
            case "shield":
                entity = new Shield(type, position);
        }
        initialMapEntities.add(entity);
    }

    public List<Entity> getInitialMapEntities() {
        return initialMapEntities;
    }

    public String getGoals() {
        return goals;
    }
 
}
