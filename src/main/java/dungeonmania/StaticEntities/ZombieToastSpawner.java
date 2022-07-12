package dungeonmania.StaticEntities;

import dungeonmania.DungeonMap;
import dungeonmania.StaticEntity;
import dungeonmania.movingEntity.ZombieToast;
import dungeonmania.util.Position;

public class ZombieToastSpawner extends StaticEntity {

    public ZombieToastSpawner(String type, Position position) {
        super(type, position);
        this.isTraversable = true;
    }
    
    /**
     * Method to spawn zombie
     * @param currentTick curent tick 
     * @param spawnTick zombe_spawn_rate, read in from the config file
     */
    public void spawnZombie(int currentTick, int spawnRate, DungeonMap map) {

        if ((currentTick + 1) % spawnRate == 0) {
            //spawn zombie to zombie spwaner position 
            ZombieToast newZToast = new ZombieToast("zombie_toast", this.getPosition(), true);
            map.addEntityToMap(newZToast);
        }

    }
}
