package dungeonmania.util;

public class JSONConfig {
    
    public static int bomb_radius;
    public static int bow_durability;
    public static int bribe_amount;
    public static int bribe_radius;
    public static int mercenary_attack;
    public static int mercenary_health;
    public static int player_attack;
    public static int player_health;
    public static int shield_defence;
    public static int shield_durability;
    public static int spider_attack;
    public static int spider_health;
    public static int spider_spawn_rate;
    public static int sword_attack;
    public static int sword_durability;
    public static int treasure_goal;
    public static int zombie_attack;
    public static int zombie_health;
    public static int zombie_spawn_rate;

    /**
     * Constructor for a class which has fields consistent with the json config
     * @param bomb_radius
     * @param bow_durability
     * @param bribe_amount
     * @param bribe_radius
     * @param mercenary_attack
     * @param mercenary_heath
     * @param player_attack
     * @param player_health
     * @param shield_defence
     * @param shield_durability
     * @param spider_attack
     * @param spider_health
     * @param spider_spawn_rate
     * @param sword_attack
     * @param sword_durability
     * @param treasure_goal
     * @param zombie_attack
     * @param zombie_health
     * @param zombie_spawn_rate
     */
    public JSONConfig(int bomb_radius, int bow_durability, int bribe_amount, int bribe_radius, int mercenary_attack,
            int mercenary_health, int player_attack, int player_health, int shield_defence, int shield_durability,
            int spider_attack, int spider_health, int spider_spawn_rate, int sword_attack, int sword_durability,
            int treasure_goal, int zombie_attack, int zombie_health, int zombie_spawn_rate) {

        JSONConfig.bomb_radius = bomb_radius;
        JSONConfig.bow_durability = bow_durability;
        JSONConfig.bribe_amount = bribe_amount;
        JSONConfig.bribe_radius = bribe_radius;
        JSONConfig.mercenary_attack = mercenary_attack;
        JSONConfig.mercenary_health = mercenary_health;
        JSONConfig.player_attack = player_attack;
        JSONConfig.player_health = player_health;
        JSONConfig.shield_defence = shield_defence;
        JSONConfig.shield_durability = shield_durability;
        JSONConfig.spider_attack = spider_attack;
        JSONConfig.spider_health = spider_health;
        JSONConfig.spider_spawn_rate = spider_spawn_rate;
        JSONConfig.sword_attack = sword_attack;
        JSONConfig.sword_durability = sword_durability;
        JSONConfig.treasure_goal = treasure_goal;
        JSONConfig.zombie_attack = zombie_attack;
        JSONConfig.zombie_health = zombie_health;
        JSONConfig.zombie_spawn_rate = zombie_spawn_rate;
    }

    public int getBomb_radius() {
        return bomb_radius;
    }

    public int getBow_durability() {
        return bow_durability;
    }

    public int getBribe_amount() {
        return bribe_amount;
    }

    public int getBribe_radius() {
        return bribe_radius;
    }

    public int getMercenary_attack() {
        return mercenary_attack;
    }

    public int getMercenary_health() {
        return mercenary_health;
    }

    public int getPlayer_attack() {
        return player_attack;
    }

    public int getPlayer_health() {
        return player_health;
    }

    public int getShield_defence() {
        return shield_defence;
    }

    public int getShield_durability() {
        return shield_durability;
    }

    public int getSpider_attack() {
        return spider_attack;
    }

    public int getSpider_health() {
        return spider_health;
    }

    public int getSpider_spawn_rate() {
        return spider_spawn_rate;
    }

    public int getSword_attack() {
        return sword_attack;
    }

    public int getSword_durability() {
        return sword_durability;
    }

    public int getTreasure_goal() {
        return treasure_goal;
    }

    public int getZombie_attack() {
        return zombie_attack;
    }

    public int getZombie_health() {
        return zombie_health;
    }

    public int getZombie_spawn_rate() {
        return zombie_spawn_rate;
    }

    

    
}
