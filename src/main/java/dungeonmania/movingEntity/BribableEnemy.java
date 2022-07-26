package dungeonmania.movingEntity;

import dungeonmania.DungeonMap;
import dungeonmania.util.JSONConfig;
import dungeonmania.util.Position;

public abstract class BribableEnemy extends Enemy {
    
    private int bribeAmount;
    
    public BribableEnemy(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        setMovingStrategy(new RandomSpawn());
    }

    public int getBribeRadius() {
        // return bribeRadius;
        return (int) JSONConfig.getConfig("bribe_radius");
    }

    public int getBribeAmount() {
        return bribeAmount;
    }

    public void setBribeAmount(int bribeAmount) {
        this.bribeAmount = bribeAmount;
    }

    public boolean isInRad(DungeonMap map, int radius) {
        boolean inRadius = false;

        Player player = map.getPlayer();
        Position playerPos = player.getPosition();
        Position enemyPos = this.getPosition();
        if (enemyPos.getDistanceBetween(playerPos) <= radius) {
            inRadius = true;
        }

        return inRadius;
    }
    
}
