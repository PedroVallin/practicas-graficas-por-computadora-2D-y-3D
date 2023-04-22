
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;


/**
 * BY
 * PEDRO ALBERTO VALLIN DÍAZ  20310071
 * PRACTICA 10 SEGUNDO PARCIAL
 */

public class CirculoAlgoritmoPuntoMedio extends JFrame {
    private BufferedImage buffer;

    public CirculoAlgoritmoPuntoMedio() {
        super("Mi ventana");
        setSize(200, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, x, y, this);
    }

    public void dibujarCirculo(int xc, int yc, int r, Color c) {
        int x = 0, y = r;
        int d = 1 - r;
        
        dibujarOctantes(xc, yc, x, y, c);

        while (y > x) {
            if (d < 0) {
                d += 2 * x + 3;
            } else {
                d += 2 * (x - y) + 5;
                y--;
            }
            x++;
            dibujarOctantes(xc, yc, x, y, c);
        }
    }

    public void dibujarOctantes(int xc, int yc, int x, int y, Color c) {
        putPixel(xc + x, yc + y, c);
        putPixel(xc + y, yc + x, c);
        putPixel(xc - x, yc + y, c);
        putPixel(xc - y, yc + x, c);
        putPixel(xc + x, yc - y, c);
        putPixel(xc + y, yc - x, c);
        putPixel(xc - x, yc - y, c);
        putPixel(xc - y, yc - x, c);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        dibujarCirculo(100, 100, 50, Color.BLACK);
    }

    public static void main(String[] args) {
        CirculoAlgoritmoPuntoMedio ventana = new CirculoAlgoritmoPuntoMedio();
        ventana.setVisible(true);
    }
}
