import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BattleShip {
    static private List<User> users = new ArrayList<User>();
    static private List<Game> games = new ArrayList<Game>();

    static private User currentUser;

    static private int gid = 0;

    public static void main(String [] args) throws IOException {

        while(true) {

            int choise = Menu.regLog();

            if(choise == 2) {
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

            if(choise == 1) {
                String login, password;
                do {
                    print("Логин: ");
                    login = readLine();
                } while (findUser(login) == null);

                User user = findUser(login);

                do {
                    print("Пароль: ");
                    password = readLine();
                } while (password.compareTo(user.getPassword()) != 0);

                println("");
                println("Пользователь "+user.getName()+" авторизован");
                println("");

                currentUser = user;

                int locchoise = Menu.createPlay();

                if(locchoise == 2) {
                    println("Авторизация соперника: ");

                    String login2, password2;
                    do {
                        print("Логин: ");
                        login2 = readLine();
                    } while (findUser(login2) == null);

                    User user2 = findUser(login2);

                    do {
                        print("Пароль: ");
                        password2 = readLine();
                    } while (password2.compareTo(user2.getPassword()) != 0);

                    games.add(new Game(gid, user, user2));

                    println("");
                    println("Соперник "+user2.getName()+" добавлен в игру №"+gid);
                    println("");

                    gid++;
                }

                List<Game> locgames = getCurrentUserGames();

                if(locgames.size() == 0) {
                    println("Нет доступных игр для данного игрока");
                    println("");
                } else {
                    for(Game game : locgames) {
                        game.makeTurn();
                    }

                    println("Игрок "+user.getName()+" закончил ходы");
                    println("");
                    continue;
                }
            }

        }
    }

    static String readLine() throws IOException {
        int ch = 0;
        String input = "";
        do {
            ch = System.in.read();
            input += (char)ch;
        } while(ch != 13 && ch != 10);

        return input.replaceAll(""+(char)13, "").replaceAll(""+(char)10, "");
    }

    static public void print(String text) {
        System.out.print(""+text);
    }

    static public void println(String text) {
        System.out.println(""+text);
    }

    static public void addUser (String name, String login, String password, int type) {

    }

    static public User findUser (String login) {
        for(User user : users) {
            if(user.getLogin().compareTo(login) == 0)
                return user;
        }

        return null;
    }

    static public List<Game> getCurrentUserGames () {
        List<Game> locgames = new ArrayList<Game>();

        for(Game game : games) {
            if(game.getCurrentPlayer() == currentUser)
                locgames.add(game);
        }

        return locgames;
    }
}