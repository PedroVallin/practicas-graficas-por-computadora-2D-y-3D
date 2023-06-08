import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class CurvaExplicita extends JFrame implements KeyListener {

    private double anguloX;
    private double anguloY;
    private double anguloZ;
    private double deltaAngulo;

    private BufferedImage buffer;

    public CurvaExplicita() {
        super("Curva 3D");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addKeyListener(this);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

        anguloX = 0;
        anguloY = 0;
        anguloZ = 0;
        deltaAngulo = 0.01;
    }

    private void putPixel(Graphics g, double x, double y, Color c) {
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, (int) Math.round(x), (int) Math.round(y), this);
    }

    private void dibujarCurva(Graphics g) {
        double tInicio = -10;
        double tFin = 10;
        double paso = 0.01;

        double escala = 100; // Escala de la proyección
        int centroX = getWidth() / 2;
        int centroY = getHeight() / 2;

        for (double t = tInicio; t <= tFin; t += paso) {
            // Definir la función paramétrica de la curva en 3D
            double x = Math.sin(t);
            double y = Math.cos(t);
            double z = t / 5;

            // Aplicar las rotaciones en los ejes X, Y y Z
            double xRot = x * Math.cos(anguloY) * Math.cos(anguloZ)
                    - y * (Math.cos(anguloX) * Math.sin(anguloZ) + Math.sin(anguloX) * Math.sin(anguloY) * Math.cos(anguloZ))
                    + z * (Math.sin(anguloX) * Math.sin(anguloZ) - Math.cos(anguloX) * Math.sin(anguloY) * Math.cos(anguloZ));

            double yRot = x * Math.cos(anguloY) * Math.sin(anguloZ)
                    + y * (Math.cos(anguloX) * Math.cos(anguloZ) - Math.sin(anguloX) * Math.sin(anguloY) * Math.sin(anguloZ))
                    - z * (Math.sin(anguloX) * Math.cos(anguloZ) + Math.cos(anguloX) * Math.sin(anguloY) * Math.sin(anguloZ));

            double zRot = x * Math.sin(anguloY) + y * Math.sin(anguloX) * Math.cos(anguloY) + z * Math.cos(anguloX) * Math.cos(anguloY);

            // Proyección paralela
            int xProy = (int) (xRot * escala) + centroX;
            int yProy = (int) (yRot * escala) + centroY;

            // Dibujar el punto en la proyección
            // g.setColor(Color.BLACK);
            // g.fillRect(xProy, yProy, 1, 1);
            // drawLine(g, xProy, y, x, y, getBackground());
            if ( t <= 0 ) {
                putPixel(g, xProy, yProy, Color.BLACK);
            } else {
                putPixel(g, xProy, yProy, Color.BLUE);
            }
        }
    }

    private void drawLine(Graphics g, double x1, double y1, double x2, double y2, Color c) {
        double dx = Math.abs(x2 - x1);
        double dy = Math.abs(y2 - y1);
        double sx = x1 < x2 ? 1 : -1;
        double sy = y1 < y2 ? 1 : -1;
        double err = dx - dy;

        while (true) {
            putPixel(g, x1, y1, c);

            if (x1 == x2 && y1 == y2) {
                break;
            }

            double err2 = 2 * err;

            if (err2 > -dy) {
                err -= dy;
                x1 += sx;
            }

            if (err2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        dibujarCurva(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_LEFT) {
            anguloY -= deltaAngulo;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            anguloY += deltaAngulo;
        } else if (keyCode == KeyEvent.VK_UP) {
            anguloX -= deltaAngulo;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            anguloX += deltaAngulo;
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        CurvaExplicita ventana = new CurvaExplicita();
        ventana.setVisible(true);
    }
}
