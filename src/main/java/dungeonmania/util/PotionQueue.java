package dungeonmania.util;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.collectableEntities.*;

public class PotionQueue {
    
    private List<Potion> potionQueue = new ArrayList<Potion>();

    public void addPotionToQueue(Potion potion) {
        potionQueue.add(potion);
    }

    public Potion updatePotionQueue() {
        if (potionQueue == null || potionQueue.size() == 0) { 
            return null; 
        }
        
        Potion currentPotion = potionQueue.get(0);
        currentPotion.updateTicks();
        if (currentPotion.getTicks() == 0) {
            potionQueue.remove(currentPotion);
        }
        return currentPotion;
    }

}
