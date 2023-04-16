
import java.awt.*;
import javax.swing.*;
import java.util.Calendar;

import java.io.*;
import javax.sound.sampled.*;

/**
 * PEDRO ALBERTO VALLIN DIAZ
 * 20310071
 */

public class ProyectoRelojAnalogico extends JPanel implements Runnable {

    private Thread thread;
    private boolean isRunning;

    private Calendar calendar;

    private Image buffer;
    private Graphics2D g2dBuffer;

    private int ancho;
    private int alto;
    private int centroEnX;
    private int centroEnY;
    private int manecillaSegundos;
    private int manecillaMinutos;
    private int manecillaHoras;

    private double manecillaSegundosAngulo;
    private double manecillaMinutosAngulo;
    private double manecillaHorasAngulo;

    private Clip tickClip;

    public ProyectoRelojAnalogico() {
        setPreferredSize(new Dimension(400, 400));
        setLayout(null);

        // Agregar el panel del reloj primero
        JPanel relojPanel = new JPanel();
        relojPanel.setBounds(0, 0, 400, 400);
        relojPanel.setOpaque(false);
        add(relojPanel);

        // Agregar la imagen de fondo después del panel del reloj
        JLabel backgroundLabel = new JLabel(new ImageIcon("relojSinAgujas.png"));
        backgroundLabel.setBounds(0, 0, 400, 400);
        relojPanel.add(backgroundLabel);

        calendar = Calendar.getInstance();

        try {
            // Cargar el archivo de sonido
            AudioInputStream tickStream = AudioSystem.getAudioInputStream(new File("sonido.wav"));
            tickClip = AudioSystem.getClip();
            tickClip.open(tickStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        isRunning = true;
        thread = new Thread(this);
        thread.start();
        
    }

    public void run() {
        while (isRunning) {
            calendar = Calendar.getInstance();
            updateHands();
            repaint();
            try {
                // Reproducir el sonido cada segundo
                Thread.sleep(1000);
                tickClip.setFramePosition(0);
                tickClip.start();
            } catch (InterruptedException ex) {
            }
        }
    }

    public void updateHands() {
        int seconds = calendar.get(Calendar.SECOND);
        int minutes = calendar.get(Calendar.MINUTE);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);

        manecillaHorasAngulo = (hours % 12 + minutes / 60.0) * 30.0;
        manecillaMinutosAngulo = minutes * 6.0;
        manecillaSegundosAngulo = seconds * 6.0;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (buffer == null) {
            buffer = createImage(getWidth(), getHeight());
            g2dBuffer = (Graphics2D) buffer.getGraphics();
        }

        ancho = getWidth();
        alto = getHeight();
        centroEnX = ancho / 2;
        centroEnY = alto / 2;

        // Establecer el largo de las manecillas
        manecillaSegundos = (int) (Math.min(ancho, alto) * 0.45);
        manecillaMinutos = (int) (Math.min(ancho, alto) * 0.4);
        manecillaHoras = (int) (Math.min(ancho, alto) * 0.3);

        g2dBuffer.setColor(getBackground());
        g2dBuffer.fillRect(0, 0, getWidth(), getHeight());

        g2dBuffer.setColor(Color.BLACK);
        g2dBuffer.setStroke(new BasicStroke(2));

        g2dBuffer.drawOval(centroEnX - manecillaSegundos, centroEnY - manecillaSegundos, manecillaSegundos * 2, manecillaSegundos * 2);

        drawHand(g2dBuffer, centroEnX, centroEnY, manecillaHorasAngulo, manecillaHoras, 8);
        drawHand(g2dBuffer, centroEnX, centroEnY, manecillaMinutosAngulo, manecillaMinutos, 5);
        drawHand(g2dBuffer, centroEnX, centroEnY, manecillaSegundosAngulo, manecillaSegundos, 2);

        g.drawImage(buffer, 0, 0, null);
    }

    public void drawHand(Graphics2D g2d, int x, int y, double angle, int length, int ancho) {
        double radians = Math.toRadians(angle - 90);
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);
        int x1 = (int) (x + cos * length);
        int y1 = (int) (y + sin * length);
        g2d.setStroke(new BasicStroke(ancho));
        g2d.drawLine(x, y, x1, y1);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Reloj Analógico");

        // Crear panel del reloj y añadirlo al frame
        ProyectoRelojAnalogico relojPanel = new ProyectoRelojAnalogico();
        frame.setContentPane(relojPanel);

        // Configurar el frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Crear hilo y comenzar a ejecutarlo
        Thread hilo = new Thread(relojPanel);
        hilo.start();
    }
}

