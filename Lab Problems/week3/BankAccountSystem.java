class BankAccount {
    private String accountNumber;
    private String accountHolderName;
    private double balance;

    private static int totalAccounts = 0;
    private static int nextAccountNumber = 1;

    public BankAccount(String accountHolderName, double initialDeposit) {
        this.accountHolderName = accountHolderName;
        this.balance = (initialDeposit > 0) ? initialDeposit : 0;
        this.accountNumber = generateAccountNumber();
        totalAccounts++;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            System.out.println("Deposit successful. New balance for " + accountNumber + ": $" + String.format("%.2f", this.balance));
        } else {
            System.out.println("Invalid deposit amount. Please enter a positive value.");
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount. Please enter a positive value.");
        } else if (amount > this.balance) {
            System.out.println("Insufficient funds for withdrawal from " + accountNumber + ". Current balance: $" + String.format("%.2f", this.balance));
        } else {
            this.balance -= amount;
            System.out.println("Withdrawal successful. New balance for " + accountNumber + ": $" + String.format("%.2f", this.balance));
        }
    }

    public void checkBalance() {
        System.out.println("The balance for account " + accountNumber + " is $" + String.format("%.2f", this.balance));
    }

    public void displayAccountInfo() {
        System.out.println("===================================");
        System.out.println("Account Information:");
        System.out.println("  Account Number:    " + this.accountNumber);
        System.out.println("  Account Holder:    " + this.accountHolderName);
        System.out.println("  Current Balance:   $" + String.format("%.2f", this.balance));
        System.out.println("===================================");
    }

    private static String generateAccountNumber() {
        return "ACC" + String.format("%03d", nextAccountNumber++);
    }

    public static int getTotalAccounts() {
        return totalAccounts;
    }
    
    public String getAccountNumberForDemo() { return this.accountNumber; }
    public double getBalanceForDemo() { return this.balance; }
}

public class BankAccountSystem {
    public static void main(String[] args) {
        System.out.println("--- Welcome to the Simple Bank Management System ---");
        System.out.println("Initially, Total Bank Accounts: " + BankAccount.getTotalAccounts());
        System.out.println();

        BankAccount[] accounts = new BankAccount[3];

        System.out.println("--- Creating Accounts ---");
        accounts[0] = new BankAccount("Alice Johnson", 500.00);
        accounts[1] = new BankAccount("Bob Williams", 1200.50);
        accounts[2] = new BankAccount("Charlie Brown", 150.75);

        for (BankAccount acc : accounts) {
            acc.displayAccountInfo();
        }

        System.out.println("\n--- Performing Transactions ---");
        accounts[0].deposit(150.00);
        accounts[1].withdraw(200.00);
        accounts[2].withdraw(200.00);
        accounts[0].withdraw(-50.00);
        System.out.println();

        System.out.println("--- Final Account States ---");
        for (BankAccount acc : accounts) {
            acc.displayAccountInfo();
        }

        System.out.println("\n--- Static vs. Instance Variable Demonstration ---");
        System.out.println("The 'balance' is an INSTANCE variable. Each account has its own balance:");
        System.out.println("  - Balance of " + accounts[0].getAccountNumberForDemo() + ": $" + String.format("%.2f", accounts[0].getBalanceForDemo()));
        System.out.println("  - Balance of " + accounts[1].getAccountNumberForDemo() + ": $" + String.format("%.2f", accounts[1].getBalanceForDemo()));

        System.out.println("\n'totalAccounts' is a STATIC variable. It's shared by all objects of the class.");
        System.out.println("Total number of accounts created (accessed via static method): " + BankAccount.getTotalAccounts());
    }
}
