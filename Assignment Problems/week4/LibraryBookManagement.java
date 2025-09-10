class Book {
    String title;
    String author;
    String isbn;
    boolean isAvailable;

    // Default constructor
    public Book() {
        this.title = "No Title";
        this.author = "No Author";
        this.isbn = "N/A";
        this.isAvailable = false;
    }

    // Constructor with title and author
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isbn = "Unknown ISBN";
        this.isAvailable = true;
    }

    // Constructor with all details
    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true;
    }

    public void borrowBook() {
        if (isAvailable) {
            this.isAvailable = false;
            System.out.println("'" + this.title + "' has been borrowed.");
        } else {
            System.out.println("Sorry, '" + this.title + "' is currently unavailable.");
        }
    }

    public void returnBook() {
        if (!isAvailable) {
            this.isAvailable = true;
            System.out.println("'" + this.title + "' has been returned. Thank you!");
        } else {
            System.out.println("This book is already in the library.");
        }
    }

    public void displayBookInfo() {
        System.out.println("\n--- Book Information ---");
        System.out.println("Title: " + this.title);
        System.out.println("Author: " + this.author);
        System.out.println("ISBN: " + this.isbn);
        System.out.println("Status: " + (this.isAvailable ? "Available" : "Checked Out"));
        System.out.println("------------------------");
    }
}

public class LibraryBookManagement {
    public static void main(String[] args) {
        System.out.println("=== Library Book Management System ===");

        // Create books
        Book book1 = new Book("Ponniyin Selvan", "Kalki Krishnamurthy");
        Book book2 = new Book("The Alchemist", "Paulo Coelho", "978-0061122415");

        // Initial status
        book1.displayBookInfo();
        book2.displayBookInfo();

        // Perform actions
        System.out.println("\n--- Library Actions ---");
        book1.borrowBook();
        book2.borrowBook();
        book1.borrowBook(); // Should fail
        book1.returnBook();

        // Final status
        System.out.println("\n--- Final Book Statuses ---");
        book1.displayBookInfo();
        book2.displayBookInfo();
    }
}