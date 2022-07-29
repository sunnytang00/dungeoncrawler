package dungeonmania.entities.StaticEntities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.StaticEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Helper;
import dungeonmania.util.Position;

public class Portal extends StaticEntity {

    private Portal pair;

    public Portal(String type, Position position, String colour) {
        super(type, position, colour);
        this.pair = null;
        setType("portal_" + colour.toLowerCase());
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
    
    @Override
    public JSONObject toJSON() {
        JSONObject obj = super.toJSON();
        obj.put("colour", getColour());
        return obj;
    }
    
}