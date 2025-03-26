/*
 * Universidad del Valle de Guatemala 
 * Autora: Tiffany Salazar Suarez
 * Código: 24630
 * Fecha de creación: 24/03/2025
 * Fecha de finalización: 25/03/2025
 * Descripción: Clase encargada de gestionar la lectura del archivo CSV de productos y cargar los datos al árbol binario de búsqueda (BST).
 */

 import java.io.BufferedReader;
 import java.io.FileReader;
 import java.io.IOException;
 
 public class GestorCSV{
     private String archCSV;
 
     public GestorCSV(String archCSV){
         this.archCSV = archCSV;
     }

     //Provisional
     public String mostrarCSV(){
         StringBuilder resultado = new StringBuilder();
         try (BufferedReader br = new BufferedReader(new FileReader(archCSV))){
             String lin;
             resultado.append("Contenido del archivo CSV:\n");
             while ((lin = br.readLine()) != null) {
                 resultado.append(lin).append("\n");
             }
         }catch (IOException e){
             return "Error al leer el archivo CSV: " + e.getMessage();
         }
         return resultado.toString();
     }
 
     public String cargarProductosAlArbol(BST<Product> arbol){
         StringBuilder resultado = new StringBuilder();
         try (BufferedReader br = new BufferedReader(new FileReader(archCSV))){
             String lin = br.readLine();
             if (lin == null){
                 return "El archivo CSV está vacío.";
             }
 
             while ((lin = br.readLine()) != null){
                 String[] valo = lin.split(",");
 
                 if (valo.length < 5){
                     resultado.append("Línea incompleta, se omite.\n");
                     continue;
                 }
 
                 String sku = valo[0].trim();
                 double priceRetail = Double.parseDouble(valo[1].trim());
                 double priceCurrent = Double.parseDouble(valo[2].trim());
                 String productName = valo[3].trim();
                 String category = valo[4].trim();
 
                 Product producto = new Product(sku, priceRetail, priceCurrent, productName, category);
                 arbol.insert(producto);
             }
 
             resultado.append("Productos cargados exitosamente al árbol.");
             return resultado.toString();
 
         }catch (IOException e){
             return "Error al leer el archivo CSV: " + e.getMessage();
         }catch (NumberFormatException e){
             return "Error de formato numérico en el archivo CSV: " + e.getMessage();
         }
     }
 }
 