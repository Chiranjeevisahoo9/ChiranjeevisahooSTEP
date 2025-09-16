import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// --- Immutable Product Class ---
final class Product {
    private final String productId;
    private final String name;
    private final String category;
    private final double basePrice;
    private final String[] features;
    private final Map<String, String> specifications;

    private Product(String name, String category, double price, String[] features, Map<String, String> specs) {
        this.productId = "PROD-" + UUID.randomUUID().toString().toUpperCase().substring(0, 8);
        this.name = name;
        this.category = category;
        this.basePrice = price;
        this.features = features.clone(); // Defensive copy
        this.specifications = Collections.unmodifiableMap(new HashMap<>(specs)); // Defensive copy
    }
    
    // Getters only
    public String getProductId() { return productId; }
    public String getName() { return name; }
    public double getBasePrice() { return basePrice; }
    public String[] getFeatures() { return features.clone(); }
    public Map<String, String> getSpecifications() { return specifications; }

    public final double calculateTax(String region) {
        return "domestic".equalsIgnoreCase(region) ? basePrice * 0.18 : basePrice * 0.25;
    }

    // Factory Methods
    public static Product createLaptop(String name, double price) {
        Map<String, String> specs = new HashMap<>();
        specs.put("RAM", "16GB");
        specs.put("Storage", "512GB SSD");
        return new Product(name, "Electronics", price, new String[]{"Backlit Keyboard", "Webcam"}, specs);
    }
    
    public static Product createTShirt(String name, double price) {
        Map<String, String> specs = new HashMap<>();
        specs.put("Material", "Cotton");
        specs.put("Fit", "Regular");
        return new Product(name, "Clothing", price, new String[]{"Crew Neck", "Printed"}, specs);
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", name, category);
    }
}

// --- Customer Class with Privacy Tiers ---
class Customer {
    private final String customerId;
    private final String email;
    private final LocalDateTime accountCreationDate;
    private String name;
    
    public Customer(String name, String email) {
        this.customerId = "CUST-" + UUID.randomUUID().toString().toUpperCase().substring(0, 8);
        this.email = email;
        this.name = name;
        this.accountCreationDate = LocalDateTime.now();
    }
    
    public String getCustomerId() { return customerId; }
    public String getEmail() { return email; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getPublicProfile() {
        return "Customer: " + name;
    }
}

// --- ShoppingCart Class ---
class ShoppingCart {
    private final String cartId;
    private final String customerId;
    private final List<Product> items = new ArrayList<>();
    private double totalAmount = 0.0;
    
    public ShoppingCart(String customerId) {
        this.cartId = "CART-" + UUID.randomUUID().toString().toUpperCase().substring(0, 8);
        this.customerId = customerId;
    }
    
    public boolean addItem(Object product, int quantity) {
        if (!(product instanceof Product)) {
            System.out.println("Error: Only valid products can be added to the cart.");
            return false;
        }
        Product p = (Product) product;
        for (int i = 0; i < quantity; i++) {
            items.add(p);
        }
        calculateTotal();
        System.out.printf("Added %d x '%s' to cart.\n", quantity, p.getName());
        return true;
    }
    
    private void calculateTotal() {
        this.totalAmount = items.stream().mapToDouble(Product::getBasePrice).sum();
    }

    // Package-private method for checkout process
    String getCartSummary() {
        return String.format("Cart ID: %s, Items: %d, Total: $%.2f", cartId, items.size(), totalAmount);
    }
}

// --- Order & Payment Classes ---
class Order {
    private final String orderId;
    private final LocalDateTime orderTime;
    private final String cartSummary;
    public Order(String cartSummary) {
        this.orderId = "ORD-" + UUID.randomUUID().toString().toUpperCase().substring(0, 8);
        this.orderTime = LocalDateTime.now();
        this.cartSummary = cartSummary;
    }
    @Override public String toString() { return "Order " + orderId + " placed at " + orderTime; }
}

class PaymentProcessor {
    public boolean processPayment(double amount) {
        System.out.printf("Processing payment of $%.2f... Payment successful.\n", amount);
        return true;
    }
}

// --- Final ECommerceSystem Class ---
public final class ECommerceSystem {
    private static final Map<String, Product> productCatalog = new HashMap<>();
    
    private ECommerceSystem() {} // Prevent instantiation

    public static void addProductToCatalog(Product product) {
        productCatalog.put(product.getProductId(), product);
    }
    
    public static Order processOrder(ShoppingCart cart, Customer customer) {
        if (cart == null || customer == null) {
            System.out.println("Order failed: Invalid cart or customer.");
            return null;
        }
        System.out.println("\n--- Processing Order for " + customer.getName() + " ---");
        System.out.println(cart.getCartSummary());
        // In a real system, you'd calculate total from the cart summary.
        double total = Double.parseDouble(cart.getCartSummary().split("\\$")[1]);
        
        PaymentProcessor processor = new PaymentProcessor();
        if (processor.processPayment(total)) {
            Order newOrder = new Order(cart.getCartSummary());
            System.out.println("Order confirmed: " + newOrder);
            return newOrder;
        }
        System.out.println("Payment failed. Order cancelled.");
        return null;
    }
    
    public static void main(String[] args) {
        // Populate product catalog using factory methods
        Product laptop = Product.createLaptop("Starlight Pro 15", 1299.99);
        Product shirt = Product.createTShirt("Classic Cotton Tee", 25.50);
        ECommerceSystem.addProductToCatalog(laptop);
        ECommerceSystem.addProductToCatalog(shirt);
        
        // Create a customer
        Customer alice = new Customer("Alice Smith", "alice@example.com");
        
        // Customer shops
        ShoppingCart aliceCart = new ShoppingCart(alice.getCustomerId());
        aliceCart.addItem(laptop, 1);
        aliceCart.addItem(shirt, 2);
        
        // Process the order
        ECommerceSystem.processOrder(aliceCart, alice);
        
        // Demonstrate immutability
        System.out.println("\n--- Testing Product Immutability ---");
        Map<String, String> specs = laptop.getSpecifications();
        try {
            // This will fail because the map is unmodifiable
            specs.put("CPU", "M5"); 
        } catch (UnsupportedOperationException e) {
            System.out.println("Success: Attempted to modify immutable product specifications and failed as expected.");
        }
    }
}
