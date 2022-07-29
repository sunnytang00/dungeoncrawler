package dungeonmania.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

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
        currentPotion.setDurability(currentPotion.getDurability() - 1);
        if (currentPotion.getDurability() == 0) {
            potionQueue.remove(currentPotion);
        }

        return currentPotion;
    }

    public JSONArray toJSON(String mode) {
        JSONArray arr = new JSONArray();
        for (Potion p : potionQueue) {
            arr.put(p.toJSON(mode));
        }
        return arr;
    }

}
