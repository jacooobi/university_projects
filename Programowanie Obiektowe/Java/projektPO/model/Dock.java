package projektPO.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Dock extends Location{
    /**
     * Licznik portow
     */
    private static int ID = -1;

    /**
     * Konstruktor portu
     * @param x wspolrzedna X portu
     * @param y wspolrzedna Y portu
     */
    public Dock(double x, double y) {
        super(x, y, ++ID);
        this.setIcon("http://i.imgur.com/2Q1vYk4.png");
        this.radius = 20;
    }

    public int getIdentity() {
        return id;
    }

    /**
     * Umieszcza port na Canvasie
     * @param context obiekt na ktorym rysowany jest port
     */
    public void draw(GraphicsContext context) {
        context.drawImage(icon, x, y);
        context.setFill(Color.rgb(19, 15, 153));
        context.setStroke(Color.rgb(19, 15, 153));
        context.setLineDashes(null);
        context.setLineDashOffset(0);
        context.setLineWidth(1.5);
        context.setFont(Font.font(20.0));
        context.strokeText(Integer.toString(id), x+40, y);
    }

    public String identityStringified() {
        return "Port";
    }
}
