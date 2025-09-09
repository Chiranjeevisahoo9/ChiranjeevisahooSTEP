public class BankAccount {
    // Static variables (shared by all instances)
    private static String bankName;
    private static int totalAccounts = 0;
    private static double interestRate;

    // Instance variables (unique for each instance)
    private String accountNumber;
    private String accountHolder;
    private double balance;

    // Constructor
    public BankAccount(String accountHolder, double initialBalance) {
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        // Generate a simple unique account number
        this.accountNumber = "ACCT-" + (1000 + totalAccounts);
        totalAccounts++; // Increment the static counter
    }

    // Static methods
    public static void setBankName(String name) {
        bankName = name;
    }

    public static void setInterestRate(double rate) {
        interestRate = rate;
    }

    public static int getTotalAccounts() {
        return totalAccounts;
    }

    public static void displayBankInfo() {
        System.out.println("\n--- Bank Information ---");
        System.out.println("Bank Name: " + bankName);
        System.out.println("Interest Rate: " + interestRate + "%");
        System.out.println("Total Accounts: " + totalAccounts);
        System.out.println("------------------------");
    }

    // Instance methods
    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            System.out.printf("Deposited $%.2f. New balance: $%.2f\n", amount, this.balance);
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= this.balance) {
            this.balance -= amount;
            System.out.printf("Withdrew $%.2f. New balance: $%.2f\n", amount, this.balance);
        } else {
            System.out.println("Withdrawal failed. Insufficient funds or invalid amount.");
        }
    }

    public void calculateInterest() {
        double interest = this.balance * (interestRate / 100);
        this.balance += interest;
        System.out.printf("Interest of $%.2f added. New balance: $%.2f\n", interest, this.balance);
    }

    public void displayAccountInfo() {
        System.out.println("\n--- Account Details ---");
        System.out.println("Bank: " + bankName); // Can access static from instance method
        System.out.println("Account Holder: " + this.accountHolder);
        System.out.println("Account Number: " + this.accountNumber);
        System.out.printf("Balance: $%.2f\n", this.balance);
        System.out.println("-----------------------");
    }

    public static void main(String[] args) {
        // Set bank name and interest rate using static methods
        BankAccount.setBankName("Global Java Bank");
        BankAccount.setInterestRate(2.5);

        // Display bank-wide info before creating accounts
        BankAccount.displayBankInfo();

        // Create multiple BankAccount objects
        System.out.println("\nCreating accounts...");
        BankAccount acc1 = new BankAccount("John Doe", 1500.0);
        BankAccount acc2 = new BankAccount("Jane Smith", 3000.0);
        
        System.out.println("\nBank info after creating accounts:");
        BankAccount.displayBankInfo(); // Called via Class

        // Show that instance members are unique
        System.out.println("\n--- Demonstrating Unique Instance Members ---");
        acc1.displayAccountInfo();
        acc2.displayAccountInfo();
        
        // Show that static members are shared
        System.out.println("\n--- Demonstrating Shared Static Members ---");
        System.out.println("Changing bank name via one object...");
        acc1.setBankName("First National Java"); // Calling static method via instance (not recommended but possible)

        // The bank name changes for BOTH accounts
        acc1.displayAccountInfo();
        acc2.displayAccountInfo();
    }
}
