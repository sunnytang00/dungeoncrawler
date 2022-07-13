package dungeonmania.movingEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.DungeonMap;
import dungeonmania.Entity;
import dungeonmania.StaticEntities.*;
import dungeonmania.entities.Item;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.JSONConfig;
import dungeonmania.util.Position;
import dungeonmania.entities.*;

public class Player extends MovingEntity {

    private static final int DEFAULT_BRIBE_AMOUNT = JSONConfig.getConfig("bribe_amount");
    private static final int DEFAULT_PLAYER_HEALTH = JSONConfig.getConfig("player_health");
    private static final int DEFAULT_PLAYER_ATTACK = JSONConfig.getConfig("player_attack");

    private boolean isInvisible;
    private boolean isInvincible;
    private Position prevPosition;
    private int wealth;
    private PlayerState state;
    private List<Item> inventory = new ArrayList<Item>();

    public Player(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        //TODO Auto-generated constructor stub
        this.isInvisible = false;
        this.isInvincible = false;
        this.prevPosition = null;
        this.setHealth(DEFAULT_PLAYER_HEALTH);
        this.setAttack(DEFAULT_PLAYER_ATTACK);
        this.wealth = 0; // initially has not collected any treasure
        this.setState(new PlayerDefaultState());
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
        int totalTreasure = (int) inventory.stream().filter(i -> i instanceof Treasure).count();
        
        return totalTreasure;
    }


    public boolean hasEnoughToBribe() {
        boolean enoughWealth = false;
        if (this.wealth >= DEFAULT_BRIBE_AMOUNT) {
            enoughWealth = true;
        }
        return enoughWealth;
    }

    

    public PlayerState getState() {
        return state;
    }


    public void setState(PlayerState state) {
        this.state = state;
    }


    public List<Item> getInventory() {
        return inventory;
    }


    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }


    public void move(DungeonMap map, Direction direction) {
        
        boolean blocked = false;

        this.setDirection(direction);
        Position newPos = getPosition().translateBy(direction);
        List<Entity> encounters = map.getEntityFromPos(newPos);
        // interact
        for (Entity encounter : encounters) {
            interact(encounter);
            if (getNonTraversibles().contains(encounter.getType())) {
                blocked = true;
            }
        }
        if (!blocked) {
            this.setPosition(newPos);
        }
    }

    public void interact(Entity entity) {

        // create interact method in each entity
        if (entity instanceof Boulder) {
            pushBoulder();
        } else if (entity instanceof Exit) {
            // remove exit from goals 
            // remove player from map entities 
        } else if (entity instanceof Item) {
            collectToInventory((Item) entity);
        } else if (entity instanceof Door) {
            // check if door is already opened 
            // check if corresponding key is in inventory 
        } else if (entity instanceof Portal) {
        
        } else if (entity instanceof Enemy) {
            Enemy enemy = (Enemy) entity;
            if (!enemy.becomeAlly()) {
                // could not only bribe when encounter, could also bribe within certain radius
                if (entity instanceof Mercenary && hasEnoughToBribe()) {
                    //bribeMerc();
                } else {
                    battleWithEnemy();
                }
            }
        }

    }

    public void pushBoulder(){

    }

    public void collectToInventory(Item item) {
        inventory.add(item);
    }

    public void consumePotion() {

        // setState()

    }

    public void destorySpawner(){

    }

    public void bribeMerc(Mercenary merc) {
        if (!merc.isBribed() && merc.isInRad() && this.hasEnoughToBribe()) {
            merc.setState(new MercBribedState());
        }
        
        consumeInventory("treasure", DEFAULT_BRIBE_AMOUNT);
    }
    
    public void consumeInventory(String type, int amount) {
        int count = 0;
        while (count < amount) {
            Item delete = null;
            for (Item item : inventory) {
                if (item.getType().equals(type)) {
                    delete = item; 
                }
            
            }
            inventory.remove(delete);
        }
    }

    public void battleWithEnemy(){

    }

    public boolean isAlive() {

        return true;
    }

    public boolean hasKey() {
        return inventory.stream().anyMatch(i -> i.getType() == "key");
    }

    public List<ItemResponse> getInventoryResponses() {
        System.out.print(inventory);
        return inventory.stream().map(Item::getItemResponse).collect(Collectors.toList());
    }

    public List<String> getBuildables() {

        List<String> ret = new ArrayList<String>();
        
        if (canBuildBow()) {
            ret.add("bow");
        }

        if (canBuildShield()) {
            ret.add("shield");
        }
        
        return ret;
    }

    public boolean canBuildShield() {
        
        if (!inventory.isEmpty()) {
            int woodNumber = 0;
            int treasureOrKeyNumber = 0;
            
            for (Item item : inventory) {

                if (item instanceof Wood) {
                    woodNumber++;
                }

                if (item instanceof Treasure || item instanceof Key) {
                    treasureOrKeyNumber++;
                }
            }

            if ((woodNumber >= 2) && (treasureOrKeyNumber >= 1)) {
                return true;
            } else {
                return false;
            }
                
        }
        return false;
    }

    public boolean canBuildBow() {

        if (!inventory.isEmpty()) {
            int woodNumber = 0;
            int arrowsNumber = 0;
            
            for (Item item : inventory) {

                if (item instanceof Wood) {
                    woodNumber++;
                }

                if (item instanceof Arrows) {
                    arrowsNumber++;
                }
            }

            if ((woodNumber >= 1) && (arrowsNumber >= 3)) {
                return true;
            } else {
                return false;
            }
                
        }
        return false;
    }

}
