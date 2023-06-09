
import java.awt.*;
import javax.swing.*;

/*
 * Pedro Alberto Vallín Díaz 20310071
 */

public class UsoGraficosMonito extends JFrame {

    public UsoGraficosMonito() {
        super("Uso de gráficos");
        setSize(200, 300);
        this.setVisible(true);
        // show();
    }

    public void paint(Graphics g) {
        g.drawString("Demo de gráficos", 10, 50);
        //Dibujar cara
        g.drawArc(50, 60, 50, 50, 0, 360);
        g.drawArc(60, 70, 30, 30, 180, 180);
        g.fillOval(65, 75, 5, 5);
        g.fillOval(80, 75, 5, 5);

        // Dibujar cuerpo
        g.drawLine(75, 110, 75, 200);
        
        // Dibujar brazos
        g.drawLine(75, 120, 45, 160);
        g.drawLine(75, 120, 105, 160);
        // Piernas
        g.drawLine(75, 200, 45, 240);
        g.drawLine(75, 200, 105, 240);

    }

    public static void main(String[] args) {
        new UsoGraficosMonito();
    }

}
