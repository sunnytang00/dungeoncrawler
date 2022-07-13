package dungeonmania.StaticEntities;

import dungeonmania.StaticEntity;
import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public class Boulder extends StaticEntity{

    public Boulder(String type, Position position) {
        super(type, position);
        
    }
    
    public void move(Direction direction) {
        setPosition(getPosition().translateBy(direction));
    }
    
}
