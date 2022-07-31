package dungeonmania.entities;

import org.json.JSONObject;

import dungeonmania.DungeonMap;
import dungeonmania.entities.movingEntity.player.Player;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Position;

public abstract class Item extends Entity {
    /**
     * Record the position of the item in the map
     */
    protected Position position;
    /**
     * Indicate whether the current potion is activated
     */
    protected boolean isTriggered;

    /**
     * Record the durability times of the sword
     */
    private int durability = 0;

    public Item(String type) {
        super(type);
    }

    public Item(String type, Position position) {
        super(type, position);
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public int getDurability() {
        return durability;
    }

    /**
     * Judge whether the weapon could still be used according
     * to the used times
     * @return
     */
    // public boolean canBeStillUsed() {
    //     return usedTimes > 0;
    // }

    public ItemResponse getItemResponse() {
        return new ItemResponse(getId(), getType());
    }

    public JSONObject weaponryUsedToJSON() {
        JSONObject obj = new JSONObject();
        obj.put("id", getId());
        return obj;
    }

    @Override
    public JSONObject toJSON(String mode) {
        JSONObject obj = super.toJSON(mode);
        // mode: "durability" - means we have to save the durability
        if (mode.equals("durability")) {
            obj.put("durability", getDurability());
            return obj;
        }
        return null;
    }

    @Override
    public boolean interact(DungeonMap map, Player player) {
        player.addToInventory(this);
        map.removeEntityFromMap(this);
        return false;
    }

}

