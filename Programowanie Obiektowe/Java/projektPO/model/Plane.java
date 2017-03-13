package projektPO.model;

public class Plane extends Vehicle {
    /**
     * Poziom paliwa
     */
    protected double fuel;

    /**
     *
     */


    /**
     * Konstruktor samolotu
     */
    public Plane() {
        super();
        this.radius = 15;
        this.maxSpeed = 1;
        this.fuel = 10000;
        this.passedStopsCount = 0;
        this.emergencyLandingRequired = false;
    }

    /**
     * Zwraca poziom paliwa w postaci Stringa
     * @return poziom paliwa jako String
     */
    public String fuelStringified() {
        return Integer.toString((int)(fuel));
    }

    /**
     * Zwraca kolejny przystanek(lotnisko) na trasie
     * @return przystanek, lotnisko
     */
    public String nextStopStringified() {
        Location loc = currentRoute.get(passedStopsCount+1);

        return "Lotnisko nr " + Integer.toString(loc.getId());
    }
}
