import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/**
 * BY
 * PEDRO ALBERTO VALLIN DÍAZ  20310071
 * PRACTICA 4 TERCER PARCIAL
 * 
 */

public class EscalamientoCubo extends JFrame implements KeyListener{

    private BufferedImage buffer;
    int sizeDelCubo = 50;
    int cuboPosX = 50;
    int cuboPosY = 50;
    double factorDeEscalamiento = 2;

    public EscalamientoCubo() {
        super("Cubo con proyección paralela");
        setSize(600, 600);
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

        // Vértices aplicandoles factor de escalamiento
        // factorDeEscalamiento = 5;
        double[] verticesEscaladosXCubo = new double[8];
        double[] verticesEscaladosYCubo = new double[8];
        double[] verticesEscaladosZCubo = new double[8];
        for ( int i = 0; i < 8; i++ ) {
            verticesEscaladosXCubo[i] = verticesXCubo[i] * factorDeEscalamiento;
            verticesEscaladosYCubo[i] = verticesYCubo[i] * factorDeEscalamiento;
            verticesEscaladosZCubo[i] = verticesZCubo[i] * factorDeEscalamiento;
        }

        // Proyección paralela
        double scaleFactor = 0.5; // Escala de proyección
        double[] verticeX = new double[8];
        double[] verticeY = new double[8];

        for (int i = 0; i < 8; i++) {
            verticeX[i] = verticesEscaladosXCubo[i] + scaleFactor * verticesEscaladosZCubo[i];
            verticeY[i] = verticesEscaladosYCubo[i] + scaleFactor * verticesEscaladosZCubo[i];
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

    private void escalarCubo(double ds) {
        factorDeEscalamiento += ds;
        repaint();
    }
    

    public static void main(String[] args) {

        EscalamientoCubo ventana = new EscalamientoCubo();
        ventana.setVisible(true);

    }


    @Override
    public void keyTyped(KeyEvent e) {
        
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        switch (key) {

            case KeyEvent.VK_UP:
                escalarCubo(1);
                break;
            case KeyEvent.VK_DOWN:
                escalarCubo(-1);
                break;
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        
    }

}
