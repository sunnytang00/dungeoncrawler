package dungeonmania.entities.logicSwitches;

public enum LogicEnum {
    AND("AND"),
    OR("OR"),
    XOR("XOR"),
    CO_AND("CO_AND");
    String logic;

    LogicEnum(String logic) {
        this.logic = logic;
    }
}
