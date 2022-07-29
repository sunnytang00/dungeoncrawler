package dungeonmania.entities;

import java.util.UUID;

import org.json.JSONObject;

import dungeonmania.DungeonGame;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public abstract class Entity {
    private String id;
    private String type;
    private Position position;
    protected boolean isInteractable;
    private String colour;

    public Entity(String type) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
    }

    public Entity(String type, Position position) {
        this(type);
        this.position = position;
        this.isInteractable = false;
    }

    public Entity(String type, Position position, String colour) {
        this(type, position);
        this.colour = colour;
    }

    public Entity(String type, Position position, boolean isInteractable) {
        this(type, position);
        this.isInteractable = isInteractable;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Position getPosition() {
        return position;
    }

    public int getXCoordinate() {
        return position.getX();
    }

    public int getYCoordinate() {
        return position.getY();
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public EntityResponse getEntityResponse() {
        return new EntityResponse(id, type, position, isInteractable);
    }

    public String getColour() {
        return colour;
    }

    public boolean isInteractable() {
        return isInteractable;
    }

    public void setInteractable(boolean isInteractable) {
        this.isInteractable = isInteractable;
    }

    public boolean getInteractable() {
        return isInteractable;
    }

    public JSONObject toJSON() {
        JSONObject entity = new JSONObject();
        entity.put("type", getType());
        entity.put("id", getId());
        entity.put("x", getXCoordinate());
        entity.put("y", getYCoordinate());

        return entity;
    }

    public JSONObject toJSON(String mode) {
        JSONObject entity = new JSONObject();
        entity.put("type", getType());
        entity.put("id", getId());
        return entity;
    }

    public void tick(DungeonGame game) {
    
    }

}
