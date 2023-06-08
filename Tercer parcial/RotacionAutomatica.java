import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class RotacionAutomatica extends JFrame {

    private BufferedImage buffer;
    private int sizeDelCubo = 50;
    private int cuboPosX = 200;
    private int cuboPosY = 200;
    private double angulo = 0.01;

    public RotacionAutomatica() {
        super("Cubo con proyección paralela");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                angulo += 0.01;
                repaint();
            }
        }).start();
    }

    private void putPixel(Graphics g, double x, double y, Color c) {
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, (int) Math.round(x), (int) Math.round(y), this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Vértices del cubo en 3D
        double[] verticesXCubo = { cuboPosX, cuboPosX + sizeDelCubo, cuboPosX + sizeDelCubo, cuboPosX, cuboPosX, cuboPosX + sizeDelCubo, cuboPosX + sizeDelCubo, cuboPosX };
        double[] verticesYCubo = { cuboPosY, cuboPosY, cuboPosY + sizeDelCubo, cuboPosY + sizeDelCubo, cuboPosY, cuboPosY, cuboPosY + sizeDelCubo, cuboPosY + sizeDelCubo };
        double[] verticesZCubo = { cuboPosY, cuboPosY, cuboPosY, cuboPosY, cuboPosY + sizeDelCubo, cuboPosY + sizeDelCubo, cuboPosY + sizeDelCubo, cuboPosY + sizeDelCubo };
        
        // Proyección paralela
        double[] verticeX = new double[8];
        double[] verticeY = new double[8];

        for (int i = 0; i < 8; i++) {
            verticeX[i] = verticesXCubo[i] + verticesZCubo[i] / 2;
            verticeY[i] = verticesYCubo[i] + verticesZCubo[i] / 2;
        }

        // Coordenadas del centro
        double centroX = 0;
        double centroY = 0;

        for (int i = 0; i < 8; i++) {
            centroX += verticeX[i];
            centroY += verticeY[i];
        }

        centroX /= 8;
        centroY /= 8;

        for (int i = 0; i < 8; i++) {
            double x = verticeX[i];
            double y = verticeY[i];
        
            // Aplicar rotación alrededor del eje vertical (Y)
            double cos = Math.cos(angulo);
            double sin = Math.sin(angulo);
            verticeX[i] = Math.round((x - centroX) * cos - (y - centroY) * sin + centroX);
            verticeY[i] = Math.round((x - centroX) * sin + (y - centroY) * cos + centroY);
        }
        


        putPixel(g, 300, 300, Color.BLACK);

        dibujarCubo(g, verticeX, verticeY);
    }

    private void dibujarCubo(Graphics g, double[] verticesX, double[] verticesY) {
        for (int i = 0; i < 4; i++) {
            drawLine(g, verticesX[i], verticesY[i], verticesX[(i + 1) % 4], verticesY[(i + 1) % 4], Color.BLACK);
            drawLine(g, verticesX[i + 4], verticesY[i + 4], verticesX[((i + 1) % 4) + 4], verticesY[((i + 1) % 4) + 4], Color.BLUE);
            drawLine(g, verticesX[i], verticesY[i], verticesX[i + 4], verticesY[i + 4], Color.RED);
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

    public static void main(String[] args) {
        RotacionAutomatica ventana = new RotacionAutomatica();
        ventana.setVisible(true);
    }
}