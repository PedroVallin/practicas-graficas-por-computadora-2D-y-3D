public class Hex2IPConvertidora {
    
    private static final int BYTE_MASK = 0xFF;
    
    private static final int NUM_BYTES = 4;
    
    private String parametro;
    private String direccion;
    
    public Hex2IPConvertidora(String parametro, String direccion) {
        this.parametro = parametro;
        this.direccion = direccion;
    }
    
    public String convertirHex2IP() {

        try {
            // Convertir la cadena hexadecimal a un arreglo de bytes
            byte[] bytes = new byte[NUM_BYTES];
            for (int i = 0; i < NUM_BYTES; i++) {
                int inicio = i * 2; // 4
                int fin = inicio + 2; // 6
                String byteHex = direccion.substring(inicio, fin);
                bytes[i] = (byte) Integer.parseInt(byteHex, 16);
            }
            
            // Construir la dirección IP en formato texto
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < NUM_BYTES; i++) {
                sb.append(bytes[i] & BYTE_MASK);
                if (i < NUM_BYTES - 1) {
                    sb.append(".");
                }
            }
            return sb.toString();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return "Error";
        }

        
    }
    
    public String convertirIP2Hex() {

        try {
            // Convertir la dirección IP a un arreglo de bytes
            String[] bytesTexto = direccion.split("\\.");
            byte[] bytes = new byte[NUM_BYTES];
            for (int i = 0; i < NUM_BYTES; i++) {
                bytes[i] = (byte) Integer.parseInt(bytesTexto[i]);
            }
            
            // Construir la cadena hexadecimal
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < NUM_BYTES; i++) {
                sb.append(String.format("%02X", bytes[i] & BYTE_MASK));
            }
            return sb.toString();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return "Error";
        }

        
    }
    
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Debe proporcionar el parámetro (-hex o -ip) y la dirección en formato hexadecimal o IP");
            System.exit(1);
        }
        
        String parametro = args[0];
        String direccion = args[1];
        
        Hex2IPConvertidora convertidora = new Hex2IPConvertidora(parametro, direccion);
        
        if ("-hex".equals(parametro)) {
            String direccionIP = convertidora.convertirHex2IP();
            System.out.println(direccionIP);
        } else if ("-ip".equals(parametro)) {
            String direccionHex = convertidora.convertirIP2Hex();
            System.out.println(direccionHex);
        } else {
            System.err.println("El primer parámetro debe ser -hex o -ip");
            System.exit(1);
        }
    }
}
