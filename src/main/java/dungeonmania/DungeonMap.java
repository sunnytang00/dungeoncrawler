package dungeonmania;

import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.movingEntity.*;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;
import dungeonmania.util.FileLoader;
import dungeonmania.util.JSONMap;
import dungeonmania.util.Position;
import dungeonmania.Entity;
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
    private List<Entity> mapEntities;
    private String dungeonName;

    public DungeonMap(List<Entity> mapEntities, String dungeonName) {
        this.mapEntities = mapEntities;
        this.dungeonName = dungeonName;
    }

    public List<Entity> getMapEntities() {
        return mapEntities;
    }

    public List<Entity> getEntityFromPos(Position position) {
        List<Entity> currEntity = mapEntities.stream()
                            .filter(entity -> position.equals(entity.getPosition())).toList();
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

    public List<EntityResponse> getEntityResponses() {
        return mapEntities.stream().map(Entity::getEntityResponse).collect(Collectors.toList());
    }

    public String getDungeonName() {
        return dungeonName;
    }
}
