public class Ship {
    private int length;
    private int x;
    private int y;
    private int wounds = 0;

    public Ship(int length, int x, int y) {
        this.length = length;
        this.x = x;
        this.y = y;
    }

    public int getLength() {
        return length;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    //public makeWound() ? TO DO

    public boolean isDestroyed() {
        // TO DO
        return true;
    }
}