package dungeonmania.movingEntity;

import dungeonmania.util.JSONConfig;

public class MercBribedState implements MercenaryState {


    @Override
    public void currentState(Mercenary merc) {
        merc.setDefence(JSONConfig.getConfig("ally_defence"));
        merc.setAttack(JSONConfig.getConfig("ally_attack"));
        merc.setBribed(true);
    }
    
}
