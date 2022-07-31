package dungeonmania.util;

import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.StaticEntity;
import dungeonmania.entities.StaticEntities.logicSwitches.LogicEnum;

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

    public static LogicEnum getLogic(String logic) {
        switch(logic) {
            case "and":
                return LogicEnum.AND;
            case "or":
                return LogicEnum.OR;
            case "xor":
                return LogicEnum.XOR;
            case "co_and":
                return LogicEnum.CO_AND;
        }
        return null;
    }

    public static String logicToJSON(LogicEnum logic) {
        switch(logic) {
            case AND:
                return "and";
            case OR:
                return "or";
            case XOR:
                return "xor";
            case CO_AND:
                return "co_and";
        }
        return null;
    }
}
