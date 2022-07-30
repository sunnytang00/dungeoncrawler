package dungeonmania;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONObject;

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
    private int timeTravelTick = 0;
    private List<JSONObject> tickHistory = new ArrayList<JSONObject>();

    public DungeonGame(String goals, List<Item> inventories, List<Battle> battles, List<String> buildables,
            DungeonMap map) {
        this.dungeonId = UUID.randomUUID().toString();
        this.inventories = inventories;
        this.buildables = buildables;
        this.battles = battles;
        this.map = map;
    }

    public String getDungeonId() {
        return dungeonId;
    }

    public List<Item> getInventory() {
        return inventories;
    }

    public final List<Battle> getBattles() {
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

    // returns which tick time travel rewinds back
    public int getTimeTravelTick() {
        return timeTravelTick;
    }

    public int getTickHistorySize() {
        return tickHistory.size();
    }
    public void setTimeTravelTick(int tick) {
        timeTravelTick = tick;
    }

    public List<JSONObject> getTickHistory() {
        return tickHistory;
    }

    public JSONObject getGameFromTickHistory(int tick) {
        return tickHistory.get(tick);
    }

    public void addToTickHistory(JSONObject game) {
        tickHistory.add(game);
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
