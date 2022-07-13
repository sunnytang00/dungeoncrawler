package dungeonmania.movingEntity;

import java.util.Arrays;

public class InvincibleState implements PlayerState {

    @Override
    public void playerStateChange(Player player) {
        player.setInvisible(false);
        player.setInvincible(true); 
        player.setInteractable(true);
        player.setNonTraversibles(Arrays.asList("wall", "door"));
    }

    
}
