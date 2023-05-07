

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;


public class ProyectoSegundoParcial extends JFrame implements KeyListener{

    // private final Color COLOR_GUSANITO = new Color(27, 125, 2);
    private final Color COLOR_GUSANITO = Color.YELLOW;
    private int direccion;
    private int coordCabezaX, 
                coordCabezaY, 
                posXLineaPeligrosa,
                posXObjetivo = 200,
                posYObjetivo = 200;

    private boolean gusanitoVivo;
    private boolean gusanitoEnZonaSegura;
    private boolean llenarRectangulos;

    // Buffers
    private BufferedImage buffer;
    private BufferedImage buffer2;
    private Graphics2D g2d;

    
    
    
    public ProyectoSegundoParcial() {
        super("El juego del gusanito");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        buffer2 = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        g2d = buffer2.createGraphics();

        
        addKeyListener(this);

        iniciarJuego();
    }

    // public void putPixel(int x, int y, Color c) {
    //     buffer.setRGB(0, 0, c.getRGB());
    //     this.getGraphics().drawImage(buffer, x, y, this);
    // }
    public void putPixel(Graphics2D g2d, int x, int y, Color c) {
        buffer.setRGB(0, 0, c.getRGB());
        g2d.drawImage(buffer, x, y, this);

    }

    public void iniciarJuego() {
        // Iniciar variables
        coordCabezaX = 120;
        coordCabezaY = 200;
        gusanitoVivo = true;
        gusanitoEnZonaSegura = false;
        posXLineaPeligrosa = 0;
        llenarRectangulos = false;
        direccion = KeyEvent.VK_RIGHT; // Iniciar hacia la derecha
        // Iniciar loop hilo del gusanito
        new Thread(() -> {
            while (gusanitoVivo) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
                movimiento();
                
            }
        }).start();

        // Iniciar loop hilo de la linea peligrosa
        new Thread(() -> {
            while (gusanitoVivo) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
                movimientoLineaPeligrosa();
                
            }
        }).start();
    }

    public void movimientoLineaPeligrosa() {
        if ( posXLineaPeligrosa <= 400 ) {
            posXLineaPeligrosa ++;
        } else {
            posXLineaPeligrosa = 0;
        }

        // Colision con limites de la ventana
        if (coordCabezaX < 0 || coordCabezaX >= 400 || coordCabezaY < 0 || coordCabezaY >= 400) {
            gusanitoVivo = false;   
        }

        // Colision con linea peligrosa
        if (coordCabezaX == posXLineaPeligrosa && !(gusanitoEnZonaSegura)) {
            gusanitoVivo = false;
        }

        // Logica de zona segura
        if ( ((coordCabezaX < 100 || coordCabezaX > 150) || (coordCabezaY < 100 || coordCabezaY > 150)) 
            && ((coordCabezaX < 200 || coordCabezaX > 250) || (coordCabezaY < 130 || coordCabezaY > 180))
            && ((coordCabezaX < 50 || coordCabezaX > 130) || (coordCabezaY < 240 || coordCabezaY > 350))
            && ((coordCabezaX < 250 || coordCabezaX > 330) || (coordCabezaY < 240 || coordCabezaY > 350)) ) {
                gusanitoEnZonaSegura = false;
        } else {
            gusanitoEnZonaSegura = true;
        }
    }

    public void movimiento() {
        switch (direccion) {
            case KeyEvent.VK_LEFT:
                coordCabezaX --;
                break;
            case KeyEvent.VK_RIGHT:
                coordCabezaX ++;
                break;
            case KeyEvent.VK_UP:
                coordCabezaY --;
                break;
            case KeyEvent.VK_DOWN:
                coordCabezaY ++;
                break;
        }
        

    }

    /**
     * Método para dibujar una linea recta usando el algoritmo de Bresenham
     * 
     * @param x1 Coordenada inicial en X
     * @param y1 Coordenada inicial en Y
     * @param x2 Coordenada final en X
     * @param y2 Coordenada final en Y
     * @param c Color del pixel
     */
    public void dibujarLinea(Graphics2D g2d, int x1, int y1, int x2, int y2, Color c) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;
    
        while (x1 != x2 || y1 != y2) {
            putPixel(g2d, x1, y1, c);
                
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    public void dibujarRefugio(Graphics2D g2d, int x1, int y1, int x2, int y2, Color c) {
        dibujarLinea(g2d, x1, y1, x2, y1, c);
        dibujarLinea(g2d, x1 + 10, y1 + 10, x2, y1 + 10, c);
        dibujarLinea(g2d, x1, y2, x2, y2, c);
        dibujarLinea(g2d, x1 + 10, y2 - 10, x2, y2 - 10, c);
        dibujarLinea(g2d, x1, y1, x1, y2, c);
        dibujarLinea(g2d, x1 + 10, y1 + 10, x1 + 10, y2 - 10, c);

        dibujarLinea(g2d, x2, y1, x2, y1 + 11, c);
        dibujarLinea(g2d, x2, y2, x2, y2 - 11, c);

        if (llenarRectangulos) {
            llenarRectangulo(g2d, x1, y1, x2, (y1 + 10), c);
            llenarRectangulo(g2d, x1, (y2 - 10), x2, y2, c);
            llenarRectangulo(g2d, x1, y1, (x1 + 10), y2, c);
        }
    }


    public void dibujarCirculo(Graphics2D g2d, double xc, double yc, double R, Color c) {
        
        double x, y;

        x = xc - R;
        for (double theta = 0; theta < 2*Math.PI; theta += 0.01) {
            x = xc + R*Math.cos(theta);
            y = yc + R*Math.sin(theta);
            putPixel(g2d, (int)x, (int)y, c);
        }   
    }

        
    public void dibujarLineaPeligrosa( Graphics2D g2d, int x1, int y1, int x2, int y2, int limX1, int limY1, int limX2, int limY2, Color c) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;
    
        while (x1 != x2 || y1 != y2) {

            // Recorte por zona segura x1, x2, y1, y2

            if ( ((x1 < 100 || x1 > 150) || (y1 < 100 || y1 > 150)) 
            && ((x1 < 200 || x1 > 250) || (y1 < 130 || y1 > 180))
            && ((x1 < 50 || x1 > 130) || (y1 < 240 || y1 > 350))
            && ((x1 < 250 || x1 > 330) || (y1 < 240 || y1 > 350)) ) {
                putPixel(g2d, x1, y1, c);
                
            }
    
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    public void llenarCirculo(Graphics2D g2d, int xc, int yc, int r, Color color) {
        // Se recorren todas las coordenadas del círculo y se pinta el interior
        for (int x = xc - r; x <= xc + r; x++) {
            for (int y = yc - r; y <= yc + r; y++) {
                // Se comprueba si la coordenada se encuentra dentro del círculo
                if ((x - xc) * (x - xc) + (y - yc) * (y - yc) <= r * r) {
                    putPixel(g2d, x, y, color);
                }
            }
        }
    }

    public void llenarRectangulo( Graphics2D g2d, int x1, int y1, int x2, int y2, Color c) {
        
        int xaux, yaux, xc, yc; // Centro de la figura
        xaux = (x2 - x1) / 2;
        yaux = (y2 - y1) / 2;

        xc = x1 + xaux; // Obtener el valor X central de la figura
        yc = y1 + yaux; // obtener el valor Y central de la figura

        
        for ( int y = yc; y <= y2; y++ ) { // Recorrer en y
            for ( int x = xc; x <= x2; x++) {  // Recorrer en x
                putPixel(g2d, x, y, c);
            }
        }

        for ( int y = yc; y >= y1; y-- ) { // Recorrer en y
            for ( int x = xc; x <= x2; x++) {  // Recorrer en x
                putPixel(g2d, x, y, c);
            }
        }

        for ( int y = yc; y <= y2; y++ ) { // Recorrer en y
            for ( int x = xc; x >= x1; x--) {  // Recorrer en x
                putPixel(g2d, x, y, c);
            }
        }

        for ( int y = yc; y >= y1; y-- ) { // Recorrer en y
            for ( int x = xc; x >= x1; x-- ) {  // Recorrer en x
                putPixel(g2d, x, y, c);
            }
        }

    }
    
    

    @Override
    public void paint(Graphics g) {    

        // super.paint(g);
        super.repaint();

        if ( (direccion == KeyEvent.VK_RIGHT) || (direccion == KeyEvent.VK_LEFT) ) {
            dibujarLinea(g2d, coordCabezaX + 20 , coordCabezaY, coordCabezaX, coordCabezaY, COLOR_GUSANITO);
            
        } else if ( (direccion == KeyEvent.VK_UP) || (direccion == KeyEvent.VK_DOWN) ) {
            dibujarLinea(g2d, coordCabezaX, coordCabezaY + 20, coordCabezaX, coordCabezaY, COLOR_GUSANITO);
        }

  
        // ---- Lineas peligrosas ----
        // dibujarLinea(g2d, posXLineaPeligrosa, 0, posXLineaPeligrosa, 400, Color.RED);
        // Linea peligrosa 1
        dibujarLineaPeligrosa(g2d, posXLineaPeligrosa, 0, posXLineaPeligrosa, 400,100, 100, 150, 150, Color.RED);
        // Refugio 1
        dibujarRefugio(g2d, 100, 100, 150, 150, Color.MAGENTA);
        
        // Refugio 2
        dibujarRefugio(g2d, 200, 130, 250, 180, Color.MAGENTA);
        
        // Refugio 3
        dibujarRefugio(g2d, 50, 240, 130, 350, Color.MAGENTA);

        // Refugio 4
        dibujarRefugio(g2d, 250, 240, 330, 350, Color.MAGENTA);

        // ------ Objetivos -------
        // Objetivo 1
        dibujarCirculo(g2d, posXObjetivo, posYObjetivo, 10, Color.DARK_GRAY);
        llenarCirculo(g2d, posXObjetivo, posYObjetivo, 10, new Color(27, 125, 2));

        g.drawImage(buffer2, 0, 0, null);
        g2d.clearRect(0, 0, getWidth(), getHeight());
        

    }

    public static void main(String[] args) {
        ProyectoSegundoParcial ventana = new ProyectoSegundoParcial();
        ventana.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

      
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_LEFT) ) { 
            direccion = KeyEvent.VK_LEFT;

        }
        
        if ((key == KeyEvent.VK_RIGHT) ) {
            direccion = KeyEvent.VK_RIGHT;
        }

        if ((key == KeyEvent.VK_UP)) {
            direccion = KeyEvent.VK_UP;
        }

        if ((key == KeyEvent.VK_DOWN)) {
            direccion = KeyEvent.VK_DOWN;
        }

        if ((key == KeyEvent.VK_SPACE)) {
            System.out.println("Espacio");
            iniciarJuego();
        }

        if ((key == KeyEvent.VK_R)) {
            if (!(llenarRectangulos)) {
                llenarRectangulos = true;
            } else if (llenarRectangulos) {
                llenarRectangulos = false;
            }
        }
      
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
      
    }
}

