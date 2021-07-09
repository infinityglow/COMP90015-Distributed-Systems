
import java.awt.*;

public class Rect extends Shape {
    public Rect() {
    }
    public Rect(int x1, int y1, int x2, int y2, String name, Color color, float stroke) {
        super(x1, y1, x2, y2, name, color, stroke);
    }

    public void drawShape(Graphics2D g) {
        g.setColor(color);
        g.setStroke(new BasicStroke(stroke));
        g.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));

    }

}