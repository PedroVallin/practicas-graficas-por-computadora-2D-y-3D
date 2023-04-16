import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PendulumnAnimation extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private final int WIDTH = 600;
    private final int HEIGHT = 600;
    private final int CENTER_X = WIDTH / 2;
    private final int CENTER_Y = HEIGHT / 2;
    private final int RADIUS = 100;
    private final int RADIUS_LENGTH = 150;
    private final double GRAVITY = 9.81;
    private final double ANGLE_SCALE = 0.01;
    private double angle = Math.PI / 4;
    private double angularVelocity = 0;
    private double angularAcceleration = 0;
    private Timer timer;

    public PendulumnAnimation() {
        timer = new Timer(10, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.WHITE);
        g.setColor(Color.BLACK);

        // Draw the support line
        g.drawLine(CENTER_X, 0, CENTER_X, CENTER_Y);

        // Calculate the position of the pendulum
        int x = CENTER_X + (int) (RADIUS * Math.sin(angle));
        int y = CENTER_Y + (int) (RADIUS * Math.cos(angle));

        // Draw the pendulum rod
        g.drawLine(CENTER_X, CENTER_Y, x, y);

        // Draw the pendulum bob
        g.fillOval(x - 10, y - 10, 20, 20);
    }

    public void actionPerformed(ActionEvent e) {
        // Calculate the angular acceleration
        angularAcceleration = -GRAVITY / RADIUS * Math.sin(angle);

        // Update the angular velocity and angle
        angularVelocity += angularAcceleration;
        angle += angularVelocity * ANGLE_SCALE;

        // Repaint the panel
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Pendulum Animation");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        PendulumnAnimation panel = new PendulumnAnimation();
        frame.add(panel);

        frame.setVisible(true);
    }
}
