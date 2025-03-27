import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BinarySearchTree<Product> bst = new BinarySearchTree<>();
        String csvFile = "productos.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean headerProcessed = false;
            int[] indices = new int[5]; // SKU, Price_Retail, Price_Current, Product_Name, Category

            while ((line = br.readLine()) != null) {
                if (!headerProcessed) {
                    String[] headers = line.split(",");
                    for (int i = 0; i < headers.length; i++) {
                        String header = headers[i].trim();
                        switch (header) {
                            case "SKU": indices[0] = i; break;
                            case "Price_Retail": indices[1] = i; break;
                            case "Price_Current": indices[2] = i; break;
                            case "Product_Name": indices[3] = i; break;
                            case "Category": indices[4] = i; break;
                        }
                    }
                    headerProcessed = true;
                } else {
                    String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                    if (fields.length < 5) continue;

                    String sku = fields[indices[0]].trim();
                    if (sku.isEmpty()) continue;

                    try {
                        double priceRetail = Double.parseDouble(fields[indices[1]].trim());
                        double priceCurrent = Double.parseDouble(fields[indices[2]].trim());
                        String productName = fields[indices[3]].trim();
                        String category = fields[indices[4]].trim();

                        Product product = new Product(sku, priceRetail, priceCurrent, productName, category);
                        Product existing = bst.find(product);

                        if (existing != null) {
                            // Comparación usando getPriceCurrent() ✔️
                            if (product.getPriceCurrent() < existing.getPriceCurrent()) {
                                bst.delete(existing);
                                bst.insert(product);
                            }
                        } else {
                            bst.insert(product);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Error en SKU: " + sku);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nIngrese SKU (o 'salir'):");
            String sku = scanner.nextLine().trim();
            if (sku.equalsIgnoreCase("salir")) break;

            Product dummy = new Product(sku, 0, 0, "", "");
            Product found = bst.find(dummy);
            if (found != null) {
                System.out.println("\n=== Producto ===");
                System.out.println(found);
            } else {
                System.out.println("SKU no existe.");
            }
        }
        scanner.close();
    }
}