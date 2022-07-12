package dungeonmania;

import java.util.List;
import java.util.UUID;

import dungeonmania.entities.Item;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Battle;

public class DungeonGame {

    private int currentTick = 0;
    private String dungeonId;
    private String goals;
    private List<Item> inventories;
    private List<Battle> battles;
    private List<String> buildables;

    public DungeonGame(String goals, List<Item> inventories, List<Battle> battles, List<String> buildables) {
        this.dungeonId = UUID.randomUUID().toString();
        this.goals = goals;
        this.inventories = inventories;
        this.battles = battles;
        this.buildables = buildables;
    }

    public String getDungeonId() {
        return dungeonId;
    }

    public String getGoals() {
        return goals;
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

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public void setInventories(List<Item> inventories) {
        this.inventories = inventories;
    }

    public void setBattles(List<Battle> battles) {
        this.battles = battles;
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
