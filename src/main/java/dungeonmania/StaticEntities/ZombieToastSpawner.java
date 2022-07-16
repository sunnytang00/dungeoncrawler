package dungeonmania.StaticEntities;

import dungeonmania.DungeonMap;
import dungeonmania.StaticEntity;
import dungeonmania.movingEntity.ZombieToast;
import dungeonmania.util.Direction;
import dungeonmania.util.JSONConfig;
import dungeonmania.util.Position;

public class ZombieToastSpawner extends StaticEntity {

    public ZombieToastSpawner(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        this.isTraversable = true;
    }
    
    /**
     * Method to spawn zombie
     * @param currentTick curent tick 
     * @param spawnTick zombe_spawn_rate, read in from the config file
     */
    public ZombieToast spawnZombie(int currentTick, DungeonMap map) {

        if ((currentTick + 1) % JSONConfig.getConfig("zombie_spawn_rate") == 0) {
            //spawn zombie to zombie spwaner position 
            //ZombieToast newZToast = new ZombieToast("zombie_toast", this.getPosition().translateBy(Direction.UP), true);
            //map.addEntityToMap(newZToast);
            return new ZombieToast("zombie_toast", this.getPosition().translateBy(Direction.UP), true);
        }

        return null;

    }
}
