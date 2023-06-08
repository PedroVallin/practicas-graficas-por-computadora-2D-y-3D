import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Mallado3D extends JPanel {

    private int meshWidth = 10;
    private int meshHeight = 10;
    private int meshSize = meshWidth * meshHeight;
    private double meshScale = 20.0;
    private int meshXOffset = 100;
    private int meshYOffset = 100;
    private double[][] meshPoints;

    public Mallado3D() {
        calculateMeshPoints();
    }

    private void calculateMeshPoints() {
        meshPoints = new double[meshWidth][meshHeight];

        for (int x = 0; x < meshWidth; x++) {
            for (int y = 0; y < meshHeight; y++) {
                meshPoints[x][y] = calculateZPosition(x, y);
            }
        }
    }

    private double calculateZPosition(int x, int y) {
        // Aquí puedes definir la función que calcula la posición en Z para cada punto del mallado
        // Puedes usar cualquier fórmula o función matemática según tus necesidades
        return Math.sin(x * 0.1) * Math.cos(y * 0.1) * 50.0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        double meshStepX = (double) getWidth() / (meshWidth - 1);
        double meshStepY = (double) getHeight() / (meshHeight - 1);

        for (int x = 0; x < meshWidth; x++) {
            for (int y = 0; y < meshHeight; y++) {
                double xPos = x * meshStepX + meshXOffset;
                double yPos = y * meshStepY + meshYOffset;
                double zPos = meshPoints[x][y];

                int xProy = (int) (xPos + zPos);
                int yProy = (int) (yPos + zPos);

                g.setColor(Color.BLACK);
                g.fillRect(xProy, yProy, 1, 1);
            }
        }
    }

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Mallado Rectangular 3D");
        ventana.setSize(800, 600);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Mallado3D panel = new Mallado3D();
        ventana.add(panel);

        ventana.setVisible(true);
    }
}
