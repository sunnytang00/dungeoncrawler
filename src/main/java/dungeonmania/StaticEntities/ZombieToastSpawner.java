package dungeonmania.StaticEntities;

import dungeonmania.DungeonMap;
import dungeonmania.StaticEntity;
import dungeonmania.movingEntity.ZombieToast;
import dungeonmania.util.JSONConfig;
import dungeonmania.util.Position;

public class ZombieToastSpawner extends StaticEntity {

    private static final int DEFAULT_ZOMBIE_SPAWN_RATE = JSONConfig.getConfig("zombie_spawn_rate");

    public ZombieToastSpawner(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        this.isTraversable = true;
    }
    
    /**
     * Method to spawn zombie
     * @param currentTick curent tick 
     * @param spawnTick zombe_spawn_rate, read in from the config file
     */
    public void spawnZombie(int currentTick, DungeonMap map) {

        if ((currentTick + 1) % DEFAULT_ZOMBIE_SPAWN_RATE == 0) {
            //spawn zombie to zombie spwaner position 
            ZombieToast newZToast = new ZombieToast("zombie_toast", this.getPosition(), true);
            map.addEntityToMap(newZToast);
        }

    }
}
