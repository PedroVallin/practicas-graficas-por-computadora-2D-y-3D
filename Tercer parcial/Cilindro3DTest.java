import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Cilindro3DTest extends JFrame implements KeyListener {
    private static final int WIDTH = 800; // Ancho de la ventana
    private static final int HEIGHT = 600; // Alto de la ventana
    private static final int SCALE = 40; // Escala de la superficie
    private static final double ROTATION_SPEED = 0.1; // Velocidad de rotación en radianes

    private double rotationAngle; // Ángulo de rotación en radianes

    private BufferedImage buffer;

    // TODO: Doble buffereo
    private BufferedImage buffer2;
    private Graphics2D g2d;

    public Cilindro3DTest() {
        
        
        addKeyListener(this);
        setFocusable(true);

        setTitle("Superficie Cilíndrica");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        // TODO: Doble buffereo
        buffer2 = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        g2d = buffer2.createGraphics();
    }

    private void putPixel(double x, double y, Color c) {
        buffer.setRGB(0, 0, c.getRGB());
        // this.getGraphics().drawImage(buffer, (int) Math.round(x), (int) Math.round(y), this);
        g2d.drawImage(buffer, (int) Math.round(x), (int) Math.round(y), this);
    }

    @Override
    public void paint(Graphics g) {
        super.repaint();
        // super.paintComponent(g);
        // Graphics2D g2d = (Graphics2D) g;

        int resolution = 100; // Resolución de la discretización
        double tMin = 0.0;
        double tMax = 2 * Math.PI;
        double phiMin = 0.0;
        double phiMax = 2 * Math.PI;
        double dt = (tMax - tMin) / resolution;
        double dPhi = (phiMax - phiMin) / resolution;

        for (double t = tMin; t <= tMax; t += dt) {
            for (double phi = phiMin; phi <= phiMax; phi += dPhi) {
                double x = (2 + Math.cos(t)) * Math.cos(phi);
                double y = t;
                double z = (2 + Math.cos(t)) * Math.sin(phi);

                // Aplicar transformaciones de rotación en el eje X
                double yRotated = y * Math.cos(rotationAngle) - z * Math.sin(rotationAngle);
                double zRotated = y * Math.sin(rotationAngle) + z * Math.cos(rotationAngle);

                // Aplicar proyección perspectiva
                double distance = 10; // Distancia entre el observador y la superficie
                double projectionFactor = distance / (distance + zRotated);

                double xProj = (x * SCALE * projectionFactor) + WIDTH / 2;
                double yProj = (yRotated * SCALE * projectionFactor) + HEIGHT / 2;

                putPixel(xProj, yProj, Color.BLUE);
            }
        }

        // TODO: Doble buffereo
        g.drawImage(buffer2, 0, 0, null);
        g2d.clearRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            rotationAngle -= ROTATION_SPEED;
            repaint();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            rotationAngle += ROTATION_SPEED;
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        Cilindro3DTest surface = new Cilindro3DTest();
        surface.setVisible(true);
    }
}



// public class Cilindro3DTest extends JFrame implements KeyListener {
//     private static final int WIDTH = 800; // Ancho de la ventana
//     private static final int HEIGHT = 600; // Alto de la ventana
//     private static final int SCALE = 40; // Escala de la superficie
//     private static final double ROTATION_SPEED = 0.1; // Velocidad de rotación en radianes

//     private double rotationAngle; // Ángulo de rotación en radianes

//     private BufferedImage buffer;
//     private BufferedImage buffer2;
//     private Graphics2D g2d;

//     public Cilindro3DTest() {
//         addKeyListener(this);
//         setFocusable(true);

//         setTitle("Superficie Cilíndrica");
//         setSize(WIDTH, HEIGHT);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//         buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
//         buffer2 = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
//         g2d = buffer2.createGraphics();
//     }

//     private void putPixel(double x, double y, Color c) {
//         buffer.setRGB(0, 0, c.getRGB());
//         g2d.drawImage(buffer, (int) Math.round(x), (int) Math.round(y), this);
//     }

//     private void drawLine(double x1, double y1, double x2, double y2, Color c) {
//         double dx = Math.abs(x2 - x1);
//         double dy = Math.abs(y2 - y1);
//         double sx = x1 < x2 ? 1 : -1;
//         double sy = y1 < y2 ? 1 : -1;
//         double err = dx - dy;

//         while (true) {
//             putPixel(x1, y1, c);

//             if (x1 == x2 && y1 == y2) {
//                 break;
//             }

//             double err2 = 2 * err;

//             if (err2 > -dy) {
//                 err -= dy;
//                 x1 += sx;
//             }

//             if (err2 < dx) {
//                 err += dx;
//                 y1 += sy;
//             }
//         }
//     }

//     @Override
//     public void paint(Graphics g) {
//         super.repaint();

//         int resolution = 100; // Resolución de la discretización
//         double tMin = 0.0;
//         double tMax = 2 * Math.PI;
//         double phiMin = 0.0;
//         double phiMax = 2 * Math.PI;
//         double dt = (tMax - tMin) / resolution;
//         double dPhi = (phiMax - phiMin) / resolution;

//         for (double t = tMin; t <= tMax; t += dt) {
//             for (double phi = phiMin; phi <= phiMax; phi += dPhi) {
//                 double x = (2 + Math.cos(t)) * Math.cos(phi);
//                 double y = t;
//                 double z = (2 + Math.cos(t)) * Math.sin(phi);

//                 // Aplicar transformaciones de rotación en el eje X
//                 double yRotated = y * Math.cos(rotationAngle) - z * Math.sin(rotationAngle);
//                 double zRotated = y * Math.sin(rotationAngle) + z * Math.cos(rotationAngle);

//                 // Aplicar proyección perspectiva
//                 double distance = 10; // Distancia entre el observador y la superficie
//                 double projectionFactor = distance / (distance + zRotated);

//                 double xProj = (x * SCALE * projectionFactor) + WIDTH / 2;
//                 double yProj = (yRotated * SCALE * projectionFactor) + HEIGHT / 2;

//                 // Dibujar los puntos
//                 putPixel(xProj, yProj, Color.BLUE);

//                 // Dibujar líneas con los puntos adyacentes
//                 double nextT = t + dt;
//                 double nextPhi = phi + dPhi;

//                 if (nextT <= tMax) {
//                     double nextX = (2 + Math.cos(nextT)) * Math.cos(phi);
//                     double nextY = nextT;
//                     double nextZ = (2 + Math.cos(nextT)) * Math.sin(phi);

//                     double nextXProj = (nextX * SCALE * projectionFactor) + WIDTH / 2;
//                     double nextYProj = (nextY * SCALE * projectionFactor) + HEIGHT / 2;

//                     // Dibujar la línea entre los puntos (x, y, z) y (nextX, nextY, nextZ)
//                     drawLine(Math.round(xProj), Math.round(yProj), Math.round(nextXProj), Math.round(nextYProj), Color.BLUE);
//                 }

//                 if (nextPhi <= phiMax) {
//                     double nextX = (2 + Math.cos(t)) * Math.cos(nextPhi);
//                     double nextY = t;
//                     double nextZ = (2 + Math.cos(t)) * Math.sin(nextPhi);

//                     double nextXProj = (nextX * SCALE * projectionFactor) + WIDTH / 2;
//                     double nextYProj = (nextY * SCALE * projectionFactor) + HEIGHT / 2;

//                     // Dibujar la línea entre los puntos (x, y, z) y (nextX, nextY, nextZ)
//                     drawLine(Math.round(xProj), Math.round(yProj), Math.round(nextXProj), Math.round(nextYProj), Color.BLUE);
//                 }
//             }
//         }

//         g.drawImage(buffer2, 0, 0, null);
//         g2d.clearRect(0, 0, getWidth(), getHeight());
//     }

//     @Override
//     public void keyPressed(KeyEvent e) {
//         if (e.getKeyCode() == KeyEvent.VK_UP) {
//             rotationAngle -= ROTATION_SPEED;
//             repaint();
//         } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
//             rotationAngle += ROTATION_SPEED;
//             repaint();
//         }
//     }

//     @Override
//     public void keyTyped(KeyEvent e) {
//     }

//     @Override
//     public void keyReleased(KeyEvent e) {
//     }

//     public static void main(String[] args) {
//         Cilindro3DTest surface = new Cilindro3DTest();
//         surface.setVisible(true);
//     }
// }

