package dungeonmania.StaticEntities;

import dungeonmania.StaticEntity;
import dungeonmania.util.Position;

public class TimeTravellingPortal extends StaticEntity {

    public TimeTravellingPortal(String type, Position position) {
        super(type, position);
        setType("time_travelling_portal");
    }
   
}
