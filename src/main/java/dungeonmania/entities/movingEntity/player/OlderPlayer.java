package dungeonmania.entities.movingEntity.player;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.DungeonMap;
import dungeonmania.entities.Item;
import dungeonmania.entities.movingEntity.enemies.Enemy;
import dungeonmania.entities.movingEntity.enemies.OlderPlayerMovement;
import dungeonmania.util.Position;

public class OlderPlayer extends Enemy {

    private PlayerState state;
    private List<Position> movements;
    
    public OlderPlayer(String type, Position position, boolean isInteractable, List<Position> movements) {
        super(type, position, isInteractable);
        this.movements = movements;
        setMovingStrategy(new OlderPlayerMovement());
    }

    public PlayerState getState() {
        return state;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

    public List<Position> getMovements() {
        return movements;
    }

    public void setMovements(List<Position> movements) {
        this.movements = movements;
    }

    public int remainingTimeTravel() {
        return movements.size();
    }

    public Position getMovingPosition() {
        Position movingOnto = null;
        if (movements.size() != 0) {
            movingOnto = movements.get(0);
        }
        this.movements.remove(movingOnto);
        return movingOnto;
    }

    @Override
    public void move(Enemy movingEntity, DungeonMap map) {
        this.stuckOnSwamp(map);
        if (getRemainingStuckTicks() == 0) {
            getMovingStrategy().move(this, map);  
        } 
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("type", "older_player");
        obj.put("id", getId());
        obj.put("x", getXCoordinate());
        obj.put("y", getYCoordinate());

        JSONArray arr = new JSONArray();
        for (Position p : movements) {
            JSONObject pos = new JSONObject();
            pos.put("x", p.getX());
            pos.put("y", p.getY());
            arr.put(pos);
        }
        obj.put("remaining_movements", arr);
        return obj;
    }
    
}
