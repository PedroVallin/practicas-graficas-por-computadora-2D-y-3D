import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;

/**
 * Pedro Alberto Vallín Díaz 20310071
 */

public class EventosMouse implements MouseListener {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Eventos del mouse");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.addMouseListener(new EventosMouse());
        frame.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Click en x=" + e.getX() + ", y=" + e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Evento mousePressed en x=" + e.getX() + ", y=" + e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Evento mouseReleased en x=" + e.getX() + ", y=" + e.getY());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Evento mouseEntered en x=" + e.getX() + ", y=" + e.getY());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Evento mouseExited en x=" + e.getX() + ", y=" + e.getY());
    }
}

