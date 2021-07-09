import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Shape {
    public int x1, y1, x2, y2;
    public String name;
    public Color color;
    public float stroke;

    public Shape() {
    };

    public Shape(int x1, int y1, int x2, int y2, String name, Color color, float stroke) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.name = name;
        this.color = color;
        this.stroke = stroke;
    }

    public void drawShape(Graphics2D g) {

    }
}