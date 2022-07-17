package dungeonmania.movingEntity;

import dungeonmania.Entity;
import dungeonmania.StaticEntities.*;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class MovingEntity extends Entity  {

    private Position initialPosition;
    private Direction direction;
    private double health;
    private double attack;
    private double defence;
    private List<String> nontraversibles = new ArrayList<String>();

    public MovingEntity(String type, Position position, boolean isInteractable) {
        super(type, position);
        this.initialPosition = position;
        this.nontraversibles = null;
    }
    

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Position getInitialPosition() {
        return initialPosition;
    }
    
    public boolean blockedBy(List<Entity> atAdj) {
        for (Entity entity : atAdj) {
            if (getNonTraversibles().contains(entity.getType())) {
                return true;
            }
        }
        return false;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;

    }

    public double getAttack() {
        return attack;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }
    

    public double getDefence() {
        return defence;
    }


    public void setDefence(double defence) {
        this.defence = defence;
    }


    public List<String> getNonTraversibles() {
        return nontraversibles;
    }

    public void setNonTraversibles(List<String> nontraversibles) {
        this.nontraversibles = nontraversibles;
    }
    
    
}
