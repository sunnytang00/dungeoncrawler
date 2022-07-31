package dungeonmania.entities.StaticEntities;

import org.json.JSONObject;

import dungeonmania.entities.StaticEntity;
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

    @Override
    public JSONObject toJSON() {
        JSONObject obj = super.toJSON();
        obj.put("movement_factor", movementFactor);
        return obj;
    }

}