package projektPO.model;

public class Ship extends Vehicle{
    /**
     * Konstruktor statku
     */
    public Ship() {
        super();
        this.radius = 15;
        this.maxSpeed = 1;
        this.passedStopsCount = 0;
    }
}
