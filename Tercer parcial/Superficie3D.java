import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Superficie3D extends JFrame {

    private static final int WIDTH = 800; // Ancho de la ventana
    private static final int HEIGHT = 600; // Alto de la ventana
    private static final int SCALE = 40; // Escala de la superficie
    private static final double ANGLE_STEP = 0.01; // Incremento del ángulo de rotación

    private double angleX; // Ángulo de rotación en el eje X
    private double angleY; // Ángulo de rotación en el eje Y

    // Buffers
    private BufferedImage buffer;


    public Superficie3D() {
        setTitle("Superficie 3D");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

    }

    private void putPixel(Graphics g, double x, double y, Color c) {
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, (int) Math.round(x), (int) Math.round(y), this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Recorremos la superficie en 3D
        for (double x = -10; x <= 10; x += 0.1) {
            for (double y = -10; y <= 10; y += 0.1) {
                // Calculamos la altura de la superficie en el punto (x, y)
                double z = f(x, y);

                // Realizamos las transformaciones de rotación
                double xRot = x * Math.cos(angleY) - z * Math.sin(angleY);
                double yRot = y * Math.cos(angleX) + z * Math.sin(angleX) * Math.sin(angleY);
                double zRot = x * Math.sin(angleY) + z * Math.cos(angleY);

                // Realizamos la proyección en el plano de la pantalla
                int xProj = (int) (xRot * SCALE) + centerX;
                int yProj = (int) (yRot * SCALE) + centerY;

                // Dibujamos un píxel en la posición proyectada
                // g.setColor(Color.BLACK);
                // g.fillRect(xProj, yProj, 1, 1);
                if (x < 0 ) {

                    putPixel(g, xProj, yProj, Color.BLACK);
                } else {
                    putPixel(g, xProj, yProj, Color.BLUE);
                }
            }
        }
    }

    private double f(double x, double y) {
        // Función para definir la superficie (puedes modificarla)
        return Math.sin(Math.sqrt(x * x + y * y)) / Math.sqrt(x * x + y * y);
    }
    // private double f(double x, double y) {
    //     // Función para dibujar una esfera
    //     double radius = 5.0; // Radio de la esfera
    //     double centerX = 0.0; // Coordenada x del centro de la esfera
    //     double centerY = 0.0; // Coordenada y del centro de la esfera
    //     double centerZ = 0.0; // Coordenada z del centro de la esfera
    
    //     // Calcular la distancia desde el punto (x, y) hasta el centro de la esfera
    //     double distance = Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY));
    
    //     // Si la distancia es menor o igual al radio, el punto está dentro de la esfera
    //     if (distance <= radius) {
    //         // Calcular la altura de la superficie en función de la distancia al centro
    //         double z = Math.sqrt(radius * radius - distance * distance) + centerZ;
    //         return z;
    //     } else {
    //         // Si la distancia es mayor que el radio, el punto está fuera de la esfera
    //         return Double.NEGATIVE_INFINITY; // Retornar un valor infinitamente pequeño para que no se dibuje
    //     }
    // }
    


    public void startAnimation() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Incrementamos los ángulos de rotación
                angleX += ANGLE_STEP;
                angleY += ANGLE_STEP;

                repaint();
            }
        }).start();
    }

    public static void main(String[] args) {
        Superficie3D surface = new Superficie3D();
        surface.setVisible(true);
        surface.startAnimation();
    }
}
