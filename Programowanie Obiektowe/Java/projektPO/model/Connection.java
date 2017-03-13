package projektPO.model;

public class Connection {
    /**
     * wspolrzedna X punktu poczatkowego
     */
    private double startX;
    /**
     * wspolrzedna Y punktu poczatkowego
     */
    private double startY;
    /**
     * wspolrzedna X punktu koncowego
     */
    private double finishX;
    /**
     * wspolrzedna Y punktu koncowego
     */
    private double finishY;

    /**
     * Konstruktor polaczenia dwoch lokacji
     * @param startX wspolrzedna X punktu poczatkowego
     * @param startY wspolrzedna Y punktu poczatkowego
     * @param finishX wspolrzedna X punktu koncowego
     * @param finishY wspolrzedna Y punktu koncowego
     */
    public Connection(double startX, double startY, double finishX, double finishY) {
        this.startX = startX;
        this.startY = startY;
        this.finishX = finishX;
        this.finishY = finishY;
    }

    /**
     * getter dla wspolrzednej X punktu poczatkowego
     * @return wspolrzedna X punktu poczatkowego
     */
    public double getStartX() {
        return startX;
    }

    /**
     * getter dla wspolrzednej Y punktu poczatkowego
     * @return wspolrzedna Y punktu poczatkowego
     */
    public double getStartY() {
        return startY;
    }

    /**
     * getter dla wspolrzednej X punktu koncowego
     * @return wspolrzedna X punktu koncowego
     */
    public double getFinishX() {
        return finishX;
    }

    /**
     * getter dla wspolrzednej Y punktu koncowego
     * @return wspolrzedna Y punktu koncowego
     */
    public double getFinishY() {
        return finishY;
    }
}
