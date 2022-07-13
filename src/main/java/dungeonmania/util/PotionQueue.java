package dungeonmania.util;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import dungeonmania.entities.collectableEntities.*;

public class PotionQueue {
    
    private List<Potion> potionQueue = new ArrayList<Potion>();

    public void addPotionToQueue(Potion potion) {
        potionQueue.add(potion);
    }

    public void removePotionFromQueue(Potion potion) {
        potionQueue.remove(potion);
    }

    public List<Potion> getPotionQueue() {
        return potionQueue;
    }

    public int getSize() {
        return potionQueue.size();
    }

    public void updatePotionQueue() {
        if (potionQueue == null) { return; }
        
        Potion currentPotion = potionQueue.get(0);
        currentPotion.updateTicks();
        if (currentPotion.getTicks() == 0) {
            potionQueue.remove(currentPotion);
        }
    }

    public boolean isInvincible() {
        if (potionQueue == null) { return false; }
        
        Potion currentPotion = potionQueue.get(0);
        if (currentPotion instanceof InvincibilityPotion) {
            return true;
        }
        return false;
    }

    public boolean isInvisible() {
        if (potionQueue == null) { return false; }

        Potion currentPotion = potionQueue.get(0);
        if (currentPotion instanceof InvisibilityPotion) {
            return true;
        }
        return false;
    }

}
