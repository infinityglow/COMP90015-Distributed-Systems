
import java.awt.*;

public class Line extends Shape {
    public Line() {
    }

    public Line(int x1, int y1, int x2, int y2, String name, Color color, float stroke) {
        super(x1, y1, x2, y2, name, color, stroke);
    }

    public void drawShape(Graphics2D g) {
        g.setColor(color);
        g.setStroke(new BasicStroke(stroke));
        g.drawLine(x1, y1, x2, y2);
    }

}