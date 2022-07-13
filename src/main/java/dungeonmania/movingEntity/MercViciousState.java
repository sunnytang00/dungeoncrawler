package dungeonmania.movingEntity;

import dungeonmania.util.JSONConfig;

public class MercViciousState implements MercenaryState {

    private static final double DEFAULT_MERC_HEALTH = JSONConfig.getConfig("mercenary_health");
    private static final double DEFAULT_MERC_ATTACK = JSONConfig.getConfig("mercenary_attack");

    @Override
    public void currentState(Mercenary merc) {
        merc.setHealth(DEFAULT_MERC_HEALTH);
        merc.setAttack(DEFAULT_MERC_ATTACK);
        merc.setBribed(false);
    }
  
}
