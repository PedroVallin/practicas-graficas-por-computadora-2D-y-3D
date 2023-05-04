import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 * BY
 * PEDRO ALBERTO VALLIN DÍAZ  20310071
 * PRACTICA 6 SEGUNDO PARCIAL
 */

public class RecorteLineasRectas extends JFrame {
    private BufferedImage buffer;

    public RecorteLineasRectas() {
        super("Mi ventana");
        setSize(200, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, x, y, this);
    }

    public void dibujarLinea(float x1, float y1, float x2, float y2, Color c) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        float m = dy / dx;

        if (Math.abs(dx) > Math.abs(dy)) {
            // Avance en X
            float y = y1;
            if (x2 < x1) {
                float temp = x1;
                x1 = x2;
                x2 = temp;
                y = y2;
            }
            for (float x = x1; x <= x2; x++) {
                putPixel(Math.round(x), Math.round(y), c);
                y += m;
            }
        } else {
            // Avance en Y
            float x = x1;
            if (y2 < y1) {
                float temp = y1;
                y1 = y2;
                y2 = temp;
                x = x2;
            }
            for (float y = y1; y <= y2; y++) {
                putPixel(Math.round(x), Math.round(y), c);
                x += 1 / m;
            }
        }
    }

    public void dibujarLinea2(float x1, float y1, float x2, float y2, float limX1, float limY1, float limX2, float limY2, Color c) {
        float m;
        float b;
        float y, x;
        float xaux;
    
        // m = (y2 - y1)/(x2 - x1);
        // b = y1 - m * x1;

        
        for ( x = x1; x <= x2; x ++ ) {
            y = ((y2 - y1)/(x2 - x1))*(x - x1) + y1;
            xaux = ((x2 - x1)/(y2 - y1)) * (y - y1) + x1;
            // y = m * x + b;
            if ( x <= limX2 && y <= limY2 && x >= limX1 && y >= limY1) {
                putPixel(Math.round(xaux), Math.round(y), Color.BLACK);
            }
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
        dibujarLinea2(100, 75, 175, 80, 50, 50, 150, 100, Color.BLACK);
        dibujarLinea2(25, 75, 100, 125, 50, 50, 150, 100, Color.BLACK);
    }

    public static void main(String[] args) {
        RecorteLineasRectas ventana = new RecorteLineasRectas();
        ventana.setVisible(true);
    }
}
