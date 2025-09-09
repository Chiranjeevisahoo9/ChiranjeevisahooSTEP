import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Book {
    String bookId;
    String title;
    String author;
    boolean isIssued;
    LocalDate issueDate;
    LocalDate dueDate;

    public Book(String bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isIssued = false;
        this.issueDate = null;
        this.dueDate = null;
    }

    @Override
    public String toString() {
        return String.format("ID: %-5s | Title: %-30s | Author: %-20s | Issued: %s", bookId, title, author, isIssued ? "Yes" : "No");
    }
}

class Member {
    String memberId;
    String memberName;
    String memberType; // "Student", "Faculty", "General"
    ArrayList<Book> booksIssued;
    double totalFines;
    int maxBooksAllowed;

    public Member(String memberId, String memberName, String memberType) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberType = memberType;
        this.booksIssued = new ArrayList<>();
        this.totalFines = 0.0;
        // Set borrowing privileges based on member type
        switch (memberType) {
            case "Student":
                this.maxBooksAllowed = 3;
                break;
            case "Faculty":
                this.maxBooksAllowed = 10;
                break;
            default:
                this.maxBooksAllowed = 5;
                break;
        }
    }
    
    public void display() {
        System.out.println("-----------------------------------");
        System.out.println("Member ID: " + memberId + " | Name: " + memberName + " | Type: " + memberType);
        System.out.println("Books Issued: " + booksIssued.size() + "/" + maxBooksAllowed);
        System.out.printf("Total Fines Due: $%.2f\n", totalFines);
        if(!booksIssued.isEmpty()){
            System.out.println("Issued Books:");
            for(Book book : booksIssued) {
                System.out.println("  - " + book.title);
            }
        }
        System.out.println("-----------------------------------");
    }
}

class Library {
    // Static variables
    public static String libraryName = "City Central Library";
    public static double finePerDay = 0.50; // $0.50 per day
    
    // Instance variables
    private ArrayList<Book> books;
    private ArrayList<Member> members;

    public Library() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
    }
    
    public void addBook(Book book) { books.add(book); }
    public void addMember(Member member) { members.add(member); }

    public Book findBookById(String bookId) {
        for (Book book : books) {
            if (book.bookId.equalsIgnoreCase(bookId)) return book;
        }
        return null;
    }
    
    public Member findMemberById(String memberId) {
        for (Member member : members) {
            if (member.memberId.equalsIgnoreCase(memberId)) return member;
        }
        return null;
    }

    public void issueBook(String memberId, String bookId) {
        Member member = findMemberById(memberId);
        Book book = findBookById(bookId);

        if (member == null) { System.out.println("Error: Member not found."); return; }
        if (book == null) { System.out.println("Error: Book not found."); return; }

        if (book.isIssued) {
            System.out.println("Error: Book '" + book.title + "' is already issued.");
            return;
        }
        if (member.booksIssued.size() >= member.maxBooksAllowed) {
            System.out.println("Error: Member " + member.memberName + " has reached the borrowing limit.");
            return;
        }

        book.isIssued = true;
        book.issueDate = LocalDate.now();
        book.dueDate = book.issueDate.plusDays(member.memberType.equals("Faculty") ? 30 : 14); // Faculty get more time
        member.booksIssued.add(book);
        
        System.out.printf("Success: '%s' issued to %s. Due Date: %s\n", book.title, member.memberName, book.dueDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }
    
    private double calculateFine(Book book) {
        if (!book.isIssued || book.dueDate.isAfter(LocalDate.now()) || book.dueDate.isEqual(LocalDate.now())) {
            return 0.0;
        }
        long overdueDays = ChronoUnit.DAYS.between(book.dueDate, LocalDate.now());
        return overdueDays * finePerDay;
    }

    public void returnBook(String memberId, String bookId) {
        Member member = findMemberById(memberId);
        Book book = findBookById(bookId);
        
        if (member == null) { System.out.println("Error: Member not found."); return; }
        if (book == null) { System.out.println("Error: Book not found."); return; }

        if (!member.booksIssued.contains(book)) {
            System.out.println("Error: " + member.memberName + " did not issue this book.");
            return;
        }
        
        double fine = calculateFine(book);
        if (fine > 0) {
            member.totalFines += fine;
            System.out.printf("Fine of $%.2f for overdue book has been added to %s's account.\n", fine, member.memberName);
        }

        book.isIssued = false;
        book.issueDate = null;
        book.dueDate = null;
        member.booksIssued.remove(book);

        System.out.printf("Success: '%s' has been returned by %s.\n", book.title, member.memberName);
    }
    
    // --- Static-like reporting methods (acting on instance data) ---
    public void generateLibraryReport() {
        long totalBooks = books.size();
        long issuedBooks = books.stream().filter(b -> b.isIssued).count();
        System.out.println("\n============== " + libraryName + " Report ==============");
        System.out.println("Total Members: " + members.size());
        System.out.println("Total Books: " + totalBooks);
        System.out.println("Issued Books: " + issuedBooks);
        System.out.println("Available Books: " + (totalBooks - issuedBooks));
        System.out.println("--- Overdue Books ---");
        getOverdueBooks().forEach(System.out::println);
        System.out.println("===================================================\n");
    }
    
    public List<Book> getOverdueBooks() {
        return books.stream()
                .filter(b -> b.isIssued && b.dueDate.isBefore(LocalDate.now()))
                .collect(Collectors.toList());
    }
}


public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();

        // Add books
        library.addBook(new Book("B001", "The Lord of the Rings", "J.R.R. Tolkien"));
        library.addBook(new Book("B002", "Pride and Prejudice", "Jane Austen"));
        library.addBook(new Book("B003", "To Kill a Mockingbird", "Harper Lee"));
        library.addBook(new Book("B004", "1984", "George Orwell"));
        library.addBook(new Book("B005", "The Catcher in the Rye", "J.D. Salinger"));

        // Add members
        library.addMember(new Member("M01", "Alice Wonder", "Student"));
        library.addMember(new Member("M02", "Prof. Robert Langdon", "Faculty"));
        library.addMember(new Member("M03", "Bob Builder", "General"));

        System.out.println("Welcome to " + Library.libraryName + "\n");
        
        // --- Demonstrate Workflow ---
        System.out.println("--- Issuing books ---");
        library.issueBook("M01", "B003"); // Student issues a book
        library.issueBook("M02", "B001"); // Faculty issues a book
        library.issueBook("M02", "B004"); 
        library.issueBook("M01", "B005");
        library.issueBook("M01", "B002"); // Should be successful
        library.issueBook("M01", "B004"); // Should fail, limit reached for student

        System.out.println("\n--- Member Status after Issuing ---");
        library.findMemberById("M01").display();
        library.findMemberById("M02").display();

        System.out.println("\n--- Simulating an Overdue Book ---");
        Book overdueBook = library.findBookById("B003");
        // Manually set the due date to the past for demonstration
        if(overdueBook != null){
            overdueBook.dueDate = LocalDate.now().minusDays(10);
            System.out.println("Set 'To Kill a Mockingbird' due date to 10 days ago for member M01.");
        }
        
        System.out.println("\n--- Returning Books ---");
        library.returnBook("M01", "B003"); // This will incur a fine
        library.returnBook("M02", "B001"); // This should be on time

        System.out.println("\n--- Final Member Status ---");
        library.findMemberById("M01").display();
        
        // --- Generate Final Report ---
        library.generateLibraryReport();
    }
}