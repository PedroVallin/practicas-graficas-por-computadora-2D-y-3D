import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Pedro Alberto Vallín Díaz  20310071
 */

public class TecladoEnJFrame {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Teclado en JFrame");
        frame.setSize(400, 400);
        frame.setVisible(true);
        frame.addKeyListener(new TecladoListener());
    }

    private static class TecladoListener implements KeyListener {

        public void keyPressed(KeyEvent e) {
            System.out.println("Tecla presionada: " + e.getKeyChar());
        }

        public void keyReleased(KeyEvent e) {
            System.out.println("Tecla liberada: " + e.getKeyChar());
        }

        public void keyTyped(KeyEvent e) {
            System.out.println("Tecla tipeada: " + e.getKeyChar());
        }
    }
}
