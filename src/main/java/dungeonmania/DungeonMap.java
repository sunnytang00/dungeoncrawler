package dungeonmania;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.movingEntity.*;
import dungeonmania.response.models.*;
import dungeonmania.util.*;
import dungeonmania.StaticEntities.*;


import java.util.ArrayList;

public class DungeonMap {
    // assume mapEntities contain all current entities on the map
    private List<Entity> mapEntities = new ArrayList<Entity>();
    private String dungeonName;
    private int remainingConditions; 
    private boolean gameWin = false;
    private JSONObject goalsJSON;

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

    public Entity getSwampAtPos(Position position) {
        List<Entity> entitiesAtPos = this.getEntityFromPos(position);
        for (Entity entity : entitiesAtPos) {
            if (entity instanceof SwampTile) {
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

    public List<Entity> getEntitiesFromType(List<Entity> list, String type) {
        return list.stream()
                   .filter(entity -> entity.getType().equals(type))
                   .collect(Collectors.toList ());
    }

    public Entity getEntityFromID(String id) {
        Entity entity = mapEntities.stream()
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
            // do we need assassin as well here
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

    /**
     * 
     * @param wins
     *        true if player wins the game, false if player loses the game
     */
    public void removePlayerFromMap(boolean wins) {
        mapEntities.remove(getPlayer());
        gameWin = wins;
    }

    public boolean gameResult() {
        return gameWin;
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

    public void setRemainingConditions(int num) {
        remainingConditions += num;
    }

    public int getRemainingConditions() {
        return remainingConditions;
    }

    public void setJSONGoals(JSONObject goalsJSON) {
        this.goalsJSON = goalsJSON;
    }

    public JSONObject getJSONGoals() {
        return goalsJSON;
    }
    
    /**
     * Method to spawn spider
     * @param currentTick curent tick 
     * @param spawnTick spider_spawn_rate, read in from the config file
     */
    public Spider spawnSpider(int currentTick, DungeonMap map) {
        int spawnrate = (int) JSONConfig.getConfig("spider_spawn_rate");
        if (spawnrate == 0 ) { return null;}
        if (getPlayer() == null) {return null;}
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

        if (currentTick % spawnrate == 0 && possiblePos != null && possiblePos.size() > 0) {
            return new Spider("spider", getRandomPosition(possiblePos), false);
        }

        return null;
    }

    public Position getRandomPosition(List<Position> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    public boolean hasZombies() {
        for (Entity e : mapEntities) {
            if (e instanceof ZombieToast) {
                return true;
            }
        }
        return false;
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

    public JSONArray mapEntitiesToJSON() {
        JSONArray entitiesJSON = new JSONArray();
        for (Entity e : mapEntities) {
            JSONObject obj = e.toJSON();
            entitiesJSON.put(obj);
        }
        return entitiesJSON;
    }

}

