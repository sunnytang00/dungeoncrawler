package dungeonmania.entities.movingEntity.enemies;

import java.util.List;

import javax.swing.text.Position;

import dungeonmania.DungeonMap;
import dungeonmania.entities.movingEntity.player.OlderPlayer;

public class OlderPlayerMovement implements MovingStrategy {
    
    @Override
    public void move(Enemy movingEntity, DungeonMap map) {
        OlderPlayer olderPlayer = (OlderPlayer) movingEntity;

        movingEntity.setPosition(olderPlayer.getMovingPosition());

        if (olderPlayer.remainingTimeTravel() == 0) {
            map.removeEntityFromMap(olderPlayer);
        }
    }
}
