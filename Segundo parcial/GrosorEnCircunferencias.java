import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 * BY
 * PEDRO ALBERTO VALLIN DÍAZ 20310071
 * PRACTICA 16 SEGUNDO PARCIAL
 */

public class GrosorEnCircunferencias extends JFrame {
    private BufferedImage buffer;

    public GrosorEnCircunferencias() {
        super("Mi ventana");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, x, y, this);
    }


    
    public void dibujarCirculo(double xc, double yc, double R, int grosor, Color c) {
        
        double x, y;
        int delta = grosor / 2;

        x = xc - R;
        for (double theta = 0; theta < 2*Math.PI; theta += 0.01) {
            x = xc + R*Math.cos(theta);
            y = yc + R*Math.sin(theta);
            for (int i = -delta; i <= delta; i++) {
                putPixel((int)x + i, (int) y, c); // Pinta en horizontal
                putPixel( (int) x, (int) y + i, c); // Pinta en vertical
            }
            
        }

        
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        dibujarCirculo(100, 100, 50, 6, Color.BLACK);
    }

    public static void main(String[] args) {
        GrosorEnCircunferencias ventana = new GrosorEnCircunferencias();
        ventana.setVisible(true);
    }
}
