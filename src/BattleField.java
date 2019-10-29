import java.util.ArrayList;
import java.util.List;

public class BattleField {
    private List<Ship> ships = new ArrayList<Ship>();
    private List<Shot> shots = new ArrayList<Shot>();

    public boolean placeShip(Ship ship) {
        if(ship.getX()<0 || ship.getX()>=10 || ship.getY()<0 || ship.getY()>=10)
            return false;

        if(ship.getY() + ship.getLength() >= 10)
            return false;


        int x = ship.getX();
        for(int y=ship.getY(); y<ship.getY()+ship.getLength(); y++)
            for(Ship lship : ships) {
                if(x == lship.getX()) {
                    for(int i=lship.getY(); i<lship.getY()+lship.getLength(); i++)
                        if(y == i) {
                            return false;
                        }
                }
            }

        if(ships.size() >= 3)
            return false;

        ships.add(ship);

        return true;
    }

    public List<Ship> getShips () {
        return ships;
    }

    public List<Shot> getShots () {
        return shots;
    }

    public boolean makeShot (int x, int y) {
        if(x<0 || x>=10 || y<0 || y>=10)
            return false;

        for(Shot shot : shots) {
            if(shot.getX() == x && shot.getY() == y)
                return false;
        }

        boolean stat = false;

        for(Ship ship : ships) {
            if(x == ship.getX()) {
                for(int i=ship.getY(); i<ship.getY()+ship.getLength(); i++)
                    if(y == i) {
                        ship.makeWound();
                        stat = true;
                    }
            }
        }

        shots.add(new Shot(x, y, stat));

        return true;
    }

    public boolean isDefeat () {
        int destroyed = 0;

        for(Ship ship : ships) {
            if(ship.isDestroyed())
                destroyed++;
        }

        if(destroyed >= 3)
            return true;

        return false;
    }
}