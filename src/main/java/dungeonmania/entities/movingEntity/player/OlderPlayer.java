package dungeonmania.entities.movingEntity.player;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.Item;
import dungeonmania.entities.movingEntity.enemies.Enemy;
import dungeonmania.util.Position;

public class OlderPlayer extends Enemy {

    private List<Item> inventory = new ArrayList<Item>();
    
    public OlderPlayer(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        
    }
    
}
