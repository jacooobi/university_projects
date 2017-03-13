package projektPO.model;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Random;

public class Plan {
    /**
     * Poczatek trasy
     */
    protected Location start;
    /**
     * Koniec trasy
     */
    protected Location finish;
    /**
     * Droga pierwotna
     */
    protected ArrayList<Location> originalRoute;
    /**
     * Droga powrotna
     */
    protected ArrayList<Location> returnRoute;

    /**
     * Konstruktor planu podrozy
     */
    public Plan() {
        this.originalRoute = new ArrayList();
        this.returnRoute = new ArrayList<>();
    }

    /**
     * Poczatek podrozy
     * @return lokacja bedaca poczatek trasy
     */
    public Location getStart() {
        return start;
    }

    /**
     * Koniec podrozy
     * @return lokacja bedaca koncem trasy
     */
    public Location getFinish() {
        return finish;
    }

    /**
     * Getter dla trasy pierwotnej
     * @return trasa pierwotna
     */
    public ArrayList<Location> getOriginalRoute() {
        return originalRoute;
    }

    /**
     * Getter dla trasy powrotnej
     * @return trasa powrotna
     */
    public ArrayList<Location> getReturnRoute() {
        return returnRoute;
    }

    /**
     * Zamienia iterator na kolekcje
     * @param iterable iterator
     * @return kolekcje skladajaca sie z kolejnych punktow podrozy
     */
    protected ArrayList<Integer> iterToCollection(Iterable iterable) {
        ArrayList<Integer> targetCollection = new ArrayList();
        CollectionUtils.addAll(targetCollection, iterable.iterator());
        return targetCollection;
    }

    /**
     * Wybiera poczatek i koniec trasy podroznej
     * @param locationIds zbior punktow do wyboru startu i finiszu podrozy
     * @return indeksy poczatku i konca
     */
    protected int[] pickEndpoints(int[] locationIds) {
        Random rand = new Random();
        int n = locationIds[rand.nextInt(locationIds.length)];
        int m = n;
        while (m == n)
            m = locationIds[rand.nextInt(locationIds.length)];
        int []endpoints = {n,m};
        return endpoints;
    }

    /**
     * Wybiera trase podrozy w podklasach
     */
    public void selectRoute() {
        System.out.println("not implemented");
    }
}
