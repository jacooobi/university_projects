package projektPO.model;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Random;

public class Passenger {
    private static int mainId = 0;

    private String firstName;
    private String lastName;
    private String age;
    private String pesel;
    private Plan plan;
    private ArrayList<Location> currentRoute;
    private Location currentStop;
    private Location nextStop;
    private Vehicle currentVehicle;
    private int passedStopsCount;
    private int id;
    private String journeyType;

    /**
     * Konstruktor dla pasazera
     * @param typ rodzaj pasazera
     */
    public Passenger(boolean typ) {
        this.id = mainId++;
        this.firstName = getFaker().name().firstName();
        this.lastName = getFaker().name().lastName();
        this.age = generateAge();

        this.pesel = generatePESEL();

        if (typ) {
            this.plan = new PassengerFlightPlan();
        } else {
            this.plan = new SailPlan();
        }

        plan.selectRoute();
        this.currentRoute = plan.originalRoute;
        this.passedStopsCount = 0;
    }

    /**
     * Getter dla identyfikatora pasazera
     * @return identyfikator pasazera
     */
    public int getId() {
        return id;
    }

    /**
     * Nazwa pasazera jaka pojawi sie w options boxie
     * @return nazwa pasazera
     */
    public String optionsBoxName() {
        return firstName + " " + lastName + " ( " + id + " ) ";
    }

    /**
     * Zwraca imie pasazera jako String
     * @return imie pasazera w postaci Stringa
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Zwraca nazwisko pasazera jako String
     * @return nazwisko pasazera w postaci Stringa
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Ustawia liczbe przejechanych przystankow
     * @param passedStopsCount liczba minietych przystankow
     */
    public void setPassedStopsCount(int passedStopsCount) {
        this.passedStopsCount = passedStopsCount;
    }

    /**
     * Inkrementuje liczbe przystankow jakie pasazer juz minal ze swojej trasy
     */
    public void incrementPassedStopsCount() {
        this.passedStopsCount += 1;
    }

    /**
     * Zwraca wiek pasazera jako String
     * @return wiek pasazera w postaci Stringa
     */
    public String getAge() {
        return age;
    }

    /**
     * Zwraca PESEL pasazera jako String
     * @return PESEL pasazera w postaci Stringa
     */
    public String getPesel() {
        return pesel;
    }

    /**
     * Zwraca plana podrozy pasazera
     * @return plan podorzy
     */
    public Plan getPlan() {
        return plan;
    }

    /**
     * Zwraca aktualna trase pasazera
     * @return trasa pasazera
     */
    public ArrayList<Location> getCurrentRoute() {
        return currentRoute;
    }

    /**
     * Zwraca aktualny przystanek na ktorym zatrzymal sie pasazer
     * @return przystanek
     */
    public Location getCurrentStop() {
        return currentStop;
    }

    /**
     * Resetuje aktualny przystanek, jezeli pasazer zaczal sie przemieszczac samolotem lub statkiem
     */
    public void resetCurrentStop() {
        this.currentStop = null;
    }

    /**
     * Resetuje aktualny pojazd w ktorym znajduje sie pasazer jezeli przestal sie poruszac
     */
    public void resetCurrentVehicle() {
        this.currentVehicle = null;
    }

    /**
     * Ustawia pojazd w ktorym aktualnie przemieszcza sie pasazer
     * @param vehicle pojazd pasazera
     */
    public void setCurrentVehicle(Vehicle vehicle) {
        this.currentVehicle = vehicle;
    }

    /**
     * Przedstawia pasazera
     * @return String z numerem identyfikacyjnym pasazera
     */
    public String getIntroduction() {
        return "Pasażer numer " + id;
    }

    /**
     * Ustawia koleny przystanek na trasie pasazera
     * @param nextStop nastepny przystanek ( lotnisko lub port )
     */
    public void setNextStop(Location nextStop) {
        this.nextStop = nextStop;
    }

    /**
     * Zwraca kolejne lotnisko na trasie pasazera
     * @return nastepne lotnisko
     */
    public Location getNextAirportStop() {
        int counter = passedStopsCount+1;

        while (currentRoute.get(counter).getClass().getSimpleName().equals("MilitaryAirport")) {
            counter += 1;
        }

        return currentRoute.get(counter);
    }

    /**
     * Zwraca kolejny port na trasie pasazera
     * @return nastepny port
     */
    public Location getNextDockStop() {
        return currentRoute.get(passedStopsCount);
    }

    /**
     * Zwraca ostatni przystanek na trasie pasazera
     * @return ostatni port lub lotnisko
     */
    public Location getLastStop() {
        return currentRoute.get(currentRoute.size()-1);
    }

    private String generatePESEL() {
        Integer year = getRandom().nextInt(99) + 1;
        Integer month = getRandom().nextInt(12) + 1;
        Integer day = getRandom().nextInt(31) + 1;
        Integer remaining = getRandom().nextInt(9000) + 1000;

        return year.toString() + month.toString() + day.toString() + remaining.toString();
    }

    private String generateAge() {
        Integer age = getRandom().nextInt(120) + 1;
        return age.toString() + " lat";
    }

    private Faker getFaker() {
        return new Faker();
    }

    private Random getRandom() {
        return new Random();
    }

    public String originalTripStringified() {
        String result = "";
        for(Location loc : plan.getOriginalRoute()) {
            result += " -> ";
            result += Integer.toString(loc.getId());
        }
        return result;
    }

    public String returnTripStringified() {
        String result = "";
        for(Location loc : plan.getReturnRoute()) {
            result += " -> ";
            result += Integer.toString(loc.getId());
        }
        return result;
    }
}
