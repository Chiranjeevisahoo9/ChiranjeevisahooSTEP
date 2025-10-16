// File: EmployeeAuth.java

import java.util.HashSet;
import java.util.Objects;

class Employee {
    private String empCode;
    private String name;
    
    public Employee(String empCode, String name) {
        this.empCode = empCode;
        this.name = name;
    }
    
    // Override equals() - same empCode means same employee
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Employee employee = (Employee) obj;
        return Objects.equals(empCode, employee.empCode);
    }
    
    // Override hashCode() based on empCode
    @Override
    public int hashCode() {
        return Objects.hash(empCode);
    }
    
    // Write toString() showing both fields
    @Override
    public String toString() {
        return "Employee{empCode='" + empCode + "', name='" + name + "'}";
    }
}

public class EmployeeAuth {
    public static void main(String[] args) {
        // 1. Employee e1 = new Employee("BL001", "Ritika");
        Employee e1 = new Employee("BL001", "Ritika");
        
        // 2. Employee e2 = new Employee("BL001", "Ritika S.");
        Employee e2 = new Employee("BL001", "Ritika S.");
        
        // 3. Compare using e1 == e2 and e1.equals(e2)
        System.out.println("e1 == e2: " + (e1 == e2)); // false (different references)
        System.out.println("e1.equals(e2): " + e1.equals(e2)); // true (same empCode)
        
        System.out.println("\ne1: " + e1);
        System.out.println("e2: " + e2);
        
        // 4. Test using HashSet<Employee>
        HashSet<Employee> employees = new HashSet<>();
        employees.add(e1);
        employees.add(e2); // Won't be added because e1 and e2 are equal
        
        System.out.println("\nHashSet size: " + employees.size()); // 1
        System.out.println("HashSet contains e1: " + employees.contains(e1)); // true
        System.out.println("HashSet contains e2: " + employees.contains(e2)); // true
    }
}