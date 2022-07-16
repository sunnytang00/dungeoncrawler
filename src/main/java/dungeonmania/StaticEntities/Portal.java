package dungeonmania.StaticEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Entity;
import dungeonmania.StaticEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Helper;
import dungeonmania.util.Position;

public class Portal extends StaticEntity {

    private Portal pair;

    public Portal(String type, Position position, String colour) {
        super(type, position, colour);
        this.pair = null;
        setType("portal_" + colour);
    }
    
    /**
     * Method to link portals
     * Only call this after all entities have been read in and created
     * @param list
     */
    public void linkPortals(List<Entity> list) {
        
        for (Entity entity : list) {

            if (entity instanceof Portal) {

                if ((entity.getColour().equals(this.getColour())) && ((Portal) entity != this)) {
                    this.pair = (Portal) entity;
                    break;
                }
            }
        }
    }


    public Position getPairPosition() {
        return pair.getPosition();
    }

    public Portal getPair() {
        return pair;
    }
    
    
    
}
