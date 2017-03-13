package projektPO.model;

import projektPO.utility.DepthFirstPaths;

import java.util.ArrayList;

public class SailPlan extends Plan {
    /**
     * Lista punktow oznaczajacych kolejne porty
     */
    public static final int[] LocationIds = {0,1,2,3,4};

    /**
     * Konstruktor planu podrozy statkow
     */
    public SailPlan() {
        super();
        int []endpoints = pickEndpoints(LocationIds);

        this.start = World.getInstance().getDocks().get(endpoints[0]);
        this.finish = World.getInstance().getDocks().get(endpoints[1]);
    }

    /**
     * Tworzy trase podrozy
     */
    public void selectRoute() {
        DepthFirstPaths szukamy = new DepthFirstPaths(World.getInstance().getDocksGraph(), start.getGraphId());
        ArrayList<Integer> myList = iterToCollection(szukamy.pathTo(finish.getGraphId()));
        saveRoute(myList);
    }

    private void saveRoute(ArrayList list) {
        for(int i = list.size() -1; i>=0; i--) {
            Location loc = World.getInstance().getDocks().get((int)(list.get(i)));
            this.originalRoute.add(loc);
        }


        for(int i = 0; i < list.size(); i++) {
            Location loc = World.getInstance().getDocks().get((int)(list.get(i)));
            this.returnRoute.add(loc);
        }
    }
}
