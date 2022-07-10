package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import dungeonmania.util.JSONConfig;
import static dungeonmania.TestUtils.getValueFromConfigFile;

public class TestJSONConfig {

    @Test
    public void TestReturnsCorrectConfig() throws IOException {

        DungeonManiaController dmc = new DungeonManiaController();
        JSONConfig config = dmc.getConfig("c_battleTests_basicMercenaryMercenaryDies");

  
        assertEquals(config.getBomb_radius(), Integer.valueOf(getValueFromConfigFile("bomb_radius", "c_battleTests_basicMercenaryMercenaryDies")));
        assertEquals(config.getMercenary_attack(), Integer.valueOf(getValueFromConfigFile("mercenary_attack", "c_battleTests_basicMercenaryMercenaryDies")));
        assertEquals(config.getMercenary_health(), Integer.valueOf(getValueFromConfigFile("mercenary_health", "c_battleTests_basicMercenaryMercenaryDies")));
        assertEquals(config.getShield_durability(), Integer.valueOf(getValueFromConfigFile("shield_durability", "c_battleTests_basicMercenaryMercenaryDies")));
        assertEquals(config.getSpider_attack(), Integer.valueOf(getValueFromConfigFile("spider_attack", "c_battleTests_basicMercenaryMercenaryDies")));
    }
       
}
