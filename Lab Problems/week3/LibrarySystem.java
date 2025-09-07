class Book {
    private String bookId;
    private String title;
    private String author;
    private boolean isAvailable;

    private static int totalBooks = 0;
    private static int nextBookId = 1;

    public Book(String title, String author) {
        this.bookId = generateBookId();
        this.title = title;
        this.author = author;
        this.isAvailable = true;
        totalBooks++;
    }

    public void issueBook() {
        if (this.isAvailable) {
            this.isAvailable = false;
        }
    }

    public void returnBook() {
        if (!this.isAvailable) {
            this.isAvailable = true;
        }
    }

    public void displayBookInfo() {
        System.out.printf("  | %-7s | %-25s | %-18s | %-10s |\n",
                this.bookId, this.title, this.author, (this.isAvailable ? "Available" : "Issued"));
    }

    public boolean isAvailable() { return isAvailable; }
    public String getBookId() { return bookId; }
    public String getTitle() { return title; }

    private static String generateBookId() {
        return "B" + String.format("%03d", nextBookId++);
    }

    public static int getTotalBooks() {
        return totalBooks;
    }
}

class Member {
    private String memberId;
    private String memberName;
    private String[] booksIssued;
    private int bookCount;
    private static final int MAX_BOOKS_ISSUED = 5;
    private static int nextMemberId = 1;

    public Member(String memberName) {
        this.memberId = generateMemberId();
        this.memberName = memberName;
        this.booksIssued = new String[MAX_BOOKS_ISSUED];
        this.bookCount = 0;
    }

    public void borrowBook(Book book) {
        if (bookCount >= MAX_BOOKS_ISSUED) {
            System.out.println("Action failed: " + memberName + " has already issued the maximum of " + MAX_BOOKS_ISSUED + " books.");
            return;
        }
        if (book.isAvailable()) {
            book.issueBook();
            this.booksIssued[bookCount] = book.getBookId();
            this.bookCount++;
            System.out.println("Success: '" + book.getTitle() + "' issued to " + this.memberName + ".");
        } else {
            System.out.println("Action failed: '" + book.getTitle() + "' is currently unavailable.");
        }
    }

    public void returnBook(String bookId, Book[] allBooks) {
        boolean bookFoundAndReturned = false;
        for (int i = 0; i < bookCount; i++) {
            if (booksIssued[i] != null && booksIssued[i].equals(bookId)) {
                for (Book book : allBooks) {
                    if (book.getBookId().equals(bookId)) {
                        book.returnBook();
                        System.out.println("Success: '" + book.getTitle() + "' returned by " + this.memberName + ".");
                        bookFoundAndReturned = true;
                        break;
                    }
                }
                booksIssued[i] = null;
                for (int j = i; j < bookCount - 1; j++) {
                    booksIssued[j] = booksIssued[j + 1];
                }
                booksIssued[bookCount - 1] = null;
                bookCount--;
                break;
            }
        }
        if (!bookFoundAndReturned) {
            System.out.println("Action failed: Member " + memberName + " did not borrow book with ID " + bookId + ".");
        }
    }

    public void displayMemberInfo() {
        System.out.println("\nMember ID: " + memberId + ", Name: " + memberName);
        System.out.print("  Books Issued (" + bookCount + "/" + MAX_BOOKS_ISSUED + "): ");
        if (bookCount == 0) {
            System.out.println("None");
        } else {
            for (int i = 0; i < bookCount; i++) {
                System.out.print(booksIssued[i] + (i == bookCount - 1 ? "" : ", "));
            }
            System.out.println();
        }
    }

    private static String generateMemberId() {
        return "M" + String.format("%03d", nextMemberId++);
    }
}

public class LibrarySystem {
    public static void main(String[] args) {
        System.out.println("--- Welcome to the Library Management System ---\n");

        Book[] books = {
            new Book("The Hobbit", "J.R.R. Tolkien"),
            new Book("1984", "George Orwell"),
            new Book("The Great Gatsby", "F. Scott Fitzgerald"),
            new Book("Dune", "Frank Herbert")
        };

        Member[] members = {
            new Member("Peter Jones"),
            new Member("Mary Smith")
        };

        displayLibraryStatus(books, members);

        System.out.println("\n--- Performing Transactions ---");
        members[0].borrowBook(books[0]);
        members[1].borrowBook(books[2]);
        members[0].borrowBook(books[0]);
        members[0].returnBook("B001", books);
        members[1].returnBook("B004", books);
        System.out.println("-----------------------------\n");

        System.out.println("--- Final Library Status ---");
        displayLibraryStatus(books, members);

        System.out.println("\n--- Library-wide Statistics ---");
        System.out.println("Total Books in Library: " + Book.getTotalBooks());
    }

    public static void displayLibraryStatus(Book[] books, Member[] members) {
        System.out.println("Current Book Inventory:");
        System.out.println("  -------------------------------------------------------------------------");
        System.out.printf("  | %-7s | %-25s | %-18s | %-10s |\n", "ID", "Title", "Author", "Status");
        System.out.println("  -------------------------------------------------------------------------");
        for (Book book : books) {
            book.displayBookInfo();
        }
        System.out.println("  -------------------------------------------------------------------------");

        System.out.println("\nCurrent Member Status:");
        for (Member member : members) {
            member.displayMemberInfo();
        }
    }
}