import java.io.IOException;

public class Game {
    private BattleField[] battlefields = new BattleField[2];
    private User[] players = new User[2];

    char field1[][] = new char[10][10];
    char field2[][] = new char[10][10];

    private int id = -1;

    private int currentPlayer = 0;

    /*
     * 0 - Расстановка кораблей
     * 1 - Игра
     * 2 - Конец игры
    */
    private int status = 0;

    public Game(int id, User player1, User player2) {
        this.id = id;
        players[0] = player1;
        players[1] = player2;

        battlefields[0] = new BattleField();
        battlefields[1] = new BattleField();
    }

    private void updateFields () {
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

    private void printFields () {
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

    private void println (String text) {
        System.out.println("Игра("+id+"): "+text);
    }

    private void print (String text) {
        System.out.print("Игра("+id+"): "+text);
    }

    public User getPlayer(int id) {

        if(id <= 1)
            return players[id];

        return null;
    }

    public User getCurrentPlayer () {

        if(id <= 1)
            return players[currentPlayer];

        return null;
    }

    public int getStatus () {
        return status;
    }

    public BattleField getField (int id) {

        if(id <= 1)
            return battlefields[id];

        return null;
    }

    public void makeTurn () throws IOException {
        if(battlefields[0].isDefeat())
            println("Победил игрок "+players[1].getName()+"!");
        if(battlefields[1].isDefeat())
            println("Победил игрок "+players[0].getName()+"!");

        switch (status) {
            case 0:
                printFields();

                for(int i=1; i<4; i++) {
                    Ship ship;
                    int x, y;
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

            case 1:
                println("Ход игрока "+players[currentPlayer].getName());

                printFields();

                int x, y;
                do {

                    print("X: ");
                    x = Integer.parseInt(BattleShip.readLine());

                    print("Y: ");
                    y = Integer.parseInt(BattleShip.readLine());

                } while (!battlefields[currentPlayer == 0 ? 1 : 0].makeShot(x , y));

                printFields();

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

                if(currentPlayer == 0)
                    currentPlayer = 1;
                else
                    currentPlayer = 0;
                break;
        }

    }
}
