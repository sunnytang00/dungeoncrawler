package dungeonmania;

import java.util.UUID;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public abstract class Entity {
    private String id;
    private String type;
    private Position position;
    protected boolean isInteractable;
    private String colour;

    public Entity(String type, Position position) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.position = position;
        this.isInteractable = false;
    }

    public Entity(String type, Position position, String colour) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.position = position;
        this.isInteractable = false;
        this.colour = colour;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isInteractable() {
        return isInteractable;
    }

    public void setInteractable(boolean isInteractable) {
        this.isInteractable = isInteractable;
    }

    public EntityResponse getEntityResponse() {
        return new EntityResponse(id, type, position, isInteractable);
    }

    public String getColour() {
        return colour;
    }

}
