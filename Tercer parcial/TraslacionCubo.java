
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/**
 * BY
 * PEDRO ALBERTO VALLIN DÍAZ  20310071
 * PRACTICA 3 TERCER PARCIAL
 */

public class TraslacionCubo extends JFrame implements KeyListener{

    private BufferedImage buffer;
    int sizeDelCubo = 50;
    int cuboPosX = 50;
    int cuboPosY = 50;

    public TraslacionCubo() {
        super("Cubo con proyección paralela");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        addKeyListener(this);
        setFocusable(true);
    }


    private void putPixel(Graphics g, double x, double y, Color c) {
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, (int) Math.round(x), (int) Math.round(y), this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        
        // Vértices del cubo en 3D
        double[] verticesXCubo = { cuboPosX, cuboPosX + sizeDelCubo, cuboPosX + sizeDelCubo, cuboPosX, cuboPosX, cuboPosX + sizeDelCubo, cuboPosX + sizeDelCubo, cuboPosX };
        double[] verticesYCubo = { cuboPosY, cuboPosY, cuboPosY + sizeDelCubo, cuboPosY + sizeDelCubo, cuboPosY, cuboPosY, cuboPosY + sizeDelCubo, cuboPosY + sizeDelCubo };
        double[] verticesZCubo = { cuboPosY, cuboPosY, cuboPosY, cuboPosY, cuboPosY + sizeDelCubo, cuboPosY + sizeDelCubo, cuboPosY + sizeDelCubo, cuboPosY + sizeDelCubo };
        // Proyección paralela
        double scaleFactor = 0.5; // Escala de proyección
        double[] verticeX = new double[8];
        double[] verticeY = new double[8];

        for (int i = 0; i < 8; i++) {
            verticeX[i] = verticesXCubo[i] + scaleFactor * verticesZCubo[i];
            verticeY[i] = verticesYCubo[i] + scaleFactor * verticesZCubo[i];
        }

        dibujarCubo(g, verticeX, verticeY);
    }

    private void drawLine(Graphics g, double x1, double y1, double x2, double y2) {
        double dx = Math.abs(x2 - x1);
        double dy = Math.abs(y2 - y1);
        double sx = x1 < x2 ? 1 : -1;
        double sy = y1 < y2 ? 1 : -1;
        double err = dx - dy;

        while (true) {
            putPixel(g, x1, y1, Color.BLACK);

            if (x1 == x2 && y1 == y2) {
                break;
            }

            double err2 = 2 * err;

            if (err2 > -dy) {
                err -= dy;
                x1 += sx;
            }

            if (err2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    private void dibujarCubo(Graphics g, double[] verticesX, double[] verticesY) {
        for (int i = 0; i < 4; i++) {
            drawLine(g, verticesX[i], verticesY[i], verticesX[(i + 1) % 4], verticesY[(i + 1) % 4]);
            drawLine(g, verticesX[i + 4], verticesY[i + 4], verticesX[((i + 1) % 4) + 4], verticesY[((i + 1) % 4) + 4]);
            drawLine(g, verticesX[i], verticesY[i], verticesX[i + 4], verticesY[i + 4]);
        }
    }
    

    public static void main(String[] args) {

        TraslacionCubo ventana = new TraslacionCubo();
        ventana.setVisible(true);

    }

    private void transladarCubo(int dx, int dy) {
        cuboPosX += dx;
        cuboPosY += dy;
        repaint();
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        switch (key) {
            case KeyEvent.VK_LEFT:
                transladarCubo(-10, 0);
                System.out.println("izquierda");
                break;
            case KeyEvent.VK_RIGHT:
                transladarCubo(10, 0);
                break;
            case KeyEvent.VK_UP:
                transladarCubo(0, -10);
                break;
            case KeyEvent.VK_DOWN:
                transladarCubo(0, 10);
                break;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {

    }
    
    @Override
    public void keyReleased(KeyEvent e) {

    }

}

