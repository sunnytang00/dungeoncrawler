package dungeonmania.entities.StaticEntities;

import dungeonmania.entities.StaticEntity;
import dungeonmania.util.Position;

public class Exit extends StaticEntity {

    public Exit(String type, Position position) {
        super(type, position);
        this.isTraversable = true;
    }
    
}
