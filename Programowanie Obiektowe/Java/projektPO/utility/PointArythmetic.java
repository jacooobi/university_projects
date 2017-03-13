package projektPO.utility;

import projektPO.model.Location;

import java.awt.geom.Line2D;

public class PointArythmetic {

    /**
     * Oblicza wspolrzedne X i Y punktu przeciecia dwoch lini
     * @param locations lista lokacji
     * @return wspolrzedne punktu przeciecia dwoch prostych
     */
    public static double[] intersectionDetected(Location[] locations) {
        double[] result = {};
        double x1 = locations[0].getX();
        double x2 = locations[1].getX();
        double x3 = locations[2].getX();
        double x4 = locations[3].getX();

        double y1 = locations[0].getY();
        double y2 = locations[1].getY();
        double y3 = locations[2].getY();
        double y4 = locations[3].getY();

        Line2D line1 = new Line2D.Double(x1, y1, x2, y2);
        Line2D line2 = new Line2D.Double(x3, y3, x4, y4);

        if (!line1.intersectsLine(line2)) return result;

        double  px = line1.getX1(),
                py = line1.getY1(),
                rx = line1.getX2()-px,
                ry = line1.getY2()-py;
        double  qx = line2.getX1(),
                qy = line2.getY1(),
                sx = line2.getX2()-qx,
                sy = line2.getY2()-qy;

        double det = sx*ry - sy*rx;

        if (det == 0) {
            return result;
        } else {
            double z = (sx*(qy-py)+sy*(px-qx))/det;
            if (z==0 || z==1) {
                return result;
            } else {
                double[] finalResult = {px+z*rx, py+z*ry};
                return finalResult;
            }
        }
    }
}
