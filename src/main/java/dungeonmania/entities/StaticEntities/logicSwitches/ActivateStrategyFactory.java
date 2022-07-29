package dungeonmania.entities.StaticEntities.logicSwitches;

public class ActivateStrategyFactory {
    public static ActivateStrategy getActivateStrategyByLogic(LogicEnum logicEnum) {
        if (logicEnum == LogicEnum.AND) {
            return new ANDActivateStrategy();
        } else if (logicEnum == LogicEnum.OR) {
            return new ORActivateStrategy();
        } else if (logicEnum == LogicEnum.XOR) {
            return new XORActivateStrategy();
        } else if (logicEnum == LogicEnum.CO_AND) {
            return new CO_ANDActivateStrategy();
        } else {
            return null;
        }
    }
}
