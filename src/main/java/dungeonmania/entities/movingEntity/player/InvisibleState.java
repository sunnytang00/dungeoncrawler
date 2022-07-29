package dungeonmania.entities.movingEntity.player;

import java.util.ArrayList;
import java.util.List;

public class InvisibleState implements PlayerState {

    @Override
    public void playerStateChange(Player player) {
        player.setInvisible(true);
        player.setInvincible(false); 
        List<String> types = new ArrayList<String>();
        player.setNonTraversibles(types);
    }

}
