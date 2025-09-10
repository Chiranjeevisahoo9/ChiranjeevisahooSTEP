import java.util.Random;

class BankAccount {
    String accountHolder;
    int accountNumber;
    double balance;

    // Default constructor
    public BankAccount() {
        this.accountHolder = "Unknown";
        this.balance = 0.0;
        this.accountNumber = 0;
    }

    // Constructor with name
    public BankAccount(String accountHolder) {
        this.accountHolder = accountHolder;
        this.balance = 0.0;
        // Assigns a random 6-digit account number
        this.accountNumber = 100000 + new Random().nextInt(900000);
    }

    // Constructor with name and initial balance
    public BankAccount(String accountHolder, double initialBalance) {
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        this.accountNumber = 100000 + new Random().nextInt(900000);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            System.out.printf("Deposited Rs. %.2f into account %d. New balance: Rs. %.2f\n", amount, this.accountNumber, this.balance);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= this.balance) {
            this.balance -= amount;
            System.out.printf("Withdrew Rs. %.2f from account %d. New balance: Rs. %.2f\n", amount, this.accountNumber, this.balance);
        } else {
            System.out.println("Withdrawal failed. Invalid amount or insufficient funds.");
        }
    }

    public void displayAccount() {
        System.out.println("\n--- Account Details ---");
        System.out.println("Account Holder: " + this.accountHolder);
        System.out.println("Account Number: " + this.accountNumber);
        System.out.printf("Current Balance: Rs. %.2f\n", this.balance);
        System.out.println("-----------------------");
    }
}

public class BankAccountSystem {
    public static void main(String[] args) {
        System.out.println("=== Bank Account Management ===");

        // Create accounts
        BankAccount acc1 = new BankAccount("Suresh Kumar");
        BankAccount acc2 = new BankAccount("Priya Sharma", 5000.0);
        
        // Display initial details
        acc1.displayAccount();
        acc2.displayAccount();
        
        // Perform transactions
        System.out.println("\n--- Performing Transactions ---");
        acc1.deposit(1500.0);
        acc2.withdraw(1000.0);
        acc2.withdraw(5000.0); // This should fail
        
        // Display final details
        System.out.println("\n--- Final Account Statuses ---");
        acc1.displayAccount();
        acc2.displayAccount();
    }
}
