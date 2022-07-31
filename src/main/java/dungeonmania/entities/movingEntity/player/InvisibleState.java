package dungeonmania.entities.movingEntity.player;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.DungeonGame;
import dungeonmania.DungeonMap;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class InvisibleState implements PlayerState {

    @Override
    public void playerStateChange(Player player) {
        player.setInvisible(true);
        player.setInvincible(false); 
        List<String> types = new ArrayList<String>();
        player.setNonTraversibles(types);
    }

    @Override
    public void move(Player player, Direction direction, DungeonMap map) {

        player.setDirection(direction);
        Position newPos = player.getPosition().translateBy(direction);
        player.setPosition(newPos);
        
    }

    @Override
    public void battle(Player player, DungeonMap map, DungeonGame game) {
        return;
    }

}
