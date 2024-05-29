import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.List;

public class Environment extends JPanel {
    private Image backgroundImage;
    private List<RedAir> redAirUnits;
    private List<BlueAir> blueAirUnits;
    private Bullseye bullseye;
    private int mouseX, mouseY;
    private int initialClickX, initialClickY;
    private boolean shiftPressed;
    private boolean mousePressed;

    public Environment() {
        redAirUnits = new ArrayList<>();
        blueAirUnits = new ArrayList<>();
        // Initialize with some units
        redAirUnits.add(new RedAir(100, 100));
        blueAirUnits.add(new BlueAir(200, 200));
        // Initialize bullseye
        bullseye = new Bullseye();
        // Load background image
        try {
            System.out.println("Loading background image...");
            backgroundImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("resources/background.png"));
            if (backgroundImage == null) {
                System.out.println("Background image not found!");
            } else {
                System.out.println("Background image loaded successfully.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading background image.");
        }
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                repaint();
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    initialClickX = e.getX();
                    initialClickY = e.getY();
                    mousePressed = true;
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    mousePressed = false;
                    repaint();
                }
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    shiftPressed = true;
                    System.out.println("Shift key pressed.");
                    repaint();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    shiftPressed = false;
                    System.out.println("Shift key released.");
                    repaint();
                }
            }
        });
        setFocusable(true);
        requestFocusInWindow(); // Request focus to capture key events

        // Enable double buffering
        setDoubleBuffered(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Use super.paintComponent to ensure the JPanel's double buffering is used
        super.paintComponent(g);
        // Fill the entire background with black
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1800, 1200);

        // Draw the centered background image
        int bgWidth = backgroundImage.getWidth(null);
        int bgHeight = backgroundImage.getHeight(null);
        int x = (1800 - bgWidth) / 2;
        int y = (1200 - bgHeight) / 2;
        g.drawImage(backgroundImage, x, y, null);

        // Render the red and blue air units
        for (RedAir redAir : redAirUnits) {
            redAir.render(g);
        }
        for (BlueAir blueAir : blueAirUnits) {
            blueAir.render(g);
        }
        // Render the bullseye
        bullseye.render(g);

        // Calculate and display the bearing and range from the bullseye to the cursor
        double bearing = calculateBearing(bullseye.getX() + 16, bullseye.getY() + 16, mouseX, mouseY);
        double range = calculateRange(bullseye.getX() + 16, bullseye.getY() + 16, mouseX, mouseY);
        g.setColor(Color.YELLOW);
        g.drawString(String.format("%03d/%d", (int) bearing, (int) range), mouseX + 15, mouseY - 15);

        // Draw the green circle when shift is pressed
        if (shiftPressed) {
            System.out.println("Shift is pressed, drawing range rollover.");
            int radius = 30; // 3 units * 10 pixels/unit
            g.setColor(Color.GREEN);
            g.drawOval(mouseX - radius, mouseY - radius, 2 * radius, 2 * radius);
        }

        // Draw the cyan line and bearing/range from initial click point to current cursor position
        if (mousePressed) {
            g.setColor(Color.CYAN);
            g.drawLine(initialClickX, initialClickY, mouseX, mouseY);
            double clickBearing = calculateBearing(initialClickX, initialClickY, mouseX, mouseY);
            double clickRange = calculateRange(initialClickX, initialClickY, mouseX, mouseY);
            g.drawString(String.format("%03d/%d", (int) clickBearing, (int) clickRange), mouseX + 15, mouseY);
        }
    }

    private double calculateBearing(int x1, int y1, int x2, int y2) {
        double angle = Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));
        if (angle < 0) {
            angle += 360;
        }
        angle = (angle + 90) % 360;  // Adjusting to make 0° at the top
        return angle;
    }

    private double calculateRange(int x1, int y1, int x2, int y2) {
        double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        return Math.round(distance / 10.0);
    }
}
