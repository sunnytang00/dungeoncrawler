package dungeonmania.entities.movingEntity.player;

import java.util.Arrays;
import java.util.List;

import dungeonmania.DungeonGame;
import dungeonmania.DungeonMap;
import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntity.enemies.Enemy;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class InvincibleState implements PlayerState {

    @Override
    public void playerStateChange(Player player) {
        player.setInvisible(false);
        player.setInvincible(true); 
        player.setNonTraversibles(Arrays.asList("wall", "door", "switch_door"));
    }

    @Override
    public void move(Player player, Direction direction, DungeonMap map) {
        boolean blocked = false;

        player.setDirection(direction);
        Position newPos = player.getPosition().translateBy(direction);
        List<Entity> encounters = map.getEntityFromPos(newPos);

        // interact with non-moving entities 
        for (Entity encounter : encounters) {

            if (!(encounter instanceof Enemy)) {
                blocked = player.interactWithEntities(encounter, map);
            }
            if (player.getNonTraversibles().contains(encounter.getType())) {
                blocked = true;
            }
        }

        if (!blocked) {
            player.setPosition(newPos);
        }
        
    }

    @Override
    public void battle(Player player, DungeonMap map, DungeonGame game) {
        List<Enemy> enemies = map.getEnemies();
        for (Enemy enemy : enemies) {
            player.interactWithEnemies(enemy, map);
            player.battleWithEnemies(map, game);
        }
        
    }

}
