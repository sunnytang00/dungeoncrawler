package dungeonmania.movingEntity;

public class PlayerDefaultState implements PlayerState {

    @Override
    public void playerStateChange(Player player) {
        player.setInvisible(false);
        player.setInvincible(false); 
        player.setInteractable(true);       
    }

    @Override
    public void playerInBattle(Player player, Enemy enemy) {
        // TODO Auto-generated method stub
        
    }

    
}
