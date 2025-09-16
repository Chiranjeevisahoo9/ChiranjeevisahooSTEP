public class SecureBankAccount {
    private final String accountNumber;
    private double balance;
    private int pin;
    private boolean isLocked;
    private int failedAttempts;

    private static final int MAX_FAILED_ATTEMPTS = 3;
    private static final double MIN_BALANCE = 0.0;

    public SecureBankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = Math.max(initialBalance, MIN_BALANCE);
        this.pin = 0;
        this.isLocked = false;
        this.failedAttempts = 0;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public double getBalance() {
        if (this.isLocked) {
            System.out.println("Account " + accountNumber + " is locked. Cannot check balance.");
            return -1.0;
        }
        return this.balance;
    }
    
    public boolean isAccountLocked() {
        return this.isLocked;
    }

    public void setPin(int oldPin, int newPin) {
        if (this.pin == 0 || this.pin == oldPin) {
            this.pin = newPin;
            System.out.println("PIN for account " + accountNumber + " has been set/updated.");
        } else {
            System.out.println("Incorrect old PIN. PIN change failed.");
        }
    }

    private boolean validatePin(int enteredPin) {
        if (isLocked) {
            System.out.println("Account is locked. Cannot perform operation.");
            return false;
        }
        if (this.pin == enteredPin) {
            resetFailedAttempts();
            return true;
        } else {
            incrementFailedAttempts();
            System.out.println("Invalid PIN. Attempts remaining: " + (MAX_FAILED_ATTEMPTS - failedAttempts));
            return false;
        }
    }

    public void deposit(double amount, int pin) {
        if (validatePin(pin)) {
            if (amount > 0) {
                this.balance += amount;
                System.out.printf("Successfully deposited Rs.%.2f. New balance: Rs.%.2f\n", amount, this.balance);
            } else {
                System.out.println("Deposit amount must be positive.");
            }
        }
    }
    
    public void withdraw(double amount, int pin) {
        if (validatePin(pin)) {
            if (amount <= 0) {
                 System.out.println("Withdrawal amount must be positive.");
            } else if (amount > this.balance) {
                System.out.println("Insufficient funds. Withdrawal failed.");
            } else {
                this.balance -= amount;
                System.out.printf("Successfully withdrew Rs.%.2f. New balance: Rs.%.2f\n", amount, this.balance);
            }
        }
    }

    private void lockAccount() {
        this.isLocked = true;
        System.out.println("ACCOUNT LOCKED due to too many failed PIN attempts.");
    }

    private void resetFailedAttempts() {
        this.failedAttempts = 0;
    }

    private void incrementFailedAttempts() {
        this.failedAttempts++;
        if (this.failedAttempts >= MAX_FAILED_ATTEMPTS) {
            lockAccount();
        }
    }

    public static void main(String[] args) {
        SecureBankAccount acc1 = new SecureBankAccount("SB-123", 5000.0);
        
        System.out.println("--- Setting initial PIN ---");
        acc1.setPin(0, 1234);
        
        System.out.println("\n--- Attempting Security Breach (Wrong PIN) ---");
        acc1.withdraw(100.0, 9999);
        acc1.withdraw(100.0, 8888);
        acc1.withdraw(100.0, 7777);
        
        System.out.println("\n--- Operating on Locked Account ---");
        System.out.println("Account locked status: " + acc1.isAccountLocked());
        acc1.withdraw(100.0, 1234);
        
        System.out.println("\n--- Correct Transactions ---");
        SecureBankAccount acc2 = new SecureBankAccount("SB-456", 10000.0);
        acc2.setPin(0, 5678);
        acc2.deposit(2000, 5678);
        acc2.withdraw(3000, 5678);
        System.out.printf("Final balance for acc2: Rs.%.2f\n", acc2.getBalance());
    }
}
