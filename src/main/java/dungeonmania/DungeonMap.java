package dungeonmania;

import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.movingEntity.*;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
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
}

