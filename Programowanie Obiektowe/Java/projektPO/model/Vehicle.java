package projektPO.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.ArrayList;

public class Vehicle implements Serializable {
    private static int GLOBAL_ID = 0;
    /**
     * pojazd zostal usuniety
     */
    protected boolean deleted;
    /**
     * wspolrzedna X polozenia pojazdu
     */
    protected double x;
    /**
     * wspolrzedna X polozenia pojazdu
     */
    protected double y;
    /**
     * promien ikony pojazdu
     */
    protected double radius;
    /**
     * maksymalna predkosc osiagana przez pojazd
     */
    protected double maxSpeed;
    /**
     * poczatkowy kat pod jakim nalezy obrocic ikone pojazdu
     */
    protected double baseAngle;
    /**
     * obszar do "wyczyszczenie" po zmianie polozenia przez pojazd
     */
    protected double clearArea;
    /**
     * pojazd zostal zaznaczony
     */
    protected boolean selected;
    /**
     * liczba minietych przystankow
     */
    protected int passedStopsCount;
    /**
     * kierunek animacji pojazdu
     */
    protected int dir;
    /**
     * ikona pojazdu
     */
    protected Image icon;
    /**
     * identyfikator pojazdu
     */
    protected String id;
    /**
     * plan, trasa pojazdu
     */
    protected Plan plan;
    /**
     * lista pasazerow
     */
    protected ArrayList<Passenger> passengers;
    /**
     * lista lokacji z ktorych sklada sie trasa pojazdu
     */
    protected ArrayList<Location> currentRoute;
    /**
     * obiekt odpowiedzialny za malowanie pojazdu
     */
    protected VehiclePainter painter;

    protected boolean emergencyLandingRequired;

    protected Location currentLocation;

    public boolean isLockAcquired() {
        return lockAcquired;
    }

    protected boolean lockAcquired;

    /**
     * Konstruktor pojazdu
     */
    public Vehicle() {
        this.id = Integer.toString(++GLOBAL_ID);
        this.passengers = new ArrayList();
        this.lockAcquired = false;
    }

    /**
     * Przypisuje "pedzel"
     * @param painter obiekt malujacy pojazdy
     */
    public void setPainter(VehiclePainter painter) {
        this.painter = painter;
    }

    /**
     * Getter dla listy pasazerow pojazdu
     * @return lista pasazerow
     */
    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    /**
     * Rysuje pojazd na mapie
     * @param context obiekt na ktorym rysowane sa wszystkie pojazdy
     */
    public void draw(GraphicsContext context) {

    }

    /**
     * @return zwraca true jak pojazd zostal usuniety, w przeciwynym wypadku false
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * @return zwraca true jak pojazd zostal zaznaczony, w przeciwnym wypadku false
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Ustawia stan zaznaczenia
     * @param selected typ boolowski
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Ustawia stan usuniecia
     * @param deleted typ boolowski
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * Dodaje pasazera
     * @param passenger nowy pasazer
     */
    public void addPassenger(Passenger passenger) {
        this.passengers.add(passenger);
    }

    /**
     * Zwraca id pojazdu
     * @return id pojazdu w postaci Stringa
     */
    public String getId() {
        return id;
    }

    /**
     * Zwraca wspolrzedna X pojazdu
     * @return wspolrzedna X pojazdu
     */
    public double getX() {
        return x;

    }

    /**
     * Zwraca wspolrzedna Y pojazdu
     * @return wspolrzedna Y pojazdu
     */
    public double getY() {
        return y;
    }

    /**
     * Zwraca centralna wspolrzedna X pojazdu
     * @return centralna wspolrzedna X pojazdu
     */
    public double getCenterX() {
        return x + radius;
    }

    /**
     * Zwraca centralna wspolrzedna Y pojazdu
     * @return centralna wspolrzedna Y pojazdu
     */
    public double getCenterY() {
        return y + radius;
    }

    /**
     * Ustawia obraz/grafike pojazdu
     * @param location obrazek pojazdu
     */
    public void setIcon(String location) {
        this.icon = new Image(location);
    }


    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }


    /**
     * Zwraca plan podrozy pojazdu
     * @return plan podrozy pojazdu
     */
    public Plan getPlan() {
        return plan;
    }

    /**
     * Ustawia plan podrozy pojazdu
     * @param plan plan podrozy pojazdu
     */
    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    /**
     * Zwraca aktualne polozenie pojazdu jako String
     * @return aktualne polozenie w postaci Stringa
     */
    public String currentLocationStringified() {
        return ("X: " + Integer.toString((int)(painter.getCurrentX())) + " Y: " + Integer.toString((int)(painter.getCurrentY())));
    }

    /**
     * Zwraca pojemnosc pojazdu jako String
     * @return pojemnosc pojazdu w postaci Stringa
     */
    public String capacityStringified() {
        return notApplicable();
    }

    /**
     * Zwraca predkosc pojazdu jako String
     * @return predkosc pojazdu w postaci Stringa
     */
    public String speedStringified() {
        return Integer.toString((int)(maxSpeed*1000)) + " km/h";
    }

    /**
     * Zwraca rodzaj uzbrojenia pojazdu jako String
     * @return rodzaj uzbrojenia pojazdu w postaci Stringa
     */
    public String armatureTypeStringified() {
        return notApplicable();
    }

    /**
     * Zwraca identyfikator pojazdu jako String
     * @return identyfikator pojazdu w postaci Stringa
     */
    public String idStringified() {
        return id;
    }

    /**
     * Zwraca liste pasazerow pojazdu jako String
     * @return lista pasazerow pojazdu w postaci Stringa
     */
    public String passengersStringified() {
        return notApplicable();
    }

    /**
     * Zwraca zaloge pojazdu jako String
     * @return zaloga pojazdu w postaci Stringa
     */
    public String staffStringified() {
        return notApplicable();
    }

    /**
     * Zwraca poziom paliwa jako String
     * @return poziom paliwa w postaci Stringa
     */
    public String fuelStringified() {
        return notApplicable();
    }

    /**
     * Zwraca plan trasy pojazdu jako String
     * @return plan trasy pojazdu w postaci Stringa
     */
    public String planStringified() {
        String result = "";
        for(Location loc : currentRoute) {
            result += " -> ";
            result += Integer.toString(loc.getId());
        }
        return result;
    }

    /**
     * Zwraca nastepny postoj pojazdu jako String
     * @return nastepny postoj pojazdu w postaci Stringa
     */
    public String nextStopStringified() {
        return notApplicable();
    }

    /**
     * Zwraca firme bedaca wlascicielem pojazdu jako String
     * @return firma bedaca wlascicielem pojazdu w postaci Stringa
     */
    public String companyStringified() {
        return notApplicable();
    }

    /**
     * Zwraca drugi identyfikator pojazdu jako String
     * @return drugi identyfikator pojazdu w postaci Stringa
     */
    public String identityStringified() {
        return "WEHIKUŁ";
    }

    private String notApplicable() {
        return  "nie dotyczy";
    }

    public void setWeaponType(String weaponType) {
        return;
    }

    public String getWeaponType() {
        return "";
    }

    public void setEmergencyLandingRequired(boolean emergencyLandingRequired) {
        this.emergencyLandingRequired = emergencyLandingRequired;
    }

    public boolean isEmergencyLandingRequired() {
        return emergencyLandingRequired;
    }
}

