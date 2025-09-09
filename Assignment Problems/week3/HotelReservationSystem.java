import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class Room {
    String roomNumber;
    String roomType;
    double pricePerNight;
    boolean isAvailable;
    int maxOccupancy;

    public Room(String roomNumber, String roomType, double pricePerNight, int maxOccupancy) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.isAvailable = true;
        this.maxOccupancy = maxOccupancy;
    }

    @Override
    public String toString() {
        return String.format("Room #%-5s | Type: %-10s | Price: $%-7.2f | Occupancy: %d | Available: %s",
                roomNumber, roomType, pricePerNight, maxOccupancy, isAvailable ? "Yes" : "No");
    }
}

class Guest {
    String guestId;
    String guestName;
    String phoneNumber;
    ArrayList<String> bookingHistory;

    public Guest(String guestName, String phoneNumber) {
        this.guestId = "G-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        this.guestName = guestName;
        this.phoneNumber = phoneNumber;
        this.bookingHistory = new ArrayList<>();
    }

     @Override
    public String toString() {
        return String.format("Guest ID: %-10s | Name: %-15s | Phone: %s", guestId, guestName, phoneNumber);
    }
}

class Booking {
    String bookingId;
    Guest guest;
    Room room;
    LocalDate checkInDate;
    LocalDate checkOutDate;
    double totalAmount;

    public Booking(Guest guest, Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        this.bookingId = "B-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalAmount = calculateBill();
    }
    
    public double calculateBill() {
        long numberOfNights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        return numberOfNights > 0 ? numberOfNights * room.pricePerNight : room.pricePerNight;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format("Booking ID: %s | Guest: %s | Room: %s | Check-in: %s | Check-out: %s | Total: $%.2f",
            bookingId, guest.guestName, room.roomNumber, checkInDate.format(formatter), checkOutDate.format(formatter), totalAmount);
    }
}

class Hotel {
    // Static variables for hotel-wide data
    public static String hotelName = "The Grand Gemini Hotel";
    private static double hotelRevenue = 0;

    private ArrayList<Room> rooms;
    private ArrayList<Guest> guests;
    private ArrayList<Booking> bookings;

    public Hotel() {
        rooms = new ArrayList<>();
        guests = new ArrayList<>();
        bookings = new ArrayList<>();
    }

    public void addRoom(Room room) { rooms.add(room); }
    public Guest addGuest(String name, String phone) {
        Guest guest = new Guest(name, phone);
        guests.add(guest);
        return guest;
    }

    public void checkAvailability() {
        System.out.println("\n--- Room Availability ---");
        for (Room room : rooms) {
            if (room.isAvailable) {
                System.out.println(room);
            }
        }
        System.out.println("------------------------\n");
    }

    public void makeReservation(Guest guest, String roomNumber, String checkInStr, String checkOutStr) {
        Room targetRoom = null;
        for (Room room : rooms) {
            if (room.roomNumber.equalsIgnoreCase(roomNumber) && room.isAvailable) {
                targetRoom = room;
                break;
            }
        }

        if (targetRoom == null) {
            System.out.println("Sorry, room " + roomNumber + " is not available or does not exist.");
            return;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate checkIn = LocalDate.parse(checkInStr, formatter);
            LocalDate checkOut = LocalDate.parse(checkOutStr, formatter);

            if(checkIn.isAfter(checkOut) || checkIn.isEqual(checkOut)){
                System.out.println("Error: Check-out date must be after check-in date.");
                return;
            }

            Booking newBooking = new Booking(guest, targetRoom, checkIn, checkOut);
            bookings.add(newBooking);
            guest.bookingHistory.add(newBooking.bookingId);
            targetRoom.isAvailable = false;
            hotelRevenue += newBooking.totalAmount;

            System.out.println("\nReservation Successful!");
            System.out.println(newBooking);

        } catch (Exception e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
        }
    }

    public void cancelReservation(String bookingId) {
        Booking bookingToCancel = null;
        for (Booking booking : bookings) {
            if (booking.bookingId.equalsIgnoreCase(bookingId)) {
                bookingToCancel = booking;
                break;
            }
        }

        if (bookingToCancel != null) {
            bookingToCancel.room.isAvailable = true;
            hotelRevenue -= bookingToCancel.totalAmount; // Simple refund logic
            bookings.remove(bookingToCancel);
            bookingToCancel.guest.bookingHistory.remove(bookingId);
            System.out.println("Booking " + bookingId + " has been successfully cancelled.");
        } else {
            System.out.println("Booking ID not found.");
        }
    }
    
    // --- Static reporting methods ---
    public void getOccupancyRate() {
        if(rooms.isEmpty()) {
            System.out.println("No rooms in the hotel.");
            return;
        }
        long occupiedRooms = rooms.stream().filter(r -> !r.isAvailable).count();
        double rate = ((double) occupiedRooms / rooms.size()) * 100;
        System.out.printf("Current Occupancy Rate: %.2f%% (%d out of %d rooms occupied)\n", rate, occupiedRooms, rooms.size());
    }

    public void getTotalRevenue() {
        System.out.printf("Total Hotel Revenue: $%.2f\n", hotelRevenue);
    }
    
    public void getMostPopularRoomType() {
        if(bookings.isEmpty()){
            System.out.println("No bookings yet to determine popularity.");
            return;
        }
        Map<String, Integer> roomTypeCounts = new HashMap<>();
        for(Booking b : bookings){
            String type = b.room.roomType;
            roomTypeCounts.put(type, roomTypeCounts.getOrDefault(type, 0) + 1);
        }
        
        String mostPopular = "";
        int maxCount = 0;
        for(Map.Entry<String, Integer> entry : roomTypeCounts.entrySet()){
            if(entry.getValue() > maxCount){
                maxCount = entry.getValue();
                mostPopular = entry.getKey();
            }
        }
        System.out.println("Most Popular Room Type: " + mostPopular + " with " + maxCount + " bookings.");
    }

    public void displayAllBookings() {
        System.out.println("\n--- All Hotel Bookings ---");
        if (bookings.isEmpty()) {
            System.out.println("No bookings have been made yet.");
        } else {
            for (Booking booking : bookings) {
                System.out.println(booking);
            }
        }
        System.out.println("--------------------------\n");
    }
}

public class HotelReservationSystem {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        hotel.addRoom(new Room("101", "Single", 100.00, 1));
        hotel.addRoom(new Room("102", "Single", 100.00, 1));
        hotel.addRoom(new Room("201", "Double", 150.00, 2));
        hotel.addRoom(new Room("202", "Double", 150.00, 2));
        hotel.addRoom(new Room("301", "Suite", 250.00, 4));

        Guest guest1 = hotel.addGuest("John Doe", "555-1234");
        Guest guest2 = hotel.addGuest("Jane Smith", "555-5678");

        System.out.println("Welcome to " + Hotel.hotelName + "!");
        
        // Workflow demonstration
        System.out.println("\n1. Checking initial availability...");
        hotel.checkAvailability();
        
        System.out.println("\n2. Making two reservations...");
        hotel.makeReservation(guest1, "201", "2024-10-10", "2024-10-15");
        hotel.makeReservation(guest2, "301", "2024-11-01", "2024-11-03");

        System.out.println("\n3. Displaying all bookings...");
        hotel.displayAllBookings();

        System.out.println("\n4. Checking availability after bookings...");
        hotel.checkAvailability();

        System.out.println("\n5. Generating hotel reports...");
        hotel.getOccupancyRate();
        hotel.getTotalRevenue();
        hotel.getMostPopularRoomType();
        
        System.out.println("\n6. Cancelling a reservation...");
        // Assuming we know the booking ID from the output above. Let's hardcode one.
        // In a real app, we'd get this from user input.
        // Let's assume the first booking ID is B-XXXXXX and we want to cancel it.
        // Since UUID is random, we can't hardcode. A better approach for demo is to
        // make it predictable or search for it. Let's just create a test one to cancel.
        hotel.makeReservation(guest1, "101", "2025-01-01", "2025-01-02");
        // Let's find a booking to cancel. This would be better if we store the ID.
        // For this example, let's just make one to cancel.
        hotel.cancelReservation(guest1.bookingHistory.get(1)); // Cancel the second booking for guest1
        
        System.out.println("\n7. Final state of the hotel:");
        hotel.displayAllBookings();
        hotel.checkAvailability();
        hotel.getTotalRevenue();
    }
}