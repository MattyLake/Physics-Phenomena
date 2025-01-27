import java.awt.*;
import javax.swing.*;
/**
 * Matthew Lakin U6 V
 */
public class Slit {
    int x1, x2, y;
    final int h = 10;

    public Slit(int x1, int x2, int y) {
        this.x1 = x1;
        this.x2 = x2;
        this.y = y;
    }

    public void drawMe(Graphics2D g, Color rectColor) {
        g.setColor(rectColor);
        if (x2 - x1 > 0) {
            g.fillRect(x1, y, Math.abs(x2-x1), h);
        }
    }
}



