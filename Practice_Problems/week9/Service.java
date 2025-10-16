interface Notifier {
    void send(String message);
}

class Service {
    public void triggerAlert() {
        // Create Notifier instance using anonymous inner class
        Notifier notifier = new Notifier() {
            @Override
            public void send(String message) {
                System.out.println("ALERT: " + message);
                System.out.println("Notification sent successfully!");
            }
        };
        
        // Send alert using the anonymous inner class
        notifier.send("System maintenance scheduled at 2:00 AM");
    }
    
    public static void main(String[] args) {
        new Service().triggerAlert();
        
        // Additional example: Creating another anonymous inner class inline
        System.out.println("\nSending another notification:");
        Notifier urgentNotifier = new Notifier() {
            @Override
            public void send(String message) {
                System.out.println("URGENT: " + message.toUpperCase());
            }
        };
        urgentNotifier.send("Security breach detected!");
    }
}