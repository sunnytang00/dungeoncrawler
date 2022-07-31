package dungeonmania.util;

import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.entities.Item;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.RoundResponse;

public class Round {
    private double deltaPlayerHealth;
    private double deltaEnemyHealth;
    private List<Item> weaponryUsed;

    public Round(double deltaPlayerHealth, double deltaEnemyHealth, List<Item> weaponryUsed) {
        this.deltaPlayerHealth = deltaPlayerHealth;
        this.deltaEnemyHealth = deltaEnemyHealth;
        this.weaponryUsed = weaponryUsed;
    }

    public List<ItemResponse> getItemResponses(List<Item> items) {
        return items.stream().map(Item::getItemResponse).collect(Collectors.toList());
    }

    public RoundResponse getRoundResponse() {
        return new RoundResponse(deltaPlayerHealth, deltaEnemyHealth, getItemResponses(weaponryUsed));
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("delta_player_health", deltaPlayerHealth);
        obj.put("delta_enemy_health", deltaEnemyHealth);
        JSONArray objI = new JSONArray();
        for (Item i : weaponryUsed) {
            objI.put(i);
        }
        obj.put("weaponry_used", objI);
        return obj;
    }
}
