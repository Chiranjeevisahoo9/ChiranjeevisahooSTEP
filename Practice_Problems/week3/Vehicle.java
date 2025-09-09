class Car extends Vehicle {
    private int numberOfDoors;

    public Car(String make, String model, int year, int doors) {
        super(make, model, year);
        this.numberOfDoors = doors;
    }

    // Overriding the display method to add Car-specific info
    @Override
    public void displayVehicleInfo() {
        super.displayVehicleInfo(); // Reuse the parent's method
        System.out.println("Type: Car, Doors: " + this.numberOfDoors);
    }
}

class Truck extends Vehicle {
    private double payloadCapacity; // in tons

    public Truck(String make, String model, int year, double capacity) {
        super(make, model, year);
        this.payloadCapacity = capacity;
    }

    @Override
    public void displayVehicleInfo() {
        super.displayVehicleInfo();
        System.out.println("Type: Truck, Payload Capacity: " + this.payloadCapacity + " tons");
    }
}

class Motorcycle extends Vehicle {
    private boolean hasSidecar;

    public Motorcycle(String make, String model, int year, boolean sidecar) {
        super(make, model, year);
        this.hasSidecar = sidecar;
    }

    @Override
    public void displayVehicleInfo() {
        super.displayVehicleInfo();
        System.out.println("Type: Motorcycle, Has Sidecar: " + this.hasSidecar);
    }
}


public class Vehicle {
    // Protected instance variables are accessible by subclasses
    protected String make;
    protected String model;
    protected int year;
    protected double fuelLevel; // percentage

    // Constructor
    public Vehicle(String make, String model, int year) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.fuelLevel = 100.0; // Assume full tank on creation
    }

    // Common methods
    public void startVehicle() {
        System.out.println(this.make + " " + this.model + " is starting.");
    }

    public void stopVehicle() {
        System.out.println(this.make + " " + this.model + " is stopping.");
    }

    public void refuel(double amount) {
        this.fuelLevel += amount;
        if (this.fuelLevel > 100.0) {
            this.fuelLevel = 100.0;
        }
        System.out.printf("Refueled. Current fuel level: %.1f%%\n", this.fuelLevel);
    }

    public void displayVehicleInfo() {
        System.out.println("\n--- Vehicle Info ---");
        System.out.println("Make: " + this.make + ", Model: " + this.model + ", Year: " + this.year);
        System.out.printf("Fuel Level: %.1f%%\n", this.fuelLevel);
    }

    public static void main(String[] args) {
        // Create different types of vehicles
        Car myCar = new Car("Toyota", "Camry", 2023, 4);
        Truck myTruck = new Truck("Ford", "F-150", 2022, 1.5);
        Motorcycle myMotorcycle = new Motorcycle("Harley-Davidson", "Street Glide", 2021, false);

        // Create an array of Vehicle objects to hold different types
        Vehicle[] fleet = { myCar, myTruck, myMotorcycle };
        
        System.out.println("--- Demonstrating Polymorphism ---");
        // Demonstrate polymorphic behavior: calling the same method on different objects
        for (Vehicle v : fleet) {
            v.displayVehicleInfo(); // Calls the overridden method specific to each object's actual type
            v.startVehicle();
            System.out.println("---------------------------------");
        }
        
        // In comments, explain:
        // - How does this show reusability?
        //   Reusability is shown because all subclasses (Car, Truck, Motorcycle) reuse the
        //   fields (make, model, etc.) and methods (startVehicle, stopVehicle) from the
        //   base Vehicle class without rewriting them. The common logic is centralized.

        // - How could this be extended for new vehicle types?
        //   To extend this, you could simply create a new class like 'public class Bus extends Vehicle'
        //   or 'public class ElectricCar extends Vehicle'. The new class would instantly inherit
        //   all the common vehicle functionality and only need to implement its unique features.

        // - What are the benefits over writing separate classes?
        //   The benefits are significant:
        //   1. Reduced Code Duplication: Avoids rewriting the same code for each vehicle type.
        //   2. Easier Maintenance: A change in a common method (e.g., refuel logic) only
        //      needs to be made in one place (the Vehicle class).
        //   3. Polymorphism: Allows treating different objects in a uniform way, as shown by the
        //      'fleet' array. We can manage a collection of different vehicles with the same code.
    }
}