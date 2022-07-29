package dungeonmania;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import dungeonmania.entities.Item;
import dungeonmania.util.Battle;

public class DungeonGame {

    private int currentTick = 0;
    private String dungeonId;
    private List<Item> inventories;
    private List<Battle> battles = new ArrayList<Battle>();
    private List<String> buildables;


    public DungeonGame(String goals, List<Item> inventories, List<Battle> battles, List<String> buildables) {
        this.dungeonId = UUID.randomUUID().toString();
        this.inventories = inventories;
        this.buildables = buildables;
    }

    public DungeonGame(DungeonGame game) {
        this.dungeonId = game.dungeonId;
        this.inventories = game.inventories;
        this.buildables = game.buildables;
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

    
}
