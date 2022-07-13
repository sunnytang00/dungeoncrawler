package dungeonmania.movingEntity;

import dungeonmania.util.JSONConfig;

public class MercBribedState implements MercenaryState {

    private static final double DEFAULT_ALLY_DEFENCE = JSONConfig.getConfig("ally_defence");
    private static final double DEFAULT_ALLY_ATTACK = JSONConfig.getConfig("ally_attack");

    @Override
    public void currentState(Mercenary merc) {
        merc.setDefence(DEFAULT_ALLY_DEFENCE);
        merc.setAttack(DEFAULT_ALLY_ATTACK);
        merc.setBribed(true);
    }
    
}
