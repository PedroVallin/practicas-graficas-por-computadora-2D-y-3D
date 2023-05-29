

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JFrame;

/**
 * BY
 * PEDRO ALBERTO VALLIN DÍAZ  20310071
 * PRACTICA 5 TERCER PARCIAL
 */

public class RotacionCubo extends JFrame implements KeyListener {

    private BufferedImage buffer;
    int sizeDelCubo = 50;
    int cuboPosX = 200;
    int cuboPosY = 200;
    char ejeRotacion;
    // double anguloRotacion = 100;
    // double anguloRotacion = 0;
    double anguloRotacion = 0;
    

    public RotacionCubo() {
        super("Rotacion del cubo");
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

        
        List<double[]> verticesRotados = aplicarRotacion(verticesXCubo, verticesYCubo, verticesZCubo, anguloRotacion, ejeRotacion);
        double[] nuevosVerticesXCubo = verticesRotados.get(0);
        double[] nuevosVerticesYCubo = verticesRotados.get(1);
        double[] nuevosVerticesZCubo = verticesRotados.get(2);
        
        // Proyección paralela
        double scaleFactor = 0.5; // Escala de proyección
        double[] verticeX = new double[8];
        double[] verticeY = new double[8];

        for (int i = 0; i < 8; i++) {
            verticeX[i] = nuevosVerticesXCubo[i] + scaleFactor * nuevosVerticesZCubo[i];
            verticeY[i] = nuevosVerticesYCubo[i] + scaleFactor * nuevosVerticesZCubo[i];
            // System.out.println(verticeX[i] + ", " + verticeY[i]);
        }
        
        dibujarCubo(g, verticeX, verticeY);
        
    }

    private void drawLine(Graphics g, double x1, double y1, double x2, double y2, Color c) {
        double dx = Math.abs(x2 - x1);
        double dy = Math.abs(y2 - y1);
        double sx = x1 < x2 ? 1 : -1;
        double sy = y1 < y2 ? 1 : -1;
        double err = dx - dy;

    

        while (true) {

            putPixel(g, x1, y1, c);

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
            
            drawLine(g, Math.round(verticesX[i]), Math.round(verticesY[i]), Math.round(verticesX[(i + 1) % 4]), Math.round(verticesY[(i + 1) % 4]), Color.BLUE);
            drawLine(g, Math.round(verticesX[i + 4]), Math.round(verticesY[i + 4]), Math.round(verticesX[((i + 1) % 4) + 4]), Math.round(verticesY[((i + 1) % 4) + 4]), Color.BLACK);
            drawLine(g, Math.round(verticesX[i]), Math.round(verticesY[i]), Math.round(verticesX[i + 4]), Math.round(verticesY[i + 4]), Color.RED);
        }
    }

    // Método para aplicar rotaciones a los vertices

    /**
     * Función para rotar cubo dadas las coordenadas de sus angulos
     * 
     * @param verticesX Lista de vertices en X a los que se les aplicará rotación
     * @param verticesY Lista de vertices en Y a los que se les aplicará rotación
     * @param verticesZ Lista de vertices en Z a los que se les aplicará rotación
     * @param angulo Angulo de rotación 
     * @param eje Eje sobre el que se rotará al cubo
     * 
     * @return Lista de los arreglos que contienen las nuevas coordenadas X, Y y Z después de aplicar rotación
     */
    public List<double[]> aplicarRotacion(double[] verticesX, double[] verticesY, double[] verticesZ, double angulo, char eje) {


        List<double[]> arreglosDeVertices = new ArrayList<>();

        double[] verticesRotadosX = new double[8];
        double[] verticesRotadosY = new double[8];
        double[] verticesRotadosZ = new double[8];

        double anguloRadianes = Math.toRadians(angulo);

        switch (eje) {
            case 'x':

                for (int i = 0; i < 8; i++) {
                    verticesRotadosX[i] = verticesX[i];
                    verticesRotadosY[i] = ( verticesY[i] * Math.cos(anguloRadianes) ) - ( verticesZ[i] * Math.sin(anguloRadianes));
                    verticesRotadosZ[i] = ( verticesY[i] * Math.sin(anguloRadianes) ) + ( verticesZ[i] * Math.cos(anguloRadianes));
                }
                
                break;

            case 'y':

                for (int i = 0; i < 8; i++) {
                    verticesRotadosX[i] = ( verticesX[i] * Math.cos(anguloRadianes) ) + ( verticesZ[i] * Math.sin(anguloRadianes));
                    verticesRotadosY[i] = verticesY[i];
                    verticesRotadosZ[i] = ( (-1 * verticesX[i]) * Math.sin(anguloRadianes) ) + ( verticesZ[i] * Math.cos(anguloRadianes));
                }

                break;
            case 'z':
                for (int i = 0; i < 8; i++) {
                    verticesRotadosX[i] = ( verticesX[i] * Math.cos(anguloRadianes) ) - ( verticesY[i] * Math.sin(anguloRadianes));
                    verticesRotadosY[i] = ( verticesX[i] * Math.sin(anguloRadianes) ) + ( verticesY[i] * Math.cos(anguloRadianes));
                    verticesRotadosZ[i] = verticesZ[i];
                }
                break;
        
            default:
                break;
        }

        arreglosDeVertices.add(verticesRotadosX);
        arreglosDeVertices.add(verticesRotadosY);
        arreglosDeVertices.add(verticesRotadosZ);
        return arreglosDeVertices;
    }
    

    public static void main(String[] args) {

        RotacionCubo ventana = new RotacionCubo();
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
                ejeRotacion = 'x';
                anguloRotacion += 2;
                
                break;
            case KeyEvent.VK_DOWN:
                ejeRotacion = 'x';
                anguloRotacion -= 2;
                break;

            case KeyEvent.VK_RIGHT:
                ejeRotacion = 'y';
                anguloRotacion += 2;
                
                break;
            case KeyEvent.VK_LEFT:
                ejeRotacion = 'y';
                anguloRotacion -= 2;
                break;
            
            case KeyEvent.VK_Z:
                ejeRotacion = 'z';
                anguloRotacion += 2;
                break;
            case KeyEvent.VK_X:
                ejeRotacion = 'z';
                anguloRotacion -= 2;
                break;
        }

        repaint();
    }


    @Override
    public void keyReleased(KeyEvent e) {
        
    }

}
