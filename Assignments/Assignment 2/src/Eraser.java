
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Eraser extends Shape {
    public Eraser() {
    };

    public Eraser(int x1, int y1, int x2, int y2, String name, Color color, float stroke) {
        super(x1, y1, x2, y2, name, color, stroke);
    }

    public void drawShape(Graphics2D g) {
        g.setColor(color);
        ((Graphics2D) g).setStroke(new BasicStroke(20));
        g.drawLine(x1, y1, x2, y2);
        ((Graphics2D) g).setStroke(new BasicStroke(1));
    }
}