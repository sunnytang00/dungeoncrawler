package dungeonmania;

import dungeonmania.util.Position;

public class StaticEntity extends Entity {

    protected boolean isTraversable;

    public StaticEntity(String type, Position position) {
        super(type, position);
        this.isTraversable = false;
    }

    public boolean isTraversable() {
        return isTraversable;
    }

}
