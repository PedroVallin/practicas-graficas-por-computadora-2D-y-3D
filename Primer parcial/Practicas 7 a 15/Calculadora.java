import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/*
 * PEDRO ALBERTO VALLIN DIAZ 20310071
 */

public class Calculadora extends JFrame implements ActionListener{
    
    private TextField pantalla;
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnSuma, btnResta, btnMultiplicacion, btnDivision, btnIgual, btnPunto, btnBorrar;

    private double operando1, operando2, resultado;
    private char operador;
    
    public Calculadora() {
        super("Calculadora");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        pantalla = new TextField("0", 20);
        
       
        
        btn1 = new Button("1");
        btn2 = new Button("2");
        btn3 = new Button("3");
        btn4 = new Button("4");
        btn5 = new Button("5");
        btn6 = new Button("6");
        btn7 = new Button("7");
        btn8 = new Button("8");
        btn9 = new Button("9");
        btn0 = new Button("0");
        btnSuma = new Button("+");
        btnResta = new Button("-");
        btnMultiplicacion = new Button("*");
        btnDivision = new Button("/");
        btnIgual = new Button("=");
        btnPunto = new Button(".");
        btnBorrar = new Button("C");
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 4;
        panel.add(pantalla, c);
        c.gridwidth = 1;
        c.gridy = 1;
        panel.add(btnBorrar, c);
        c.gridx = 1;
        panel.add(btnDivision, c);
        c.gridx = 2;
        panel.add(btnMultiplicacion, c);
        c.gridx = 3;
        panel.add(btnResta, c);
        c.gridy = 2;
        c.gridx = 0;
        panel.add(btn7, c);
        c.gridx = 1;
        panel.add(btn8, c);
        c.gridx = 2;
        panel.add(btn9, c);
        c.gridx = 3;
        c.gridheight = 2;
        panel.add(btnSuma, c);
        c.gridheight = 1;
        c.gridy = 3;
        c.gridx = 0;
        panel.add(btn4, c);
        c.gridx = 1;
        panel.add(btn5, c);
        c.gridx = 2;
        panel.add(btn6, c);
        c.gridx = 0;
        c.gridy = 4;
        panel.add(btn1, c);
        // c.gridy = 4;
        c.gridx = 1;
        panel.add(btn2, c);
        c.gridx = 2;
        panel.add(btn3, c);
        // c.gridy = 5;
        c.gridx = 3;
        c.gridheight = 2;
        panel.add(btnIgual, c);
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        panel.add(btn0, c);
        c.gridheight = 1;
        c.gridx = 2;
        c.gridy = 5;
        c.gridwidth = 1;
        panel.add(btnPunto, c);


        // Eventos de botones numericos
        btn1.addActionListener(this);
        btn2.addActionListener(this);
        btn3.addActionListener(this);
        btn4.addActionListener(this); 
        btn5.addActionListener(this);
        btn6.addActionListener(this);
        btn7.addActionListener(this);
        btn8.addActionListener(this);
        btn9.addActionListener(this);
        btn0.addActionListener(this);
        btnSuma.addActionListener(this);
        btnResta.addActionListener(this);
        btnMultiplicacion.addActionListener(this);
        btnDivision.addActionListener(this);
        btnIgual.addActionListener(this);
        btnPunto.addActionListener(this);
        btnBorrar.addActionListener(this);
        
        
        add(panel);
        pack();
        setLocationRelativeTo(null);
        setSize(200, 230);
        setResizable(false);
        setVisible(true);
    }

    // Metodo que se ejecuta al hacer clic en cada botón numerico
    public void actionPerformed(ActionEvent e) {
        
        String boton = ((Button) e.getSource()).getLabel();
        

        switch (boton) {

            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                imprimirDigito(boton);
                break;
            case ".":
                agregarPunto();
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                establecerOperador(boton.charAt(0));
            break;

            case "=":
                calcularResultado();
            break;

            case "C":
                limpiarPantalla();
            break;
        
            default:
                break;
        }

    }

    private void imprimirDigito(String digito) {
        System.out.println(digito);
        if (pantalla.getText().equals("0")) {
            pantalla.setText(digito);
        } else {
            pantalla.setText(pantalla.getText() + digito);
        }
    }

    private void agregarPunto() {
        if (!pantalla.getText().contains(".")) {
            pantalla.setText(pantalla.getText() + ".");
        }
    }

    private void establecerOperador(char operador) {
        this.operador = operador;
        operando1 = Double.parseDouble(pantalla.getText());
        pantalla.setText("0");
    }

    private void calcularResultado() {
        operando2 = Double.parseDouble(pantalla.getText());
        switch (operador) {
            case '+':
                resultado = operando1 + operando2;
                break;
            case '-':
                resultado = operando1 - operando2;
                break;
            case '*':
                resultado = operando1 * operando2;
                break;
            case '/':
                resultado = operando1 / operando2;
                break;
        }
        pantalla.setText(Double.toString(resultado));
    }

    private void limpiarPantalla(){
        pantalla.setText("0");
    }
    
    public static void main(String[] args) {
        new Calculadora();
    }
}
