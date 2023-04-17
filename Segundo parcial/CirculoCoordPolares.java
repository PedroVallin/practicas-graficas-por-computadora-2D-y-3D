import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 * 
 * 
 * PEDRO ALBERTO VALLIN DÍAZ  20310071
 * PRACTICA 8 SEGUNDO PARCIAL
 * 
 *                .----.
      .---------. | == |
      |.-"""""-.| |----|
      ||       || | == |
      ||       || |----|
      |'-.....-'| |::::|
      `"")---(""` |___.|
     /:::::::::::\" _  "
    /:::=======:::\`\`\
    `"""""""""""""`  '-'
 */

public class CirculoCoordPolares extends JFrame {
    private BufferedImage buffer;

    public CirculoCoordPolares() {
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
        for (double theta = 0; theta < 2*Math.PI; theta += 0.01) {
            x = xc + R*Math.cos(theta);
            y = yc + R*Math.sin(theta);
            putPixel(x, y, c);
        }

        
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        dibujarCirculo(100, 100, 50, Color.BLACK);
    }

    public static void main(String[] args) {
        CirculoCoordPolares ventana = new CirculoCoordPolares();
        ventana.setVisible(true);
    }
}
