import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class EmployeeBean implements Serializable {
    private String employeeId;
    private String firstName;
    private String lastName;
    private double salary;
    private String department;
    private Date hireDate;
    private boolean isActive;

    public EmployeeBean() {
    }

    public EmployeeBean(String employeeId, String firstName, String lastName, double salary, String department, Date hireDate, boolean isActive) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.department = department;
        this.hireDate = hireDate;
        this.isActive = isActive;
    }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public double getSalary() { return salary; }
    public void setSalary(double salary) {
        if (salary >= 0) {
            this.salary = salary;
        } else {
            System.err.println("Salary cannot be negative.");
        }
    }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public Date getHireDate() { return hireDate; }
    public void setHireDate(Date hireDate) { this.hireDate = hireDate; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public String getFullName() {
        return (firstName != null ? firstName : "") + " " + (lastName != null ? lastName : "");
    }
    
    public long getYearsOfService() {
        if (this.hireDate == null) return 0;
        long diffInMillis = new Date().getTime() - this.hireDate.getTime();
        return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS) / 365;
    }
    
    public String getFormattedSalary() {
        return NumberFormat.getCurrencyInstance().format(this.salary);
    }
    
    @Override
    public String toString() {
        return "EmployeeBean{" + "employeeId='" + employeeId + '\'' + ", fullName='" + getFullName() + '\'' + ", salary=" + getFormattedSalary() + ", department='" + department + '\'' + ", yearsOfService=" + getYearsOfService() + ", isActive=" + isActive + '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeBean that = (EmployeeBean) o;
        return Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId);
    }

    public static void main(String[] args) {
        EmployeeBean emp1 = new EmployeeBean();
        emp1.setEmployeeId("E101");
        emp1.setFirstName("Suresh");
        emp1.setLastName("Kumar");
        emp1.setDepartment("IT");
        emp1.setSalary(80000.0);
        emp1.setHireDate(new Date(System.currentTimeMillis() - 1000L * 3600 * 24 * 365 * 5));
        emp1.setActive(true);

        EmployeeBean emp2 = new EmployeeBean("E102", "Priya", "Sharma", 120000.0, "HR", new Date(), true);
        
        System.out.println("--- Employee Details ---");
        System.out.println(emp1);
        System.out.println(emp2);

        System.out.println("\n--- Testing Computed Properties ---");
        System.out.println("Employee 1 Full Name: " + emp1.getFullName());
        System.out.println("Employee 1 Years of Service: " + emp1.getYearsOfService());

        List<EmployeeBean> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        employees.add(new EmployeeBean("E103", "Amit", "Patel", 95000.0, "IT", new Date(), false));
        
        System.out.println("\n--- Sorting Employees by Salary (Highest First) ---");
        employees.sort(Comparator.comparingDouble(EmployeeBean::getSalary).reversed());
        employees.forEach(System.out::println);

        System.out.println("\n--- Filtering Active Employees ---");
        employees.stream()
                 .filter(EmployeeBean::isActive)
                 .forEach(e -> System.out.println(e.getFullName() + " is active."));
    }
}


