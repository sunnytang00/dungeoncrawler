package dungeonmania;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import dungeonmania.entities.Item;
import dungeonmania.entities.movingEntity.player.Player;
import dungeonmania.util.Battle;

public class DungeonGame {

    private int currentTick = 0;
    private String dungeonId;
    private List<Item> inventories;
    private List<Battle> battles = new ArrayList<Battle>();
    private List<String> buildables;
    private DungeonMap map;


    public DungeonGame(String goals, List<Item> inventories, List<Battle> battles, List<String> buildables, DungeonMap map) {
        this.dungeonId = UUID.randomUUID().toString();
        this.inventories = inventories;
        this.buildables = buildables;
        this.map = map;
    }

    public String getDungeonId() {
        return dungeonId;
    }

    public List<Item> getInventory() {
        return inventories;
    }

    public final List<Battle> getBattles(){
        return battles;
    }

    public final List<String> getBuildables() {
        return buildables;
    }

    public void setDungeonId(String dungeonId) {
        this.dungeonId = dungeonId;
    }

    public void setInventories(List<Item> inventories) {
        this.inventories = inventories;
    }

    public void setBattles(List<Battle> battles) {
        this.battles = battles;
    }

    public void addToBattles(Battle battle) {
        battles.add(battle);
    }
    
    public void setBuildables(List<String> buildables) {
        this.buildables = buildables;
    }

    // assuming game starts with tick 0
    public int getCurrentTick() {
        return currentTick;
    }

    public void incrementTick() {
        currentTick += 1;
    }

    public DungeonMap getMap() {
        return map;
    }

    public void setMap(DungeonMap map) {
        this.map = map;
    }

    public Player getPlayer() {
        return map.getPlayer();
    }

    
    
}
