package projektPO.model;

public class PassengerFlightPlan extends FlightPlan {
    /**
     * Lista punktow oznaczajacych kolejne lotniska dla samolotow pasazerskich
     */
    public static final int[] LocationIds = {0,2,3,4,7,8,10,11,14};

    /**
     * Konstruktor dla planu podrozy samolotem pasazerskim
     */
    public PassengerFlightPlan() {
        super();
        int []endpoints = pickEndpoints(LocationIds);
        this.start = World.getInstance().getAirports().get(endpoints[0]);
        this.finish = World.getInstance().getAirports().get(endpoints[1]);
    }
}
