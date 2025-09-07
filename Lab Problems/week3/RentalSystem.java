class Vehicle {
    private String vehicleId;
    private String brand;
    private String model;
    private double rentPerDay;
    private boolean isAvailable;

    private static int totalVehicles = 0;
    private static double totalRevenue = 0.0;
    private static String companyName = "Default Rental Co.";
    private static int totalRentalDays = 0;
    private static int nextVehicleId = 1;

    public Vehicle(String brand, String model, double rentPerDay) {
        this.vehicleId = "V" + String.format("%03d", nextVehicleId++);
        this.brand = brand;
        this.model = model;
        this.rentPerDay = rentPerDay;
        this.isAvailable = true;
        totalVehicles++;
    }

    public void rentVehicle(int days) {
        if (isAvailable) {
            if (days > 0) {
                double rentAmount = calculateRent(days);
                this.isAvailable = false;
                System.out.printf("Success: %s %s rented for %d days. Total Cost: $%.2f\n",
                        this.brand, this.model, days, rentAmount);
            } else {
                System.out.println("Error: Rental days must be a positive number.");
            }
        } else {
            System.out.printf("Sorry, the %s %s is currently not available for rent.\n",
                    this.brand, this.model);
        }
    }

    public void returnVehicle() {
        if (!isAvailable) {
            this.isAvailable = true;
            System.out.printf("Info: The %s %s has been returned and is now available.\n",
                    this.brand, this.model);
        } else {
            System.out.printf("Info: The %s %s was already available.\n", this.brand, this.model);
        }
    }

    private double calculateRent(int days) {
        double rent = days * this.rentPerDay;
        totalRevenue += rent;
        totalRentalDays += days;
        return rent;
    }

    public void displayVehicleInfo() {
        System.out.printf(" | %-10s | %-12s | %-15s | $%-12.2f | %-11s |\n",
                this.vehicleId, this.brand, this.model, this.rentPerDay, (this.isAvailable ? "Available" : "Rented Out"));
    }

    public static void setCompanyName(String name) {
        companyName = name;
    }

    public static double getTotalRevenue() {
        return totalRevenue;
    }

    public static double getAverageRentPerDay() {
        if (totalRentalDays == 0) return 0.0;
        return totalRevenue / totalRentalDays;
    }

    public static void displayCompanyStats() {
        System.out.println("\n--- " + companyName + " - Company Statistics ---");
        System.out.println("Total Vehicles in Fleet: " + totalVehicles);
        System.out.println("Total Cumulative Rental Days: " + totalRentalDays);
        System.out.printf("Total Company Revenue: $%.2f\n", getTotalRevenue());
        System.out.printf("Average Rent Per Day: $%.2f\n", getAverageRentPerDay());
        System.out.println("-------------------------------------------------");
    }
}

public class RentalSystem {
    public static void main(String[] args) {
        Vehicle.setCompanyName("Prestige Worldwide Rentals");

        Vehicle car1 = new Vehicle("Toyota", "Camry", 55.00);
        Vehicle car2 = new Vehicle("Honda", "CR-V", 70.00);
        Vehicle truck1 = new Vehicle("Ford", "F-150", 95.50);

        Vehicle.displayCompanyStats();

        System.out.println("\n--- Initial Vehicle Fleet Status ---");
        displayFleetStatusHeader();
        car1.displayVehicleInfo();
        car2.displayVehicleInfo();
        truck1.displayVehicleInfo();
        displayFleetStatusFooter();

        System.out.println("\n--- Processing Rentals ---");
        car1.rentVehicle(5);
        car2.rentVehicle(3);
        car2.rentVehicle(2);
        truck1.returnVehicle();
        System.out.println();

        System.out.println("\n--- Fleet Status After Rentals ---");
        displayFleetStatusHeader();
        car1.displayVehicleInfo();
        car2.displayVehicleInfo();
        truck1.displayVehicleInfo();
        displayFleetStatusFooter();

        Vehicle.displayCompanyStats();

        System.out.println("\n--- Processing Returns ---");
        car1.returnVehicle();

        System.out.println("\n--- Final Fleet Status ---");
        displayFleetStatusHeader();
        car1.displayVehicleInfo();
        car2.displayVehicleInfo();
        truck1.displayVehicleInfo();
        displayFleetStatusFooter();

        Vehicle.displayCompanyStats();
    }

    public static void displayFleetStatusHeader() {
        System.out.println(" -------------------------------------------------------------------------");
        System.out.printf(" | %-10s | %-12s | %-15s | %-13s | %-11s |\n", "Vehicle ID", "Brand", "Model", "Rent Per Day", "Status");
        System.out.println(" -------------------------------------------------------------------------");
    }

    public static void displayFleetStatusFooter() {
        System.out.println(" -------------------------------------------------------------------------");
    }
}

