
import java.util.Arrays;

public class OrdenarListaDeNumeros_20310071 {
    public static void main(String[] args) {
        
        int[] numeros = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            numeros[i] = Integer.parseInt(args[i]);
        }

        Arrays.sort(numeros);

        System.out.println("Numeros ordenados:");

        for (int numero : numeros) {
            System.out.println(numero);
        }

    }
}
