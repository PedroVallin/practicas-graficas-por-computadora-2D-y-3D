import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Cilindro3D extends JFrame {

    private static final int WIDTH = 800; // Ancho de la ventana
    private static final int HEIGHT = 600; // Alto de la ventana
    private static final int SCALE = 40; // Escala de la superficie


    private BufferedImage buffer;

    // TODO: Doble buffereo
    private BufferedImage buffer2;
    private Graphics2D g2d;

    public Cilindro3D() {
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
        // super.paint(g);
        // TODO: Doble buffereo
        super.repaint();

        drawCylindricalSurface();

        // TODO: Doble buffereo
        g.drawImage(buffer2, 0, 0, null);
        g2d.clearRect(0, 0, getWidth(), getHeight());
    }

   

    
    private void drawCylindricalSurface() {
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
    
                // Realiza las transformaciones de escala, traslación, proyección, etc., si es necesario
                // Por ejemplo, podrías aplicar una traslación y una escala para centrar y ajustar el tamaño de la figura
                double scaleZ = 2; // Factor de escala en el eje z
    
                // Aplicar proyección perspectiva
                double distance = 10; // Distancia entre el observador y la superficie
                double projectionFactor = distance / (distance + z);
    
                double xProj = (x * SCALE * projectionFactor) + 200;
                double yProj = (y * SCALE * projectionFactor) + 200;
    
                // Dibujar el punto (x, y, z)
                putPixel((int) xProj, (int) yProj, Color.BLUE);
    
                // Conectar los puntos adyacentes con líneas
                double nextT = t + dt;
                double nextPhi = phi + dPhi;
    
                if (nextT <= tMax) {
                    double nextX = (2 + Math.cos(nextT)) * Math.cos(phi);
                    double nextY = nextT;
                    double nextZ = (2 + Math.cos(nextT)) * Math.sin(phi);
    
                    double nextXProj = (nextX * SCALE * projectionFactor) + 200;
                    double nextYProj = (nextY * SCALE * projectionFactor) + 200;
    
                    // Dibujar la línea entre los puntos (x, y, z) y (nextX, nextY, nextZ)
                    drawLine((int) xProj, (int) yProj, (int) nextXProj, (int) nextYProj, Color.BLUE);
                }
    
                if (nextPhi <= phiMax) {
                    double nextX = (2 + Math.cos(t)) * Math.cos(nextPhi);
                    double nextY = t;
                    double nextZ = (2 + Math.cos(t)) * Math.sin(nextPhi);
    
                    double nextXProj = (nextX * SCALE * projectionFactor) + 200;
                    double nextYProj = (nextY * SCALE * projectionFactor) + 200;
    
                    // Dibujar la línea entre los puntos (x, y, z) y (nextX, nextY, nextZ)
                    drawLine((int) xProj, (int) yProj, (int) nextXProj, (int) nextYProj, Color.BLUE);
                }
            }
        }
    }
    
       

    private void drawLine( double x1, double y1, double x2, double y2, Color c) {
        double dx = Math.abs(x2 - x1);
        double dy = Math.abs(y2 - y1);
        double sx = x1 < x2 ? 1 : -1;
        double sy = y1 < y2 ? 1 : -1;
        double err = dx - dy;

        while (true) {
            putPixel(x1, y1, c);

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
    
    

    public static void main(String[] args) {
        Cilindro3D surface = new Cilindro3D();
        surface.setVisible(true);
    }
}


