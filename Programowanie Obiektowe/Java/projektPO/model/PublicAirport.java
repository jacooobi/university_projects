package projektPO.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PublicAirport extends Airport {
    private static int ID = -1;

    /**
     * Konstruktor lotniska dla samolotow pasazerskich
     * @param x wspolrzedna X lotniska
     * @param y wspolrzedna Y lotniska
     */
    public PublicAirport(double x, double y) {
        super(x, y, ++ID);
        this.setIcon("http://i.imgur.com/nKWyv0m.jpg");
        this.radius = 20;
    }

    /**
     * Umieszcza lotnisko na mapie swiata
     * @param context obiekt na ktorym umieszczana jest lokacja
     */
    public void draw(GraphicsContext context) {
        context.drawImage(icon, x, y);
        context.setFill(Color.DARKRED);
        context.setStroke(Color.DARKRED);
        context.setLineDashes(null);
        context.setLineDashOffset(0);
        context.setLineWidth(1.5);
        context.setFont(Font.font(20.0));
        context.strokeText(Integer.toString(id), x, y);
    }

    public String identityStringified() {
        return "Lotnisko publiczne";
    }
}
