package dungeonmania.entities.movingEntity.player;

import dungeonmania.DungeonGame;
import dungeonmania.DungeonMap;
import dungeonmania.util.Direction;

public interface PlayerState {

    public void playerStateChange(Player player);
    public void move(Player player, Direction direction, DungeonMap map);
    public void battle(Player player, DungeonMap map, DungeonGame game);

}
