package dungeonmania.movingEntity;

import java.util.Arrays;

import dungeonmania.DungeonMap;
import dungeonmania.util.Direction;
import dungeonmania.util.JSONConfig;
import dungeonmania.util.Position;

public class Player extends MovingEntity {

    private static final int DEFAULT_BRIBE_AMOUNT = JSONConfig.bribe_amount;

    private boolean isInvisible;
    private boolean isInvincible;
    private Position prevPosition;
    private int wealth;

    public Player(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        //TODO Auto-generated constructor stub
        this.isInvisible = false;
        this.isInvincible = false;
        this.prevPosition = null;
        this.wealth = 0; // imitially has not collected any treasure
        setNonTraversibles(Arrays.asList("wall", "door"));
    }


    public boolean isInvisible() {
        return isInvisible;
    }

    public void setInvisible(boolean isInvisible) {
        this.isInvisible = isInvisible;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    public void setInvincible(boolean isInvincible) {
        this.isInvincible = isInvincible;
    }

    public Position getPrevPosition() {
        return prevPosition;
    }

    public void setPrevPosition() {
        Direction newD = null;
        // can we use equals for comparing direction
        if (getDirection().equals(Direction.UP)) {
            newD = Direction.DOWN;
        } else if (getDirection().equals(Direction.DOWN)) {
            newD = Direction.UP;
        } else if (getDirection().equals(Direction.LEFT)) {
            newD = Direction.RIGHT;
        } else if (getDirection().equals(Direction.RIGHT)) {
            newD = Direction.LEFT;
        }
        this.prevPosition = getPosition().translateBy(newD);

    }

    
    public int getWealth() {
        return wealth;
    }


    public void setWealth(int wealth) {
        this.wealth = wealth;
    }


    public boolean hasEnoughToBribe() {
        boolean enoughWealth = false;
        if (this.wealth >= DEFAULT_BRIBE_AMOUNT) {
            enoughWealth = true;
        }
        return enoughWealth;
    }
}
