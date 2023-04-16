import javax.swing.*;
import java.awt.*;


/**
 * Pedro Alberto Vallín Díaz  20310071
 */


public class DiagramaPastel extends JPanel {
    
    private int[] valores;
    private Color[] colores = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.MAGENTA, Color.CYAN, Color.PINK};
    
    public DiagramaPastel(int[] valores) {
        this.valores = valores;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int total = 0;
        for (int i = 0; i < valores.length; i++) {
            total += valores[i];
        }
        
        int anguloInicio = 0;
        for (int i = 0; i < valores.length; i++) {
            int angulo = (int) Math.round(360.0 * valores[i] / total);
            g.setColor(colores[i]);
            g.fillArc(50, 50, 200, 200, anguloInicio, angulo);
            anguloInicio += angulo;
        }
    }
    
    public static void main(String[] args) {
        int[] valores = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            valores[i] = Integer.parseInt(args[i]);
        }
        
        JFrame frame = new JFrame("Diagrama de pastel");
        DiagramaPastel diagrama = new DiagramaPastel(valores);
        frame.add(diagrama);
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
