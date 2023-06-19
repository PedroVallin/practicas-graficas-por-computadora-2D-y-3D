import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProyectoTercerParcial extends JFrame implements KeyListener {
    private static final int WIDTH = 800; // Ancho de la ventana
    private static final int HEIGHT = 800; // Alto de la ventana
    private static int escala = 40; // Escala de la superficie

    private char rotationAngleSelected = 'X'; // Angulo selecionado para la rotacion
    private int traslationX, traslationY, traslationZ = 0;
    private Boolean traslationMode = false;
    private Boolean superficieMode = false;

    private double rotationAngle; // Ángulo de rotación en radianes

    private double x, y, z;
    private double x1, y1, x2, y2;
    Vector3D v1, v2;

    private List<Vector3D> vertices;

    private BufferedImage buffer;

    // TODO: Doble buffereo
    private BufferedImage buffer2;
    private Graphics2D g2d;

    public ProyectoTercerParcial() {
        addKeyListener(this);
        setFocusable(true);

        setTitle("Proyecto 3er Parcial");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        vertices = new ArrayList<>();

        // loadObjFile("BASEmodel.obj");
        loadObjFile("AirbusA310.obj");

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

        // TODO: Doble buffereo
        buffer2 = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        g2d = buffer2.createGraphics();
    }

    private void loadObjFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("v ")) {
                    String[] parts = line.split("\\s+");
                    double x = Double.parseDouble(parts[1]);
                    double y = Double.parseDouble(parts[2]);
                    double z = Double.parseDouble(parts[3]);
                    vertices.add(new Vector3D(x, y, z));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void putPixel(double x, double y, Color color) {
        // Implementa tu lógica para dibujar un píxel en la ventana
        buffer.setRGB(0, 0, color.getRGB());
        // this.getGraphics().drawImage(buffer, (int) Math.round(x), (int) Math.round(y), this);
        // TODO: Doble buffereo
        g2d.drawImage(buffer, (int) Math.round(x), (int) Math.round(y), this);
    }

    

    private void dibujarObjeto() {
        // Renderiza el objeto en la ventana

        // Aplica transformaciones de rotación en el eje Y
        List<Vector3D> transformedVertices = new ArrayList<>();
        for (Vector3D vertex : vertices) {

            switch (rotationAngleSelected) {
                case 'X':
                    // Rotación en X
                    x = (vertex.getX() + traslationX);
                    y = (vertex.getY() + traslationY) * Math.cos(rotationAngle) + vertex.getZ() * Math.sin(rotationAngle);
                    z = (-vertex.getY() + traslationZ) * Math.sin(rotationAngle) + vertex.getZ() * Math.cos(rotationAngle);
                    transformedVertices.add(new Vector3D(x, y, z));
                    break;
                case 'Y':
                    // Rotación en Y
                    x = (vertex.getX() + traslationX) * Math.cos(rotationAngle) + vertex.getZ() * Math.sin(rotationAngle);
                    y = (vertex.getY() + traslationY);
                    z = (-vertex.getX() + traslationZ) * Math.sin(rotationAngle) + vertex.getZ() * Math.cos(rotationAngle);
                    transformedVertices.add(new Vector3D(x, y, z));
                    break;
                case 'Z':
                    // Rotación en Z
                    x = (vertex.getX() + traslationX) * Math.cos(rotationAngle) - vertex.getY() * Math.sin(rotationAngle);
                    y = (vertex.getX() + traslationY) * Math.sin(rotationAngle) + vertex.getY() * Math.cos(rotationAngle);
                    z = (vertex.getZ() + traslationZ);
                    transformedVertices.add(new Vector3D(x, y, z));
                    break;
                default:
                    break;
            }

            // Rotación en Y
            // double x = vertex.getX();
            // double y = vertex.getY() * Math.cos(rotationAngle) - vertex.getZ() * Math.sin(rotationAngle);
            // double z = vertex.getY() * Math.sin(rotationAngle) + vertex.getZ() * Math.cos(rotationAngle);
            // transformedVertices.add(new Vector3D(x, y, z));

        }

        // Aplica proyección perspectiva y dibuja las líneas
        for (int i = 0; i < transformedVertices.size() - 1; i++) {
            v1 = transformedVertices.get(i);
            v2 = transformedVertices.get(i + 1);

            x1 = v1.getX() * escala + WIDTH / 2 + traslationX;
            y1 = v1.getY() * escala + HEIGHT / 2 + traslationY;
            x2 = v2.getX() * escala + WIDTH / 2 + traslationX;
            y2 = v2.getY() * escala + HEIGHT / 2 + traslationY;

            
            
            if (!superficieMode) {
                putPixel(x1, y1, Color.BLUE);
                // putPixel(x2, y2, Color.BLUE);
            } else {
                drawLine(Math.round(x1), Math.round(y1), Math.round(x2), Math.round(y2), Color.BLUE);
            }
        }
    }

    private void drawLine(double x1, double y1, double x2, double y2, Color c) {
        double dx = Math.abs(x2 - x1);
        double dy = Math.abs(y2 - y1);
        double sx = x1 < x2 ? 1 : -1;
        double sy = y1 < y2 ? 1 : -1;
        double err = dx - dy;

        while (true) {
            putPixel(x1, y1, c);

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

    @Override
    public void paint(Graphics g) {
        // super.paint(g);
        // TODO: Doble buffereo
        super.repaint();

        dibujarObjeto();

        // TODO: Doble buffereo
        g.drawImage(buffer2, 0, 0, null);
        g2d.clearRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        if (traslationMode) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                traslationX ++;
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                traslationX --;
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                traslationY --;
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                traslationY ++;
            }
        } else {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                rotationAngle -= Math.toRadians(5);
                repaint();
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rotationAngle += Math.toRadians(5);
                repaint();
            } else if (e.getKeyCode() == KeyEvent.VK_UP ) {
                escala ++;
                repaint();
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN ) {
                escala --;
                repaint();
            }
        }

        if ( e.getKeyCode() == KeyEvent.VK_X ) {
            rotationAngleSelected = 'X';
        } else if ( e.getKeyCode() == KeyEvent.VK_Y ) {
            rotationAngleSelected = 'Y';
        } else if ( e.getKeyCode() == KeyEvent.VK_Z ) {
            rotationAngleSelected = 'Z';
        } else if ( e.getKeyCode() == KeyEvent.VK_T ) {
            if (traslationMode) {
                traslationMode = false;
            } else {
                traslationMode = true;
            }
            System.out.println("Modo traslacion: " + traslationMode);
        } else if ( e.getKeyCode() == KeyEvent.VK_M ) {
            if (superficieMode) {
                superficieMode = false;
            } else {
                superficieMode = true;
            }
            System.out.println("Superficie en malla: " + superficieMode);
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        ProyectoTercerParcial viewer = new ProyectoTercerParcial();
        viewer.setVisible(true);
    }
}

class Vector3D {
    private double x;
    private double y;
    private double z;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
