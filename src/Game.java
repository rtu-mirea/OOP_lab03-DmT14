import java.io.IOException;

public class Game {
    private BattleField[] battlefields = new BattleField[2]; // Список глобальных полей
    private User[] players = new User[2]; // Список игроков

    char field1[][] = new char[10][10]; // Локальное представление поля текущего игрока
    char field2[][] = new char[10][10]; // Локальное представление поля соперника

    private int id = -1; // Id этого экземпляра игры

    private int currentPlayer = 0; // Номер активного игрока

    /*
     * 0 - Расстановка кораблей
     * 1 - Игра
     * 2 - Конец игры
     */
    private int status = 0; // Состояние игры

    public Game(int id, User player1, User player2) { // Конструктор
        this.id = id;
        players[0] = player1;
        players[1] = player2;

        battlefields[0] = new BattleField();
        battlefields[1] = new BattleField();
    }

    private void updateFields () { // Перестроить локальные представления полей
        for(int x = 0; x < 10; x++)
            for(int y = 0; y < 10; y++) {
                field1[x][y] = '^';
                field2[x][y] = '^';
            }

        for(Ship ship : battlefields[currentPlayer].getShips()) {
            for(int i=0; i<ship.getLength(); i++)
                field1[ship.getX()][ship.getY()+i] = '0';
        }

        for(Shot shot : battlefields[currentPlayer].getShots()) {
            field1[shot.getX()][shot.getY()] = shot.getStat() ? '+' : '-';
        }

        for(Shot shot : battlefields[currentPlayer == 0 ? 1 : 0].getShots()) {
            field2[shot.getX()][shot.getY()] = shot.getStat() ? '+' : '-';
        }
    }

    private void printFields () { // Вывести в консоль локальные представления полей
        updateFields();

        for(int y = 0; y < 10; y++) {
            for(int x = 0; x < 10; x++) {
                System.out.print(field1[x][y]);
            }
            System.out.print("    ");
            for(int x = 0; x < 10; x++) {
                System.out.print(field2[x][y]);
            }
            System.out.println();
        }

        System.out.println();
    }

    private void println (String text) { // Написать в консоль строку-сообщение от имени текущей игры
        System.out.println("Игра("+id+"): "+text);
    }

    private void print (String text) { // Написать в консоль сообщение от имени текущей игры
        System.out.print("Игра("+id+"): "+text);
    }

    public User getPlayer(int id) { // Получить ссылку на экземпляр игрока по id

        if(id <= 1)
            return players[id];

        return null;
    }

    public User getCurrentPlayer () { // Получить ссылку на экземпляр активного игрока

        if(id <= 1)
            return players[currentPlayer];

        return null;
    }

    public int getStatus () { // Получить состояние игры
        return status;
    }

    public BattleField getField (int id) { // получить ссылку на экземпляр поля по id

        if(id <= 1)
            return battlefields[id];

        return null;
    }

    public void makeTurn () throws IOException { // Провести ход
        if(battlefields[0].isDefeat()) // Если все корабли на поле 0 уничтожены
            println("Победил игрок "+players[1].getName()+"!");
        if(battlefields[1].isDefeat()) // Если все корабли на поле 1 уничтожены
            println("Победил игрок "+players[0].getName()+"!");

        switch (status) {
            case 0: // Если идет расстановка
                printFields();

                for(int i=1; i<4; i++) {
                    Ship ship;
                    int x, y;
                    // Получить координаты корабля
                    do {
                        println("Игрок "+players[currentPlayer].getName()+", укажите координаты корабля "+i);

                        print("X: ");
                        x = Integer.parseInt(BattleShip.readLine());

                        print("Y: ");
                        y = Integer.parseInt(BattleShip.readLine());

                        println("");

                        ship = new Ship(i, x, y);

                    } while (!battlefields[currentPlayer].placeShip(ship));

                    printFields();
                }

                if(currentPlayer == 0)
                    currentPlayer = 1;
                else {
                    currentPlayer = 0;
                    status = 1;
                }
                break;

            case 1: // Если идет бой
                println("Ход игрока "+players[currentPlayer].getName());

                printFields();
                // Получить координаты выстрела
                int x, y;
                do {

                    print("X: ");
                    x = Integer.parseInt(BattleShip.readLine());

                    print("Y: ");
                    y = Integer.parseInt(BattleShip.readLine());

                } while (!battlefields[currentPlayer == 0 ? 1 : 0].makeShot(x , y));

                printFields();

                // Вывод победителя после хода
                if(battlefields[0].isDefeat()) {
                    println("");
                    println("Победил игрок "+players[1].getName()+"!");
                    status = 2;
                }
                if(battlefields[1].isDefeat()) {
                    println("");
                    println("Победил игрок "+players[0].getName()+"!");
                    status = 2;
                }

                println("Конец хода игрока "+players[currentPlayer].getName());

                // Сменить активного игрока
                if(currentPlayer == 0)
                    currentPlayer = 1;
                else
                    currentPlayer = 0;
                break;
        }

    }
}
