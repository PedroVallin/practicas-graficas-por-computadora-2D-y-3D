
public class GeneracionDeSubcadenas_20310071 {
    public static void main(String[] args) {
        // Obtener la cadena de texto de los argumentos de la línea de comandos
        String texto = args[0];

        System.out.println(texto.substring(0, texto.length()));
        
        // Imprimir todas las subcadenas posibles
        for (int i = texto.length(); i > 0; i--) {
            System.out.println(texto.substring(0, i));
        }
        for (int i = 1; i <= texto.length(); i++) {
            System.out.println(texto.substring(0, i));
        }
    }
}
