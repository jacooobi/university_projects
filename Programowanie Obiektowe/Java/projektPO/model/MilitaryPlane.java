package projektPO.model;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;

public class MilitaryPlane extends Plane {
    private String weaponType;

    /**
     * Konstruktor samolotu wojskowego
     */
    public MilitaryPlane() {
        super();
        this.setIcon("http://i.imgur.com/ayI8rml.png");
        this.baseAngle = 85;
        this.clearArea = 18;
        this.painter = new VehiclePainter(clearArea, baseAngle, icon);
    }

    /**
     * Umieszcza i aktualizuje polozenie graficzne pojazdu
     * @param context obiekt na ktorych rysowany jest pojazd
     */
    public void draw(GraphicsContext context) {
        this.currentRoute = plan.getOriginalRoute();
        this.dir = 0;
        initialStart();

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
                                Connection con = new Connection(painter.getCurrentX(), painter.getCurrentY(), painter.closestMilitaryAirport().getCenterX(), painter.closestMilitaryAirport().getCenterY());
                                painter.setDestination(con);
                                painter.redraw(context);
                                setEmergencyLandingRequired(false);
                            }
                            if (painter.getDistance() < 15) {

                                passedStopsCount += 1;
                                setDestination();
                                painter.redraw(context);

                            }
                            fuel = fuel - 1;
                            painter.draw(context);

                        }
                    });
                }
            }
        };

        renderer.setDaemon(true);
        renderer.start();
    }

    private void initialStart() {
        Location loc = currentRoute.get(0);
        Connection con = new Connection(loc.getX(), loc.getY(), currentRoute.get(1).getCenterX(), currentRoute.get(1).getCenterY());
        painter.setDestination(con);
        painter.calculateAnimation();
        passedStopsCount = -1;
        currentRoute.remove(0);
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

    /**
     * Oblicza srodkowa wspolrzedna X samolotu
     * @return srodkowa wspolrzedna X samolotu
     */
    public double getCenterX() {
        return painter.getCurrentX() + radius;
    }

    /**
     * Oblicza srodkowa wspolrzedna Y samolotu
     * @return srodkowa wspolrzedna Y samolotu
     */
    public double getCenterY() {
        return painter.getCurrentY() + radius;
    }

    /**
     * Identyfikator w postaci Stringa
     * @return identyfikator pojazdu jako String
     */
    public String identityStringified() {
        return "Samolot wojskowy";
    }

    /**
     * Typ uzbrojenia w postaci Stringa
     * @return typ uzbrojenia pojazdu jako String
     */
    public String armatureTypeStringified() {
        return weaponType;
    }

    public void setWeaponType(String weaponType) {
        this.weaponType = weaponType;
    }
}