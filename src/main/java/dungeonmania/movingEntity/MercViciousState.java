package dungeonmania.movingEntity;

public class MercViciousState implements MercenaryState {

    private static final int DEFAULT_MERC_HEALTH = 0;//JSONConfig.mercenary_health
    private static final int DEFAULT_MERC_ATTACK = 0;//JSONConfig.mercenary_attack

    @Override
    public void currentState(Mercenary merc) {
        merc.setHealth(DEFAULT_MERC_HEALTH);
        merc.setAttack(DEFAULT_MERC_ATTACK);
        merc.setBribed(false);
    }
  
}
