package dungeonmania.movingEntity;

import dungeonmania.util.Position;

import java.util.Random;

import dungeonmania.DungeonMap;
import dungeonmania.util.JSONConfig;

public class Hydra extends ZombieToast {

    public Hydra(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        this.setHealth(JSONConfig.getConfig("hydra_health"));
        this.setAttack(JSONConfig.getConfig("hydra_attack"));
        
    }

    public double inBattle(double deltaEnemyHealth) {
        double newDelta = deltaEnemyHealth;
        if (Math.random() < JSONConfig.getConfig("hydra_health_increase_rate")) {
            newDelta = getHealth() + JSONConfig.getConfig("hydra_health_increase_amount");
        }
        return newDelta;
    }

}
