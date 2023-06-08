import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class ProyectoTercerParcial extends JFrame {

    private BufferedImage buffer;
    List<Point3D> vertices;
    List<Face> faces;
    private BufferedImage buffer2;
    Graphics2D g2d;

    public ProyectoTercerParcial() {
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

         // Inicializar las listas de vértices y caras
         vertices = new ArrayList<>();
         faces = new ArrayList<>();
 
         try {
             readOBJFile("Test1.obj");
             readFacesFromOBJ("Test1.obj");
         } catch (IOException e) {
             e.printStackTrace();
             return;
         }

         buffer2 = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
         g2d = buffer2.createGraphics();
        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }

    private void putPixel(Graphics g, double x, double y, Color c) {
        buffer.setRGB(0, 0, c.getRGB());
        g.drawImage(buffer, (int) Math.round(x), (int) Math.round(y), this);
    }

    private void readOBJFile(String filePath) throws IOException {
        // List<Point3D> vertices = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.startsWith("v ")) {
                // Parse vertex coordinates
                String[] parts = line.split(" ");
                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);
                double z = Double.parseDouble(parts[3]);
                vertices.add(new Point3D(x, y, z));
                //TODO: Esto no funciona, hay que buscar la forma de guardar los vertices
            }
        }

        reader.close();
        // return vertices;
    }

    private List<Face> readFacesFromOBJ(String filePath) throws IOException {
        // List<Face> faces = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("f ")) {
                String[] tokens = line.split("\\s+");
                int index1 = Integer.parseInt(tokens[1].split("/")[0]);
                int index2 = Integer.parseInt(tokens[2].split("/")[0]);
                int index3 = Integer.parseInt(tokens[3].split("/")[0]);
                
                Face face = new Face(index1, index2, index3);

                //TODO: Esto no funciona, hay que buscar la forma de guardar las caras
                faces.add(face);
                System.out.println("--" + faces.get(0).getIndex1());
            }
        }

        reader.close();
        return faces;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

               

        System.out.println(vertices.get(1).getX() + " : " + faces.get(1).getIndex1());

        // Iterar sobre las caras y dibujar los triángulos correspondientes
        
        for (Face face : faces) {
            // System.out.println("---->" + (face.getIndex1() - 1));
            Point3D v1 = vertices.get(face.getIndex1() - 1);
            System.out.println(v1.getX());
            Point3D v2 = vertices.get(face.getIndex2() - 1);
            Point3D v3 = vertices.get(face.getIndex3() - 1);
            // System.out.println(vertices.get(face.getIndex1() - 1));

            // Aplicar transformaciones de rotación, traslación, escala, etc., si es necesario

            // Realizar la proyección perspectiva o ortográfica, mapeando las coordenadas 3D a 2D

            // Dibujar el triángulo utilizando el método drawLine() o drawPolygon()
            int[] xPoints = {(int) v1.getX(), (int) v2.getX(), (int) v3.getX()};
            int[] yPoints = {(int) v1.getY(), (int) v2.getY(), (int) v3.getY()};
            // System.out.println(xPoints[0]);
            // g2d.drawPolygon(xPoints, yPoints, 3);
            putPixel(g, v1.getX(), v1.getY(), Color.BLACK);
            putPixel(g, v2.getX(), v2.getY(), Color.BLACK);
            putPixel(g, v3.getX(), v3.getY(), Color.BLACK);
        }
        
    }

    public static void main(String[] args) {
        ProyectoTercerParcial ventana = new ProyectoTercerParcial();
        ventana.setVisible(true);
    }

    public class Point3D {
        private double x;
        private double y;
        private double z;

        public Point3D(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getZ() {
            return z;
        }

        public void setX(double x) {
            this.x = x;
        }

        public void setY(double y) {
            this.y = y;
        }

        public void setZ(double z) {
            this.z = z;
        }
    }

    public static class Face {
        private int index1;
        private int index2;
        private int index3;

        public Face(int index1, int index2, int index3) {
            this.index1 = index1;
            this.index2 = index2;
            this.index3 = index3;
        }

        public int getIndex1() {
            return index1;
        }

        public int getIndex2() {
            return index2;
        }

        public int getIndex3() {
            return index3;
        }
    }

}





// import java.awt.Color;
// import java.awt.Graphics;
// import java.awt.Graphics2D;
// import java.awt.image.BufferedImage;
// import java.io.BufferedReader;
// import java.util.List;
// import java.util.ArrayList;
// import java.io.File;
// import java.io.FileReader;
// import java.io.IOException;

// import javax.swing.JFrame;



// public class ProyectoTercerParcial extends JFrame{


//     private BufferedImage buffer;

    

//     public ProyectoTercerParcial() {
//         setSize(800, 600);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setLocationRelativeTo(null);

//     }

//     private void putPixel(Graphics g, double x, double y, Color c) {
//         buffer.setRGB(0, 0, c.getRGB());
//         this.getGraphics().drawImage(buffer, (int) Math.round(x), (int) Math.round(y), this);
//     }


//     private List<Point3D> readOBJFile(String filePath) throws IOException {
//         List<Point3D> vertices = new ArrayList<>();
    
//         BufferedReader reader = new BufferedReader(new FileReader(filePath));
//         String line;
    
//         while ((line = reader.readLine()) != null) {
//             if (line.startsWith("v ")) {
//                 // Parse vertex coordinates
//                 String[] parts = line.split(" ");
//                 double x = Double.parseDouble(parts[1]);
//                 double y = Double.parseDouble(parts[2]);
//                 double z = Double.parseDouble(parts[3]);
//                 vertices.add(new Point3D(x, y, z));
//             }
//         }
    
//         reader.close();
//         return vertices;
//     }

//     @Override
//     public void paint(Graphics g) {
//         super.paint(g);
        
//         // Obtener los vértices y las caras de la figura desde el archivo OBJ
//         List<Point3D> vertices;
//         List<Face> faces;
//         try {
//             vertices = readOBJFile("Test.obj");
//             faces = readFacesFromOBJ("Test.obj"); // Debes implementar este método para leer las caras
//         } catch (IOException e) {
//             e.printStackTrace();
//             return;
//         }
        
//         // Iterar sobre las caras y dibujar los triángulos correspondientes
//         Graphics2D g2d = (Graphics2D) g;
//         for (Face face : faces) {
//             Point3D v1 = vertices.get(face.getIndex1() - 1);
//             Point3D v2 = vertices.get(face.getIndex2() - 1);
//             Point3D v3 = vertices.get(face.getIndex3() - 1);
            
//             // Aplicar transformaciones de rotación, traslación, escala, etc., si es necesario
            
//             // Realizar la proyección perspectiva o ortográfica, mapeando las coordenadas 3D a 2D
            
//             // Dibujar el triángulo utilizando el método drawLine() o drawPolygon()
//             int[] xPoints = { (int) v1.getX(), (int) v2.getX(), (int) v3.getX() };
//             int[] yPoints = { (int) v1.getY(), (int) v2.getY(), (int) v3.getY() };
//             g2d.drawPolygon(xPoints, yPoints, 3);
//         }
//     }

//     public List<Face> readFacesFromOBJ(String filePath) throws IOException {
//         List<Face> faces = new ArrayList<>();
//         BufferedReader reader = new BufferedReader(new FileReader(filePath));
    
//         String line;
//         while ((line = reader.readLine()) != null) {
//             if (line.startsWith("f ")) {
//                 String[] tokens = line.split("\\s+");
//                 int index1 = Integer.parseInt(tokens[1].split("/")[0]);
//                 int index2 = Integer.parseInt(tokens[2].split("/")[0]);
//                 int index3 = Integer.parseInt(tokens[3].split("/")[0]);
//                 Face face = new Face(index1, index2, index3);
//                 faces.add(face);
//             }
//         }
    
//         reader.close();
//         return faces;
//     }
    

//     public static void main(String[] args) {
//         ProyectoTercerParcial ventana = new ProyectoTercerParcial();
//         ventana.setVisible(true);
//     }
    



//     public class Point3D {
//         private double x;
//         private double y;
//         private double z;
    
//         public Point3D(double x, double y, double z) {
//             this.x = x;
//             this.y = y;
//             this.z = z;
//         }
    
//         public double getX() {
//             return x;
//         }
    
//         public double getY() {
//             return y;
//         }
    
//         public double getZ() {
//             return z;
//         }
    
//         public void setX(double x) {
//             this.x = x;
//         }
    
//         public void setY(double y) {
//             this.y = y;
//         }
    
//         public void setZ(double z) {
//             this.z = z;
//         }
//     }

//     public class Face {
//         private int index1;
//         private int index2;
//         private int index3;
    
//         public Face(int index1, int index2, int index3) {
//             this.index1 = index1;
//             this.index2 = index2;
//             this.index3 = index3;
//         }
    
//         public int getIndex1() {
//             return index1;
//         }
    
//         public int getIndex2() {
//             return index2;
//         }
    
//         public int getIndex3() {
//             return index3;
//         }
//     }
    
    
    

// }

