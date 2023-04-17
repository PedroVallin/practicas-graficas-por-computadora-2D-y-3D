import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 * BY
 * PEDRO ALBERTO VALLIN DÍAZ  20310071
 * PRACTICA 7 SEGUNDO PARCIAL
 */

public class Circulo extends JFrame {
    private BufferedImage buffer;

    public Circulo() {
        super("Mi ventana");
        setSize(200, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }

    public void putPixel(double x, double y, Color c) {
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, (int)x, (int)y, this);
    }

    

    public void dibujarCirculo(double x, double y, double xc, double yc, Color c) {
        // dibujarLinea(x1, y1, x2, y1, c);

        double R = Math.sqrt(Math.pow(x - xc, 2) + Math.pow(y - yc, 2));

        x = xc - R;
        while ( x < (xc + R)) {
            y = yc + Math.sqrt(Math.pow(R, 2) - Math.pow(x - xc, 2));
            putPixel(x, y, c);
            y = yc - Math.sqrt(Math.pow(R, 2) - Math.pow(x - xc, 2));
            putPixel(x, y, c);
            x++;
        }

        
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        dibujarCirculo(50, 100, 100, 100, Color.BLACK);
    }

    public static void main(String[] args) {
        Circulo ventana = new Circulo();
        ventana.setVisible(true);
    }
}
