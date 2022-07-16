package dungeonmania.util;

import dungeonmania.DungeonMap;
import dungeonmania.Entity;
import dungeonmania.StaticEntities.*;
import dungeonmania.movingEntity.*;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.goals.*;

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

    private ArrayList<Entity> initialMapEntities = new ArrayList<Entity>();
    private JSONObject JSONgoals;
    // private JSONArray JSONgoals;

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
            case "treasure":
                entity = new Treasure(type, position); break;
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
        }
        initialMapEntities.add(entity);
    }

    public List<Entity> getInitialMapEntities() {
        return initialMapEntities;
    }

    public JSONObject getGoals() {
        return JSONgoals;
    }

    public Goals getComposedGoals(JSONObject goals, DungeonMap map) {
        switch(goals.getString("goal")) {
            case "AND":
                // CompositeGoal compositeAndGoal = new CompositeAnd();
                JSONArray subgoalsAnd = goals.getJSONArray("subgoals");
                // for (int i = 0; i < subgoals1.length(); ++i) {
                //     JSONObject subgoal = subgoals1.getJSONObject(i);
                //     compositeAndGoal.composeGoal(getComposedGoals(subgoal));
                // }
                CompositeGoal compositeAndGoal = new CompositeAnd(getComposedGoals(subgoalsAnd.getJSONObject(0), map),
                                                                  getComposedGoals(subgoalsAnd.getJSONObject(1), map));
                return compositeAndGoal;
            // case "OR":
            //     CompositeGoal compositeOrGoal = new CompositeOr();
            //     JSONArray subgoals2 = goals.getJSONArray("subgoals");
            //     for (int i = 0; i < subgoals2.length(); ++i) {
            //         JSONObject subgoal = subgoals2.getJSONObject(i);
            //         compositeOrGoal.composeGoal(getComposedGoals(subgoal));
            //     }
            //     return compositeOrGoal;
            case "exit":
                return new GetExit(map);
            case "enemies":
                return new DestroyEnemy(map);
            case "boulders":
                return new BoulderOnSwitch(map);
            case "treasure":
                return new CollectTreasure(map);
        }
        return null;
    }

}
