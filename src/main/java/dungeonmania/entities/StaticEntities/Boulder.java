package dungeonmania.entities.StaticEntities;

import dungeonmania.util.Position;

import dungeonmania.DungeonMap;
import dungeonmania.entities.StaticEntity;
import dungeonmania.entities.movingEntity.player.Player;
import dungeonmania.util.Direction;

public class Boulder extends StaticEntity{

    public Boulder(String type, Position position) {
        super(type, position);
        
    }
    
    public void move(Direction direction) {
        setPosition(getPosition().translateBy(direction));
    }

    @Override
    public boolean interact(DungeonMap map, Player player) {
        Direction direction = player.getDirection();
        boolean blockedBy = false;

        //Check if there is no entity in the direction that the boulder is being pushed
        if (map.checkIfEntityAdjacentIsPushable(this, direction)) {
            this.setPosition(this.getPosition().translateBy(direction));
        } else {
            blockedBy = true;
        }

        return blockedBy;   
    }
    
}
