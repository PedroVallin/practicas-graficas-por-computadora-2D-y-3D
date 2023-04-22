import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/**
 * BY
 * PEDRO ALBERTO VALLIN DÍAZ 20310071
 * PRACTICA 13 SEGUNDO PARCIAL
 */
public class TiposDeLineas extends JFrame {
    private BufferedImage buffer;

    public TiposDeLineas() {
        super("Dibujando lineas con mascara");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, x, y, this);
    }

    public void dibujarLinea(int x1, int y1, int x2, int y2, int mascara) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;
        int e2;
        int contador = 0;
        while (true) {
            if ((mascara >> contador & 1) == 1) {
                putPixel(x1, y1, Color.BLACK);
            }
            contador++;
            e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
            if (x1 == x2 && y1 == y2) {
                if ((mascara >> contador & 1) == 1) {
                    putPixel(x1, y1, Color.BLACK);
                }
                break;
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        dibujarLinea(50, 50, 200, 50, 0b1010101010101010);
        dibujarLinea(50, 100, 200, 100, 0b1110001110001110);
        dibujarLinea(50, 150, 200, 150, 0b1111111111110000);
    }

    public static void main(String[] args) {
        TiposDeLineas ventana = new TiposDeLineas();
        ventana.setVisible(true);
    }
}
