package dungeonmania;

import dungeonmania.util.Position;

public class StaticEntity extends Entity {

    protected boolean isTraversable;

    public StaticEntity(String type, Position position) {
        super(type, position);
        this.isTraversable = false;
    }

    public StaticEntity(String type, Position position, String colour) {
        super(type, position, colour);
        this.isTraversable = true;
    }

    public boolean isTraversable() {
        return isTraversable;
    }

    public void setTraversable(boolean isTraversable) {
        this.isTraversable = isTraversable;
    }

}
