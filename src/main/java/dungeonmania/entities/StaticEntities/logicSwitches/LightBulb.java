package dungeonmania.entities.StaticEntities.logicSwitches;

import dungeonmania.DungeonGame;
import dungeonmania.DungeonMap;
import dungeonmania.entities.Entity;
import dungeonmania.entities.StaticEntities.Boulder;
import dungeonmania.util.Helper;
import dungeonmania.util.Position;


import org.json.JSONObject;

public class LightBulb extends LogicItem {

    public LightBulb(String type, Position position, LogicEnum logicEnum) {
        super(type, position, logicEnum);
    }


    @Override
    public void setActivated(boolean activated) {
        super.setActivated(activated);
        if (activated) {
            setType("light_bulb_on");
        } else {
            setType("light_bulb_off");
        }
    }


}
