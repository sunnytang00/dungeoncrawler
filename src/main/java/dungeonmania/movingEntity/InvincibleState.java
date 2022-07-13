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

    @Override
    public void playerInBattle(Player player, Enemy enemy) {
        // TODO Auto-generated method stub
        
    }

    
}
