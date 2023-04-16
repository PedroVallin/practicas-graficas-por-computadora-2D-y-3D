// import javax.swing.JFrame;
// import javax.swing.JPanel;
// import java.awt.Graphics;
// import java.awt.Color;

// public class EspiralArco extends JPanel {
//     private static final long serialVersionUID = 1L;
//     private static final int WIDTH = 500;
//     private static final int HEIGHT = 500;
//     private static final int DELAY = 50;

//     private int x1, y1, x2, y2;
//     private double angle = 0;
//     private double radius1 = 10;
//     private double radius2 = 30;

//     public EspiralArco() {
//         setBackground(Color.WHITE);

//         x1 = WIDTH / 2;
//         y1 = HEIGHT / 2;
//         x2 = WIDTH / 2;
//         y2 = HEIGHT / 2;
//     }

//     public void paintComponent(Graphics g) {
//         super.paintComponent(g);

//         while (radius1 < 200 && radius2 < 220) {
//             double x1Prev = x1;
//             double y1Prev = y1;
//             double x2Prev = x2;
//             double y2Prev = y2;

//             angle += 0.1;
//             radius1 += 0.1;
//             radius2 += 0.2;

//             x1 = (int) (WIDTH / 2 + radius1 * Math.cos(angle));
//             y1 = (int) (HEIGHT / 2 + radius1 * Math.sin(angle));
//             x2 = (int) (WIDTH / 2 + radius2 * Math.cos(angle + Math.PI));
//             y2 = (int) (HEIGHT / 2 + radius2 * Math.sin(angle + Math.PI));

//             g.setColor(Color.RED);
//             g.drawLine((int) x1Prev, (int) y1Prev, x1, y1);

//             g.setColor(Color.BLUE);
//             g.drawLine((int) x2Prev, (int) y2Prev, x2, y2);

//             try {
//                 Thread.sleep(DELAY);
//             } catch (InterruptedException ex) {
//                 ex.printStackTrace();
//             }
//         }
//     }

//     public static void main(String[] args) {
//         JFrame frame = new JFrame("Espiral de Arquímedes");
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.setSize(WIDTH, HEIGHT);

//         EspiralArco espiral = new EspiralArco();
//         frame.add(espiral);

//         frame.setVisible(true);
//     }
// }



// Prueba 2
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;

public class EspiralArco extends JFrame implements Runnable {
    private int x, y, x2, y2;
    private double angulo, radio, radio2;
    
    public EspiralArco() {
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
        EspiralArco espiral = new EspiralArco();
        Thread hilo = new Thread(espiral);
        hilo.start();
    }
}



// programa 3
// import java.awt.Color;
// import java.awt.Graphics;
// import javax.swing.JFrame;

// public class EspiralArco extends JFrame implements Runnable {
//     private int x, y;
//     private double angulo, radio;
    
//     public EspiralArco() {
//         super("Espiral de Arco");
//         setSize(500, 500);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setVisible(true);
//         setLocationRelativeTo(null);
//         x = getWidth() / 2;
//         y = getHeight() / 2;
//         radio = 7;
//         angulo = 0;
//     }
    
//     public void paint(Graphics g) {
//         super.paint(g);
//         g.setColor(Color.BLUE);
//         int xPrev = x;
//         int yPrev = y;
//         while(true) {
//             radio += 0.1;
//             angulo += 0.1;
//             x = (int) (getWidth() / 2 + radio * Math.cos(angulo));
//             y = (int) (getHeight() / 2 + radio * Math.sin(angulo));
//             try {
//                 Thread.sleep(50);
//             } catch (InterruptedException e) {
//                 e.printStackTrace();
//             }
//             g.drawLine(xPrev, yPrev, x, y);
//             g.drawLine(xPrev, yPrev, x-7, y);
//             xPrev = x;
//             yPrev = y;
//         }
//     }
    
//     public void run() {
//         while (true) {
//             repaint();
//         }
//     }
    
//     public static void main(String[] args) {
//         EspiralArco espiral = new EspiralArco();
//         Thread hilo = new Thread(espiral);
//         hilo.start();
//     }
// }