package dungeonmania.StaticEntities;

import dungeonmania.StaticEntity;
import dungeonmania.util.Position;

public class SwampTile extends StaticEntity {
    private int movementFactor;
    
    public SwampTile(String type, Position position, int movementFactor) {
        super(type, position);
        setMovementFactor(movementFactor);
    }
    public int getMovementFactor() {
        return movementFactor;
    }
    public void setMovementFactor(int movementFactor) {
        this.movementFactor = movementFactor;
    }

}