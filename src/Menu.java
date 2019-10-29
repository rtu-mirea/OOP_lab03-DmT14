import java.io.IOException;

public class Menu {
    public static int regLog() throws IOException {
        int choise = 0;

        do { // Вывести меню и получить выбор
            System.out.println("==============");
            System.out.println("1 - Авторизация");
            System.out.println("2 - Регистрация");
            System.out.println("==============");

            choise = Integer.parseInt(BattleShip.readLine());
        } while (choise < 1 || choise > 2);

        System.out.println();

        return choise;
    }

    public static int createPlay() throws IOException {
        int choise = 0;

        do { // Вывести меню и получить выбор
            System.out.println("==============");
            System.out.println("1 - Играть");
            System.out.println("2 - Создать партию");
            System.out.println("==============");

            choise = Integer.parseInt(BattleShip.readLine());
        } while (choise < 1 || choise > 2);

        System.out.println();

        return choise;
    }
}
