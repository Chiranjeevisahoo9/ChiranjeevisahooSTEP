class MovieTicket {
    String movieName;
    String theatreName;
    int seatNumber;
    double price;

    // 1. Default constructor
    public MovieTicket() {
        this.movieName = "Unknown";
        this.theatreName = "N/A";
        this.seatNumber = 0;
        this.price = 0.0;
    }

    // 2. Constructor with movie name
    public MovieTicket(String movieName) {
        this.movieName = movieName;
        this.theatreName = "N/A";
        this.seatNumber = 0;
        this.price = 200.0; // Default price
    }

    // 3. Constructor with movie name and seat number
    public MovieTicket(String movieName, int seatNumber) {
        this.movieName = movieName;
        this.theatreName = "PVR"; // Default theatre
        this.seatNumber = seatNumber;
        this.price = 250.0; // Price for a specific seat
    }

    // 4. Full constructor
    public MovieTicket(String movieName, String theatreName, int seatNumber, double price) {
        this.movieName = movieName;
        this.theatreName = theatreName;
        this.seatNumber = seatNumber;
        this.price = price;
    }

    // Method to display ticket details
    public void printTicket() {
        System.out.println("\n--- MOVIE TICKET ---");
        System.out.println("Movie: " + this.movieName);
        System.out.println("Theatre: " + this.theatreName);
        System.out.println("Seat No: " + (this.seatNumber == 0 ? "Any" : this.seatNumber));
        System.out.printf("Price: Rs. %.2f\n", this.price);
        System.out.println("--------------------");
    }
}

public class MovieTicketSystem {
    public static void main(String[] args) {
        System.out.println("=== Movie Ticket Booking System ===");

        // Create tickets using different constructors
        MovieTicket ticket1 = new MovieTicket(); // Default
        MovieTicket ticket2 = new MovieTicket("Jawan"); // Movie name only
        MovieTicket ticket3 = new MovieTicket("Leo", 25); // Movie and seat
        MovieTicket ticket4 = new MovieTicket("Vikram", "INOX", 12, 300.00); // Full details

        // Print all tickets
        System.out.println("\nPrinting generated tickets...");
        ticket1.printTicket();
        ticket2.printTicket();
        ticket3.printTicket();
        ticket4.printTicket();
    }
}
