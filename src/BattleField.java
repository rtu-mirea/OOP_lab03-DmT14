import java.util.List;

public class BattleField {
    private List ships;
    private List shots;

    public void placeShip(Ship ship, int x, int y) {
        // TO DO
    }

    public List getShips() {
        // TO DO
        return ships;
    }

    public List getShots() {
        // TO DO
        return shots;
    }

    /*---------------------------------------------------------*/
    /* Метод makeShot() класса BattleField проверяет выстрел   */
    /* противника, занося его в список прострелянных областей  */
    /* shots и проверяя, не задет ли выстрелом корабль (если   */
    /* да, то у корабля вызывается метод makeWound().          */
    /* Предполагается, что два выстрела по одной точке сделать */
    /* нельзя (за этим следит класс BattleShip).               */
    /*                                                         */
    /* Методы, управляющие ходом игры (например, getTurn()),   */
    /* могут быть запрограммированы отдельно, а могут быть     */
    /* реализованы в обработчиках событий от интерфейса        */
    /* (слушателях).                                           */
    /*---------------------------------------------------------*/

    public void makeShot(int x, int y) {
        // TO DO
    }

    public boolean isDefault() {
        // TO DO
        return true;
    }
}