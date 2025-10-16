
class AppConfig {
    private String appName;
    private static String companyName = "TechCorp";
    
    public AppConfig(String appName) {
        this.appName = appName;
    }
    
    // Static Nested Class
    public static class NetworkConfig {
        private String host;
        private int port;
        
        public NetworkConfig(String host, int port) {
            this.host = host;
            this.port = port;
        }
        
        // Display network config with optional access to static members
        public void displayConfig() {
            System.out.println("Network Configuration:");
            System.out.println("Host: " + host);
            System.out.println("Port: " + port);
            // Can access static members of outer class
            System.out.println("Company: " + companyName);
            // Cannot access non-static members like appName
        }
    }
}

public class AppConfigurator {
    public static void main(String[] args) {
        // Create instance of NetworkConfig, print details
        // Note: Static nested class doesn't need outer class instance
        AppConfig.NetworkConfig networkConfig = new AppConfig.NetworkConfig("localhost", 8080);
        networkConfig.displayConfig();
        
        System.out.println("\nCreating another network config:");
        AppConfig.NetworkConfig prodConfig = new AppConfig.NetworkConfig("prod.server.com", 443);
        prodConfig.displayConfig();
    }
}