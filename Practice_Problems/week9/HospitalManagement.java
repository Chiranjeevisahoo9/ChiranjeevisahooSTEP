class Hospital {
    private String name;
    
    public Hospital(String name) {
        this.name = name;
    }
    
    // Member Inner Class
    public class Department {
        private String deptName;
        
        public Department(String deptName) {
            this.deptName = deptName;
        }
        
        // Display department info along with hospital name
        public void displayInfo() {
            System.out.println("Department: " + deptName + " at " + name);
        }
    }
    
    // Method to create Department object
    public Department createDepartment(String deptName) {
        return new Department(deptName);
    }
}

public class HospitalManagement {
    public static void main(String[] args) {
        // 1. Create Hospital with 2 Departments, display info
        Hospital hospital = new Hospital("City General Hospital");
        
        // Method 1: Using the createDepartment method
        Hospital.Department dept1 = hospital.createDepartment("Cardiology");
        
        // Method 2: Direct instantiation (requires outer instance)
        Hospital.Department dept2 = hospital.new Department("Neurology");
        
        System.out.println("Hospital Departments:");
        dept1.displayInfo();
        dept2.displayInfo();
    }
}