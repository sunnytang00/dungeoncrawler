package dungeonmania.movingEntity;

public interface PlayerState {

    public void playerStateChange(Player player);

    public void playerInBattle(Player player, Enemy enemy);
}