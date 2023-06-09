


import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import java.lang.Math;

/**
 * BY
 * PEDRO ALBERTO VALLIN DÍAZ  20310071
 * PRACTICA 2 SEGUNDO PARCIAL
 */

 /*
  * Manejo de casos especiales: El algoritmo actual podría fallar si x1 - x0 = 0, es decir, si la línea es vertical. 
  En este caso, el valor de m sería infinito y se produciría una excepción. Podemos manejar este caso especial 
  simplemente comprobando si x1 - x0 = 0 y trazando la línea verticalmente en su lugar.
  */

public class LineaRectaMejorada extends JFrame {
  private BufferedImage buffer;

  public LineaRectaMejorada() {
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
    dibujarLinea(20, 0, 20, 100);
  }

  public void dibujarLinea(float x0, float y0, float x1, float y1) {


    float m;
    float b;
    float y, x;

    m = (y1 - y0)/(x1 - x0);
    b = y0 - m * x0;

    // caso especial: línea vertical
    if (x1 - x0 == 0) {
        for (y = y0; y <= y1; y++) {
            putPixel(x0, y, Color.BLACK);
        }
    } else {
        for ( x = x0; x <= x1; x ++ ) {
          y = m * x + b;
          putPixel(x, y, Color.BLACK);
        }
    }


  }

  public static void main(String[] args) {
    LineaRectaMejorada ventana = new LineaRectaMejorada();
    ventana.setVisible(true);
  }
}


