import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/**
 * BY
 * PEDRO ALBERTO VALLIN DÍAZ 20310071
 * PRACTICA 15 SEGUNDO PARCIAL
 */
public class TiposDeLineasCircunferencias extends JFrame {
    private BufferedImage buffer;

    public TiposDeLineasCircunferencias() {
        super("Dibujando lineas con mascara");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, x, y, this);
    }

    public void dibujarCirculo(int xc, int yc, int r, int mascara) {
        
        double theta = 0;
        double deltaTheta = 1.0 / r;
        int contador = 0;
    
        while (theta <= Math.PI / 4) {
            int x = (int) Math.round(r * Math.cos(theta));
            int y = (int) Math.round(r * Math.sin(theta));
    
            if ((mascara >> contador & 1) == 1) {
                putPixel(xc + x, yc + y, Color.BLACK);
                putPixel(xc - x, yc + y, Color.BLACK);
                putPixel(xc + x, yc - y, Color.BLACK);
                putPixel(xc - x, yc - y, Color.BLACK);
                putPixel(xc + y, yc + x, Color.BLACK);
                putPixel(xc - y, yc + x, Color.BLACK);
                putPixel(xc + y, yc - x, Color.BLACK);
                putPixel(xc - y, yc - x, Color.BLACK);
            }
            contador++;
    
            theta += deltaTheta;
        }
        
        
    }
    
    

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        dibujarCirculo(200, 200, 50, 0b1010101010101010);
        dibujarCirculo(200, 200, 60, 0b1110001110001110);
        dibujarCirculo(200, 200, 70, 0b1111111111110000);
    }

    public static void main(String[] args) {
        TiposDeLineasCircunferencias ventana = new TiposDeLineasCircunferencias();
        ventana.setVisible(true);
    }
}
