public class Books{
    String title;
    String author;
    double price;

    // Default constructor
    public Books() {
        this.title = "Unknown Title";
        this.author = "Unknown Author";
        this.price = 0.0;
    }

    // Parameterized constructor
    public Books(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    // Display method
    public void display() {
        System.out.println("Title: " + title + ", Author: " + author + ", Price: $" + price);
    }

    public static void main(String[] args) {
        Books book1 = new Books(); // default constructor
        Books book2 = new Books("Java Programming", "James Gosling", 499.99); // parameterized

        System.out.println("=== BOOK DETAILS ===");
        book1.display();
        book2.display();
    }
}