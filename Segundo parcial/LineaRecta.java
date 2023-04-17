


import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import java.lang.Math;

/**
 * BY
 * PEDRO ALBERTO VALLIN DÍAZ  20310071
 * PRACTICA 1 SEGUNDO PARCIAL
 */

public class LineaRecta extends JFrame {
  private BufferedImage buffer;

  public LineaRecta() {
    super("Mi ventana");
    setSize(200, 200);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
  }

  public void putPixel(float x, float y, Color c) {
    buffer.setRGB(0, 0, c.getRGB());
    this.getGraphics().drawImage(buffer, (int) Math.round(x), (int) Math.round(y), this);
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    dibujarLinea(0, 0, 100, 100);
  }

  public void dibujarLinea(float x0, float y0, float x1, float y1) {


    float m;
    float b;
    float y, x;

    m = (y1 - y0)/(x1 - x0);
    b = y0 - m * x0;

    for ( x = x0; x <= x1; x ++ ) {
      y = m * x + b;
      putPixel(x, Math.round(y), Color.BLACK);
    }

  }

  public static void main(String[] args) {
    LineaRecta ventana = new LineaRecta();
    ventana.setVisible(true);
  }
}


