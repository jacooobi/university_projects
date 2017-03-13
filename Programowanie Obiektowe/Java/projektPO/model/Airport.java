package projektPO.model;

public class Airport extends Location{
    /**
     * identyfikator lotniska
     */
    private static int mainId = -1;

    /**
     * identyfikator lotniska w grafie
     */
    protected int graphId;

    /**
     * konstruktor lotniska
     * @param x wspolrzedna X
     * @param y wspolrzedna Y
     * @param graphId identyfikator w grafie
     */
    public Airport(double x, double y, int graphId) {
        super(x, y, ++mainId);
        this.graphId = graphId;
    }
}
