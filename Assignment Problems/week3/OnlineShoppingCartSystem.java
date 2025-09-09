import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

/**
 * Represents a product in the online store.
 */
class Product {
    // Static variables
    public static int totalProducts = 0;
    public static final String[] CATEGORIES = {"Electronics", "Apparel", "Groceries", "Books"};

    // Instance variables
    String productId;
    String productName;
    double price;
    String category;
    int stockQuantity;

    public Product(String productName, double price, String category, int stockQuantity) {
        this.productId = "PROD-" + (++totalProducts);
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.stockQuantity = stockQuantity;
    }

    @Override
    public String toString() {
        return String.format("ID: %-8s | Name: %-20s | Category: %-12s | Price: $%-8.2f | Stock: %d",
                productId, productName, category, price, stockQuantity);
    }

    // --- Static Methods ---
    public static Product findProductById(ArrayList<Product> products, String productId) {
        for (Product p : products) {
            if (p.productId.equalsIgnoreCase(productId)) {
                return p;
            }
        }
        return null;
    }

    public static void getProductsByCategory(ArrayList<Product> products, String category) {
        System.out.println("\n--- Products in category: " + category + " ---");
        boolean found = false;
        for (Product p : products) {
            if (p.category.equalsIgnoreCase(category)) {
                System.out.println(p);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No products found in this category.");
        }
        System.out.println("---------------------------------------\n");
    }
}

/**
 * Represents a customer's shopping cart.
 */
class ShoppingCart {
    String cartId;
    String customerName;
    ArrayList<Product> products;
    ArrayList<Integer> quantities;
    double cartTotal;

    public ShoppingCart(String customerName) {
        this.cartId = "CART-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        this.customerName = customerName;
        this.products = new ArrayList<>();
        this.quantities = new ArrayList<>();
        this.cartTotal = 0.0;
    }

    public void addProduct(Product product, int quantity) {
        if (product == null) {
            System.out.println("Error: Product not found.");
            return;
        }
        if (quantity <= 0) {
            System.out.println("Error: Quantity must be positive.");
            return;
        }
        if (product.stockQuantity < quantity) {
            System.out.println("Error: Not enough stock for " + product.productName + ". Available: " + product.stockQuantity);
            return;
        }

        int index = products.indexOf(product);
        if (index != -1) { // Product already in cart
            quantities.set(index, quantities.get(index) + quantity);
        } else { // New product
            products.add(product);
            quantities.add(quantity);
        }
        System.out.println("Added " + quantity + "x " + product.productName + " to cart.");
        calculateTotal();
    }

    public void removeProduct(String productId) {
        Product productToRemove = null;
        for (Product p : products) {
            if (p.productId.equalsIgnoreCase(productId)) {
                productToRemove = p;
                break;
            }
        }

        if (productToRemove != null) {
            int index = products.indexOf(productToRemove);
            products.remove(index);
            quantities.remove(index);
            System.out.println("Removed " + productToRemove.productName + " from cart.");
            calculateTotal();
        } else {
            System.out.println("Error: Product with ID " + productId + " not found in cart.");
        }
    }

    public void calculateTotal() {
        this.cartTotal = 0.0;
        for (int i = 0; i < products.size(); i++) {
            this.cartTotal += products.get(i).price * quantities.get(i);
        }
    }



    public void displayCart() {
        System.out.println("\n=========== " + this.customerName + "'s Shopping Cart (" + this.cartId + ") ===========");
        if (products.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println(String.format("%-20s | %-10s | %-10s | %-10s", "Product Name", "Price", "Quantity", "Subtotal"));
            System.out.println("-----------------------------------------------------------------");
            for (int i = 0; i < products.size(); i++) {
                Product p = products.get(i);
                int q = quantities.get(i);
                double subtotal = p.price * q;
                System.out.println(String.format("%-20s | $%-9.2f | %-10d | $%-9.2f", p.productName, p.price, q, subtotal));
            }
            System.out.println("-----------------------------------------------------------------");
            System.out.println(String.format("Total: $%.2f", this.cartTotal));
        }
        System.out.println("===============================================================\n");
    }
    
    public void checkout() {
        if (products.isEmpty()) {
            System.out.println("Cannot checkout with an empty cart.");
            return;
        }
        displayCart();
        System.out.println("Processing payment of $" + String.format("%.2f", this.cartTotal) + "...");
        // Update stock quantities
        for(int i=0; i < products.size(); i++){
            Product p = products.get(i);
            p.stockQuantity -= quantities.get(i);
        }
        System.out.println("Checkout successful! Thank you for your purchase, " + this.customerName + ".");
        products.clear();
        quantities.clear();
        cartTotal = 0.0;
    }
}

/**
 * Main class to run the Online Shopping Cart System.
 */
public class OnlineShoppingCartSystem {
    public static void main(String[] args) {
        ArrayList<Product> productList = new ArrayList<>();
        productList.add(new Product("Laptop", 1200.00, "Electronics", 10));
        productList.add(new Product("Smartphone", 800.00, "Electronics", 25));
        productList.add(new Product("T-Shirt", 25.00, "Apparel", 50));
        productList.add(new Product("Jeans", 75.00, "Apparel", 40));
        productList.add(new Product("Milk", 3.50, "Groceries", 100));
        productList.add(new Product("Bread", 2.50, "Groceries", 150));
        productList.add(new Product("The Great Gatsby", 15.00, "Books", 30));
        productList.add(new Product("1984", 12.50, "Books", 35));
        productList.add(new Product("Headphones", 150.00, "Electronics", 20));
        productList.add(new Product("Jacket", 120.00, "Apparel", 30));

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name to start shopping: ");
        String customerName = scanner.nextLine();
        ShoppingCart cart = new ShoppingCart(customerName);

        int choice;
        do {
            System.out.println("\n======= Online Shopping Menu =======");
            System.out.println("1. View all products");
            System.out.println("2. View products by category");
            System.out.println("3. Add product to cart");
            System.out.println("4. Remove product from cart");
            System.out.println("5. View your cart");
            System.out.println("6. Checkout");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("\n--- All Available Products ---");
                    for (Product p : productList) System.out.println(p);
                    System.out.println("----------------------------\n");
                    break;
                case 2:
                    System.out.print("Enter category (" + String.join(", ", Product.CATEGORIES) + "): ");
                    String category = scanner.nextLine();
                    Product.getProductsByCategory(productList, category);
                    break;
                case 3:
                    System.out.print("Enter Product ID to add: ");
                    String addId = scanner.nextLine();
                    System.out.print("Enter quantity: ");
                    int qty = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Product productToAdd = Product.findProductById(productList, addId);
                    cart.addProduct(productToAdd, qty);
                    break;
                case 4:
                    System.out.print("Enter Product ID to remove: ");
                    String removeId = scanner.nextLine();
                    cart.removeProduct(removeId);
                    break;
                case 5:
                    cart.displayCart();
                    break;
                case 6:
                    cart.checkout();
                    break;
                case 0:
                    System.out.println("Thank you for shopping with us!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }
}