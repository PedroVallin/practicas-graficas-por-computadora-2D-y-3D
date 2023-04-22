import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 * 
 * 
 * PEDRO ALBERTO VALLIN DÍAZ  20310071
 * PRACTICA 9 SEGUNDO PARCIAL
 * 
 * 
 */

public class CirculoSimetriaOchoLados extends JFrame {
    private BufferedImage buffer;

    public CirculoSimetriaOchoLados() {
        super("Mi ventana");
        setSize(200, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }

    public void putPixel(double x, double y, Color c) {
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, (int)x, (int)y, this);
    }

    

    public void dibujarCirculo(double xc, double yc, double R, Color c) {
        
        double x, y;

        x = xc - R;
        for (double theta = 0; theta < Math.PI/4; theta += 0.01) {
            x = xc + R*Math.cos(theta);
            y = yc + R*Math.sin(theta);
            putPixel(x, y, c);
            putPixel(xc - (x - xc), y, c); // simetría en el eje y
            putPixel(xc + (y - yc), x, c); // simetría en el eje x
            putPixel(xc - (y - yc), x, c); // simetría en la recta y = x
            putPixel(xc - (x - xc), yc - (y - yc), c); // simetría en la recta y = -x
            putPixel(xc + (y - yc), yc - (x - xc), c); // simetría en la recta y = x
            putPixel(xc - (y - yc), yc - (x - xc), c); // simetría en la recta y = -x
            putPixel(xc + (x - xc), yc - (y - yc), c); // simetría en el eje y
        }

        
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        dibujarCirculo(100, 100, 50, Color.BLACK);
    }

    public static void main(String[] args) {
        CirculoSimetriaOchoLados ventana = new CirculoSimetriaOchoLados();
        ventana.setVisible(true);
    }
}
