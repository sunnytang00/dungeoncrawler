package dungeonmania;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.response.models.*;
import dungeonmania.util.*;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Item;
import dungeonmania.entities.StaticEntities.*;
import dungeonmania.entities.StaticEntities.logicSwitches.LogicItem;
import dungeonmania.entities.StaticEntities.logicSwitches.Wire;
import dungeonmania.entities.collectableEntities.Bomb;
import dungeonmania.entities.movingEntity.enemies.*;
import dungeonmania.entities.movingEntity.player.OlderPlayer;
import dungeonmania.entities.movingEntity.player.Player;
import dungeonmania.goals.Goals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class DungeonMap {
    // assume mapEntities contain all current entities on the map
    private List<Entity> mapEntities = new ArrayList<Entity>();
    private String dungeonName;
    private int remainingConditions; 
    private boolean gameWin = false;
    private JSONObject goalsJSON;
    private Goals goals;
    private List<Entity> enemiesToSpawn;

    public DungeonMap(List<Entity> mapEntities, String dungeonName) {
        this.mapEntities = mapEntities;
        this.dungeonName = dungeonName;
        this.enemiesToSpawn = new ArrayList<Entity>();
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

    public Portal getPortalAtPos(Position position) {
        List<Entity> entitiesAtPos = this.getEntityFromPos(position);
        for (Entity entity : entitiesAtPos) {
            if (entity instanceof Portal) {
                return (Portal) entity;
            }
        }
        return null;
    }

    public SwampTile getSwampAtPos(Position position) {
        List<Entity> entitiesAtPos = this.getEntityFromPos(position);
        for (Entity entity : entitiesAtPos) {
            if (entity instanceof SwampTile) {
                return (SwampTile) entity;
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

    public List<Enemy> getEnemies() {
        List<Enemy> enemies = new ArrayList<Enemy>();
        for (Entity entity: mapEntities) {
            if (entity instanceof Enemy) {
                enemies.add((Enemy) entity);
            }
        }
        return enemies;
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

    public boolean checkIfEntityAdjacentIsPushable(Entity entity, Direction direction) {

        List<Entity> entityList = getEntityFromPos(entity.getPosition().translateBy(direction));

        if (containsType(entityList, "boulder") || containsType(entityList, "wall")){
            return false;
        }
        return true;
    }

    public void addEntitiesToMap(List<Entity> entities) {
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


    public String goalString() {
        return goals.getGoalsAsString(this);
    }
    
    /**
     * Method to spawn spider
     * @param currentTick curent tick 
     * @param spawnTick spider_spawn_rate, read in from the config file
     */
    public void spawnSpider(DungeonGame game) {
        int currentTick = game.getCurrentTick();
        int spawnrate = (int) JSONConfig.getConfig("spider_spawn_rate");
        if (spawnrate == 0 ) { return;}
        if (getPlayer() == null) {return;}
        if (!(currentTick % spawnrate == 0)) {return;}
        Position playerPos = getPlayer().getPosition();
        List<Position> adjPos = playerPos.getPositionsWithInBox(7);
        List<Position> possiblePos = new ArrayList<Position>();
        if (adjPos != null) {
            for (Position pos : adjPos) {
                List<Entity> atAdj = getEntityFromPos(pos);
                if (atAdj == null || !containsType(atAdj, "boulder")) {
                    possiblePos.add(pos);
                }
            }
        }

        if (possiblePos != null) {
            Spider spider = new Spider("spider", getRandomPosition(possiblePos), false);
            addEnemyToSpawn(spider);
        }
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

    // change player to older player for time travel
    public void changePlayerToOlder(List<Position> movements) {
        Player player = null;
        OlderPlayer olderPlayer = null;
        for (Entity e : mapEntities) {
            if (e instanceof Player) {
                player = (Player) e;
                olderPlayer = new OlderPlayer("older_player", player.getPosition(), player.getInteractable(), movements);
                olderPlayer.setState(player.getState());
            }
        }
        if (player != null) {
            mapEntities.remove(player);
        }
        if (olderPlayer != null) {
            mapEntities.add(olderPlayer);
        }
    }

    public void BoulderSwitchOverlap() {

        for (Entity entity : mapEntities) {
            if (entity instanceof FloorSwitch) {
                FloorSwitch floorSwitch = (FloorSwitch) entity;
                List<Entity> eList = getEntityFromPos(floorSwitch.getPosition());
                for (Entity e : eList) {
                    if (e instanceof Boulder) {
                        floorSwitch.setActivated(true);
                        break;
                    } else {
                        floorSwitch.setActivated(false);
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

    // public void resetGoals(JSONObject JSONgoals, DungeonMap map) {
    //     goals = JSONLoadGoals.getComposedGoals(JSONgoals, map);
    // }

    public void resetGoals(Goals goals) {
        this.goals = goals;
    }

    public final DungeonResponse getDungeonResponse() {
        Player player = getPlayer();
        return new DungeonResponse(
                UUID.randomUUID().toString(),
                dungeonName,
                mapEntities.stream().map(Entity::getEntityResponse).collect(Collectors.toList()),
                (player != null) ? player.getInventoryResponses() : new ArrayList<ItemResponse>(),
                new ArrayList<>(),
                this.getBuildables(),
                goals.toString()
        );
    }

    private final List<String> getBuildables() {
        Player player = getPlayer();
        if (player == null) return new ArrayList<String>();
        return EntityFactory
                .allBuildables()
                .stream()
                .filter(item -> item instanceof Item && player.checkBuildable(this, item))
                .map(item -> ((Item) item).getType())
                .collect(Collectors.toList());
    }

    


    public void addEnemyToSpawn(Enemy entity) {
        enemiesToSpawn.add(entity);
    }

    public List<Entity> getEnemiesToSpawn() {
        return enemiesToSpawn;
    }

    public void setEnemiesToSpawn(List<Entity> enemiesToSpawn) {
        this.enemiesToSpawn = enemiesToSpawn;
    }

    
}

