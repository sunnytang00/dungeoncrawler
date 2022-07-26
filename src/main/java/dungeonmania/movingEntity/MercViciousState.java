package dungeonmania.movingEntity;

import dungeonmania.util.JSONConfig;

public class MercViciousState implements MercenaryState {

    @Override
    public void currentState(Mercenary merc) {
        if (merc instanceof Assassin) {
            merc.setHealth(JSONConfig.getConfig("assassin_health"));
            merc.setAttack(JSONConfig.getConfig("assassin_attack"));
            
        } else {
            merc.setHealth(JSONConfig.getConfig("mercenary_health"));
            merc.setAttack(JSONConfig.getConfig("mercenary_attack"));
        }
        merc.setBribed(false);
    }

}
