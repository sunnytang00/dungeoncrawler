package dungeonmania.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.movingEntity.*;
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


    public String getEnemy() {
        return enemy;
    }

    public double getInitialPlayerHealth() {
        return initialPlayerHealth;
    }


    public double getInitialEnemyHealth() {
        return initialEnemyHealth;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    public void addRound(Round round) {
        rounds.add(round);
    }

    public List<RoundResponse> getRoundResponses(List<Round> rounds) {
        return rounds.stream().map(Round::getRoundResponse).collect(Collectors.toList());
    }

    public BattleResponse getBattleResponse() {
        return new BattleResponse(enemy, getRoundResponses(rounds), initialPlayerHealth, initialEnemyHealth);
    }
    
}
