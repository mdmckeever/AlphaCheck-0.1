import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class Bullseye {
    private int x, y;
    private Image image;

    public Bullseye() {
        Random random = new Random();
        // Ensure the bullseye stays within bounds (window size: 1800x1200, image size: 32x32)
        this.x = random.nextInt(1800 - 32);
        this.y = random.nextInt(1200 - 32);
        try {
            System.out.println("Loading bullseye image...");
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("resources/bullseye.png"));
            if (image == null) {
                System.out.println("Bullseye image not found!");
            } else {
                System.out.println("Bullseye image loaded successfully.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading bullseye image.");
        }
    }

    public void render(Graphics g) {
        g.drawImage(image, x, y, 32, 32, null); // Draw the bullseye image at 32x32 pixels
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
