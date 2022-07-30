package dungeonmania.entities.StaticEntities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import dungeonmania.DungeonMap;
import dungeonmania.entities.Entity;
import dungeonmania.entities.StaticEntity;
import dungeonmania.entities.movingEntity.player.Player;
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

    @Override
    public boolean interact(DungeonMap map, Player player) {
        return teleportThroughPortal(this, map, player);
    }

    public boolean teleportThroughPortal(Portal portal, DungeonMap map, Player player) {
        boolean teleportByPortal = false;
        portal.linkPortals(map.getMapEntities());
        Position teleport = portal.getPairPosition();
        if (teleport == null) { return teleportByPortal; }

        List<Position> telePositions = teleport.getCardinallyAdjacentPositions();
        List<Position> possiblePos = new ArrayList<Position>();
        Position followDir = teleport.translateBy(player.getDirection());
        if (telePositions != null && telePositions.size() > 0) {
            for (Position pos : telePositions) {
                List<Entity> entitiesAtPos = map.getEntityFromPos(pos);
                if (entitiesAtPos == null || (!map.containsType(entitiesAtPos,"wall") &&
                    !map.containsType(entitiesAtPos,"door"))) {
                        possiblePos.add(pos);
                }
            }
        }
        if (possiblePos != null && possiblePos.size() != 0) {
            if (possiblePos.contains(followDir)) {
                player.setPosition(followDir);
            } else {
                player.setPosition(possiblePos.get(0));
            }
            teleportByPortal = true;
            Position teleportedP = player.getPosition();
            
            Portal newPortal = map.getPortalAtPos(teleportedP);
            if (newPortal == null) { return teleportByPortal; }

            return teleportThroughPortal(newPortal, map, player);
        }
        return teleportByPortal;
    }
    
}
