package dungeonmania.util;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Entity;
import dungeonmania.StaticEntity;
import dungeonmania.entities.Item;
import dungeonmania.response.models.ItemResponse;

public final class Helper {
    
    public static boolean CheckIfTraversable(Position position, List<Entity> entitiesList) {

        for (Entity entity : entitiesList) {
            if ((entity instanceof StaticEntity) && (entity.getPosition().equals(position))) {
                StaticEntity e = (StaticEntity) entity;
                return e.isTraversable();
            }
        }

        return false;

    }

    public static List<ItemResponse> convertFromItem(List<Item> items) {
        List<ItemResponse> itemResponses = new ArrayList<>();
        if (null != items && !items.isEmpty()) {
            for (Item item : items) {
                itemResponses.add(new ItemResponse(item.getId(), item.getType()));
            }
        }

        return itemResponses;
    }
}

