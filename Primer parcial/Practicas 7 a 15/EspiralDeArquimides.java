import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;

/*
 * PEDRO ALBERTO VALLIN DIAZ 20310071
 */

public class EspiralDeArquimides extends JFrame implements Runnable {
    private int x, y, x2, y2;
    private double angulo, radio, radio2;
    
    public EspiralDeArquimides() {
        super("Espiral de Arco");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        x = getWidth() / 2;
        y = getHeight() / 2;
        x2 = getWidth() / 2;
        y2 = getHeight() / 2;

        radio = 5;
        radio2 = 5;
        angulo = 0;
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        int xPrev = x;
        int yPrev = y;
        int x2Prev = x2;
        int y2Prev = y2;

        while(true) {
            radio += 0.2;
            radio2 += 0.2;
            angulo += 0.1;
            x = (int) (getWidth() / 2 + radio * Math.cos(angulo));
            y = (int) (getHeight() / 2 + radio * Math.sin(angulo));
            x2 = (int) (getWidth() / 2 + radio2 * Math.cos(angulo + Math.PI));
            y2 = (int) (getHeight() / 2 + radio2 * Math.sin(angulo + Math.PI));
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            g.setColor(Color.BLUE);
            g.drawLine(xPrev, yPrev, x, y);
            g.setColor(Color.RED);
            g.drawLine(x2Prev, y2Prev, x2, y2);

                    
            xPrev = x;
            yPrev = y;
            x2Prev = x2;
            y2Prev = y2;
        }
    }
    
    public void run() {
        while (true) {
            repaint();
        }
    }
    
    public static void main(String[] args) {
        EspiralDeArquimides espiral = new EspiralDeArquimides();
        Thread hilo = new Thread(espiral);
        hilo.start();
    }
}