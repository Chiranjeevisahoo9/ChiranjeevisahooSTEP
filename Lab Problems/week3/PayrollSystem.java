class Employee {
    private String empId;
    private String empName;
    private String department;
    private double baseSalary;
    private String empType;

    private static int totalEmployees = 0;
    private static int nextEmpId = 1;

    public Employee(String empName, String department, double monthlySalary) {
        this.empId = generateEmployeeId();
        this.empName = empName;
        this.department = department;
        this.baseSalary = monthlySalary;
        this.empType = "Full-Time";
        totalEmployees++;
    }

    public Employee(String empName, String department, double hourlyRate, int hoursWorked) {
        this.empId = generateEmployeeId();
        this.empName = empName;
        this.department = department;
        this.baseSalary = hourlyRate;
        this.empType = "Part-Time";
        totalEmployees++;
    }

    public Employee(String empName, String department, double contractAmount, String contractType) {
        this.empId = generateEmployeeId();
        this.empName = empName;
        this.department = department;
        this.baseSalary = contractAmount;
        this.empType = "Contract";
        totalEmployees++;
    }

    public double calculateSalary(double bonus) {
        return this.baseSalary + bonus;
    }

    public double calculateSalary(int hoursWorked) {
        return this.baseSalary * hoursWorked;
    }

    public double calculateSalary() {
        return this.baseSalary;
    }

    public double calculateTax(double salary, double taxRate) {
        return salary * taxRate;
    }

    public void generatePaySlip() {
        System.out.println("----------- PAY SLIP -----------");
        displayEmployeeInfo();

        double grossSalary = 0;
        double taxRate = 0;

        switch (this.empType) {
            case "Full-Time":
                double bonus = 500.0;
                grossSalary = calculateSalary(bonus);
                taxRate = 0.20;
                System.out.printf("  Bonus:              $%.2f\n", bonus);
                break;
            case "Part-Time":
                int hours = 80;
                grossSalary = calculateSalary(hours);
                taxRate = 0.15;
                System.out.println("  Hours Worked:       " + hours);
                System.out.printf("  Hourly Rate:        $%.2f\n", this.baseSalary);
                break;
            case "Contract":
                grossSalary = calculateSalary();
                taxRate = 0.18;
                break;
        }

        double tax = calculateTax(grossSalary, taxRate);
        double netSalary = grossSalary - tax;

        System.out.printf("  Gross Salary:       $%.2f\n", grossSalary);
        System.out.printf("  Tax (%.0f%%):          -$%.2f\n", taxRate * 100, tax);
        System.out.println("  ----------------------------");
        System.out.printf("  Net Salary:         $%.2f\n", netSalary);
        System.out.println("------------------------------\n");
    }

    public void displayEmployeeInfo() {
        System.out.println("  Employee ID:        " + this.empId);
        System.out.println("  Name:               " + this.empName);
        System.out.println("  Department:         " + this.department);
        System.out.println("  Type:               " + this.empType);
    }

    private static String generateEmployeeId() {
        return "EMP" + String.format("%04d", nextEmpId++);
    }

    public static int getTotalEmployees() {
        return totalEmployees;
    }
}

public class PayrollSystem {
    public static void main(String[] args) {
        System.out.println("--- Employee Payroll System ---\n");

        Employee fullTimeEmp = new Employee("Diana Prince", "Management", 4500.00);
        Employee partTimeEmp = new Employee("Peter Parker", "Logistics", 25.50, 80);
        Employee contractEmp = new Employee("Tony Stark", "Engineering", 7000.00, "Fixed-Term");

        System.out.println("--- Generating Individual Pay Slips ---\n");

        fullTimeEmp.generatePaySlip();
        partTimeEmp.generatePaySlip();
        contractEmp.generatePaySlip();

        System.out.println("--- Company-Wide Payroll Report ---");
        System.out.println("Total Number of Employees: " + Employee.getTotalEmployees());
        System.out.println("-----------------------------------");
    }
}