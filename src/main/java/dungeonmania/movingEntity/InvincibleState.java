package dungeonmania.movingEntity;

public class InvincibleState implements PlayerState {

    @Override
    public void playerStateChange(Player player) {
        player.setInvisible(false);
        player.setInvincible(true); 
        player.setInteractable(true); 
    }

    @Override
    public void playerInBattle(Player player, Enemy enemy) {
        // TODO Auto-generated method stub
        
    }

    
}
