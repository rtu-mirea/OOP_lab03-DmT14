public class Game {
    private BattleField[] fields = new BattleField[2];
    private Gamer[] players = new Gamer[2];

    public Game(Gamer player1, Gamer player2) {
        this.players[0] = player1;
        this.players[1] = player2;
    }

    public Gamer[] getPlayer(boolean current) {
        // TO DO
        return players;
    }

    public BattleField[] getField() {
        // TO DO
        return fields;
    }
}