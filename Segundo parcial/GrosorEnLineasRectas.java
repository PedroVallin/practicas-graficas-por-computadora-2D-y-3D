import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 * BY
 * PEDRO ALBERTO VALLIN DÍAZ 20310071
 * PRACTICA 14 SEGUNDO PARCIAL
 */

public class GrosorEnLineasRectas extends JFrame {
    private BufferedImage buffer;

    public GrosorEnLineasRectas() {
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
     * Método para dibujar una línea recta y su grosor utilizando el algoritmo de Bresenham
     * @param x1 Coordenada X inicial
     * @param y1 Coordenada Y inicial
     * @param x2 Coordenada X Final
     * @param y2 Coordenada Y final
     * @param grosor Grosor de la linea
     * @param c Color de la linea
     */
    public void dibujarLinea(int x1, int y1, int x2, int y2, int grosor, Color c) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;

        int err = dx - dy;

        int delta = grosor / 2;

        while (x1 != x2 || y1 != y2) {
            for (int i = -delta; i <= delta; i++) {
                putPixel(x1, y1, c); // Pinta en horizontal
                putPixel(x1, y1 + i, c); // Pinta en vertical
            }
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        dibujarLinea(50, 50, 300, 50, 2, Color.BLACK);
    }

    public static void main(String[] args) {
        GrosorEnLineasRectas ventana = new GrosorEnLineasRectas();
        ventana.setVisible(true);
    }
}
