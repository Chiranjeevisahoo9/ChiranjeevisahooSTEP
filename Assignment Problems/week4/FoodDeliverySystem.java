class FoodOrder {
    String customerName;
    String foodItem;
    int quantity;
    double price;
    private static final double FIXED_RATE = 150.0; // Fixed price per item

    // 1. Default constructor
    public FoodOrder() {
        this.customerName = "Guest";
        this.foodItem = "Unknown";
        this.quantity = 0;
        this.price = 0.0;
    }

    // 2. Constructor with food item
    public FoodOrder(String customerName, String foodItem) {
        this.customerName = customerName;
        this.foodItem = foodItem;
        this.quantity = 1;
        this.price = FIXED_RATE; // Default price for one item
    }

    // 3. Constructor with food item and quantity
    public FoodOrder(String customerName, String foodItem, int quantity) {
        this.customerName = customerName;
        this.foodItem = foodItem;
        this.quantity = quantity;
        this.price = quantity * FIXED_RATE; // Calculated price
    }

    // Method to print the bill
    public void printBill() {
        System.out.println("\n--- Food Order Bill ---");
        System.out.println("Customer: " + this.customerName);
        System.out.println("Item: " + this.foodItem);
        System.out.println("Quantity: " + this.quantity);
        System.out.printf("Total Price: Rs. %.2f\n", this.price);
        System.out.println("-----------------------");
    }
}

public class FoodDeliverySystem {
    public static void main(String[] args) {
        System.out.println("=== Food Delivery Order System ===");

        // Create multiple orders
        FoodOrder order1 = new FoodOrder("Ravi", "Paneer Butter Masala");
        FoodOrder order2 = new FoodOrder("Anita", "Chicken Biryani", 2);
        FoodOrder order3 = new FoodOrder("Karthik", "Masala Dosa", 4);
        
        // Print bills for all orders
        System.out.println("\nPrinting all order bills...");
        order1.printBill();
        order2.printBill();
        order3.printBill();
    }
}