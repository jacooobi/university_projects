package projektPO.model;

public class ShipCrossroad extends Crossroad {
    /**
     * Konstruktor dla skrzyzowania drog morskich
     * @param x wspolrzedna X
     * @param y wspolrzedna Y
     */
    public ShipCrossroad(double x, double y) {
        super(x, y);
        this.setIcon("http://i.imgur.com/PJXVxYT.png");
    }
}
