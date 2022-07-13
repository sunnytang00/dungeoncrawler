package dungeonmania.util;

public class JSONConfig {
    
    private int bomb_radius;
    private int bow_durability;
    private int bribe_amount;
    private int bribe_radius;
    private int mercenary_attack;
    private int mercenary_health;
    private int player_attack;
    private int player_health;
    private int shield_defence;
    private int shield_durability;
    private int spider_attack;
    private int spider_health;
    private int spider_spawn_rate;
    private int sword_attack;
    private int sword_durability;
    private int treasure_goal;
    private int zombie_attack;
    private int zombie_health;
    private int zombie_spawn_rate;

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

        this.bomb_radius = bomb_radius;
        this.bow_durability = bow_durability;
        this.bribe_amount = bribe_amount;
        this.bribe_radius = bribe_radius;
        this.mercenary_attack = mercenary_attack;
        this.mercenary_health = mercenary_health;
        this.player_attack = player_attack;
        this.player_health = player_health;
        this.shield_defence = shield_defence;
        this.shield_durability = shield_durability;
        this.spider_attack = spider_attack;
        this.spider_health = spider_health;
        this.spider_spawn_rate = spider_spawn_rate;
        this.sword_attack = sword_attack;
        this.sword_durability = sword_durability;
        this.treasure_goal = treasure_goal;
        this.zombie_attack = zombie_attack;
        this.zombie_health = zombie_health;
        this.zombie_spawn_rate = zombie_spawn_rate;
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
