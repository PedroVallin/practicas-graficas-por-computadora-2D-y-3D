
import java.util.Random;

public class DosNumerosAleatorios_20310071 {
    public static void main(String[] args) {
        Random random = new Random();
        
        // Generar dos números aleatorios
        int num1 = random.nextInt(100);
        int num2 = random.nextInt(100);
        
        // Determinar cuál número es mayor
        if (num1 > num2) {
            System.out.println(num1 + " es mayor que " + num2);
        } else if (num1 < num2) {
            System.out.println(num2 + " es mayor que " + num1);
        } else {
            System.out.println(num1 + " y " + num2 + " son iguales");
        }
    }
}
