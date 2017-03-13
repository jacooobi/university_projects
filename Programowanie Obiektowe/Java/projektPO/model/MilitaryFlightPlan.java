package projektPO.model;

public class MilitaryFlightPlan extends FlightPlan {
    /**
     * Lista identyfikator kolejnych lotnisk wojskowych
     */
    public static final int[] LocationIds = {1,5,6,9,12,13};

    /**
     * Konstruktor dla planu lotu samolotem wojskowym
     */
    public MilitaryFlightPlan() {
        super();
        int []endpoints = pickEndpoints(LocationIds);

        this.start = World.getInstance().getAirports().get(endpoints[0]);
        this.finish = World.getInstance().getAirports().get(endpoints[1]);
    }

    private void getClosestAirport() {

    }
}
