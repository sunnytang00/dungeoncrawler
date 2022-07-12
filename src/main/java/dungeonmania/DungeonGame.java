package dungeonmania;

import java.util.List;
import java.util.UUID;

import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;

public class DungeonGame {

    private String dungeonId;
    private String goals;
    private List<Item> inventories;
    private List<Battle> battles;
    private List<String> buildables;

    public DungeonGame(String goals, List<Item> inventories, List<Battle> battles, List<String> buidables) {
        this.dungeonId = UUID.randomUUID().toString();
        this.goals = goals;
        this.inventories = inventories;
        this.battles = battles;
        this.buidables = buildables;
    }

    public String getDungeonId() {
        return dungeonId;
    }

    public String getGoals() {
        return goals;
    }

    public final List<ItemResponse> getInventory() {
        return inventories;
    }

    public final List<BattleResponse> getBattles(){
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

    
    
}
