import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Test extends JFrame {

    private BufferedImage buffer;
    int sizeDelCubo = 50;
    int cuboPosX = 50;
    int cuboPosY = 50;

    public Test() {
        super("Cubo con proyección paralela");
        setSize(200, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }

    private void putPixel(Graphics g, double x, double y, Color c) {
        buffer.setRGB(0, 0, c.getRGB());
        g.drawImage(buffer, (int) Math.round(x), (int) Math.round(y), this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Vértices del cubo en 3D
        double[] verticesXCubo = { cuboPosX, cuboPosX + sizeDelCubo, cuboPosX + sizeDelCubo, cuboPosX, cuboPosX, cuboPosX + sizeDelCubo, cuboPosX + sizeDelCubo, cuboPosX };
        double[] verticesYCubo = { cuboPosY, cuboPosY, cuboPosY + sizeDelCubo, cuboPosY + sizeDelCubo, cuboPosY, cuboPosY, cuboPosY + sizeDelCubo, cuboPosY + sizeDelCubo };
        double[] verticesZCubo = { cuboPosY, cuboPosY, cuboPosY, cuboPosY, cuboPosY + sizeDelCubo, cuboPosY + sizeDelCubo, cuboPosY + sizeDelCubo, cuboPosY + sizeDelCubo };

        // Proyección paralela
        double scaleFactor = 0.5; // Escala de proyección
        double[] verticeX = new double[8];
        double[] verticeY = new double[8];

        for (int i = 0; i < 8; i++) {
            verticeX[i] = verticesXCubo[i] + scaleFactor * verticesZCubo[i];
            verticeY[i] = verticesYCubo[i] + scaleFactor * verticesZCubo[i];
        }

        // Rotación en el eje Z
        double anguloZ = Math.PI / 4; // Ángulo de rotación en el eje Z (45 grados en este ejemplo)

        double[] verticeXRotado = new double[8];
        double[] verticeYRotado = new double[8];

        for (int i = 0; i < 8; i++) {
            verticeXRotado[i] = verticeX[i] * Math.cos(anguloZ) - verticeY[i] * Math.sin(anguloZ);
            verticeYRotado[i] = verticeX[i] * Math.sin(anguloZ) + verticeY[i] * Math.cos(anguloZ);
        }

        dibujarCubo(g, verticeXRotado, verticeYRotado);
    }

    private void drawLine(Graphics g, double x1, double y1, double x2, double y2) {
        double dx = Math.abs(x2 - x1);
        double dy = Math.abs(y2 - y1);
        double sx = x1 < x2 ? 1 : -1;
        double sy = y1 < y2 ? 1 : -1;
        double err = dx - dy;

        while (true) {
            putPixel(g, x1, y1, Color.BLACK);

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

    private void dibujarCubo(Graphics g, double[] verticesX, double[] verticesY) {
        for (int i = 0; i < 4; i++) {
            drawLine(g, verticesX[i], verticesY[i], verticesX[(i + 1) % 4], verticesY[(i + 1) % 4]);
            drawLine(g, verticesX[i + 4], verticesY[i + 4], verticesX[((i + 1) % 4) + 4], verticesY[((i + 1) % 4) + 4]);
            drawLine(g, verticesX[i], verticesY[i], verticesX[i + 4], verticesY[i + 4]);
        }
    }

    public static void main(String[] args) {
        Test ventana = new Test();
        ventana.setVisible(true);
    }
}
