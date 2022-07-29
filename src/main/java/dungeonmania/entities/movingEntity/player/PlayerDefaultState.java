package dungeonmania.entities.movingEntity.player;

import java.util.Arrays;

public class PlayerDefaultState implements PlayerState {

    @Override
    public void playerStateChange(Player player) {
        player.setInvisible(false);
        player.setInvincible(false); 
        player.setNonTraversibles(Arrays.asList("wall", "door"));     
    }

    
}
