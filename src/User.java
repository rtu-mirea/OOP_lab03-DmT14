public class User {
    private String name;
    private String login;
    private String password;

    public User() {}

    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public boolean enter(String login, String password) {
        // TO DO
        return true;
    }

    public String getName() {
        return name;
    }
}