// File: PaymentGateway.java

class Payment {
    public void pay() {
        System.out.println("Generic payment");
    }
}

class CreditCardPayment extends Payment {
    @Override
    public void pay() {
        System.out.println("Processing credit card payment");
    }
}

class WalletPayment extends Payment {
    @Override
    public void pay() {
        System.out.println("Processing wallet payment");
    }
}

public class PaymentGateway {
    public static void main(String[] args) {
        // 1. Create array of Payment references with CreditCardPayment and WalletPayment
        Payment[] payments = new Payment[3];
        payments[0] = new CreditCardPayment();
        payments[1] = new WalletPayment();
        payments[2] = new CreditCardPayment();
        
        // 2. Loop, call getClass().getSimpleName(), and pay()
        System.out.println("Processing payments:");
        for (Payment payment : payments) {
            System.out.println("\nPayment type: " + payment.getClass().getSimpleName());
            payment.pay();
        }
    }
}
