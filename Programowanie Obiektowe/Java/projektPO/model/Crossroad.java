package projektPO.model;

import javafx.scene.canvas.GraphicsContext;

public class Crossroad extends Location {
    /**
     * licznik skrzyzowan
     */
    private static int counter = 0;

    /**
     * Konstruktor skrzyzowania
     * @param x wspolrzedna X
     * @param y wspolrzedna Y
     */
    public Crossroad(double x, double y) {
        super(x, y, ++counter);
        this.setIcon("http://i.imgur.com/2oDNALO.png?1");
        this.radius = 10;
    }

    /**
     * rysuje lokacje na canvasie
     * @param context obiekt na ktorym rysowane jest skrzyzowanie
     */
    public void draw(GraphicsContext context) {
        context.drawImage(icon, getCenterX(), getCenterY());
    }

}

