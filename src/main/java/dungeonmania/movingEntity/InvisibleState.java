package dungeonmania.movingEntity;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Entity;

public class InvisibleState implements PlayerState {

    @Override
    public void playerStateChange(Player player) {
        player.setInvisible(true);
        player.setInvincible(false); 
        List<String> types = new ArrayList<String>();
        player.setNonTraversibles(types);
    }

}
