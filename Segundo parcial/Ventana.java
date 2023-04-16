


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.Console;

import javax.swing.JFrame;
import java.lang.Math;

/**
 * BY
 * PEDRO ALBERTO VALLIN DÍAZ  20310071
 */

public class Ventana extends JFrame {
  private BufferedImage buffer;
  private Graphics2D graPixel;

  float[] xValues = new float[101];
  float[] yValues= new float[101];

  public Ventana() {
    super("Mi ventana");
    setSize(200, 200);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    graPixel = buffer.createGraphics();
  }

  public void putPixel(float x, float y, Color c) {
    buffer.setRGB(0, 0, c.getRGB());
    this.getGraphics().drawImage(buffer, (int) Math.round(x), (int) Math.round(y), this);
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    calcularValoresY(0, 200, 100, 180);
    for (int i = 0; i < xValues.length; i++) {
      putPixel(xValues[i], Math.round(yValues[i]), Color.BLACK);
    }
  }

  public void calcularValoresY(float x0, float y0, float x1, float y1) {


    float m;
    float b;

    m = (y1 - y0)/(x1 - x0);
    b = y0 - m * x0;

    // Llenar valores de x
    for (int i = 0; i < 101; i++) { // TODO: poner ancho de pantalla
      xValues[i] = i;      
    }

    // Llenar valores de y
    for (int i = 0; i < xValues.length; i++) {
      yValues[i] = m * xValues[i] + b; // y = mx + b
    }
  }

  public static void main(String[] args) {
    Ventana ventana = new Ventana();
    ventana.setVisible(true);
  }
}


