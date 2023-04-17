
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;


/**
 * BY
 * PEDRO ALBERTO VALLIN DÍAZ  20310071
 * PRACTICA 5 SEGUNDO PARCIAL
 */

public class AlgoritmoPuntoMedio extends JFrame {
    private BufferedImage buffer;

    public AlgoritmoPuntoMedio() {
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
        int dx = x2 - x1;
        int dy = y2 - y1;
        int x = x1;
        int y = y1;
        int D = (dy * 2) - dx;
        putPixel(x, y, c);

        while (x < x2) {
            if (D >= 0) {
                y++;
                D = D - (dx * 2);
            }
            x++;
            D = D + (dy * 2);
            putPixel(x, y, c);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        dibujarLinea(50, 50, 150, 150, Color.BLACK);
    }

    public static void main(String[] args) {
        AlgoritmoPuntoMedio ventana = new AlgoritmoPuntoMedio();
        ventana.setVisible(true);
    }
}
