import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 * BY
 * PEDRO ALBERTO VALLIN DÍAZ  20310071
 * PRACTICA 17 SEGUNDO PARCIAL
 */

public class RellenoScanLine extends JFrame {
    private BufferedImage buffer;

    public RellenoScanLine() {
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

    public void dibujarRectangulo(int x1, int y1, int x2, int y2, Color c) {
        dibujarLinea(x1, y1, x2, y1, c);
        dibujarLinea(x2, y1, x2, y2, c);
        dibujarLinea(x2, y2, x1, y2, c);
        dibujarLinea(x1, y2, x1, y1, c);
        llenarRectangulo(x1, y1, x2, y2, c);
    }

    public void llenarRectangulo(int x1, int y1, int x2, int y2, Color c) {
        
        int xaux, yaux, xc, yc; // Centro de la figura
        xaux = (x2 - x1) / 2;
        yaux = (y2 - y1) / 2;

        xc = x1 + xaux; // Obtener el valor X central de la figura
        yc = y1 + yaux; // obtener el valor Y central de la figura

        
        for ( int y = yc; y <= y2; y++ ) { // Recorrer en y
            for ( int x = xc; x <= x2; x++) {  // Recorrer en x
                putPixel(x, y, Color.BLUE);
            }
        }

        for ( int y = yc; y >= y1; y-- ) { // Recorrer en y
            for ( int x = xc; x <= x2; x++) {  // Recorrer en x
                putPixel(x, y, Color.BLUE);
            }
        }

        for ( int y = yc; y <= y2; y++ ) { // Recorrer en y
            for ( int x = xc; x >= x1; x--) {  // Recorrer en x
                putPixel(x, y, Color.BLUE);
            }
        }

        for ( int y = yc; y >= y1; y-- ) { // Recorrer en y
            for ( int x = xc; x >= x1; x-- ) {  // Recorrer en x
                putPixel(x, y, Color.BLUE);
            }
        }

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        dibujarRectangulo(50, 50, 150, 100, Color.BLACK);
    }

    public static void main(String[] args) {
        RellenoScanLine ventana = new RellenoScanLine();
        ventana.setVisible(true);
    }
}
