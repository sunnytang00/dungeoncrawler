package dungeonmania;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dungeonmania.movingEntity.*;
import dungeonmania.response.models.*;
import dungeonmania.Entity;
import dungeonmania.util.*;
import dungeonmania.StaticEntities.*;


import java.util.ArrayList;

import org.eclipse.jetty.websocket.api.InvalidWebSocketException;
import org.json.JSONArray;
import org.json.JSONML;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import netscape.javascript.JSObject;

import java.io.IOException;
import java.io.InputStream;

public class DungeonMap {
    // assume mapEntities contain all current entities on the map
    private List<Entity> mapEntities = new ArrayList<Entity>();
    private String dungeonName;

    public DungeonMap(List<Entity> mapEntities, String dungeonName) {
        this.mapEntities = mapEntities;
        this.dungeonName = dungeonName;
    }

    public List<Entity> getMapEntities() {
        return mapEntities;
    }

    public void setMapEntities(List<Entity> mapEntities) {
        this.mapEntities = mapEntities;
    }

    public List<Entity> getEntityFromPos(Position position) {
        List<Entity> currEntity = mapEntities.stream()
                            .filter(entity -> position.equals(entity.getPosition())).collect(Collectors.toList ());
        return currEntity;    
    }

    public boolean checkTypeEntityAtPos(String type, Position position) {
        List<Entity> entitiesAtPos = this.getEntityFromPos(position);
        boolean anyMatch = entitiesAtPos.stream().anyMatch(entity -> entity.getType().equals(type));
        return anyMatch;
    }

    public Entity getPortalAtPos(Position position) {
        List<Entity> entitiesAtPos = this.getEntityFromPos(position);
        for (Entity entity : entitiesAtPos) {
            if (entity instanceof Portal) {
                
                return entity;
            }
        }
        return null;
    }
    
    public Player getPlayer() {
        Player player = (Player) mapEntities.stream()
                .filter(entity -> entity.getType().equals("player"))
                .findAny()
                .orElse(null);
        return player;
    }

    public Entity getEntityFromID(String id) {
        Entity entity = (Player) mapEntities.stream()
                .filter(e -> e.getId().equals(id))
                .findAny()
                .orElse(null);
        return entity;
    }

    public List<EntityResponse> getEntityResponses() {
        return mapEntities.stream().map(Entity::getEntityResponse).collect(Collectors.toList());
    }

    public List<BattleResponse> getBattleResponses(List<Battle> battles) {
        return battles.stream().map(Battle::getBattleResponse).collect(Collectors.toList());
    }

    public int getNumOfAlly() {
        int count = 0;
        for (Entity entity : mapEntities) {
            if (entity instanceof Mercenary) {
                Mercenary merc = (Mercenary) entity;
                if (merc.isBribed()) {
                    count+=1;
                }
            }
        }
        return count;
    }

    public String getDungeonName() {
        return dungeonName;
    }
    
    public void addEntityToMap(Entity entity) {
        mapEntities.add(entity);
    }

    public void removeEntityFromMap(Entity entity) {
        mapEntities.remove(entity);
    }

    public boolean checkIfEntityAdjacentIsPushable(Entity entity, Direction direction) {

        List<Entity> entityList = getEntityFromPos(entity.getPosition().translateBy(direction));

        if (containsType(entityList, "boulder") || containsType(entityList, "wall")){
            return false;
        }
        return true;
    }

    public void addEntitiesToMap(List<ZombieToast> entities) {
        mapEntities.addAll(entities);
    }

    /**
     * Method to spawn spider
     * @param currentTick curent tick 
     * @param spawnTick spider_spawn_rate, read in from the config file
     */
    public Spider spawnSpider(int currentTick, DungeonMap map) {
        int spawnrate = JSONConfig.getConfig("spider_spawn_rate");
        if (spawnrate == 0 ) { return null;}

        Position playerPos = getPlayer().getPosition();
        List<Position> adjPos = playerPos.getPositionsWithInBox(7);
        List<Position> possiblePos = new ArrayList<Position>();
        if (adjPos != null && adjPos.size() != 0) {
            for (Position pos : adjPos) {
                List<Entity> atAdj = map.getEntityFromPos(pos);
                if (atAdj == null || !containsType(atAdj, "boulder")) {
                    possiblePos.add(pos);
                }
            }
        }

        if (currentTick % spawnrate == 0 && possiblePos != null) {
            return new Spider("spider", getRandomPosition(possiblePos), false);
        }

        return null;
    }

    public Position getRandomPosition(List<Position> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    public boolean containsType(List<Entity> entities, String type) {
        boolean found = entities.stream().anyMatch(entity -> entity.getType().equals(type));
        return found;    
    }

    public void BoulderSwitchOverlap() {

        for (Entity entity : mapEntities) {
            if (entity instanceof FloorSwitch) {
                FloorSwitch floorSwitch = (FloorSwitch) entity;
                List<Entity> eList = getEntityFromPos(floorSwitch.getPosition());
                for (Entity e : eList) {
                    if (e instanceof Boulder) {
                        floorSwitch.setTriggered(true);
                        break;
                    } else {
                        floorSwitch.setTriggered(false);
                    }
                } 
            }
        }
    }
}

