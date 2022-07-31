package dungeonmania;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Item;
import dungeonmania.entities.StaticEntities.FloorSwitch;
import dungeonmania.entities.StaticEntities.logicSwitches.LogicBomb;
import dungeonmania.entities.StaticEntities.logicSwitches.LogicItem;
import dungeonmania.entities.StaticEntities.logicSwitches.Wire;
import dungeonmania.entities.movingEntity.player.Player;
import dungeonmania.util.Battle;
import dungeonmania.util.Position;

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

    public void setDungeonId(String dungeonId) {
        this.dungeonId = dungeonId;
    }

    public final List<Battle> getBattles() {
        return battles;
    }


    public void addToBattles(Battle battle) {
        battles.add(battle);
    }

    // assuming game starts with tick 0
    public int getCurrentTick() {
        return currentTick;
    }

    public void setCurrentTick(int tick) {
        this.currentTick = tick;
    }

    public void incrementTick() {
        currentTick += 1;
    }

    // returns which tick time travel rewinds back 
    public int getTimeTravelTick() {
        return timeTravelTick;
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

    public int getTickHistorySize() {
        return tickHistory.size();
    }

    public void resetTickHistory(List<JSONObject> tickHistory) {
        this.tickHistory = tickHistory;
    }

    public DungeonMap getMap() {
        return map;
    }

    public Player getPlayer() {
        return map.getPlayer();
    }

    public void updateLogicSwitches() {

        LogicBomb logicBomb = null;
    
        for (Entity e : map.getMapEntities()) {
            if (e instanceof FloorSwitch) {
                Position switchPos = e.getPosition();
                if (((FloorSwitch) e).isActivated()) {
                    changeCircuit(null, switchPos, true);
                } else {
                    changeCircuit(null, switchPos, false);
                }
            }

            if (e instanceof LogicItem && !(e instanceof Wire)) {
                LogicItem logic = (LogicItem) e;
                logic.updateStatus(this);
                if (e instanceof LogicBomb && ((LogicBomb) e).isActivated()) {
                    logicBomb = (LogicBomb) e;
                }
            }
        }

        if (logicBomb != null) {
            logicBomb.explode(this.getMap());
        }

    }
    
    public void changeCircuit(Position prevPos, Position pos, boolean activate) {
        // If a switch cardinally adjacent to a wire is activated, all the other interactable entities cardinally adjacent to the wire are activated.
        List<Position> adjacentPositions = pos.getCardinallyAdjacentPositions();
        adjacentPositions.remove(prevPos);
        List<Entity> allEntities = new ArrayList<Entity>();
        for (Position position : adjacentPositions) {
            allEntities.addAll(map.getEntityFromPos(position));
        }

        List<Entity> list = map.getEntitiesFromType(allEntities, "wire");

        if (list == null || list.size() == 0) {
            return;
        } 

        for (Entity entity : list) {
            Wire wire = (Wire) entity;
            wire.setActivated(activate);
            if (activate) {
                wire.setActivationTick(currentTick);
            }
            Position newPos = wire.getPosition();
            changeCircuit(pos, newPos, activate);
        }
    }

}
