package dungeonmania.util;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.movingEntity.*;

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

    
    
}
