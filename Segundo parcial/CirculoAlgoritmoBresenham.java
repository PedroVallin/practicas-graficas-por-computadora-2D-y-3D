
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/**
 * BY
 * PEDRO ALBERTO VALLIN DÍAZ 20310071
 * PRACTICA 11 SEGUNDO PARCIAL
 */

public class CirculoAlgoritmoBresenham extends JFrame {
    private BufferedImage buffer;

    public CirculoAlgoritmoBresenham() {
        super("Mi ventana");
        setSize(200, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, x, y, this);
    }

    public void dibujarCirculo(int xc, int yc, int R, Color c) {
        int x = 0;
        int y = R;
        int d = 3 - 2 * R;

        while (x <= y) {
            putPixel(xc + x, yc + y, c);
            putPixel(xc - x, yc + y, c);
            putPixel(xc + x, yc - y, c);
            putPixel(xc - x, yc - y, c);
            putPixel(xc + y, yc + x, c);
            putPixel(xc - y, yc + x, c);
            putPixel(xc + y, yc - x, c);
            putPixel(xc - y, yc - x, c);

            if (d < 0) {
                d = d + 4 * x + 6;
            } else {
                d = d + 4 * (x - y) + 10;
                y--;
            }

            x++;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        dibujarCirculo(100, 100, 50, Color.BLACK);
    }

    public static void main(String[] args) {
        CirculoAlgoritmoBresenham ventana = new CirculoAlgoritmoBresenham();
        ventana.setVisible(true);
    }
}
