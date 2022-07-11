package dungeonmania.movingEntity;

public interface PlayerState {

    public void playerStateChange(Player player);

    public void playeInBattle(Player player, Enemy enemy);
}
