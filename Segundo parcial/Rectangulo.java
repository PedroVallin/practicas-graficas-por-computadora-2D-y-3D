import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 * BY
 * PEDRO ALBERTO VALLIN DÍAZ  20310071
 * PRACTICA 6 SEGUNDO PARCIAL
 */

public class Rectangulo extends JFrame {
    private BufferedImage buffer;

    public Rectangulo() {
        super("Mi ventana");
        setSize(200, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, x, y, this);
    }

    public void dibujarLinea(int x1, int y1, int x2, int y2, Color c) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int x = x1;
        int y = y1;
        int s1 = Integer.signum(x2 - x1);
        int s2 = Integer.signum(y2 - y1);
        boolean intercambio = false;

        if (dy > dx) {
            int temp = dx;
            dx = dy;
            dy = temp;
            intercambio = true;
        }

        int D = (2 * dy) - dx;
        putPixel(x, y, c);

        for (int i = 1; i <= dx; i++) {
            if (D > 0) {
                if (intercambio) {
                    x += s1;
                } else {
                    y += s2;
                }
                D -= (2 * dx);
            }
            if (intercambio) {
                y += s2;
            } else {
                x += s1;
            }
            D += (2 * dy);
            putPixel(x, y, c);
        }
    }

    public void dibujarRectangulo(int x1, int y1, int x2, int y2, Color c) {
        dibujarLinea(x1, y1, x2, y1, c);
        dibujarLinea(x2, y1, x2, y2, c);
        dibujarLinea(x2, y2, x1, y2, c);
        dibujarLinea(x1, y2, x1, y1, c);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        dibujarRectangulo(50, 50, 150, 100, Color.BLACK);
    }

    public static void main(String[] args) {
        Rectangulo ventana = new Rectangulo();
        ventana.setVisible(true);
    }
}
