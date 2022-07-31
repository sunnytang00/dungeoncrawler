package dungeonmania.entities.StaticEntities.logicSwitches;

import dungeonmania.util.Position;


public class LightBulb extends LogicItem {

    public LightBulb(String type, Position position, LogicEnum logicEnum) {
        super(type, position, logicEnum);
    }


    @Override
    public void setActivated(boolean activated) {
        this.isActivated = activated;
        if (activated) {
            setType("light_bulb_on");
        } else {
            setType("light_bulb_off");
        }
    }


}
