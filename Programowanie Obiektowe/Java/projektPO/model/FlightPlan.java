package projektPO.model;

import projektPO.utility.DepthFirstPaths;

import java.util.ArrayList;

public class FlightPlan extends Plan{
    /**
     * Konstruktor planu podrozy samolotem
     */
    public FlightPlan() {
        super();
    }

    /**
     * Konstruuje trase lotu samolotem
     */
    public void selectRoute() {
        DepthFirstPaths szukamy = new DepthFirstPaths(World.getInstance().getAirportsGraph(), start.getId());
        ArrayList<Integer> myList = iterToCollection(szukamy.pathTo(finish.getId()));
        saveRoute(myList);
    }

    private void saveRoute(ArrayList list) {
        for(int i = list.size() -1; i>=0; i--) {
            Location loc = World.getInstance().getAirports().get((int)(list.get(i)));
            this.originalRoute.add(loc);
        }

        for(int i = 0; i < list.size(); i++) {
            Location loc = World.getInstance().getAirports().get((int)(list.get(i)));
            this.returnRoute.add(loc);
        }
    }
}
