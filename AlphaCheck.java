import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class AlphaCheck extends JPanel implements ActionListener {
    private Timer timer;
    private Environment environment;

    public AlphaCheck() {
        environment = new Environment();
        setLayout(null);
        environment.setBounds(0, 0, 1800, 1200);  // Ensure environment is correctly positioned and sized
        add(environment);
        timer = new Timer(16, this); // Approximately 60 FPS
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        environment.paintComponent(g);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Alpha Check Game");
        AlphaCheck game = new AlphaCheck();
        frame.add(game);
        frame.setSize(1800, 1200);  // Set to environment's dimensions
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        game.environment.requestFocusInWindow();  // Request focus to capture key events
    }
}
