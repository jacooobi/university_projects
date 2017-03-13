package projektPO.model;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class VehiclePainter {
    private double defaultSpeed;
    private double currentX;
    private double currentY;
    private double startX;
    private double finishX;
    private double startY;
    private double finishY;
    private double dirX;
    private double dirY;
    private double distanceX;
    private double distanceY;
    private double ratio;
    private double speedX;
    private double speedY;
    private double angle;
    private double baseAngle;
    private double clearArea;
    private Image rotatedImage;
    private Image originalImage;
    protected ImageView imageView;
    protected SnapshotParameters snapshopParams;

    /**
     * Konstruktow klasy odpowiedzialnej za rysowanie pojazdow na mapie swiata
     * @param clearArea obszar do "wyczyszczenia" po zmianie polozenia przez pojazd
     * @param baseAngle kat pomiedzy dwoma punktami miedzy ktorymi przemieszcza sie pojazd
     * @param originalImage ikona pojazdu
     */
    public VehiclePainter(double clearArea, double baseAngle, Image originalImage) {
        defaultSpeed = 1;
        this.baseAngle = baseAngle;
        this.clearArea = clearArea;
        this.originalImage = originalImage;
    }

    /**
     * Ustala kierunek przemieszczania sie pojazdu
     * @param con polaczenie miedzy dwoma lokacjami
     */
    public void setDestination(Connection con) {
        this.startX = con.getStartX();
        this.finishX = con.getFinishX();
        this.startY = con.getStartY();
        this.finishY = con.getFinishY();
        this.currentX = startX;
        this.currentY = startY;
    }

    /**
     * Oblicza wartosci typu predkosc, dystans, katy potrzebne do rysowania animacji pojazdu
     */
    public void calculateAnimation() {
        this.distanceX = finishX - currentX;
        this.distanceY = finishY - currentY;
        this.dirX = Math.signum(distanceX);
        this.dirY = Math.signum(distanceY);
        this.ratio = measureRatio(distanceX, distanceY);
        if (ratio > 1) {
            this.speedX = defaultSpeed;
            this.speedY = defaultSpeed/ratio;
        } else {
            this.speedX = defaultSpeed*ratio;
            this.speedY = defaultSpeed;
        }
        this.angle = Math.toDegrees(Math.atan2((finishY - startY), (finishX - startX))) + baseAngle;
        this.imageView = new ImageView(originalImage);
        imageView.setRotate(angle);
        this.snapshopParams = new SnapshotParameters();
        snapshopParams.setFill(Color.TRANSPARENT);
        this.rotatedImage = imageView.snapshot(snapshopParams, null);
    }

    private double measureRatio(double distanceX, double distanceY) {
        if (distanceX == 0 || distanceY == 0) {
            return 1;
        } else {
            return Math.abs(distanceX) / Math.abs(distanceY);
        }
    }

    private void drawing(GraphicsContext context) {
        context.clearRect(currentX - speedX * dirX, currentY - speedY * dirY, clearArea + 8, clearArea + 8);
        context.drawImage(rotatedImage, currentX += speedX * dirX, currentY += speedY * dirY);
    }

    /**
     * Ponownie rysuje pojazd na mapie swiata
     * @param context obiekt na ktorym rysowany jest pojazd
     */
    public void redraw(GraphicsContext context) {
        context.clearRect(currentX - 20, currentY - 20, clearArea + 50, clearArea + 50);
        calculateAnimation();
        drawing(context);
    }

    /**
     * Umieszcza i aktualizuje polozenie graficzne pojazdu
     * @param context obiekt na ktorych rysowany jest pojazd
     */
    public void draw(GraphicsContext context) {
        drawing(context);
    }

    /**
     * Zwraca wspolrzedna X aktualnego polozenia pojazdu
     * @return wspolrzedna X srodka aktualnego polozenia pojazdu
     */
    public double getCurrentX() {
        return currentX;
    }

    /**
     * Zwraca wspolrzedna Y aktualnego polozenia pojazdu
     * @return wspolrzedna Y srodka aktualnego polozenia pojazdu
     */
    public double getCurrentY() {
        return currentY;
    }

    /**
     * Oblicza dystans miedzy punktem poczatkowym i koncowym
     * @return dystans dzielacy start i koniec
     */
    public double getDistance() {
        return Math.sqrt((finishX-currentX)*(finishX-currentX) + (finishY-currentY)*(finishY-currentY));
    }


    public double distanceToClosestShipLocation() {
        return calculateDistance(closestShipLocation().getCenterY(), closestShipLocation().getY());
    }

    public double distanceToClosestPlaneLocation() {
        return calculateDistance(closestPlaneLocation().getCenterY(), closestPlaneLocation().getY());
    }
    public Location closestPlaneLocation() {
        Location closestLocation = World.getInstance().getPlaneLocations().get(0);


        double minDistance = calculateDistance(closestLocation.getCenterX(), closestLocation.getCenterY());

        for (Location loc : World.getInstance().getLocations()) {
            if (minDistance > calculateDistance(loc.getCenterX(), loc.getCenterY())) {
                closestLocation = loc;
            }
        }
        return closestLocation;
    }

    public Location closestShipLocation() {
        Location closestLocation = World.getInstance().getShipLocations().get(0);


        double minDistance = calculateDistance(closestLocation.getCenterX(), closestLocation.getCenterY());

        for (Location loc : World.getInstance().getLocations()) {
            if (minDistance > calculateDistance(loc.getCenterX(), loc.getCenterY())) {
                closestLocation = loc;
            }
        }
        return closestLocation;
    }

    public Location closestMilitaryAirport() {
        Location closestLocation = World.getInstance().getMilitaryAirports().get(0);

        double minDistance = calculateDistance(closestLocation.getCenterX(), closestLocation.getCenterY());

        for (Location loc : World.getInstance().getMilitaryAirports()) {
            if (minDistance > calculateDistance(loc.getCenterX(), loc.getCenterY())) {
                closestLocation = loc;
            }
        }
        return closestLocation;
    }

    public Location closestPublicAirport() {
        Location closestLocation = World.getInstance().getPublicAirports().get(0);

        double minDistance = calculateDistance(closestLocation.getCenterX(), closestLocation.getCenterY());

        for (Location loc : World.getInstance().getPublicAirports()) {
            if (minDistance > calculateDistance(loc.getCenterX(), loc.getCenterY())) {
                closestLocation = loc;
            }
        }
        return closestLocation;
    }

    private double calculateDistance(double x, double y) {
        return Math.sqrt(Math.pow((x-currentX),2) + Math.pow((y-currentY), 2));
    }
}
