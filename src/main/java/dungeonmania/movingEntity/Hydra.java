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
        Random rand = new Random();
        int num = rand.nextInt(1000); 
        double threshold = 1000 * JSONConfig.getConfig("hydra_health_increase_rate");
        if (num <= threshold) {
            newDelta = getHealth() + JSONConfig.getConfig("hydra_health_increase_amount");
        }
        return newDelta;
    }


    
}
