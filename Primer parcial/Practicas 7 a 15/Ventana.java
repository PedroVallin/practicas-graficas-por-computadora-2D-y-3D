import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*
 *  Pedro Alberto Vallín Díaz
 *  20310071
 */

public class Ventana {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Mi ventana");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Agregar un WindowAdapter para cerrar el programa al cerrar la ventana
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println("Programa terminado");
                System.exit(0);
            }
        });
        
        frame.setSize(300, 200);
        frame.setVisible(true);
    }
}
