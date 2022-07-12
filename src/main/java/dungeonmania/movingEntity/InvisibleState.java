package dungeonmania.movingEntity;

public class InvisibleState implements PlayerState {

    @Override
    public void playerStateChange(Player player) {
        player.setInvisible(true);
        player.setInvincible(false); 
        player.setInteractable(false); 
    }

    @Override
    public void playerInBattle(Player player, Enemy enemy) {
        // TODO Auto-generated method stub
        
    }


    
}
