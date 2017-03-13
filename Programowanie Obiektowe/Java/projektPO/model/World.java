package projektPO.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import projektPO.utility.Graph;
import projektPO.utility.PointArythmetic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class World implements Serializable {
    private static World instance = new World();

    private ArrayList<Location> locations;
    private ArrayList<Vehicle> vehicles;

    private Graph airportsGraph;
    private Graph docksGraph;

    private World() {
        this.locations = new ArrayList();
        this.vehicles = new ArrayList();
    }

    /**
     * Zwraca jedyna instancje klasy World
     * @return zwraca obiekt swiat
     */
    public static World getInstance() {
        return instance;
    }

    /**
     * Tworzy lokacje na ktore skladaja sie m.in. lotniska, porty i skrzyzowania polaczen pomiedzy tymi obiektami
     */
    public void createLocations() {
        createAirports();
        createDocks();
        createEdges();
        createCrossroads();
    }

    private void createCrossroads() {
        for(int i=0; i<airportsGraph.V(); i++) {
            for(int idI : airportsGraph.adj(i)) {
                for(int j=0; j<airportsGraph.V(); j++) {
                    for(int idJ : airportsGraph.adj(j)) {
                        Location[] pointLocations = {getAirports().get(i), getAirports().get(idI), getAirports().get(j), getAirports().get(idJ)};
                        double[] position = PointArythmetic.intersectionDetected(pointLocations);

                        if (position.length != 0) {
                            Location cross = new PlaneCrossroad((int)(position[0]), (int)(position[1]));
                            if (!locations.contains(cross)) locations.add(cross);
                        }

                    }
                }
            }
        }

        for(int i=0; i<docksGraph.V(); i++) {
            for(int idI : docksGraph.adj(i)) {
                for(int j=0; j<docksGraph.V(); j++) {
                    for(int idJ : docksGraph.adj(j)) {
                        Location[] pointLocations = {getDocks().get(i), getDocks().get(idI), getDocks().get(j), getDocks().get(idJ)};
                        double[] position = PointArythmetic.intersectionDetected(pointLocations);

                        if (position.length != 0) {
                            Location cross = new ShipCrossroad((int)(position[0]), (int)(position[1]));
                            if (!locations.contains(cross)) locations.add(cross);
                        }

                    }
                }
            }
        }
    }

    private void createDocks() {
        locations.add(new Dock(545, 160));
        locations.add(new Dock(210, 280));
        locations.add(new Dock(870, 280));
        locations.add(new Dock(360, 460));
        locations.add(new Dock(740, 460));

        this.docksGraph = new Graph(getDocks().size());
    }

    private void createAirports() {
        locations.add(new PublicAirport(355, 20));
        locations.add(new MilitaryAirport(735, 20));
        locations.add(new PublicAirport(545, 90));
        locations.add(new PublicAirport(165, 230));
        locations.add(new PublicAirport(925, 230));
        locations.add(new MilitaryAirport(545, 290));
        locations.add(new MilitaryAirport(10, 515));
        locations.add(new PublicAirport(350, 515));
        locations.add(new PublicAirport(735, 515));
        locations.add(new MilitaryAirport(1050, 515));
        locations.add(new PublicAirport(165, 680));
        locations.add(new PublicAirport(925, 680));
        locations.add(new MilitaryAirport(545, 790));
        locations.add(new MilitaryAirport(355, 840));
        locations.add(new PublicAirport(735, 840));

        this.airportsGraph = new Graph(getAirports().size());
    }

    private void createEdges() {
        airportsGraph.addEdge(0, 5);
        airportsGraph.addEdge(1, 5);
        airportsGraph.addEdge(2, 3);
        airportsGraph.addEdge(2, 4);
        airportsGraph.addEdge(2, 5);
        airportsGraph.addEdge(3, 10);
        airportsGraph.addEdge(3, 4);
        airportsGraph.addEdge(4, 11);
        airportsGraph.addEdge(5, 13);
        airportsGraph.addEdge(5, 12);
        airportsGraph.addEdge(5, 14);
        airportsGraph.addEdge(6, 7);
        airportsGraph.addEdge(7, 8);
        airportsGraph.addEdge(7, 12);
        airportsGraph.addEdge(8, 12);
        airportsGraph.addEdge(8, 9);
        airportsGraph.addEdge(10, 11);
        airportsGraph.addEdge(10, 12);
        airportsGraph.addEdge(11, 12);

        docksGraph.addEdge(0, 3);
        docksGraph.addEdge(0, 4);
        docksGraph.addEdge(1, 4);
        docksGraph.addEdge(2, 3);
    }

    /**
     * Umieszcza na canvasie wszystkie lokacje
     * @param context obiekt na ktorych rysowane sa porty, lotniska itd.
     */
    public void drawLocations(GraphicsContext context) {
        drawEdges(context);
        for(Location location : locations) {
            location.draw(context);
        }
    }

    private void drawEdges(GraphicsContext context) {
        context.setStroke(Color.rgb(63, 81, 181));
        context.setLineWidth(2);
        context.setLineDashes(15);
        context.setLineDashOffset(10);


        for (int i = 0; i < airportsGraph.V() ; i++) {
            for (int j : airportsGraph.adj(i)) {
                if (j > i) continue;

                Location a = getAirports().get(i);
                Location b = getAirports().get(j);

                context.strokeLine(a.getCenterX(), a.getCenterY(), b.getCenterX(), b.getCenterY());

                if(a.getY() == b.getY()) {
                    a.getConnections().put(j, new Connection(a.getCenterX(), a.getY()+10, b.getCenterX(), b.getY()+10));
                    b.getConnections().put(i, new Connection(b.getCenterX(), b.getBottomY() - 12, a.getCenterX(), a.getBottomY() - 12));

                    continue;
                } else if(a.getX() == b.getX()) {
                    a.getConnections().put(j, new Connection(a.getX()+10, a.getY(), b.getX()+10, b.getY()));
                    b.getConnections().put(i, new Connection(b.getBottomX() - 12, b.getY(), a.getBottomX() - 12, a.getY()));
                    continue;
                } else if(a.getX() > b.getX()){
                    a.getConnections().put(j, new Connection(a.getCenterX(), a.getBottomY()-10, b.getX(), b.getCenterY()-10));
                    b.getConnections().put(i, new Connection(b.getCenterX(), b.getY()-10,a.getBottomX(), a.getCenterY()-10));
                    continue;
                } else {
                    a.getConnections().put(j, new Connection(a.getX(), a.getCenterY()-10, b.getCenterX(), b.getY()-10));
                    b.getConnections().put(i, new Connection(b.getBottomX()-10, b.getCenterY(), a.getCenterX()-10, a.getBottomY()));
                }
            }
        }

        context.setStroke(Color.rgb(33, 150, 243));
        context.setLineDashes(10);
        context.setLineDashOffset(2);

        for (int i = 0; i < docksGraph.V() ; i++) {
            for (int j : docksGraph.adj(i)) {
                if (j > i) continue;
                Location a = getDocks().get(i);
                Location b = getDocks().get(j);

                context.strokeLine(a.getCenterX(), a.getCenterY(), b.getCenterX(), b.getCenterY());

                if(a.getX() > b.getX()){
                    a.getConnections().put(j, new Connection(a.getCenterX(), a.getBottomY()-10, b.getX(), b.getCenterY()-10));
                    b.getConnections().put(i, new Connection(b.getCenterX(), b.getY()-10,a.getBottomX(), a.getCenterY()-10));
                    continue;
                } else {
                    a.getConnections().put(j, new Connection(a.getX(), a.getCenterY()-10, b.getCenterX(), b.getY()-10));
                    b.getConnections().put(i, new Connection(b.getBottomX()-10, b.getCenterY(), a.getCenterX()-10, a.getBottomY()));
                }
            }
        }
    }

    /**
     * Dodaje do listy pojazdow nowy wehikul
     * @param vehicle nowy pojazd
     */
    public void addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
    }

    /**
     * Przedstawia obiekt w postaci Stringa
     * @return zwraca Stringa
     */
    public String toString() {
        String memory = super.toString();
        return memory + "(" + locations + ", " + vehicles + ")";
    }

    public ArrayList<Location> getPlaneLocations() {
        ArrayList<Location> planeLocations = new ArrayList<Location>(getAirports());
        planeLocations.addAll(getPlaneCrossroads());
        return planeLocations;
    }

    public ArrayList<Location> getShipLocations() {
        ArrayList<Location> shipLocations = new ArrayList<Location>(getDocks());
        shipLocations.addAll(getShipCrossroads());
        return shipLocations;
    }
    /**
     * Getter dla listy lokacji
     * @return zwraca liste lokacji
     */
    public ArrayList<Location> getLocations() {
        return locations;
    }

    /**
     * Wyszukuje wsrod lokacji porty
     * @return lista portow
     */
    public ArrayList<Location> getDocks() {
        return locations.stream().filter(loc -> loc.getClass().getSimpleName().equals("Dock")).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Location> getShipCrossroads() {
        return locations.stream().filter(loc -> loc.getClass().getSimpleName().equals("ShipCrossroad")).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Location> getPlaneCrossroads() {
        return locations.stream().filter(loc -> loc.getClass().getSimpleName().equals("PlaneCrossroad")).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Wyszukuje wsrod lokacji lotniska
     * @return lista lotnisk
     */
    public ArrayList<Location> getAirports() {
        return locations.stream().filter(loc -> loc.getClass().getSuperclass().getSimpleName().equals("Airport")).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Wyszukuje wsrod lokacji lotniska dla samolotow pasazerskich
     * @return lista samolotow pasazerskich
     */
    public ArrayList<Location> getPublicAirports() {
        return getAirports().stream().filter(loc -> loc.getClass().getSimpleName().equals("PublicAirport")).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Wyszukuje wsrod lokacji lotniska dla samolotow wojskowych
     * @return lista samolotow wojskowych
     */
    public ArrayList<Location> getMilitaryAirports() {
        return getAirports().stream().filter(loc -> loc.getClass().getSimpleName().equals("MilitaryAirport")).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Zwraca liste pojazdow
     * @return lista pojazdow
     */
    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Wyszukuje wsrod listy pojazdow samoloty
     * @return lista samolotow
     */
    public ArrayList<Vehicle> getPlanes() {
        return vehicles.stream().filter(loc -> loc.getClass().getSuperclass().getSimpleName().equals("Plane")).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Wyszukuje wsrod listy pojazdow statki
     * @return lista statkow
     */
    public ArrayList<Vehicle> getShips() {
        return vehicles.stream().filter(loc -> loc.getClass().getSuperclass().getSimpleName().equals("Ship")).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Wyszukuje wsrod listy pojazdow lotniskowce
     * @return lista lotniskowcow
     */
    public ArrayList<Vehicle> getAircraftCarriers() {
        return getShips().stream().filter(ship -> ship.getClass().getSimpleName().equals("AircraftCarrier")).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Getter dla grafu lotnisk
     * @return graf lotnisk
     */
    public Graph getAirportsGraph() {
        return airportsGraph;
    }

    /**
     * Getter dla grafu portow
     * @return graf portow
     */
    public Graph getDocksGraph() {
        return docksGraph;
    }

}
