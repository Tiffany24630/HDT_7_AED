public class Product implements Comparable<Product> {
    private String sku;
    private double priceRetail;
    private double priceCurrent;
    private String productName;
    private String category;

    public Product(String sku, double priceRetail, double priceCurrent, String productName, String category) {
        this.sku = sku;
        this.priceRetail = priceRetail;
        this.priceCurrent = priceCurrent;
        this.productName = productName;
        this.category = category;
    }

    // Getters (sin cambios)

    @Override
    public int compareTo(Product other) {
        return this.sku.compareTo(other.sku); // Comparación por SKU
    }

    @Override
    public String toString() {
        return "SKU: " + sku + "\nPrecio Retail: Q" + priceRetail 
             + "\nPrecio Actual: Q" + priceCurrent 
             + "\nNombre del Producto: " + productName 
             + "\nCategoría: " + category;
    }
}