package dungeonmania.util;

import dungeonmania.entities.Entity;
import dungeonmania.entities.StaticEntities.*;
import dungeonmania.entities.buildableEntities.Bow;
import dungeonmania.entities.buildableEntities.ItemBuildable;
import dungeonmania.entities.buildableEntities.MidnightArmour;
import dungeonmania.entities.buildableEntities.Sceptre;
import dungeonmania.entities.buildableEntities.Shield;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.entities.collectableEntities.potions.InvincibilityPotion;
import dungeonmania.entities.collectableEntities.potions.InvisibilityPotion;
import dungeonmania.goals.*;
import dungeonmania.entities.movingEntity.*;
import dungeonmania.entities.movingEntity.enemies.Assassin;
import dungeonmania.entities.movingEntity.enemies.Hydra;
import dungeonmania.entities.movingEntity.enemies.Mercenary;
import dungeonmania.entities.movingEntity.enemies.Spider;
import dungeonmania.entities.movingEntity.enemies.ZombieToast;
import dungeonmania.entities.movingEntity.player.Player;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityFactory {


    public static final List<Entity> extractEntities(JSONObject jsonInfo) {
        // Extract JSON
        JSONArray entitiesInfo = jsonInfo.getJSONArray("entities");

        // Extract entities
        List<Entity> entities = new ArrayList<>();

        Entity playerEntity = null;
        for (int i = 0; i < entitiesInfo.length(); i++) {
            JSONObject entityInfo = entitiesInfo.getJSONObject(i);
            if (entityInfo.getString("type").startsWith("player")) {
                playerEntity = extractEntity(entityInfo);
                entities.add(playerEntity);
            }
        }

        if (playerEntity != null && playerEntity instanceof Player) {
            for (int i = 0; i < entitiesInfo.length(); i++) {
                JSONObject entityInfo = entitiesInfo.getJSONObject(i);
                if (entityInfo.getString("type").startsWith("player")) continue;
                Entity entity = extractEntity(entityInfo);
                if (entity != null) entities.add(entity);
            }
        }
        return entities;
    }

    /**
     * Maps a JSONObject for an entity to its Entity object
     *
     * @param entityInfo entity to extract
     *
     * @return Entity object for given entity
     */
    public static final Entity extractEntity(JSONObject entityInfo) {
        
        Position position = new Position(entityInfo.getInt("x"), entityInfo.getInt("y"));
        String type = entityInfo.getString("type");
        if (type.startsWith("wall")) {
            return new Wall("wall", position);
        } else if (type.startsWith("exit")) {
            return new Exit("exit", position);
        } else if (type.startsWith("switch")) {
            return new FloorSwitch("floor_switch", position);
        } else if (type.startsWith("boulder")) {
            return new Boulder("boulder",position);
        } else if (type.startsWith("door")) {
            int key = entityInfo.getInt("key");
            return new Door("door", position, key);
        } else if (type.startsWith("time_travelling_portal")) {
            return new TimeTravellingPortal("time_travelling_portal", position);
        } else if (type.startsWith("portal")) {
            String colour = entityInfo.getString("colour");
            return new Portal("portal", position, colour);
        } else if (type.startsWith("zombie_toast_spawner")) {
            return new ZombieToastSpawner("zombie_toast_spanwer",position, false);
        } else if (type.startsWith("treasure")) {
            return new Treasure("treasure", position);
        } else if (type.startsWith("key")) {
            int key = entityInfo.getInt("key");
            return new Key("key", position, key);
        } else if (type.startsWith("invincibility_potion")) {
            return new InvincibilityPotion("invincibility_potion", position);
        } else if (type.startsWith("invisibility_potion")) {
            return new InvisibilityPotion("invisibility_potion", position);
        } else if (type.startsWith("wood")) {
            return new Wood("woord", position);
        } else if (type.startsWith("arrow")) {
        } else if (type.startsWith("bomb")) {
            return new Bomb("bom", position);
        } else if (type.startsWith("sword")) {
            return new Sword("sword", position);
        } else if (type.startsWith("armour")) {
        } else if (type.startsWith("one_ring")) {
        } else if (type.startsWith("spider")) {
            return new Spider("spider", position, false);
        } else if (type.startsWith("zombie_toast")) {
            return new ZombieToast("zombie_toast", position, false);
        } else if (type.startsWith("mercenary")) {
            return new Mercenary("mercenary", position, false);
        } else if (type.startsWith("assassin")) {
            return new Assassin("assassin", position, false);
        } else if (type.startsWith("hydra")) {
            return new Hydra("hydra", position, false);
        } else if (type.startsWith("swamp_tile")) {
            return new SwampTile("swamp_tile",position, entityInfo.getInt("movement_factor"));
        } else if (type.startsWith("sun_stone")) {
            return new SunStone("sun_stone", position);
        } else if (type.startsWith("time_turner")) {
            return new TimeTurner("time_turner", position);
        } else if (type.startsWith("player")) {
            return new Player("player", position, false);
        }
        return null;
    }

    public static final Goals extractGoal(JSONObject dungeon) {
        return (dungeon.has("goal-condition"))
                ? doExtractGoal(dungeon.getJSONObject("goal-condition"))
                : null;
    }

    public static final Goals doExtractGoal(JSONObject json) {
        switch (json.getString("goal")) {
            case "enemies":
                return new DestroyEnemy();
            case "treasure":
                return new CollectTreasure();
            case "exit":
                return new GetExit();
            case "boulders":
                return new BoulderOnSwitch();
            case "AND":
                CompositeGoal and = new CompositeAnd();
                JSONArray andSubgoals = json.getJSONArray("subgoals");
                for (int i = 0; i < andSubgoals.length(); i++) {
                    JSONObject subgoal = andSubgoals.getJSONObject(i);
                    and.addGoal(doExtractGoal(subgoal));
                }
                return and;
            case "OR":
                CompositeGoal or = new CompositeOr();
                JSONArray orSubgoals = json.getJSONArray("subgoals");
                for (int i = 0; i < orSubgoals.length(); i++) {
                    JSONObject subgoal = orSubgoals.getJSONObject(i);
                    or.addGoal(doExtractGoal(subgoal));
                }
                return or;
            default:
                return null;
        }
    }

    public static List<ItemBuildable> allBuildables() {
        Map<String, ItemBuildable> map = new HashMap<>();
        map.put("bow", new Bow("bow"));
        map.put("shield", new Shield("shield"));
        map.put("sceptre", new Sceptre("sceptre"));
        map.put("midnight_armour", new MidnightArmour("midnight_armour"));
        return new ArrayList<>(map.values());
    }
}
