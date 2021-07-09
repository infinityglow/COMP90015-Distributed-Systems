import java.awt.*;

public class Text {

    private String text;
    private int x;
    private int y;
    private String name;
    private Color color;
    private int size;

    public Text(String text, int x, int y, String name, Color color, int size) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.name = name;
        this.color = color;
        this.size = size;
    }

    public void drawText(Graphics g) {
        g.setColor(color);
        g.setFont(new Font("", Font.PLAIN, size));
        g.drawString(text, x, y);
    }
}
