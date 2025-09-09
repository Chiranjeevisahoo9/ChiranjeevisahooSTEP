import java.util.UUID;

class PersonalAccount {
    private static int totalAccounts = 0;
    private static String bankName;

    private String accountHolderName;
    private String accountNumber;
    private double currentBalance;
    private double totalIncome;
    private double totalExpenses;

    public PersonalAccount(String accountHolderName, double initialDeposit) {
        this.accountHolderName = accountHolderName;
        this.currentBalance = initialDeposit;
        this.totalIncome = initialDeposit;
        this.totalExpenses = 0;
        this.accountNumber = generateAccountNumber();
        totalAccounts++;
    }

    public void addIncome(double amount, String description) {
        if (amount > 0) {
            this.currentBalance += amount;
            this.totalIncome += amount;
            System.out.println("Income Added: $" + amount + " (" + description + ") for " + this.accountHolderName);
        } else {
            System.out.println("Invalid income amount.");
        }
    }

    public void addExpense(double amount, String description) {
        if (amount > 0 && amount <= this.currentBalance) {
            this.currentBalance -= amount;
            this.totalExpenses += amount;
            System.out.println("Expense Added: $" + amount + " (" + description + ") for " + this.accountHolderName);
        } else if (amount > this.currentBalance) {
            System.out.println("Expense failed: Insufficient funds for " + this.accountHolderName);
        } else {
            System.out.println("Invalid expense amount.");
        }
    }

    public double calculateSavings() {
        return this.totalIncome - this.totalExpenses;
    }

    public void displayAccountSummary() {
        System.out.println("\n-------------------------------------");
        System.out.println("Bank Name: " + bankName);
        System.out.println("Account Holder: " + this.accountHolderName);
        System.out.println("Account Number: " + this.accountNumber);
        System.out.println("Current Balance: $" + String.format("%.2f", this.currentBalance));
        System.out.println("Total Income: $" + String.format("%.2f", this.totalIncome));
        System.out.println("Total Expenses: $" + String.format("%.2f", this.totalExpenses));
        System.out.println("Total Savings: $" + String.format("%.2f", this.calculateSavings()));
        System.out.println("-------------------------------------\n");
    }

    public static void setBankName(String name) {
        bankName = name;
    }

    public static int getTotalAccounts() {
        return totalAccounts;
    }

    public static String getBankName() {
        return bankName;
    }

    private static String generateAccountNumber() {
        return "ACCT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}

public class PersonalFinanceManager {
    public static void main(String[] args) {
        PersonalAccount.setBankName("Global Finance Bank");

        System.out.println("Welcome to the Personal Finance Manager!");
        System.out.println("Bank: " + PersonalAccount.getBankName());
        System.out.println("Total accounts initially: " + PersonalAccount.getTotalAccounts());

        PersonalAccount account1 = new PersonalAccount("Alice Johnson", 1500.00);
        PersonalAccount account2 = new PersonalAccount("Bob Williams", 2500.00);
        PersonalAccount account3 = new PersonalAccount("Charlie Brown", 500.00);

        System.out.println("\n--- Performing Transactions ---\n");
        account1.addIncome(500.00, "Monthly Salary");
        account1.addExpense(150.00, "Groceries");
        account1.addExpense(200.00, "Rent");
        
        account2.addIncome(1200.00, "Project Freelance");
        account2.addExpense(300.00, "Utilities");
        account2.addExpense(1000.00, "New Laptop");
        
        account3.addIncome(300.00, "Part-time Job");
        account3.addExpense(50.00, "Transport");
        account3.addExpense(600.00, "Concert Tickets");

        System.out.println("\n--- Displaying Account Summaries ---\n");
        account1.displayAccountSummary();
        account2.displayAccountSummary();
        account3.displayAccountSummary();

        System.out.println("Demonstrating static variable 'bankName':");
        System.out.println("It's the same for all accounts, accessed via an instance or the class itself.");
        System.out.println("Account 1 Bank: " + PersonalAccount.getBankName());
        System.out.println("Account 2 Bank: " + PersonalAccount.getBankName());
        System.out.println("Accessed via Class: " + PersonalAccount.getBankName());

        System.out.println("\nTotal number of accounts created: " + PersonalAccount.getTotalAccounts());
    }
}
