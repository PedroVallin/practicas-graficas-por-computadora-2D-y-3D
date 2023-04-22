
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/**
 * BY
 * PEDRO ALBERTO VALLIN DÍAZ 20310071
 * PRACTICA 12 SEGUNDO PARCIAL
 */

public class Figuras extends JFrame {
    private BufferedImage buffer;

    public Figuras() {
        super("Mi ventana");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, x, y, this);
    }

    /**
     * Este método usa la ecuación general de una elipse para dibujarla
     * @param xc Centro de la elipse en X
     * @param yc Centro de la elipse en Y
     * @param a Radio horizontal de la elipse
     * @param b Radio vertical de la elipse
     * @param color Color de los pixeles
     */
    public void dibujarElipse(int xc, int yc, int a, int b, Color color) {
        int x = 0, y = b;
        int a2 = a * a, b2 = b * b;
        int p = (int) (b2 - a2 * b + 0.25 * a2);
        int dx = 2 * b2 * x, dy = 2 * a2 * y;

        // Cuadrante 1
        while (dx < dy) {
            putPixel(xc + x, yc + y, color);
            putPixel(xc - x, yc + y, color);
            putPixel(xc + x, yc - y, color);
            putPixel(xc - x, yc - y, color);

            x++;
            dx += 2 * b2;
            if (p < 0) {
                p += b2 * (2 * x + 3);
            } else {
                y--;
                dy -= 2 * a2;
                p += b2 * (2 * x + 3) - a2 * (2 * y - 2);
            }
        }

        // Cuadrante 2
        p = (int) (b2 * (x + 0.5) * (x + 0.5) + a2 * (y - 1) * (y - 1) - a2 * b2);
        while (y >= 0) {
            putPixel(xc + x, yc + y, color);
            putPixel(xc - x, yc + y, color);
            putPixel(xc + x, yc - y, color);
            putPixel(xc - x, yc - y, color);

            y--;
            dy -= 2 * a2;
            if (p > 0) {
                p += a2 * (3 - 2 * y);
            } else {
                x++;
                dx += 2 * b2;
                p += b2 * (2 * x + 2) + a2 * (3 - 2 * y);
            }
        }
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

    public void dibujarCirculo(double xc, double yc, double R, Color c) {
        
        double x, y;

        x = xc - R;
        for (double theta = 0; theta < 2*Math.PI; theta += 0.01) {
            x = xc + R*Math.cos(theta);
            y = yc + R*Math.sin(theta);
            putPixel((int)x, (int)y, c);
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

        // Elipses en primer cuadrande de la ventana
        putPixel(180, 130, Color.BLACK);
        putPixel(20, 70, Color.BLACK);
        dibujarElipse(100, 100, 80, 30, Color.BLACK);

        putPixel(170, 120, Color.BLACK);
        putPixel(30, 80, Color.BLACK);
        dibujarElipse(100, 100, 70, 20, Color.BLACK);

        putPixel(160, 110, Color.BLACK);
        putPixel(40, 90, Color.BLACK);
        dibujarElipse(100, 100, 60, 10, Color.BLACK);

        putPixel(150, 102, Color.BLACK);
        putPixel(50, 98, Color.BLACK);
        dibujarElipse(100, 100, 50, 2, Color.BLACK);

        // Cuadrados en segundo cuadrante
        dibujarRectangulo(200, 50, 350, 100, Color.BLACK);
        dibujarRectangulo(330, 80, 220, 70, Color.BLACK);

        // Circulos en tercer cuadrante
        putPixel(160, 360, Color.BLACK);
        putPixel(40, 240, Color.BLACK);
        dibujarCirculo(100, 300, 60, Color.BLACK);

        putPixel(140, 340, Color.BLACK);
        putPixel(60, 260, Color.BLACK);
        dibujarCirculo(100, 300, 40, Color.BLACK);

        putPixel(120, 320, Color.BLACK);
        putPixel(80, 280, Color.BLACK);
        dibujarCirculo(100, 300, 20, Color.BLACK);


        // Lineas rectas en cuarto cuadrante
        dibujarLinea(200, 240, 260, 290, Color.BLACK);
        dibujarLinea(280, 240, 360, 240, Color.BLACK);
        dibujarLinea(260, 300, 200, 350, Color.BLACK);
        dibujarLinea(360, 300, 280, 300, Color.BLACK);
        
        
        

    }

    public static void main(String[] args) {
        Figuras ventana = new Figuras();
        ventana.setVisible(true);
    }
}
