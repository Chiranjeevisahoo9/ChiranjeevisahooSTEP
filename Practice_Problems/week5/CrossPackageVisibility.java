class AccessModifierDemo {
    private int privateField = 1;
    String defaultField = "Default";
    protected double protectedField = 2.0;
    public boolean publicField = true;

    public AccessModifierDemo() {}

    protected void protectedMethod() {
        System.out.println("Protected method from parent called.");
    }
    public void publicMethod() {
        System.out.println("Public method from parent called.");
    }
}

class ExtendedDemo extends AccessModifierDemo {
    public ExtendedDemo() {
        super();
    }

    public void testInheritedAccess() {
        System.out.println("\n--- Testing Access From a Subclass in a Different Package ---");
        System.out.println("this.protectedField: " + this.protectedField);
        System.out.println("this.publicField: " + this.publicField);

        this.protectedMethod();
        this.publicMethod();
        System.out.println("Conclusion: Subclasses can access public and protected members.");
    }

    @Override
    public void protectedMethod() {
        System.out.println("Overridden protected method in child class called.");
    }
}

public class CrossPackageVisibility {
    public static void main(String[] args) {
        System.out.println("--- Testing Access From a Non-Subclass in a Different Package ---");
        AccessModifierDemo demo = new AccessModifierDemo();

        System.out.println("demo.publicField: " + demo.publicField);

        demo.publicMethod();
        System.out.println("Conclusion: Only public members are accessible from a different package by non-subclasses.");


        ExtendedDemo extended = new ExtendedDemo();
        extended.testInheritedAccess();
        extended.protectedMethod();
    }
}

