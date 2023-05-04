import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 * BY
 * PEDRO ALBERTO VALLIN DÍAZ  20310071
 * PRACTICA 20 SEGUNDO PARCIAL
 */

public class RecorteCircunferencias extends JFrame {
    private BufferedImage buffer;

    public RecorteCircunferencias() {
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

    /**
     * Programa de recorte de area para circunferencias usando el método de recorte por puntos
     * @param xc Coordenada X central del círculo
     * @param yc Coordenmada Y central del círculo
     * @param r Radio del circulo
     * @param limX1 Límite izquierdo del rectangulo
     * @param limY1 Límite superior del rectangulo
     * @param limX2 Límite derecho del rectangulo
     * @param limY2 Límite inferior del rectangulo
     */
    public void dibujarCircunferenciaRecorte(int xc, int yc, int r, int limX1, int limY1, int limX2, int limY2) {
        int x = 0, y = r;
        int d = 3 - 2 * r;
    
        while (x <= y) {
            int x1 = xc + x;
            int x2 = xc - x;
            int y1 = yc + y;
            int y2 = yc - y;
            int x3 = xc + y;
            int x4 = xc - y;
            int y3 = yc + x;
            int y4 = yc - x;
    
            // Recorte por cada extremo
            // (a) Verificar si la línea es totalmente visible o trivialmente invisible
            if (x1 >= limX1 && x1 <= limX2 && y1 >= limY1 && y1 <= limY2) {
                putPixel(x1, y1, Color.BLACK);
            }
            if (x2 >= limX1 && x2 <= limX2 && y1 >= limY1 && y1 <= limY2) {
                putPixel(x2, y1, Color.BLACK);
            }
            if (x3 >= limX1 && x3 <= limX2 && y3 >= limY1 && y3 <= limY2) {
                putPixel(x3, y3, Color.BLACK);
            }
            if (x4 >= limX1 && x4 <= limX2 && y3 >= limY1 && y3 <= limY2) {
                putPixel(x4, y3, Color.BLACK);
            }
            if (x1 >= limX1 && x1 <= limX2 && y2 >= limY1 && y2 <= limY2) {
                putPixel(x1, y2, Color.BLACK);
            }
            if (x2 >= limX1 && x2 <= limX2 && y2 >= limY1 && y2 <= limY2) {
                putPixel(x2, y2, Color.BLACK);
            }
            if (x3 >= limX1 && x3 <= limX2 && y4 >= limY1 && y4 <= limY2) {
                putPixel(x3, y4, Color.BLACK);
            }
            if (x4 >= limX1 && x4 <= limX2 && y4 >= limY1 && y4 <= limY2) {
                putPixel(x4, y4, Color.BLACK);
            }
    
            // (b) Si P1 está fuera de la ventana de recorte, intercambia P1 y P2
            if (d < 0) {
                int deltaE = 4 * x + 6;
                x++;
                d += deltaE;
            } else {
                int deltaSE = 4 * (x - y) + 10;
                x++;
                y--;
                d += deltaSE;
            }
        }
    }
    

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        dibujarRectangulo(50, 50, 150, 100, Color.BLACK);
        // dibujarLinea2(100, 75, 175, 80, 50, 50, 150, 100, Color.BLACK);
        // dibujarLinea2(25, 75, 100, 125, 50, 50, 150, 100, Color.BLACK);
        dibujarCircunferenciaRecorte(100, 100, 30, 50, 50, 150, 100);
        dibujarCircunferenciaRecorte(100, 100, 20, 50, 50, 150, 100);
        dibujarCircunferenciaRecorte(100, 100, 10, 50, 50, 150, 100);
        dibujarCircunferenciaRecorte(55, 65, 10, 50, 50, 150, 100);
        dibujarCircunferenciaRecorte(150, 65, 10, 50, 50, 150, 100);
    }

    public static void main(String[] args) {
        RecorteCircunferencias ventana = new RecorteCircunferencias();
        ventana.setVisible(true);
    }
}
