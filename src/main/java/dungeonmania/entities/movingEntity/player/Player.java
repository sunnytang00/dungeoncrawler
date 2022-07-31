package dungeonmania.entities.movingEntity.player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.DungeonGame;
import dungeonmania.DungeonMap;
import dungeonmania.entities.*;
import dungeonmania.entities.StaticEntities.*;
import dungeonmania.entities.buildableEntities.*;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.entities.collectableEntities.potions.*;
import dungeonmania.entities.movingEntity.MovingEntity;
import dungeonmania.entities.movingEntity.enemies.*;
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
    private int slayedEnemy = 0;

    public Player(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        this.setHealth(JSONConfig.getConfig("player_health"));
        this.setAttack(JSONConfig.getConfig("player_attack"));
        this.setState(new PlayerDefaultState());
        state.playerStateChange(this);
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

    public int getWealth() {
        int totalTreasure = (int) inventory.stream().filter(i -> i instanceof Treasure).count();
        int totalSunstone = (int) inventory.stream().filter(i -> i instanceof SunStone).count();
        
        int totalWealth = totalTreasure + totalSunstone;
        return totalWealth;
    }

    public int getSlayedEnemy(){
        return slayedEnemy;
    }


    public void setSlayedEnemy(int slayedEnemy) {
        this.slayedEnemy = slayedEnemy;
    }

    public boolean hasEnoughToBribe(Mercenary merc) {
        boolean enoughWealth = false;
        if (this.getWealth() >= merc.getBribeAmount()) {
            enoughWealth = true;
        }
        return enoughWealth;
    }

    public void setPotionQueue(PotionQueue potions) {
        potionQueue = potions;
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
                blocked = interactWithEntities(encounter, map);
            }
            if (getNonTraversibles().contains(encounter.getType())) {
                blocked = true;
            }
        }

        if (!blocked) {
            this.setPosition(newPos);
        }
        battle(map, game);
    }


    public boolean interactWithEntities(Entity entity, DungeonMap map) {
        boolean interfereByEntity = false;
        // create interact method in each entity
        interfereByEntity = entity.interact(map, this);
        return interfereByEntity;
    }

    public void battle(DungeonMap map, DungeonGame game) {
        List<Enemy> enemies = map.getEnemies();
        if (!isInvisible()) {
            for (Enemy enemy : enemies) {
                interactWithEnemies(enemy, map);
                battleWithEnemies(map, game);
            }
        }
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
                boolean hasBow = false;
                if (weaponryUsed != null) {
                    for (Item weapon : weaponryUsed) {
                        if (weapon instanceof Bow) {
                            hasBow = true;
                        }
                    }
                }
                double deltaPlayerHealth = - enemy.getAttack()/10;
                double deltaEnemyHealth = - getAttack()/5;
                if (enemy instanceof Hydra) {
                    Hydra hydra = (Hydra) enemy;
                    deltaEnemyHealth = hydra.inBattle(deltaEnemyHealth);
                }
                if (hasBow) {
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
                    map.removePlayerFromMap(false);
                    return;
                    // return battles;
                } else if (enemyHealth <= 0) {
                    // enemy dies
                    map.removeEntityFromMap(enemy);
                    // increment slayed enemy number
                    setSlayedEnemy(slayedEnemy+1);
                }
                
                if (isInvincible()) {
                    // map.setGameWin(true);
                    battles.add(currBattle);
                    game.addToBattles(currBattle);
                    return;
                }
            }
        }
        game.addToBattles(currBattle);
        // map.setGameWin(true);
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


    // may need to debug later, update potion queue etc, turn currPotion to null whenever ticks over
    public void consumePotion(Potion item) { 
        potionQueue.addPotionToQueue(item);
    }

    public void playerPotionQueueUpdateTick() {
        
        Potion currPotion = potionQueue.updatePotionQueue();
        if (currPotion != null) { 
            if (currPotion instanceof InvincibilityPotion) {
                setState(new InvincibleState());
            } else if (currPotion instanceof InvisibilityPotion) {
                setState(new InvisibleState());
            }
        } else {
            setState(new PlayerDefaultState());
        }
        state.playerStateChange(this);
    }

    public void addToInventory(Item item) {
        inventory.add(item);
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

    public boolean hasSceptre() {
        return inventory.stream().anyMatch(i -> i.getType().equals("sceptre"));
    }

    public List<ItemResponse> getInventoryResponses() {
        return inventory.stream().map(Item::getItemResponse).collect(Collectors.toList());
    }

    public List<String> getBuildables(DungeonMap map) {

        List<String> ret = new ArrayList<String>();
        
        if (canBuildBow()) {
            ret.add("bow");
        }

        if (canBuildShield()) {
            ret.add("shield");
        }

        if (canBuildArmour(map)) {
            ret.add("midnight_armour");
        }

        if (canBuildSceptre()) {
            ret.add("sceptre");
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

    public boolean canBuildArmour(DungeonMap map) {
        
        if (map.hasZombies()) {
            return false;
        }

        if (!inventory.isEmpty()) {
            int sunStoneNum = 0;
            int swordNum = 0;
            
            for (Item item : inventory) {

                if (item instanceof SunStone) {
                    sunStoneNum++;
                }

                if (item instanceof Sword) {
                    swordNum++;
                }
            }

            if ((sunStoneNum >= 1) && (swordNum >= 1) && (map.getEntitiesFromType(map.getMapEntities(), "zombie").isEmpty())) {
                return true;
            } else {
                return false;
            }
                
        }
        return false;

    }

    public boolean canBuildSceptre() {

        if (!inventory.isEmpty()) {
            int woodNumber = 0;
            int arrowsNumber = 0;
            int keyOrTreasureNum = 0;
            int sunStoneNum = 0;

            for (Item item : inventory) {

                if (item instanceof Wood) {
                    woodNumber++;
                }

                if (item instanceof Arrows) {
                    arrowsNumber++;
                }

                if ((item instanceof Treasure) || (item instanceof Key)) {
                    keyOrTreasureNum++;
                }

                if (item instanceof SunStone) {
                    sunStoneNum++;
                }
            }

            if (((woodNumber >= 1) || (arrowsNumber >= 2)) && (keyOrTreasureNum >= 1) && (sunStoneNum >= 1)) {
                return true;
            } else {
                return false;
            }
                
        }
        return false;
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
    
        if (!merc.isInRad(map, merc.getBribeRadius()) && !hasSceptre()) {
            throw new InvalidActionException("Bribable enemy not in radius");
        } else if (!hasEnoughToBribe(merc) && !hasSceptre()) {
            throw new InvalidActionException("Player does not have enough treasure and does not have a sceptre to bribe/mind-control enemy");
        } else {
            // assume use sceptre in priority
            if (hasSceptre()) {
                merc.mindControl();
                // assume sceptre can be consumed like potion and can only be used once
                consumeInventory("sceptre", 1);
            } else if (hasEnoughToBribe(merc) && !hasSceptre()) {
                merc.bribe();
                merc.setBribedByTreasure(true);
                consumeInventory("treasure", merc.getBribeAmount());
            }
        }
        
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = super.toJSON();
        
        String state = getState().toString();
        state = state.replaceAll("dungeonmania.movingEntity.", "");
        state = state.substring(0, state.indexOf("@"));

        obj.put("state", state);
        obj.put("slayed-enemies", slayedEnemy);
        
        return obj;
    }

    public JSONArray potionQueueToJSON() {
        JSONArray potions = potionQueue.toJSON("durability");
        return potions;
    }

    public JSONArray inventoryToJSON() {
        JSONArray inventoryJSON = new JSONArray();
        for (Item i : inventory) {
            JSONObject obj = i.toJSON("durability");
            inventoryJSON.put(obj);
        }
        return inventoryJSON;
    }

    // public JSONArray battlesToJSON() {
    //     JSONArray battlesJSON = new JSONArray();
    //     for (Enemy enemy : battleQueue) {
            
    //         JSONObject obj = enemy.toJSON("battles");
    //         battlesJSON.put(obj);
    //     }
    //     return battlesJSON;
    // }

}
