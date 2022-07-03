package dungeonmania;

import java.util.UUID;

import dungeonmania.util.Position;

public abstract class Entity {
    private String id;
    private String type;
    private Position position;
    private boolean isInteractable;

    public Entity(String id, String type, Position position, boolean isInteractable) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.position = position;
        this.isInteractable = isInteractable;
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


}
