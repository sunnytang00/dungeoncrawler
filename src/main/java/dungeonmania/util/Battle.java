package dungeonmania.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.RoundResponse;

public class Battle {
    private String enemy;
    private double initialPlayerHealth;
    private double initialEnemyHealth;
    private List<Round> rounds;


    public Battle(String enemy, List<Round> rounds, double initialPlayerHealth, double initialEnemyHealth) {
        this.initialPlayerHealth = initialPlayerHealth;
        this.initialEnemyHealth = initialEnemyHealth;
        this.enemy = enemy;
        this.rounds = new ArrayList<Round>();
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }
    
    public List<RoundResponse> getRoundResponses(List<Round> rounds) {
        return rounds.stream().map(Round::getRoundResponse).collect(Collectors.toList());
    }

    public BattleResponse getBattleResponse() {
        return new BattleResponse(enemy, getRoundResponses(rounds), initialPlayerHealth, initialEnemyHealth);
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("enemy", enemy);
        obj.put("initial_player_health", initialPlayerHealth);
        obj.put("initial_enemy_health", initialEnemyHealth);
        JSONArray roundsJSON = new JSONArray();
        for (Round r : rounds) {
            JSONObject objR = r.toJSON();
            roundsJSON.put(objR);
        }
        obj.put("rounds", roundsJSON);
        return obj;
    }
    
}
