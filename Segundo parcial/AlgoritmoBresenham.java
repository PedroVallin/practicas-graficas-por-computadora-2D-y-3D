
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import java.lang.Math;

public class AlgoritmoBresenham extends JFrame {
  private final int ANCHO = 400;
  private final int ALTO = 400;

  private float x1 = 100, y1 = 100;
  private float x2 = 300, y2 = 300;

  private BufferedImage buffer;
  private Graphics2D graPixel;

  public AlgoritmoBresenham() {
    super("Algoritmo DDA");
    setSize(ANCHO, ALTO);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    
    buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    graPixel = buffer.createGraphics();
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    drawLineBresenham( x1, y1, x2, y2, Color.BLACK);
  }

  public void drawLineBresenham(float x0, float y0, float x1, float y1, Color c) {
    float dx = Math.abs(x1 - x0);
    float dy = Math.abs(y1 - y0);

    float sx = x0 < x1 ? 1 : -1;
    float sy = y0 < y1 ? 1 : -1;

    float err = dx - dy;

    while (x0 != x1 || y0 != y1) {
        putPixel(Math.round(x0), Math.round(y0), c);
        float e2 = 2 * err;
        if (e2 > -dy) {
            err -= dy;
            x0 += sx;
        }
        if (e2 < dx) {
            err += dx;
            y0 += sy;
        }
    }
}


  private void putPixel( int x, int y, Color c) {
    // g.setColor(color);
    // g.drawLine(x, y, x, y);
    buffer.setRGB(0, 0, c.getRGB());
    this.getGraphics().drawImage(buffer, (int) Math.round(x), (int) Math.round(y), this);
  }

  public static void main(String[] args) {
    AlgoritmoBresenham ventana = new AlgoritmoBresenham();
    ventana.setVisible(true);
  }
}
