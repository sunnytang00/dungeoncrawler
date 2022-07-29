package dungeonmania.entities.StaticEntities;

import dungeonmania.util.Position;
import dungeonmania.entities.StaticEntity;
import dungeonmania.util.Direction;

public class Boulder extends StaticEntity{

    public Boulder(String type, Position position) {
        super(type, position);
        
    }
    
    public void move(Direction direction) {
        setPosition(getPosition().translateBy(direction));
    }
    
}
