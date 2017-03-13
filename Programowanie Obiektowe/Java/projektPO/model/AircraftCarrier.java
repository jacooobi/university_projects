package projektPO.model;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;

import java.util.Random;

public class AircraftCarrier extends Ship {
    /**
     * Lista mozliwych typow uzbrojenia
     */
    public static String[] weaponTypes = {
        "Pocisk przeciwlotniczy", "Pocisk przeciwrakietowy", "Rakiety nakierowane", "Działko lotnicze", "Działko maszynowe",
            "Bomby burzące", "Bomby odłamkowe", "Bomby głębinowe", "Bomby zapaląjące", "Bomby kasetowe"
    };

    private String weaponType;

    /**
     * konstruktor lotniskowca
     */
    public AircraftCarrier() {
        super();
        Random rand = new Random();
        this.setIcon("http://i.imgur.com/7fFmXCK.png");
        this.baseAngle = 180;
        this.clearArea = 30;
        this.painter = new VehiclePainter(clearArea, baseAngle, icon);
        this.weaponType = weaponTypes[rand.nextInt(weaponTypes.length - 1)];
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

    /**
     * Umieszcza i aktualizuje polozenie graficzne pojazdu
     * @param context obiekt na ktorych rysowany jest pojazd
     */
    public void draw(GraphicsContext context) {
        this.currentRoute = plan.getOriginalRoute();
        this.dir = 0;
        setDestination();

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

                            if (painter.getDistance() < 15) {

                                passedStopsCount += 1;
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
     * Typ uzbrojenia w postaci Stringa
     * @return typ uzbrojenia pojazdu jako String
     */
    public String armatureTypeStringified() {
        return weaponType;
    }

    public String getWeaponType() {
        return weaponType;
    }

    public String identityStringified() {
        return "Lotniskowiec";
    }

}
