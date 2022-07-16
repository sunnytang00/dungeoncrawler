package dungeonmania.StaticEntities;

import java.util.ArrayList;

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
     * @param entityList
     */
    public void linkPortals(ArrayList<Entity> entityList) {
        
        for (Entity entity : entityList) {

            if (entity instanceof Portal) {

                if ((entity.getColour().equals(this.getColour())) && ((Portal) entity != this)) {
                    this.pair = (Portal) entity;
                    break;
                }
            }
        }
    }


    // /**
    //  * Precondition: Entity must be an entity that can pass through portals
    //  * Postcondition: Entity that is passed in will end up at the paired portal
    //  * @param entity
    //  * @param direction
    //  */
    public void teleport(Entity entity, Direction direction, ArrayList<Entity> entitiesList) {

        Position newPosition = getPairPosition().translateBy(direction);

        //Check if the portal pair exists and that the new position can be teleported to
        if ((this.pair != null) && (Helper.CheckIfTraversable(newPosition, entitiesList) != false)) {
            entity.setPosition(newPosition);
        }

    }

    public Position getPairPosition() {
        return pair.getPosition();
    }

    public Portal getPair() {
        return pair;
    }
    
    
    
}
