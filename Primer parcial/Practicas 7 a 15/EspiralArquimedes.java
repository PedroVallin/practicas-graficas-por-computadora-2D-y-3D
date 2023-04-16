import java.awt.*;
import javax.swing.*;

public class EspiralArquimedes extends JFrame {

    private int[] xCoords;
    private int[] yCoords;

    public EspiralArquimedes() {
        super("Espiral de Arquimides");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        xCoords = new int[1000];
        yCoords = new int[1000];

        int x0 = getWidth() / 2;
        int y0 = getHeight() / 2;

        double a = 1;
        double b = 0.3;
        double t = 0;
        for (int i = 0; i < 1000; i++) {
            double r = a + b * t;
            xCoords[i] = (int) (x0 + r * Math.cos(t));
            yCoords[i] = (int) (y0 + r * Math.sin(t));
            t += 0.1;
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < 999; i++) {
            g.drawLine(xCoords[i], yCoords[i], xCoords[i + 1], yCoords[i + 1]);
            try {
                Thread.sleep(10); // Agregar retraso de 10 milisegundos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        EspiralArquimedes frame = new EspiralArquimedes();
        frame.setVisible(true);
    }
}
