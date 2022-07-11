package dungeonmania.movingEntity;

public class MercBribedState implements MercenaryState {

    private static final int DEFAULT_ALLY_HEALTH = 0;//JSONConfig.ally_health
    private static final int DEFAULT_ALLY_ATTACK = 0;//JSONConfig.ally_attack

    @Override
    public void currentState(Mercenary merc) {
        merc.setHealth(DEFAULT_ALLY_HEALTH);
        merc.setAttack(DEFAULT_ALLY_ATTACK);
        merc.setBribed(true);
    }
    
}
