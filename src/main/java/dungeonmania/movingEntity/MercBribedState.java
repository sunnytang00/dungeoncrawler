package dungeonmania.movingEntity;

import dungeonmania.util.JSONConfig;

public class MercBribedState implements MercenaryState {

    private static final int DEFAULT_ALLY_HEALTH = JSONConfig.getConfig("ally_health");
    private static final int DEFAULT_ALLY_ATTACK = JSONConfig.getConfig("ally_attack");

    @Override
    public void currentState(Mercenary merc) {
        merc.setHealth(DEFAULT_ALLY_HEALTH);
        merc.setAttack(DEFAULT_ALLY_ATTACK);
        merc.setBribed(true);
    }
    
}
