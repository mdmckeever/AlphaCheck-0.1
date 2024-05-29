import java.awt.Color;
import java.awt.Graphics;

public class RedAir {
    private int x, y;

    public RedAir(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        // Update the position or behavior of red air unit
        x += 1; // Example movement
    }

    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, 20, 20);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
