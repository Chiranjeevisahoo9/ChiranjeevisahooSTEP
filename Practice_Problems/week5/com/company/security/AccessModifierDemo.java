package com.company.security;

public class AccessModifierDemo {
    private int privateField;
    String defaultField;
    protected double protectedField;
    public boolean publicField;

    public AccessModifierDemo(int privateField, String defaultField, double protectedField, boolean publicField) {
        this.privateField = privateField;
        this.defaultField = defaultField;
        this.protectedField = protectedField;
        this.publicField = publicField;
    }

    private void privateMethod() {
        System.out.println("Private method called");
    }

    void defaultMethod() {
        System.out.println("Default method called");
    }

    protected void protectedMethod() {
        System.out.println("Protected method called");
    }

    public void publicMethod() {
        System.out.println("Public method called");
    }

    public void testInternalAccess() {
        System.out.println("\n--- Testing Access Within the Same Class ---");
        System.out.println("privateField: " + this.privateField);
        System.out.println("defaultField: " + this.defaultField);
        System.out.println("protectedField: " + this.protectedField);
        System.out.println("publicField: " + this.publicField);
        this.privateMethod();
        this.defaultMethod();
        this.protectedMethod();
        this.publicMethod();
        System.out.println("Conclusion: All access levels are valid within the class.");
    }

    public static void main(String[] args) {
        AccessModifierDemo demo = new AccessModifierDemo(1, "Default", 2.0, true);

        System.out.println("--- Testing Access From main() in the Same Class ---");
        System.out.println("demo.defaultField: " + demo.defaultField);
        System.out.println("demo.protectedField: " + demo.protectedField);
        System.out.println("demo.publicField: " + demo.publicField);

        demo.defaultMethod();
        demo.protectedMethod();
        demo.publicMethod();

        demo.testInternalAccess();

        SamePackageTest.testAccess();
    }
}

class SamePackageTest {
    public static void testAccess() {
        System.out.println("\n--- Testing Access From Another Class in the Same Package ---");
        AccessModifierDemo demo = new AccessModifierDemo(10, "DefaultTest", 20.0, false);

        System.out.println("demo.defaultField: " + demo.defaultField);
        System.out.println("demo.protectedField: " + demo.protectedField);
        System.out.println("demo.publicField: " + demo.publicField);

        demo.defaultMethod();
        demo.protectedMethod();
        demo.publicMethod();
        System.out.println("Conclusion: public, protected, and default members are accessible from the same package.");
    }
}

