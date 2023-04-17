import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import java.lang.Math;

/**
 * BY
 * PEDRO ALBERTO VALLIN DÍAZ  20310071
 * PRACTICA 3 SEGUNDO PARCIAL
 */


public class AlgoritmoDDA extends JFrame {
  private final int ANCHO = 400;
  private final int ALTO = 400;

  private float x1 = 100, y1 = 100;
  private float x2 = 300, y2 = 300;

  private BufferedImage buffer;

  public AlgoritmoDDA() {
    super("Algoritmo DDA");
    setSize(ANCHO, ALTO);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    
    buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    dda( x1, y1, x2, y2, Color.BLACK);
  }

  private void dda( float x1, float y1, float x2, float y2, Color color) {
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
        putPixel( Math.round(x), Math.round(y), color);
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
        putPixel( Math.round(x), Math.round(y), color);
        x += 1/m;
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
    AlgoritmoDDA ventana = new AlgoritmoDDA();
    ventana.setVisible(true);
  }
}
