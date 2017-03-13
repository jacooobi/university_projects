package projektPO.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MilitaryAirport extends Airport {
    private static int ID = -1;

    /**
     * Konstruktor lotniska dla samolotow wojskowych
     * @param x wspolrzedna X lotniska
     * @param y wspolrzedna Y lotniska
     */
    public MilitaryAirport(double x, double y) {
        super(x, y, ++ID);
        this.setIcon("http://i.imgur.com/hdOfEXM.jpg");
        this.radius = 22;
    }

    /**
     * getter dla identyfikatora w grafie
     * @return identyfiaktor w grafie
     */
    public int getGraphId() {
        return graphId;
    }

    /**
     * Rysuje lotnisko na canvasie
     * @param context obiekt na ktorym umieszczana jest lokacja
     */
    public void draw(GraphicsContext context) {
        context.drawImage(icon, x, y);
        context.setFill(Color.DARKCYAN);
        context.setStroke(Color.DARKCYAN);
        context.setLineDashes(null);
        context.setLineDashOffset(0);
        context.setLineWidth(1.5);
        context.setFont(Font.font(20.0));
        context.strokeText(Integer.toString(id), x, y);
    }

    public String identityStringified() {
        return "Lotnisko wojskowe";
    }
}
