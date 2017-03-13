package projektPO.model;

import com.github.javafaker.Faker;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class CruiseShip extends Ship {
    /**
     * Firma bedaca wlascicielem statku
     */
    private String company;
    /**
     * pojemnosc wycieczkowca
     */
    private int maximumCapacity;

    /**
     * konstruktor wycieczkowca
     */
    public CruiseShip() {
        super();
        Faker faker = new Faker();
        Random rand = new Random();
        this.setIcon("http://i.imgur.com/N7n4Nel.png");
        this.clearArea = 30;
        this.painter = new VehiclePainter(clearArea, baseAngle, icon);
        this.company = faker.company().name();
        this.maximumCapacity = rand.nextInt(100) + 20;
    }

    /**
     * Ustawia kierunek animacji pojazdu
     */
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

    /**
     * Umieszcza i aktualizuje polozenie graficzne pojazdu
     * @param context obiekt na ktorych rysowany jest pojazd
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

                            if (dockReached()) {

                                updateStopsCounter();
                                updatePassengers();

                                dropPassengers();
                                pickupPassengers();

                                setDestination();
                                painter.redraw(context);

                            }

                            painter.draw(context);
                        }
                    });
                }
            }
        };

        renderer.setDaemon(true);
        renderer.start();
    }

    /**
     * Sprawdza czy wycieczkowiec dotarl do portu
     * @return true gdy wycieczkowiec doplynal do portu, w przeciwynym wypadku false
     */
    private boolean dockReached() {
        return painter.getDistance() < 15;
    }

    /**
     * Inkrementuje liczbe odwiedzonych portow przez wycieczkowiec
     */
    private void updateStopsCounter() {
        passedStopsCount += 1;
    }

    /**
     * Inkrementuje liczbe odwiedzonych portow u kazdego z pasazerow
     */
    private void updatePassengers() {
        for(Passenger pas : getPassengers()) {
            pas.incrementPassedStopsCount();
        }
    }

    /**
     * Wysadza pasazerow w porcie
     */
    private void dropPassengers() {
        if (finalStopReached()) {
            currentRoute.get(passedStopsCount).getPassengers().addAll(getPassengers());
            getPassengers().clear();
        }

        ArrayList<Passenger> janusze = getPassengers().stream()
                .filter(pas -> pas.getLastStop().getId() == currentRoute.get(passedStopsCount).getId() || pas.getNextDockStop().getId() != nextStop().getId())
                .collect(Collectors.toCollection(ArrayList::new));

        currentRoute.get(passedStopsCount).getPassengers().addAll(janusze);
        getPassengers().removeAll(janusze);
    }

    /**
     * Zabiera pasazerow z portu
     */
    private void pickupPassengers() {
        if (finalStopReached()) return;

        Location loc = currentRoute.get(passedStopsCount);
        ArrayList<Passenger> janusze = currentRoute.get(passedStopsCount).getPassengers()
                .stream().filter(pas -> (pas.getNextDockStop().getId() == nextStop().getId()))
                .collect(Collectors.toCollection(ArrayList::new));

        getPassengers().addAll(janusze);
        loc.getPassengers().removeAll(janusze);
    }

    /**
     * Zabiera pasazerow z poczatkowego portu
     */
    private void pickupInitialPassengers() {
        Location loc = plan.getOriginalRoute().get(0);

        ArrayList<Passenger> janusze = loc.getPassengers().stream().filter(pas -> pas.getNextDockStop().getId() == nextStop().getId())
                .collect(Collectors.toCollection(ArrayList::new));

        if (janusze.isEmpty()) return;

        for(Passenger pas : janusze) {
            pas.setCurrentVehicle(this);
            pas.resetCurrentStop();
        }

        getPassengers().addAll(janusze);
        loc.getPassengers().removeAll(janusze);
    }

    /**
     * Oblicza nastepny port na trasie
     * @return Zwraca nastepny port na trasie
     */
    private Location nextStop() {
        return currentRoute.get(passedStopsCount);
    }

    /**
     * Sprawdza czy wycieczkowiec doplynal do konca trasy
     * @return true gdy wycieczkowiec doplynal do konca trasy w przeciwynym wypadku false
     */
    private boolean finalStopReached() {
        return (currentRoute.size() - 1 == passedStopsCount);
    }

    /**
     * Oblicza wspolrzedna X srodka pojazdu
     * @return wspolrzedna X srodka pojazdu
     */
    public double getCenterX() {
        return painter.getCurrentX() + radius;
    }

    /**
     * Oblicza wspolrzedna Y srodka pojazdu
     * @return wspolrzedna Y srodka pojazdu
     */
    public double getCenterY() {
        return painter.getCurrentY() + radius;
    }

    /**
     * Firma wycieczkowca w postaci Stringa
     * @return Firma wycieczkowca jako String
     */
    public String companyStringified() {
        return company;
    }

    /**
     * Mkasymalna pojemnosc wycieczkowca w postaci Stringa
     * @return Mkasymalna pojemnosc wycieczkowca jako String
     */
    public String capacityStringified() {
        return Integer.toString(maximumCapacity);
    }

    public String passengersStringified() {
        return Integer.toString(passengers.size());
    }

    public String identityStringified() {
        return "Wycieczkowiec";
    }
}
