package projektPO.model;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;
import projektPO.utility.Process2;

public class PassengerPlane extends Plane{
    private int staffCount;
    private int maximumCapacity;

    private ArrayList<Passenger> passengers;

    /**
     * Konstruktor samolotu pasazerskiego
     */
    public PassengerPlane() {
        super();
        Random rand = new Random();
        this.passengers = new ArrayList();
        this.setIcon("http://i.imgur.com/Fv08lVB.png");
        this.baseAngle = 45;
        this.clearArea = 20;
        this.maximumCapacity = rand.nextInt(10) + 1;
        this.staffCount = rand.nextInt(5) + 5;
        this.painter = new VehiclePainter(clearArea, baseAngle, icon);
    }

    /**
     * Zwraca liste pasazerow samolotu
     * @return lista pasazerow
     */
    @Override
    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    /**
     * Rysuje pojazd na mapie swiata
     * @param context obiekt na ktorym rysowane sa wszystkie pojazdy
     */
    public void draw(GraphicsContext context) {
        this.currentRoute = plan.getOriginalRoute();
        this.dir = 0;
        setDestination();
        pickupInitialPassengers();

        Thread renderer = new Thread(){

            @Override
            public void run(){

                while (true) {
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException ex) {
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (isDeleted()) return;

                            if (isEmergencyLandingRequired()) {
                                Connection con = new Connection(painter.getCurrentX(), painter.getCurrentY(), painter.closestPublicAirport().getCenterX(), painter.closestPublicAirport().getCenterY());
                                painter.setDestination(con);
                                painter.redraw(context);
                                setEmergencyLandingRequired(false);
                            }

                            if (!closestLocation().equals(getCurrentLocation())) {
                                getCurrentLocation().getSem().release();
                                setCurrentLocation(closestLocation());
                                lockAcquired = false;
                            }

                            if (locationReached()) {

                                if (closestLocation().equals(getCurrentLocation()) && closestLocation().getSem().availablePermits() > 0) {
                                    try {
                                        getCurrentLocation().getSem().acquire();
                                        lockAcquired = true;
                                    } catch (InterruptedException e) {
                                        System.out.println("Nie udalo sie zalokowac!");
                                        return;
                                    }
                                } else if (lockAcquired) {

                                } else {
                                    return;
                                }
//                               getCurrentLocation().getSem().release();
                            }

                            if (airportReached()) {
                                updateStopsCounter();
                                updatePassengers();

                                if (allowedToLand()) {
                                    dropPassengers();
                                    pickupPassengers();
                                    refuel();
                                }

                                setDestination();
                                painter.redraw(context);
                            }

                            burnFuel();
                            painter.draw(context);
                        }

                    });
                }
            }
        };

        renderer.setDaemon(true);
        renderer.start();
    }

    private boolean locationReached() {
        return ((painter.distanceToClosestPlaneLocation() - 10) < 0);
    }

    private Location closestLocation() {
        return painter.closestPlaneLocation();
    }

    private void setDestination() {
        if(finalStopReached()) {
            passedStopsCount = 0;
            if(dir==0) {
                dir = 1;
                currentRoute = plan.getReturnRoute();
            } else {
                dir = 0;
                currentRoute = plan.getOriginalRoute();

            }
        }

        Connection con = currentRoute.get(passedStopsCount).getConnections().get(currentRoute.get(passedStopsCount+1).id);
        painter.setDestination(con);
        painter.calculateAnimation();
    }

    private boolean finalStopReached() {
        return (currentRoute.size() - 1 == passedStopsCount);
    }

    private void updateStopsCounter() {
        passedStopsCount += 1;
    }

    private void refuel() {
        this.fuel = 10000;
    }

    private void burnFuel() {
        this.fuel -= 5;
    }

    private boolean airportReached() {
        return painter.getDistance() < 15;
    }

    private void pickupInitialPassengers() {
        Location loc = plan.getOriginalRoute().get(0);
        setCurrentLocation(loc);

        ArrayList<Passenger> janusze = loc.getPassengers().stream().filter(pas -> pas.getNextAirportStop().getId() == nextStop().getId())
                .collect(Collectors.toCollection(ArrayList::new));

        if (janusze.isEmpty()) return;

        for(Passenger pas : janusze) {
            pas.setCurrentVehicle(this);
            pas.resetCurrentStop();
        }

        getPassengers().addAll(janusze);
        loc.getPassengers().removeAll(janusze);
    }

    private void updatePassengers() {
        for(Passenger pas : getPassengers()) {
            pas.incrementPassedStopsCount();
        }
    }

    private void dropPassengers() {
        if (finalStopReached()) {
            currentRoute.get(passedStopsCount).getPassengers().addAll(getPassengers());
            getPassengers().clear();
        }

        ArrayList<Passenger> janusze = getPassengers().stream()
                .filter(pas -> pas.getLastStop().getId() == currentRoute.get(passedStopsCount).getId() || pas.getNextAirportStop().getId() != nextStop().getId())
                .collect(Collectors.toCollection(ArrayList::new));

        currentRoute.get(passedStopsCount).getPassengers().addAll(janusze);
        getPassengers().removeAll(janusze);
    }

    private void pickupPassengers() {
        if (finalStopReached()) return;

        Location loc = currentRoute.get(passedStopsCount);
        ArrayList<Passenger> janusze = currentRoute.get(passedStopsCount).getPassengers()
                .stream().filter(pas -> (pas.getNextAirportStop().getId() == nextStop().getId()))
                .collect(Collectors.toCollection(ArrayList::new));

        getPassengers().addAll(janusze);
        loc.getPassengers().removeAll(janusze);
    }

    /**
     * Kolejny przystanek na trasie samolotu
     * @return nastepny przystanek
     */

    public Location nextStop() {
        int counter = passedStopsCount+1;

        while (currentRoute.get(counter).getClass().getSimpleName().equals("MilitaryAirport")) {
            counter += 1;
        }

        return currentRoute.get(counter);
    }

    private Location nextAirport() {
        return currentRoute.get(passedStopsCount);
    }

    private boolean allowedToLand() {
        return nextAirport().getClass().getSimpleName().equals("PublicAirport");
    }

    /**
     * Zwraca srodkowa wspolrzedna X samolotu
     * @return srodkowa wspolrzedna X
     */
    public double getCenterX() {
        return painter.getCurrentX() + radius;
    }

    /**
     * Zwraca srodkowa wspolrzedna Y samolotu
     * @return srodkowa wspolrzedna Y
     */
    public double getCenterY() {
        return painter.getCurrentY() + radius;
    }

    /**
     * Zwraca rodzaj samolotu
     * @return rodzaj samolotu w postaci Stringa
     */
    public String identityStringified() {
        return "Samolot pasażerski";
    }

    /**
     * Zwraca pojemnosc samolotu jako String
     * @return pojemnosc samolotu w postaci Stringa
     */
    public String capacityStringified() {
        return Integer.toString(maximumCapacity);
    }

    /**
     * Zwraca zaloge samolotu jako String
     * @return zaloga samolotu w postaci Stringa
     */
    public String staffStringified() { return Integer.toString(staffCount); }
}
