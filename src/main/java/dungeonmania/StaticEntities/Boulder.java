package dungeonmania.StaticEntities;

import dungeonmania.StaticEntity;
import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public class Boulder extends StaticEntity{

    private boolean isOnSwitch;

    public Boulder(String type, Position position) {
        super(type, position);
        
    }
    
    public void move(Direction direction) {
        setPosition(getPosition().translateBy(direction));
    }

    public boolean isOnSwitch() {
        return isOnSwitch;
    }

    public void setOnSwitch(boolean onSwitch) {
        isOnSwitch = onSwitch;
    }
}

