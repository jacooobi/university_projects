package projektPO.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Location implements Serializable {
    /**
     * identyfikator lokacji
     */
    protected int id;
    /**
     * wspolrzedna X lokacji
     */
    protected double x;
    /**
     * wspolrzedna Y lokacji
     */
    protected double y;
    /**
     * promien lokacji
     */
    protected double radius;
    /**
     * ikona lokacji
     */
    protected Image icon;
    /**
     * lista pasaszerow lokacji
     */
    protected ArrayList<Passenger> passengers;
    /**
     * lista polaczen lokacji z pozostalymi miejscami
     */
    protected HashMap<Integer, Connection> connections;

    protected int capacity;

    public Semaphore getSem() {
        return sem;
    }

    /**
     * Semafor lokacji
     */
    protected Semaphore sem;

    /**
     * domyslny konstruktor
     */
    public Location() {
    }

    /**
     * Konstruktor Lokacji
     * @param x wspolrzedna X
     * @param y wspolrzedna Y
     * @param id identyfikator
     */
    public Location(double x, double y, int id) {
        Random rand = new Random();
        this.x = x;
        this.y = y;
        this.id = id;
        this.radius = 15;
        this.passengers = new ArrayList();
        this.connections = new HashMap();
        this.sem = new Semaphore(1);
        this.capacity = rand.nextInt(100) + 10;
    }

    /**
     * Porownuje lokacje
     * @param o Lokacja
     * @return true gdy lokacje sa identyczne, w przeciwynym wypadku false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;

        Location location = (Location) o;

        if (Double.compare(location.x, x) != 0) return false;
        return Double.compare(location.y, y) == 0;

    }

    /**
     * nadpisany hashCode
     * @return hashCode
     */
    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /**
     * Rysuje lokacje na canvasie
     * @param context obiekt na ktorym umieszczana jest lokacja
     */
    public void draw(GraphicsContext context) {
        context.drawImage(icon, x, y);
    }

    /**
     * getter dla wspolrzednej X
     * @return wspolrzedna X
     */
    public double getX() {
        return x;
    }

    /**
     * getter dla wspolrzednej Y
     * @return wspolrzedna Y
     */
    public double getY() {
        return y;
    }

    /**
     * getter dla skrajnej dolnej wspolrzednej X
     * @return skrajna dolna wspolrzedna X
     */
    public double getBottomX() {
        return x+(2*radius);
    }

    /**
     * getter dla skrajnej dolnej wspolrzednej Y
     * @return skrajna dolna wspolrzedna Y
     */
    public double getBottomY() {
        return y+(2*radius);
    }

    /**
     * getter dla identyfikatora lokacji
     * @return identyfikator lokacji
     */
    public int getId() {
        return id;
    }

    /**
     * getter dla centralnej wspolrzednej X
     * @return centralna wspolrzedna X
     */
    public double getCenterX() {
        return x + radius;
    }

    /**
     * getter dla centralnej wspolrzednej Y
     * @return centralna wspolrzedna Y
     */
    public double getCenterY() {
        return y + radius;
    }

    /**
     * getter dla identyfikatora lokacji w grafie
     * @return identyfikator lokacji w grafie
     */
    public int getGraphId() {
        return id;
    }

    /**
     * setter dla ikony lokacji
     * @param location ikona lokacji
     */
    public void setIcon(String location) {
        this.icon = new Image(location);
    }

    /**
     * getter dla listy pasazerow lokacji
     * @return lista pasazerow lokacji
     */
    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    /**
     * getter dla listy polaczen lokacji
     * @return zwraca liste polaczen lokacji
     */
    public HashMap<Integer, Connection> getConnections() {
        return connections;
    }

    /**
     * dodaje pasazera do obecnej listy
     * @param pas pasazer
     */
    public void addPassenger(Passenger pas) {
        passengers.add(pas);
    }

    /**
     * zwraca lokacje przeksztalcona do Stringa
     * @return obiekt w postaci String
     */
    public String toString() {
        String memory = super.toString();
        return memory + "(" + id + ", " + x + ", " + y + ", " + radius + ")";
    }

    /**
     * zwraca identyfikator przeksztalcony do Stringa
     * @return identyfikator w postaci String
     */
    public String identityStringified() {
        return "LOKACJA";
    }

    /**
     * zwraca aktualne polozenie przeksztalcone do Stringa
     * @return aktualne polozenie w postaci String
     */
    public String currentLocationStringified() {
        return ("X: " + Double.toString(x) + " Y: " + Double.toString(y));
    }

    /**
     * zwraca pojemnosc lokacji przeksztalcona do Stringa
     * @return pojemnosc w postaci String
     */
    public String capacityStringified() {
        return Integer.toString(capacity);
    }
}
