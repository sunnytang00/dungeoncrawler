package dungeonmania.movingEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.DungeonGame;
import dungeonmania.DungeonMap;
import dungeonmania.Entity;
import dungeonmania.StaticEntities.*;
import dungeonmania.entities.*;
import dungeonmania.entities.buildableEntities.*;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Battle;
import dungeonmania.util.Direction;
import dungeonmania.util.JSONConfig;
import dungeonmania.util.Position;
import dungeonmania.util.PotionQueue;
import dungeonmania.util.Round;

public class Player extends MovingEntity {

    private boolean isInvisible;
    private boolean isInvincible;
    private PlayerState state;
    private List<Item> inventory = new ArrayList<Item>();
    private List<Enemy> battleQueue = new ArrayList<Enemy>();
    private PotionQueue potionQueue = new PotionQueue();
    private Potion currPotion = null;
    private Key currKey = null;
    private boolean playerWin = false;
    private boolean playerDied = false;
    private int slayedEnemy = 0;

    public Player(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        this.setHealth(JSONConfig.getConfig("player_health"));
        this.setAttack(JSONConfig.getConfig("player_attack"));
        this.setState(new PlayerDefaultState());
        state.playerStateChange(this);
    }

    
    public boolean isPlayerDied() {
        return playerDied;
    }


    public void setPlayerDied(boolean playerDied) {
        this.playerDied = playerDied;
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
        return getPosition().translateBy(newD);

    }

    

    public Potion getCurrPotion() {
        return currPotion;
    }


    public void setCurrPotion(Potion currPotion) {
        this.currPotion = currPotion;
    }


    public Key getCurrKey() {
        for (Item item : inventory) {
            if (item instanceof Key) {
                return (Key) item;
            }
        }
        return null;
    }


    public void setCurrKey(Key currKey) {
        this.currKey = currKey;
    }


    public boolean isPlayerWin() {
        return playerWin;
    }


    public void setPlayerWin(boolean playerWin) {
        this.playerWin = playerWin;
    }


    public int getWealth() {
        int totalTreasure = (int) inventory.stream().filter(i -> i instanceof Treasure).count();
        
        return totalTreasure;
    }

    public int getSlayedEnemy(){
        return slayedEnemy;
    }


    public void setSlayedEnemy(int slayedEnemy) {
        this.slayedEnemy = slayedEnemy;
    }

    public boolean hasEnoughToBribe() {
        boolean enoughWealth = false;
        if (this.getWealth() >= JSONConfig.getConfig("bribe_amount")) {
            enoughWealth = true;
        }
        return enoughWealth;
    }

    

    public void setBattleQueue(List<Enemy> battleQueue) {
        this.battleQueue = battleQueue;
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


    public List<Enemy> getBattleQueue() {
        return battleQueue;
    }

    public void move(DungeonGame game, DungeonMap map, Direction direction) {


        boolean blocked = false;

        this.setDirection(direction);
        Position newPos = getPosition().translateBy(direction);
        List<Entity> encounters = map.getEntityFromPos(newPos);

        // interact with non-moving entities 
        for (Entity encounter : encounters) {

            if (!isInvisible() && !(encounter instanceof Enemy)) {
                blocked = interactWithEntities(encounter, map, direction);
            }
            if (getNonTraversibles().contains(encounter.getType())) {
                blocked = true;
            }
        }

        if (!blocked) {
            this.setPosition(newPos);
        }
    }


    public boolean interactWithEntities(Entity entity, DungeonMap map, Direction direction) {
        boolean interfereByEntity = false;
        // create interact method in each entity
        if (entity instanceof Boulder) {
            interfereByEntity = pushBoulder(map, direction);
        } else if (entity instanceof Exit) {
            // check if only goal left is exits
            // if so exit, remove player from map, game wins

        } else if (entity instanceof Item) {
            collectToInventory((Item) entity, map);
        } else if (entity instanceof Door) {
            // check if door is already opened 
            // check if corresponding key is in inventory 
            Door door = (Door) entity;
            int doorKeyId = door.getKeyID();
            currKey = getCurrKey();
            if (currKey != null && (currKey.getDoorKeyId() == doorKeyId)) {

                door.unlockDoor(currKey);
                inventory.remove(currKey);
            }
        } else if (entity instanceof Portal) {
            interfereByEntity = teleportThroughPortal(entity, map);
        }
        return interfereByEntity;
    }

    public void interactWithEnemies(Enemy enemy, DungeonMap map) {
        if (enemy.getPosition().equals(this.getPosition()) && !enemy.becomeAlly()) {
            battleQueue.add(enemy);
        }
    }

    public void battleWithEnemies(DungeonMap map, DungeonGame game) {
        if (battleQueue.size() <= 0) {
            return;
        }
        List<Battle> battles = new ArrayList<Battle>();
        double iniPlayerHealth = this.getHealth();
        Battle currBattle = null;

        for (Enemy enemy : battleQueue) {

            List<Round> rounds = new ArrayList<Round>();
            double iniEnemyHealth = enemy.getHealth();
            currBattle = new Battle(enemy.getType(), rounds, iniPlayerHealth, iniEnemyHealth);

            while (this.getHealth() > 0 && enemy.getHealth() > 0) {
                List<Item> weaponryUsed = checkBattleBonuses(map);
                boolean hasShield = false;
                if (weaponryUsed != null) {
                    for (Item weapon : weaponryUsed) {
                        if (weapon instanceof Shield) {
                            hasShield = true;
                        }
                    }
                }
                double deltaPlayerHealth = - enemy.getAttack()/10;
                double deltaEnemyHealth = - getAttack()/5;
                if (hasShield) {
                    deltaEnemyHealth *= 2;
                }
                double newHealth = getHealth() + deltaPlayerHealth;
                double enemyHealth = enemy.getHealth() + deltaEnemyHealth;
                
                setHealth(newHealth);
                enemy.setHealth(enemyHealth);
                if (isInvincible()) {
                    weaponryUsed.add(getCurrPotion());
                }
                Round currRound = new Round(deltaPlayerHealth, deltaEnemyHealth, weaponryUsed);
                rounds.add(currRound);
                currBattle.setRounds(rounds);
                
                for (Item weapon : weaponryUsed) {
                    if (weapon != null) {
                        Weapon w = (Weapon) weapon;
                        w.useWeapon();
                    }
                }

                if (newHealth <= 0) {
                    game.addToBattles(currBattle);
                    setPlayerDied(true);
                    return;
                    // return battles;
                } else if (enemyHealth <= 0) {
                    // enemy dies
                    map.removeEntityFromMap(enemy);
                    // increment slayed enemy number
                    setSlayedEnemy(slayedEnemy+1);
                }
                
                if (isInvincible()) {
                    setPlayerWin(true);
                    battles.add(currBattle);
                    game.addToBattles(currBattle);
                    return;
                }
            }
        }
        game.addToBattles(currBattle);
        setPlayerWin(true);
    }

    public List<Item> checkBattleBonuses(DungeonMap map) {

        List<Item> weaponryUsed = new ArrayList<Item>();
        double attackBonus = 0;
        double defenceBonus = 0;
        int numAlly = map.getNumOfAlly();
        List<Weapon> usableWeapon = getUsableWeapon();
        for (Weapon weapon: usableWeapon) {
            
            attackBonus += weapon.getDamageValue();
            defenceBonus += weapon.getDefence();
            weaponryUsed.add(weapon);
        }

        if (numAlly != 0) {

            attackBonus += numAlly * JSONConfig.getConfig("ally_attack");
            defenceBonus += numAlly * JSONConfig.getConfig("ally_defence");
        }

        this.setAttack(getAttack() + attackBonus);
        this.setDefence(defenceBonus);

        return weaponryUsed;

    }

    public List<Weapon> getUsableWeapon() {
        List<Weapon> usableWeapon = new ArrayList<Weapon>();
        for (Item item: inventory) {
            if (item instanceof Weapon) {
                Weapon weapon = (Weapon) item;
                if (weapon.isUsable()) {
                    usableWeapon.add(weapon);
                }
            }
        }
        return usableWeapon;
    }

    public boolean pushBoulder(DungeonMap map, Direction direction) {
        
        boolean blockedBy = false;
        List<Entity> entitiesAtPosition = map.getEntityFromPos(getPosition().translateBy(direction));
        
        for (Entity entity : entitiesAtPosition) {
            if (entity instanceof Boulder) {
                //Check if there is no entity in the direction that the builder is being pushed
                if (map.checkIfEntityAdjacentIsPushable(entity, direction)) {
                    entity.setPosition(entity.getPosition().translateBy(direction));
                } else {
                    blockedBy = true;
                }
                
                break;
            }
        }
        return blockedBy;
    }

    public void collectToInventory(Item item, DungeonMap map) {
        if ((item instanceof Key )&& hasKey()) {
            return;
        }
        inventory.add(item);
        if (item instanceof Key) {
            setCurrKey((Key) item);
        }
        List<Entity> newMapEntities = map.getMapEntities();
        newMapEntities.remove(item);
        map.setMapEntities(newMapEntities);
    }

    // may need to debug later, update potion queue etc, turn currPotion to null whenever ticks over
    public void consumePotion(Potion item) { 
        potionQueue.addPotionToQueue(item);
    }

    public void playerPotionQueueUpdateTick() {
        
        Potion currPotion = potionQueue.updatePotionQueue();
        if (currPotion != null) { 
            if (currPotion instanceof InvincibilityPotion) {
                setState(new InvincibleState());
            } else if (currPotion instanceof InvisibilityPotion){
                setState(new InvisibleState());
            }
        } else {
            setState(new PlayerDefaultState());
        }
        state.playerStateChange(this);
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
            count++;
        }
    }


    public boolean hasKey() {
        return inventory.stream().anyMatch(i -> i.getType().equals("key"));
    }

    public List<ItemResponse> getInventoryResponses() {
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

    public boolean teleportThroughPortal(Entity entity, DungeonMap map) {
        boolean teleportByPortal = false;
        Portal portal = (Portal) entity;
        portal.linkPortals(map.getMapEntities());
        Position teleport = portal.getPairPosition();
        if (teleport == null) { return teleportByPortal; }

        List<Position> telePositions = teleport.getCardinallyAdjacentPositions();
        List<Position> possiblePos = new ArrayList<Position>();
        Position followDir = teleport.translateBy(getDirection());
        if (telePositions != null && telePositions.size() > 0) {
            for (Position pos : telePositions) {
                List<Entity> entitiesAtPos = map.getEntityFromPos(pos);
                if (entitiesAtPos == null || (!map.containsType(entitiesAtPos,"wall") &&
                    !map.containsType(entitiesAtPos,"door"))) {
                        possiblePos.add(pos);
                }
            }
        }
        if (possiblePos != null && possiblePos.size() != 0) {
            if (possiblePos.contains(followDir)) {
                this.setPosition(followDir);
            } else {
                this.setPosition(possiblePos.get(0));
            }
            teleportByPortal = true;
            Position teleportedP = this.getPosition();
            
            Entity newPortal = map.getPortalAtPos(teleportedP);
            if (newPortal == null) { return teleportByPortal; }

            return teleportThroughPortal(newPortal, map);
        }
        return teleportByPortal;
    }


    public void interactWithSpawner(ZombieToastSpawner spawner, DungeonMap map) throws InvalidActionException {
        Position spawnerPos = spawner.getPosition();
        List<Position> cdjPositions = spawnerPos.getCardinallyAdjacentPositions();
        if (!cdjPositions.contains(getPosition())) {
            throw new InvalidActionException("Player is not cardinally adjacent to the spawner");
        } else if (getUsableWeapon() == null || getUsableWeapon().size() == 0) {
            throw new InvalidActionException("the player does not have a weapon");
        } else {
            map.removeEntityFromMap(spawner);
        }
    }


    public void interactWithMercenary(Mercenary merc, DungeonMap map) throws InvalidActionException {
    
        if (!merc.isInRad(map)) {
            throw new InvalidActionException("Mercenary not in radius");
        } else if (!this.hasEnoughToBribe()) {
            throw new InvalidActionException("Player does not have enough treasure to bribe");
        } else {
            merc.setState(new MercBribedState());
            merc.getState().currentState(merc);
            consumeInventory("treasure", JSONConfig.getConfig("bribe_amount"));
        }
        
    }



}
