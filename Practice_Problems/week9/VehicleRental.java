// File: VehicleRental.java

class Vehicle {
    private String registrationNo;
    private String type;
    private double ratePerDay;
    
    // Constructor initializing all fields
    public Vehicle(String registrationNo, String type, double ratePerDay) {
        this.registrationNo = registrationNo;
        this.type = type;
        this.ratePerDay = ratePerDay;
    }
    
    // Override toString()
    @Override
    public String toString() {
        return "Vehicle: " + registrationNo + ", Type: " + type + ", Rate: $" + ratePerDay + "/day";
    }
    
    // Getters for all fields
    public String getRegistrationNo() {
        return registrationNo;
    }
    
    public String getType() {
        return type;
    }
    
    public double getRatePerDay() {
        return ratePerDay;
    }
}

public class VehicleRental {
    public static void main(String[] args) {
        // 1. Create Vehicle("MH12AB1234", "Sedan", 1500)
        Vehicle vehicle1 = new Vehicle("MH12AB1234", "Sedan", 1500);
        
        // 2. Print the Vehicle object and observe output
        System.out.println(vehicle1);
        
        // 3. Create another vehicle and compare
        Vehicle vehicle2 = new Vehicle("KA01CD5678", "SUV", 2500);
        System.out.println(vehicle2);
        
        System.out.println("\nComparison:");
        System.out.println("vehicle1 registration: " + vehicle1.getRegistrationNo());
        System.out.println("vehicle2 registration: " + vehicle2.getRegistrationNo());
    }
}
