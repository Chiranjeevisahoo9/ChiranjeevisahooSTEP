import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Abstract base class for all employee types
abstract class Employee {
    String empId;
    String empName;
    String designation;
    boolean[] attendanceRecord; // 30 days

    public Employee(String empId, String empName, String designation) {
        this.empId = empId;
        this.empName = empName;
        this.designation = designation;
        this.attendanceRecord = new boolean[30]; // false by default (absent)
    }

    public void markAttendance(int day, boolean present) {
        if (day >= 1 && day <= 30) {
            attendanceRecord[day - 1] = present;
        } else {
            System.out.println("Invalid day for attendance marking.");
        }
    }
    
    public int getDaysPresent() {
        int count = 0;
        for(boolean day : attendanceRecord) {
            if(day) count++;
        }
        return count;
    }
    
    // Abstract method to be implemented by subclasses
    public abstract double calculateSalary();
    
    public void generatePaySlip() {
        System.out.println("\n---------- PAY SLIP ----------");
        System.out.println("Employee ID: " + empId);
        System.out.println("Employee Name: " + empName);
        System.out.println("Designation: " + designation);
        System.out.println("Days Present: " + getDaysPresent() + "/30");
        System.out.printf("Calculated Salary: $%.2f\n", calculateSalary());
        System.out.println("----------------------------\n");
    }
}

class FullTimeEmployee extends Employee {
    private double baseSalary;
    private double performanceBonus;

    public FullTimeEmployee(String empId, String empName, String designation, double baseSalary) {
        super(empId, empName, designation);
        this.baseSalary = baseSalary;
        this.performanceBonus = 0;
    }

    public void setPerformanceBonus(double bonus) {
        this.performanceBonus = bonus;
    }

    @Override
    public double calculateSalary() {
        // Full salary regardless of attendance, but bonus may depend on it
        // Simplified: bonus is given if present for more than 20 days
        if(getDaysPresent() > 20) {
             return baseSalary + performanceBonus;
        }
        return baseSalary; // No bonus if attendance is low
    }
}

class PartTimeEmployee extends Employee {
    private double hourlyRate;
    private int hoursWorked;

    public PartTimeEmployee(String empId, String empName, String designation, double hourlyRate) {
        super(empId, empName, designation);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = 0;
    }
    
    public void setHoursWorked(int hours){
        this.hoursWorked = hours;
    }

    @Override
    public double calculateSalary() {
        // Salary is based on hours worked
        return hourlyRate * hoursWorked;
    }
}

class ContractEmployee extends Employee {
    private double contractAmount;

    public ContractEmployee(String empId, String empName, String designation, double contractAmount) {
        super(empId, empName, designation);
        this.contractAmount = contractAmount;
    }

    @Override
    public double calculateSalary() {
        // Fixed amount as per contract
        return contractAmount;
    }
}

class Department {
    String deptId;
    String deptName;
    List<Employee> employees;

    public Department(String deptId, String deptName) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.employees = new ArrayList<>();
    }

    public void addEmployee(Employee emp) {
        employees.add(emp);
    }
}

class Company {
    // Static variables
    public static String companyName = "Gemini Corp";
    public static int workingDaysPerMonth = 22; // Assumed
    
    private List<Department> departments;
    private List<Employee> allEmployees;

    public Company() {
        this.departments = new ArrayList<>();
        this.allEmployees = new ArrayList<>();
    }
    
    public void addDepartment(Department dept) { departments.add(dept); }
    public void addEmployee(Employee emp, Department dept) {
        allEmployees.add(emp);
        if(dept != null) {
            dept.addEmployee(emp);
        }
    }
    
    // Static-like methods
    public void calculateCompanyPayroll() {
        double totalSalaryExpense = 0;
        for (Employee emp : allEmployees) {
            totalSalaryExpense += emp.calculateSalary();
        }
        System.out.printf("\nTotal Company Payroll for the Month: $%.2f\n", totalSalaryExpense);
    }
    
    public void getDepartmentWiseExpenses() {
        System.out.println("\n--- Department-wise Expenses ---");
        for (Department dept : departments) {
            double deptTotal = 0;
            for (Employee emp : dept.employees) {
                deptTotal += emp.calculateSalary();
            }
            System.out.printf("Department: %-15s | Total Expense: $%.2f\n", dept.deptName, deptTotal);
        }
    }
    
    public void getAttendanceReport(String empId) {
        Employee employee = allEmployees.stream().filter(e -> e.empId.equals(empId)).findFirst().orElse(null);
        if (employee != null) {
            System.out.println("\n--- Attendance Report for " + employee.empName + " ---");
            System.out.println("Days Present: " + employee.getDaysPresent() + "/" + employee.attendanceRecord.length);
            System.out.println(Arrays.toString(employee.attendanceRecord));
        } else {
            System.out.println("Employee with ID " + empId + " not found.");
        }
    }
}

public class EmployeePayrollSystem {
    public static void main(String[] args) {
        Company company = new Company();
        
        Department tech = new Department("D01", "Technology");
        Department hr = new Department("D02", "Human Resources");
        company.addDepartment(tech);
        company.addDepartment(hr);

        // Create employees of different types
        FullTimeEmployee emp1 = new FullTimeEmployee("E001", "Alice", "Software Engineer", 6000);
        emp1.setPerformanceBonus(500);
        
        FullTimeEmployee emp2 = new FullTimeEmployee("E002", "Bob", "HR Manager", 5500);
        emp2.setPerformanceBonus(300);

        PartTimeEmployee emp3 = new PartTimeEmployee("E003", "Charlie", "Intern", 20);
        emp3.setHoursWorked(80); // 80 hours in the month

        ContractEmployee emp4 = new ContractEmployee("E004", "Diana", "Consultant", 4000);
        
        company.addEmployee(emp1, tech);
        company.addEmployee(emp2, hr);
        company.addEmployee(emp3, tech);
        company.addEmployee(emp4, null); // Consultant not in a dept

        // Mark attendance
        // Alice has good attendance
        for(int i = 1; i <= 25; i++) emp1.markAttendance(i, true);
        // Bob has poor attendance
        for(int i = 1; i <= 15; i++) emp2.markAttendance(i, true);
        
        System.out.println("Welcome to " + Company.companyName + " Payroll System\n");

        // --- Generate individual payslips ---
        System.out.println("--- Generating Individual Pay Slips ---");
        emp1.generatePaySlip(); // Should get bonus
        emp2.generatePaySlip(); // Should not get bonus
        emp3.generatePaySlip();
        emp4.generatePaySlip();
        
        // --- Generate Company-wide Reports ---
        company.calculateCompanyPayroll();
        company.getDepartmentWiseExpenses();
        
        // --- Generate an individual attendance report ---
        company.getAttendanceReport("E002");
    }
}