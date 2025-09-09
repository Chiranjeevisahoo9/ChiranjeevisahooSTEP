public class Student {
    // Private instance variables
    private String studentId;
    private String name;
    private double grade;
    private String course;

    // Default constructor (no parameters)
    public Student() {
        this.studentId = "N/A";
        this.name = "Unknown";
        this.grade = 0.0;
        this.course = "Undeclared";
    }

    // Parameterized constructor that accepts all attributes
    public Student(String studentId, String name, double grade, String course) {
        this.studentId = studentId;
        this.name = name;
        this.grade = grade;
        this.course = course;
    }

    // Getter and setter methods for all attributes
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        if (grade >= 0 && grade <= 100) {
            this.grade = grade;
        } else {
            System.out.println("Invalid grade. Must be between 0 and 100.");
        }
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    // Method to calculate letter grade
    public char calculateLetterGrade() {
        if (grade >= 90) return 'A';
        if (grade >= 80) return 'B';
        if (grade >= 70) return 'C';
        if (grade >= 60) return 'D';
        return 'F';
    }

    // Method to display all student information
    public void displayStudent() {
        System.out.println("\n--- Student Information ---");
        System.out.println("ID: " + this.studentId);
        System.out.println("Name: " + this.name);
        System.out.println("Course: " + this.course);
        System.out.printf("Grade: %.2f\n", this.grade);
        System.out.println("Letter Grade: " + this.calculateLetterGrade());
        System.out.println("---------------------------");
    }

    public static void main(String[] args) {
        // Create one student using the default constructor, then set values
        System.out.println("Creating student with default constructor...");
        Student student1 = new Student();
        student1.displayStudent(); // Display initial state
        
        System.out.println("\nSetting values for student1...");
        student1.setStudentId("S101");
        student1.setName("Alice");
        student1.setCourse("Computer Science");
        student1.setGrade(92.5);

        // Create another student using the parameterized constructor
        System.out.println("\nCreating student with parameterized constructor...");
        Student student2 = new Student("S102", "Bob", 78.0, "Mathematics");

        // Demonstrate all getter/setter methods and show final information
        System.out.println("\n--- Final Student Details ---");
        System.out.println("Student 1 Name (Getter): " + student1.getName());
        student1.displayStudent();
        student2.displayStudent();
    }
}