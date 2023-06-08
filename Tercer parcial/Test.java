import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Test extends JFrame {

    private static final int WIDTH = 800; // Ancho de la ventana
    private static final int HEIGHT = 600; // Alto de la ventana
    private static final double ANGLE_STEP = 0.1; // Incremento del ángulo de rotación

    private double angle; // Ángulo de rotación

    private BufferedImage buffer;

    public Test() {
        setTitle("Superficie Cilíndrica");
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

        for (double t = -10; t <= 10; t += 0.1) {
            for (double angulo = 0; angulo <= Math.PI * 2; angulo += ANGLE_STEP) {
                double x = (2 + Math.cos(t)) * Math.cos(angulo);
                double y = (2 + Math.cos(t)) * Math.sin(angulo);
                double z = t;

                int xProj = (int) (x * 40) + centerX;
                int yProj = (int) (y * 40) + centerY;

                // g.setColor(Color.BLACK);
                // g.fillRect(xProj, yProj, 1, 1);
                putPixel(g, xProj, yProj, Color.BLACK);
            }
        }
    }

    public void startAnimation() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                angle += ANGLE_STEP;

                repaint();
            }
        }).start();
    }

    public static void main(String[] args) {
        Test superficie = new Test();
        superficie.setVisible(true);
        superficie.startAnimation();
    }
}


// Rotacion de cubo
// import java.awt.Color;
// import java.awt.Graphics;
// import java.awt.image.BufferedImage;

// import javax.swing.JFrame;

// public class Test extends JFrame {

//     private BufferedImage buffer;
//     private int sizeDelCubo = 50;
//     private int cuboPosX = 300;
//     private int cuboPosY = 300;
//     private double angulo = 0.01;

//     public Test() {
//         super("Cubo con proyección paralela");
//         setSize(600, 600);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//         buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

//         new Thread(() -> {
//             while (true) {
//                 try {
//                     Thread.sleep(30);
//                 } catch (InterruptedException e) {
//                     e.printStackTrace();
//                 }
//                 angulo += 0.01;
//                 repaint();
//             }
//         }).start();
//     }

//     private void putPixel(Graphics g, double x, double y, Color c) {
//         buffer.setRGB(0, 0, c.getRGB());
//         this.getGraphics().drawImage(buffer, (int) Math.round(x), (int) Math.round(y), this);
//     }

//     @Override
//     public void paint(Graphics g) {
//         super.paint(g);

//         // Vértices del cubo en 3D
//         double[] verticesXCubo = { cuboPosX, cuboPosX + sizeDelCubo, cuboPosX + sizeDelCubo, cuboPosX, cuboPosX, cuboPosX + sizeDelCubo, cuboPosX + sizeDelCubo, cuboPosX };
//         double[] verticesYCubo = { cuboPosY, cuboPosY, cuboPosY + sizeDelCubo, cuboPosY + sizeDelCubo, cuboPosY, cuboPosY, cuboPosY + sizeDelCubo, cuboPosY + sizeDelCubo };
//         double[] verticesZCubo = { cuboPosY, cuboPosY, cuboPosY, cuboPosY, cuboPosY + sizeDelCubo, cuboPosY + sizeDelCubo, cuboPosY + sizeDelCubo, cuboPosY + sizeDelCubo };
        
//         // Proyección paralela
//         double[] verticeX = new double[8];
//         double[] verticeY = new double[8];

//         for (int i = 0; i < 8; i++) {
//             verticeX[i] = verticesXCubo[i] + verticesZCubo[i] / 2;
//             verticeY[i] = verticesYCubo[i] + verticesZCubo[i] / 2;
//         }

//         // Coordenadas del centro
//         double centroX = 0;
//         double centroY = 0;

//         for (int i = 0; i < 8; i++) {
//             centroX += verticeX[i];
//             centroY += verticeY[i];
//         }

//         centroX /= 8;
//         centroY /= 8;

//         for (int i = 0; i < 8; i++) {
//             double x = verticeX[i];
//             double y = verticeY[i];
        
//             // Aplicar rotación alrededor del eje vertical (Y)
//             double cos = Math.cos(angulo);
//             double sin = Math.sin(angulo);
//             verticeX[i] = Math.round((x - centroX) * cos - (y - centroY) * sin + centroX);
//             verticeY[i] = Math.round((x - centroX) * sin + (y - centroY) * cos + centroY);
//         }
        


//         putPixel(g, 300, 300, Color.BLACK);

//         dibujarCubo(g, verticeX, verticeY);
//     }

//     private void dibujarCubo(Graphics g, double[] verticesX, double[] verticesY) {
//         for (int i = 0; i < 4; i++) {
//             drawLine(g, verticesX[i], verticesY[i], verticesX[(i + 1) % 4], verticesY[(i + 1) % 4], Color.BLACK);
//             drawLine(g, verticesX[i + 4], verticesY[i + 4], verticesX[((i + 1) % 4) + 4], verticesY[((i + 1) % 4) + 4], Color.BLUE);
//             drawLine(g, verticesX[i], verticesY[i], verticesX[i + 4], verticesY[i + 4], Color.RED);
//         }
//     }

//     private void drawLine(Graphics g, double x1, double y1, double x2, double y2, Color c) {
//         double dx = Math.abs(x2 - x1);
//         double dy = Math.abs(y2 - y1);
//         double sx = x1 < x2 ? 1 : -1;
//         double sy = y1 < y2 ? 1 : -1;
//         double err = dx - dy;

//         while (true) {
//             putPixel(g, x1, y1, c);

//             if (x1 == x2 && y1 == y2) {
//                 break;
//             }

//             double err2 = 2 * err;

//             if (err2 > -dy) {
//                 err -= dy;
//                 x1 += sx;
//             }

//             if (err2 < dx) {
//                 err += dx;
//                 y1 += sy;
//             }
//         }
//     }

//     public static void main(String[] args) {
//         Test ventana = new Test();
//         ventana.setVisible(true);
//     }
// }

// import java.awt.Color;
// import java.awt.Graphics;
// import java.awt.image.BufferedImage;
// import java.io.Console;

// import javax.swing.JFrame;

// public class Test extends JFrame {

//     private BufferedImage buffer;
//     private int sizeDelCubo = 50;
//     private int cuboPosX = 300;
//     private int cuboPosY = 300;
//     private double angle = 0.0; // Ángulo de rotación

//     // Vértices del cubo en 3D
//     double[] verticesXCubo = {cuboPosX, cuboPosX + sizeDelCubo, cuboPosX + sizeDelCubo, cuboPosX, cuboPosX, cuboPosX + sizeDelCubo, cuboPosX + sizeDelCubo, cuboPosX};
//     double[] verticesYCubo = {cuboPosY, cuboPosY, cuboPosY + sizeDelCubo, cuboPosY + sizeDelCubo, cuboPosY, cuboPosY, cuboPosY + sizeDelCubo, cuboPosY + sizeDelCubo};
//     double[] verticesZCubo = {cuboPosY, cuboPosY, cuboPosY, cuboPosY, cuboPosY + sizeDelCubo, cuboPosY + sizeDelCubo, cuboPosY + sizeDelCubo, cuboPosY + sizeDelCubo};

//     // Proyección paralela
//     double[] verticeX = new double[8];
//     double[] verticeY = new double[8];

//     public Test() {
//         super("Cubo con proyección paralela");
//         setSize(600, 600);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

//         new Thread(() -> {
//             while (true) {
//                 try {
//                     Thread.sleep(300);
//                 } catch (InterruptedException e) {
//                     e.printStackTrace();
//                 }
//                 repaint();
//                 System.out.println(angle);
                
//             }
//         }).start();
//     }

//     private void putPixel(Graphics g, double x, double y, Color c) {
//         buffer.setRGB(0, 0, c.getRGB());
//         this.getGraphics().drawImage(buffer, (int) Math.round(x), (int) Math.round(y), this);
//     }

//     @Override
//     public void paint(Graphics g) {
//         super.paint(g);

        

//         for (int i = 0; i < 8; i++) {
//             verticeX[i] = (verticesXCubo[i] + verticesZCubo[i] / 2) - 188;
//             verticeY[i] = (verticesYCubo[i] + verticesZCubo[i] / 2) - 188;
//         }

//         putPixel(g, 300, 300, Color.BLACK);

//         dibujarCubo(g, verticeX, verticeY);

//         // Actualizar las coordenadas de los vértices para la próxima iteración
//         updateVertices(verticesXCubo, verticesYCubo, verticesZCubo);
//     }

//     private void dibujarCubo(Graphics g, double[] verticesX, double[] verticesY) {
//         for (int i = 0; i < 4; i++) {
//             drawLine(g, verticesX[i], verticesY[i], verticesX[(i + 1) % 4], verticesY[(i + 1) % 4], Color.BLACK);
//             drawLine(g, verticesX[i + 4], verticesY[i + 4], verticesX[((i + 1) % 4) + 4], verticesY[((i + 1) % 4) + 4], Color.BLUE);
//             drawLine(g, verticesX[i], verticesY[i], verticesX[i + 4], verticesY[i + 4], Color.RED);
//         }
//     }

//     private void drawLine(Graphics g, double x1, double y1, double x2, double y2, Color c) {
//         double dx = Math.abs(x2 - x1);
//         double dy = Math.abs(y2 - y1);
//         double sx = x1 < x2 ? 1 : -1;
//         double sy = y1 < y2 ? 1 : -1;
//         double err = dx - dy;

//         while (true) {
//             putPixel(g, x1, y1, c);

//             if (x1 == x2 && y1 == y2) {
//                 break;
//             }

//             double err2 = 2 * err;

//             if (err2 > -dy) {
//                 err -= dy;
//                 x1 += sx;
//             }

//             if (err2 < dx) {
//                 err += dx;
//                 y1 += sy;
//             }
//         }
//     }

//     private void updateVertices(double[] verticesXCubo, double[] verticesYCubo, double[] verticesZCubo) {
//         double cosAngle = Math.cos(angle);
//         double sinAngle = Math.sin(angle);

//         for (int i = 0; i < 8; i++) {
//             double x = verticesXCubo[i] - cuboPosX;
//             double y = verticesYCubo[i] - cuboPosY;
//             double z = verticesZCubo[i] - cuboPosY;

//             // Rotación en el eje Z
//             double rotatedX = x * cosAngle - y * sinAngle;
//             double rotatedY = x * sinAngle + y * cosAngle;

//             verticesXCubo[i] = rotatedX + cuboPosX;
//             verticesYCubo[i] = rotatedY + cuboPosY;
//             verticesZCubo[i] = z + cuboPosY;
//         }

//         angle += 0.01; // Incremento de ángulo para la rotación
//     }

//     public static void main(String[] args) {
//         Test ventana = new Test();
//         ventana.setVisible(true);
//     }
// }


// import java.awt.Color;
// import java.awt.Graphics;
// import javax.swing.JFrame;


// // CuboProyeccionParametrica


// public class Test extends JFrame {

//     private static final int WIDTH = 400;
//     private static final int HEIGHT = 400;
//     private static final int CUBE_SIZE = 100;

//     public Test() {
//         super("Cubo con proyección paramétrica");
//         setSize(WIDTH, HEIGHT);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//     }

//     @Override
//     public void paint(Graphics g) {
//         super.paint(g);

//         // Coordenadas iniciales del cubo
//         int x1 = WIDTH / 2 - CUBE_SIZE / 2;
//         int y1 = HEIGHT / 2 - CUBE_SIZE / 2;
//         int z1 = 0;

//         // Componentes de dirección para cada lado del cubo
//         int[] xp = {CUBE_SIZE, 0, 0, CUBE_SIZE, CUBE_SIZE, 0};
//         int[] yp = {0, CUBE_SIZE, 0, 0, CUBE_SIZE, CUBE_SIZE};
//         int[] zp = {0, 0, CUBE_SIZE, CUBE_SIZE, 0, CUBE_SIZE};

//         // Dibujar los lados del cubo
//         for (int i = 0; i < 4; i++) {
//             drawLine(g, x1 + xp[i], y1 + yp[i], z1 + zp[i], x1 + xp[i + 1], y1 + yp[i + 1], z1 + zp[i + 1]);
//             drawLine(g, x1 + xp[i], y1 + yp[i], z1 + zp[i], x1 + xp[i + 4], y1 + yp[i + 4], z1 + zp[i + 4]);
//             drawLine(g, x1 + xp[i + 4], y1 + yp[i + 4], z1 + zp[i + 4], x1 + xp[i + 5], y1 + yp[i + 5], z1 + zp[i + 5]);
//         }
//     }

//     private void drawLine(Graphics g, int x1, int y1, int z1, int x2, int y2, int z2) {
//         g.setColor(Color.BLACK);
//         g.drawLine(x1, y1, x2, y2);
//     }

//     public static void main(String[] args) {
//         Test ventana = new Test();
//         ventana.setVisible(true);
//     }
// }



// import java.awt.Color;
// import java.awt.Graphics;
// import java.awt.image.BufferedImage;

// import javax.swing.JFrame;

// public class Test extends JFrame {

//     private BufferedImage buffer;
//     int sizeDelCubo = 50;
//     int cuboPosX = 50;
//     int cuboPosY = 50;

//     public Test() {
//         super("Cubo con proyección paralela");
//         setSize(200, 200);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//         buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
//     }

//     private void putPixel(Graphics g, double x, double y, Color c) {
//         buffer.setRGB(0, 0, c.getRGB());
//         g.drawImage(buffer, (int) Math.round(x), (int) Math.round(y), this);
//     }

//     @Override
//     public void paint(Graphics g) {
//         super.paint(g);

//         // Vértices del cubo en 3D
//         double[] verticesXCubo = { cuboPosX, cuboPosX + sizeDelCubo, cuboPosX + sizeDelCubo, cuboPosX, cuboPosX, cuboPosX + sizeDelCubo, cuboPosX + sizeDelCubo, cuboPosX };
//         double[] verticesYCubo = { cuboPosY, cuboPosY, cuboPosY + sizeDelCubo, cuboPosY + sizeDelCubo, cuboPosY, cuboPosY, cuboPosY + sizeDelCubo, cuboPosY + sizeDelCubo };
//         double[] verticesZCubo = { cuboPosY, cuboPosY, cuboPosY, cuboPosY, cuboPosY + sizeDelCubo, cuboPosY + sizeDelCubo, cuboPosY + sizeDelCubo, cuboPosY + sizeDelCubo };

//         // Proyección paralela
//         double scaleFactor = 0.5; // Escala de proyección
//         double[] verticeX = new double[8];
//         double[] verticeY = new double[8];

//         for (int i = 0; i < 8; i++) {
//             verticeX[i] = verticesXCubo[i] + scaleFactor * verticesZCubo[i];
//             verticeY[i] = verticesYCubo[i] + scaleFactor * verticesZCubo[i];
//         }

//         // Rotación en el eje Z
//         double anguloZ = Math.PI / 4; // Ángulo de rotación en el eje Z (45 grados en este ejemplo)

//         double[] verticeXRotado = new double[8];
//         double[] verticeYRotado = new double[8];

//         for (int i = 0; i < 8; i++) {
//             verticeXRotado[i] = verticeX[i] * Math.cos(anguloZ) - verticeY[i] * Math.sin(anguloZ);
//             verticeYRotado[i] = verticeX[i] * Math.sin(anguloZ) + verticeY[i] * Math.cos(anguloZ);
//         }

//         dibujarCubo(g, verticeXRotado, verticeYRotado);
//     }

//     private void drawLine(Graphics g, double x1, double y1, double x2, double y2) {
//         double dx = Math.abs(x2 - x1);
//         double dy = Math.abs(y2 - y1);
//         double sx = x1 < x2 ? 1 : -1;
//         double sy = y1 < y2 ? 1 : -1;
//         double err = dx - dy;

//         while (true) {
//             putPixel(g, x1, y1, Color.BLACK);

//             if (x1 == x2 && y1 == y2) {
//                 break;
//             }

//             double err2 = 2 * err;

//             if (err2 > -dy) {
//                 err -= dy;
//                 x1 += sx;
//             }

//             if (err2 < dx) {
//                 err += dx;
//                 y1 += sy;
//             }
//         }
//     }

//     private void dibujarCubo(Graphics g, double[] verticesX, double[] verticesY) {
//         for (int i = 0; i < 4; i++) {
//             drawLine(g, verticesX[i], verticesY[i], verticesX[(i + 1) % 4], verticesY[(i + 1) % 4]);
//             drawLine(g, verticesX[i + 4], verticesY[i + 4], verticesX[((i + 1) % 4) + 4], verticesY[((i + 1) % 4) + 4]);
//             drawLine(g, verticesX[i], verticesY[i], verticesX[i + 4], verticesY[i + 4]);
//         }
//     }

//     public static void main(String[] args) {
//         Test ventana = new Test();
//         ventana.setVisible(true);
//     }
// }
