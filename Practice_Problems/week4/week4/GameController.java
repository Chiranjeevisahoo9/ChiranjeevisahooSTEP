public class GameController {
    private String controllerBrand;
    private String connectionType;
    private boolean hasVibration;
    private int batteryLevel;
    private double sensitivity;

    // Default constructor
    public GameController() {
        this.controllerBrand = "GenericPad";
        this.connectionType = "USB";
        this.hasVibration = true;
        this.batteryLevel = 100;
        this.sensitivity = 1.0;
    }

    // Parameterized constructor
    public GameController(String controllerBrand, String connectionType,
                          boolean hasVibration, int batteryLevel, double sensitivity) {
        this.controllerBrand = controllerBrand;
        this.connectionType = connectionType;
        this.hasVibration = hasVibration;
        this.batteryLevel = (batteryLevel >= 0 && batteryLevel <= 100) ? batteryLevel : 100;
        this.sensitivity = (sensitivity >= 0.1 && sensitivity <= 3.0) ? sensitivity : 1.0;
    }

    // Two-parameter convenience constructor
    public GameController(String brand, String connectionType) {
        this(brand, connectionType, true, 100, 1.0);
    }

    public void calibrateController() {
        System.out.println("Calibrating " + controllerBrand + " controller...");
    }

    public void displayConfiguration() {
        System.out.println("Brand: " + controllerBrand);
        System.out.println("Connection: " + connectionType);
        System.out.println("Vibration: " + (hasVibration ? "Enabled" : "Disabled"));
        System.out.println("Battery Level: " + batteryLevel + "%");
        System.out.println("Sensitivity: " + sensitivity);
    }

    public void testVibration() {
        if (hasVibration) {
            System.out.println("*BUZZ* Vibration test successful!");
        } else {
            System.out.println("Vibration disabled on this controller.");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== GAMING CONTROLLER SETUP ===");

        GameController c1 = new GameController();
        GameController c2 = new GameController("ProGamer", "Wireless", true, 85, 2.5);
        GameController c3 = new GameController("LitePad", "Bluetooth");

        c1.displayConfiguration();
        c1.calibrateController();
        c1.testVibration();

        System.out.println();
        c2.displayConfiguration();
        c2.calibrateController();
        c2.testVibration();

        System.out.println();
        c3.displayConfiguration();
        c3.calibrateController();
        c3.testVibration();
    }
}