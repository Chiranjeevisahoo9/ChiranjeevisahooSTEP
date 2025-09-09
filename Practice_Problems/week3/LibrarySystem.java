import java.util.ArrayList;
import java.util.Scanner;

// Book class with its own attributes and methods
class Book {
    private String title;
    private String author;
    private String isbn;
    private double price;
    private int quantity;

    public Book(String title, String author, String isbn, double price, int quantity) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
        this.quantity = quantity;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public void displayBook() {
        System.out.printf("| %-30s | %-20s | %-15s | $%-8.2f | %-8d |\n", title, author, isbn, price, quantity);
    }
}

// Library class that contains and manages Book objects
class Library {
    private String libraryName;
    private ArrayList<Book> books;

    public Library(String name) {
        this.libraryName = name;
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book '" + book.getTitle() + "' added to the library.");
    }

    public void searchBooks(String query) {
        System.out.println("\n--- Search Results for '" + query + "' ---");
        boolean found = false;
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(query.toLowerCase()) || 
                book.getAuthor().toLowerCase().contains(query.toLowerCase())) {
                book.displayBook();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No books found matching your query.");
        }
    }

    public void displayInventory() {
        System.out.println("\n--- " + this.libraryName + " Inventory ---");
        System.out.println("--------------------------------------------------------------------------------------------------");
        System.out.printf("| %-30s | %-20s | %-15s | %-9s | %-8s |\n", "Title", "Author", "ISBN", "Price", "Quantity");
        System.out.println("--------------------------------------------------------------------------------------------------");
        if (books.isEmpty()) {
            System.out.println("| No books in inventory yet.                                                                     |");
        } else {
            for (Book book : books) {
                book.displayBook();
            }
        }
        System.out.println("--------------------------------------------------------------------------------------------------");
    }
    
    public double calculateTotalValue() {
        double totalValue = 0.0;
        for(Book book : books) {
            totalValue += book.getPrice() * book.getQuantity();
        }
        return totalValue;
    }
}

public class LibrarySystem {
    public static void main(String[] args) {
        // Create a Library object
        Library myLibrary = new Library("City Public Library");
        
        // Pre-populate with some Book objects
        myLibrary.addBook(new Book("The Hobbit", "J.R.R. Tolkien", "978-0345339683", 15.99, 10));
        myLibrary.addBook(new Book("1984", "George Orwell", "978-0451524935", 9.99, 15));
        myLibrary.addBook(new Book("To Kill a Mockingbird", "Harper Lee", "978-0061120084", 12.50, 5));

        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        do {
            System.out.println("\n====== Library Menu ======");
            System.out.println("1. Add a New Book");
            System.out.println("2. Search for a Book");
            System.out.println("3. Display All Books");
            System.out.println("4. Calculate Total Library Value");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter ISBN: ");
                    String isbn = scanner.nextLine();
                    System.out.print("Enter price: ");
                    double price = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter quantity: ");
                    int quantity = Integer.parseInt(scanner.nextLine());
                    myLibrary.addBook(new Book(title, author, isbn, price, quantity));
                    break;
                case 2:
                    System.out.print("Enter search query (title or author): ");
                    String query = scanner.nextLine();
                    myLibrary.searchBooks(query);
                    break;
                case 3:
                    myLibrary.displayInventory();
                    break;
                case 4:
                    double totalValue = myLibrary.calculateTotalValue();
                    System.out.printf("\nTotal value of all books in the library: $%.2f\n", totalValue);
                    break;
                case 5:
                    System.out.println("Exiting the library system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
        
        scanner.close();
    }
}
