import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BattleShip {
    static private List<User> users = new ArrayList<User>(); // Список всех зарегистрированных игроков
    static private List<Game> games = new ArrayList<Game>(); // Список всех начатых игр

    static private User currentUser; // Чей ход

    static private int gid = 0; // Глобальное значение id игры (+1 за каждый начатый матч)

    public static void main(String [] args) throws IOException {

        while(true) { // Пока запущена программа

            int choise = Menu.regLog(); // Вывести главное меню

            if(choise == 2) { // Если выбрана регистрация
                String name, login, password;
                do {
                    print("Логин: ");
                    login = readLine();
                    print("Пароль: ");
                    password = readLine();
                    print("Имя игрока: ");
                    name = readLine();
                } while (findUser(login) != null);

                users.add(new User(name, login, password));

                println("");
                println("Новый пользователь ("+login+":"+password+") создан");
                println("");

                continue;
            }

            if(choise == 1) { // Если выбрана авторизация
                String login, password;
                do {
                    print("Логин: ");
                    login = readLine();
                } while (findUser(login) == null);

                User user = findUser(login);

                do {
                    print("Пароль: ");
                    password = readLine();
                } while (password.compareTo(user.getPassword()) != 0); // Проверка пароля

                println("");
                println("Пользователь "+user.getName()+" авторизован");
                println("");

                currentUser = user;

                int locchoise = Menu.createPlay(); // Вывести меню "Играть/Создать игру"

                if(locchoise == 2) { // Если выбрано создать игру
                    println("Авторизация соперника: ");

                    String login2, password2; // Запросить данные соперника
                    do {
                        print("Логин: ");
                        login2 = readLine();
                    } while (findUser(login2) == null);

                    User user2 = findUser(login2);

                    do {
                        print("Пароль: ");
                        password2 = readLine();
                    } while (password2.compareTo(user2.getPassword()) != 0);

                    games.add(new Game(gid, user, user2)); // Когда соперник авторизован добавить игру в список games

                    println("");
                    println("Соперник "+user2.getName()+" добавлен в игру №"+gid);
                    println("");

                    gid++;
                }

                List<Game> locgames = getCurrentUserGames(); // Получить список игр, в которых идет ход игрока currentUser

                if(locgames.size() == 0) {
                    println("Нет доступных игр для данного игрока");
                    println("");
                } else {
                    for(Game game : locgames) { // Если есть доступные игры, выполнить ход в каждой из них
                        game.makeTurn();
                    }

                    println("Игрок "+user.getName()+" закончил ходы");
                    println("");
                    continue;
                }
            }

        }
    }

    public static String readLine() throws IOException { // Чтение строки ввода
        int ch = 0;
        String input = "";
        do {
            ch = System.in.read(); // Считать 1 символ
            input += (char)ch; // Добавить его к выходной строке
        } while(ch != 13 && ch != 10); // проверка на достижение символов конца строки '\r\n'

        return input.replaceAll(""+(char)13, "").replaceAll(""+(char)10, ""); // Удалить символы переноса строки
    }

    public static void print (String text) {
        System.out.print(""+text);
    }

    public static void println (String text) {
        System.out.println(""+text);
    }

    //public static void addUser (String name, String login, String password, int type) { // Добавить пользователя
        //Не используется. Аналог -
        /*
        String name, login, password;
        do {
            print("Логин: ");
            login = readLine();
            print("Пароль: ");
            password = readLine();
            print("Имя игрока: ");
            name = readLine();
        } while (findUser(login) != null);

        users.add(new User(name, login, password));

        println("");
        println("Новый пользователь ("+login+":"+password+") создан");
        println("");
        */
    //}

    public static User findUser (String login) { // Найти игрока в списке users по логину
        for(User user : users) {
            if(user.getLogin().compareTo(login) == 0)
                return user;
        }

        return null;
    }

    public static List<Game> getCurrentUserGames () { // Получить список игр, в которых идет ход игрока currentUser
        List<Game> locgames = new ArrayList<Game>();

        for(Game game : games) { // Для всех игр в списке games как game
            if(game.getCurrentPlayer() == currentUser)
                locgames.add(game);
        }

        return locgames;
    }
}