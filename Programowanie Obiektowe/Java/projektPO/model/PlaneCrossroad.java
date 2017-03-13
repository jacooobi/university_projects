package projektPO.model;

public class PlaneCrossroad extends Crossroad {
    /**
     * Konstruktor skrzyzowania tras powietrznych
     * @param x wspolrzedna X skrzyzowania
     * @param y wspolrzedna Y skrzyzowania
     */
    public PlaneCrossroad(double x, double y) {
        super(x, y);
        this.setIcon("http://i.imgur.com/sAODSWd.png");
    }
}
