import java.awt.*;
import javax.swing.*;
import java.util.Calendar;

public class RelojAnalogico extends JPanel implements Runnable {

    private Thread thread;
    private boolean isRunning;

    private Calendar calendar;

    private Image buffer;
    private Graphics2D g2dBuffer;

    private int ancho;
    private int alto;
    private int centroEnX;
    private int centroEnY;
    private int secondHandLength;
    private int minuteHandLength;
    private int hourHandLength;

    private double hourHandAngle;
    private double minuteHandAngle;
    private double secondHandAngle;

    public RelojAnalogico() {
        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.WHITE);

        calendar = Calendar.getInstance();

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
                Thread.sleep(1000);
            } catch (InterruptedException ex) {}
        }
    }

    public void updateHands() {
        int seconds = calendar.get(Calendar.SECOND);
        int minutes = calendar.get(Calendar.MINUTE);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);

        hourHandAngle = (hours % 12 + minutes / 60.0) * 30.0;
        minuteHandAngle = minutes * 6.0;
        secondHandAngle = seconds * 6.0;
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
        secondHandLength = (int) (Math.min(ancho, alto) * 0.45);
        minuteHandLength = (int) (Math.min(ancho, alto) * 0.4);
        hourHandLength = (int) (Math.min(ancho, alto) * 0.3);

        g2dBuffer.setColor(getBackground());
        g2dBuffer.fillRect(0, 0, getWidth(), getHeight());

        g2dBuffer.setColor(Color.BLACK);
        g2dBuffer.setStroke(new BasicStroke(2));

        g2dBuffer.drawOval(centroEnX - secondHandLength, centroEnY - secondHandLength, secondHandLength * 2, secondHandLength * 2);

        drawHand(g2dBuffer, centroEnX, centroEnY, hourHandAngle, hourHandLength, 8);
        drawHand(g2dBuffer, centroEnX, centroEnY, minuteHandAngle, minuteHandLength, 5);
        drawHand(g2dBuffer, centroEnX, centroEnY, secondHandAngle, secondHandLength, 2);

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
        RelojAnalogico relojPanel = new RelojAnalogico();
        frame.add(relojPanel);
    
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


// -------------------- PRUEBA ----------------------

// import javax.swing.*;
// import java.awt.*;
// import java.util.Calendar;

// public class ProyectoRelojAnalogico extends JFrame implements Runnable {

// // Hilo
// private Thread thr;

// private Image fondo;
// private Image buffer;

// public ProyectoRelojAnalogico() {
// super("Reloj");

// setResizable(false);
// setSize(200, 200);
// setVisible(true);

// thr = new Thread(this);
// thr.start();

// }

// public void run() {
// while(true) {
// try {
// thr.sleep(1000);
// } catch (InterruptedException ex) {
// }
// repaint();
// }
// }

// public void paint (Graphics g) {

// if ( fondo == null ) {
// fondo = createImage(getWidth(), getHeight());
// // Pintar círculo del reloj
// Graphics gfondo = fondo.getGraphics();
// gfondo.setClip(0, 0, getWidth(), getHeight());
// gfondo.drawOval((getWidth() - 100) / 2, (getHeight() - 100) / 2, 100, 100);
// }

// update(g);
// }

// public void update(Graphics g) {
// g.setClip(0, 0, getWidth(), getHeight());
// Calendar cal = Calendar.getInstance();
// int hora;
// int min = 0;
// int sec;
// if ( cal.get(Calendar.MINUTE) != min ) {
// // Regenerar la imagen de fondo
// hora = cal.get(Calendar.HOUR);
// min = cal.get(Calendar.MINUTE);
// // Crear al imagen estática
// buffer = createImage(getWidth(), getHeight());
// Graphics gbuffer = buffer.getGraphics();
// gbuffer.setClip(0, 0, getWidth(), getHeight());
// gbuffer.drawImage(fondo, 0, 0, this);
// gbuffer.fillArc((getWidth() - 90) / 2 + 5, (getHeight() - 90) / 2 + 5, 80,
// 80, angulo12(hora), 3);
// gbuffer.fillArc((getWidth() - 100) / 2 + 5, (getHeight() - 100) / 2 + 5, 90,
// 90, angulo60(min), 3);
// }

// // Pintar buffer
// g.drawImage(buffer, 0, 0, this);
// sec = cal.get(Calendar.SECOND);
// // Pintar ente móvil
// g.fillArc((getWidth() - 100) / 2 + 5, (getHeight() - 100) / 2 + 5, 90, 90,
// angulo60(sec), 3 );
// }

// private int angulo60(int min) {
// return (int) ((60 - min) * 6.0);
// }

// private int angulo12(int hora) {
// return (int) (hora * 30.0 + 0.5 * angulo60(hora));
// }

// public static void main(String[] args) {
// new ProyectoRelojAnalogico();
// }
// }


