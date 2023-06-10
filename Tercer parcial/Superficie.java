import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Superficie extends JFrame {

    private static final int WIDTH = 800; // Ancho de la ventana
    private static final int HEIGHT = 600; // Alto de la ventana
    private static final int SCALE = 40; // Escala de la superficie

    private BufferedImage buffer;

    // TODO: Doble buffereo
    private BufferedImage buffer2;
    private Graphics2D g2d;

    public Superficie() {
        setTitle("Superficie Plana");
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
        // TODO: Doble buffereo
        g2d.drawImage(buffer, (int) Math.round(x), (int) Math.round(y), this);
    }

    @Override
    public void paint(Graphics g) {

        // super.paint(g);
        // TODO: Doble buffereo
        super.repaint();

        drawInclinedSurface();

        // TODO: Doble buffereo
        g.drawImage(buffer2, 0, 0, null);
        g2d.clearRect(0, 0, getWidth(), getHeight());
    }

    private void drawInclinedSurface() {
        int resolution = 100; // Resolución de la discretización
        double xMin = -10.0;
        double xMax = 10.0;
        double yMin = -10.0;
        double yMax = 10.0;
        double dx = (xMax - xMin) / resolution;
        double dy = (yMax - yMin) / resolution;

        double theta = Math.toRadians(45); // Ángulo de inclinación (45 grados)

        for (double x = xMin; x <= xMax; x += dx) {
            for (double y = yMin; y <= yMax; y += dy) {
                // Realiza las transformaciones de escala, traslación, proyección, etc., si es necesario
                double z = 0; // Altura constante para una superficie plana

                // Aplicar rotación sobre el eje x
                double yRot = y * Math.cos(theta) - z * Math.sin(theta);
                double zRot = y * Math.sin(theta) + z * Math.cos(theta);

                // Aplicar proyección perspectiva
                double distance = 10; // Distancia entre el observador y la superficie
                double projectionFactor = distance / (distance + zRot);

                double xProj = (x * SCALE * projectionFactor) + getWidth() / 2;
                double yProj = (yRot * SCALE * projectionFactor) + getHeight() / 2;

                // Dibujar el punto (x, y, z)
                putPixel(xProj, yProj, Color.BLUE);

                // Conectar los puntos adyacentes con líneas
                double nextX = x + dx;
                double nextY = y + dy;

                if (nextX <= xMax) {
                    double nextZ = 0; // Altura constante para una superficie plana

                    // Aplicar rotación sobre el eje x al siguiente punto
                    double nextYRot = nextY * Math.cos(theta) - nextZ * Math.sin(theta);
                    double nextZRot = nextY * Math.sin(theta) + nextZ * Math.cos(theta);

                    // Aplicar proyección perspectiva al siguiente punto
                    double nextXProj = (nextX * SCALE * projectionFactor) + getWidth() / 2;
                    double nextYProj = (nextYRot * SCALE * projectionFactor) + getHeight() / 2;

                    // Dibujar la línea entre los puntos (x, y, z) y (nextX, nextY, nextZ)
                    drawLine(Math.round(xProj), Math.round(yProj), Math.round(nextXProj), Math.round(nextYProj), Color.BLUE);
                }

                if (nextY <= yMax) {
                    double nextZ = 0; // Altura constante para una superficie plana

                    // Aplicar rotación sobre el eje x al siguiente punto
                    double nextYRot = nextY * Math.cos(theta) - nextZ * Math.sin(theta);
                    double nextZRot = nextY * Math.sin(theta) + nextZ * Math.cos(theta);

                    // Aplicar proyección perspectiva al siguiente punto
                    double nextXProj = (x * SCALE * projectionFactor) + getWidth() / 2;
                    double nextYProj = (nextYRot * SCALE * projectionFactor) + getHeight() / 2;

                    // Dibujar la línea entre los puntos (x, y, z) y (nextX, nextY, nextZ)
                    drawLine(Math.round(xProj), Math.round(yProj), Math.round(nextXProj), Math.round(nextYProj), Color.BLUE);
                }
            }
        }
    }

    private void drawLine(double x1, double y1, double x2, double y2, Color c) {
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
        Superficie surface = new Superficie();
        surface.setVisible(true);
    }
}
