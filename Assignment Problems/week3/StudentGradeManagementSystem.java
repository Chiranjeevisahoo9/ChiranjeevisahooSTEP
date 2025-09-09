import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Subject {
    String subjectCode;
    String subjectName;
    int credits;

    public Subject(String subjectCode, String subjectName, int credits) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.credits = credits;
    }
}

class Student {
    // Static variables
    public static int totalStudents = 0;
    public static String schoolName = "Gemini High School";
    public static Map<String, Double> gradingScale = new HashMap<>();
    public static double passPercentage = 40.0;

    // Instance variables
    String studentId;
    String studentName;
    String className;
    Map<Subject, Double> marks; // Maps Subject object to the mark obtained
    double gpa;

    public Student(String studentName, String className) {
        this.studentId = "STU-" + (++totalStudents);
        this.studentName = studentName;
        this.className = className;
        this.marks = new HashMap<>();
        this.gpa = 0.0;
    }

    public void addMarks(Subject subject, double mark) {
        if (mark >= 0 && mark <= 100) {
            this.marks.put(subject, mark);
        } else {
            System.out.println("Invalid mark for " + subject.subjectName);
        }
    }

    private String getGrade(double percentage) {
        if (percentage >= 90) return "A";
        if (percentage >= 80) return "B";
        if (percentage >= 70) return "C";
        if (percentage >= 60) return "D";
        if (percentage >= passPercentage) return "E";
        return "F";
    }

    public void calculateGPA() {
        if (marks.isEmpty() || gradingScale.isEmpty()) {
            this.gpa = 0.0;
            return;
        }

        double totalPoints = 0;
        int totalCredits = 0;

        for (Map.Entry<Subject, Double> entry : marks.entrySet()) {
            Subject subject = entry.getKey();
            double mark = entry.getValue();
            String grade = getGrade(mark);
            
            totalPoints += gradingScale.getOrDefault(grade, 0.0) * subject.credits;
            totalCredits += subject.credits;
        }

        this.gpa = (totalCredits > 0) ? (totalPoints / totalCredits) : 0.0;
    }

    public boolean checkPromotionEligibility() {
        for (double mark : marks.values()) {
            if (mark < passPercentage) {
                return false; // Fail if any subject is below pass mark
            }
        }
        return true;
    }
    
    public double getAveragePercentage() {
        if (marks.isEmpty()) return 0.0;
        double sum = 0;
        for(double mark : marks.values()) {
            sum += mark;
        }
        return sum / marks.size();
    }

    public void generateReportCard() {
        System.out.println("\n=============================================");
        System.out.println("           " + schoolName + " - Report Card");
        System.out.println("=============================================");
        System.out.println("Student ID: " + studentId + "\tName: " + studentName);
        System.out.println("Class: " + className);
        System.out.println("---------------------------------------------");
        System.out.printf("%-20s | %-10s | %-5s\n", "Subject", "Percentage", "Grade");
        System.out.println("---------------------------------------------");
        
        marks.forEach((subject, mark) -> {
            System.out.printf("%-20s | %-10.2f | %-5s\n", subject.subjectName, mark, getGrade(mark));
        });
        
        System.out.println("---------------------------------------------");
        calculateGPA();
        System.out.printf("GPA: %.2f\n", this.gpa);
        System.out.printf("Average Percentage: %.2f%%\n", getAveragePercentage());
        System.out.println("Promotion Status: " + (checkPromotionEligibility() ? "Promoted" : "Not Promoted"));
        System.out.println("=============================================\n");
    }

    // --- Static Methods ---
    public static void setGradingScale() {
        gradingScale.put("A", 4.0);
        gradingScale.put("B", 3.0);
        gradingScale.put("C", 2.0);
        gradingScale.put("D", 1.0);
        gradingScale.put("E", 0.5);
        gradingScale.put("F", 0.0);
        System.out.println("Grading scale has been set.");
    }
    
    public static void calculateClassAverage(List<Student> students, String className) {
        double totalPercentage = 0;
        int studentCount = 0;
        for(Student s : students) {
            if(s.className.equals(className)) {
                totalPercentage += s.getAveragePercentage();
                studentCount++;
            }
        }
        if(studentCount > 0) {
            System.out.printf("Class %s Average Percentage: %.2f%%\n", className, (totalPercentage / studentCount));
        } else {
            System.out.println("No students found for class " + className);
        }
    }

    public static void getTopPerformers(List<Student> students, int count) {
        students.sort(Comparator.comparingDouble(Student::getAveragePercentage).reversed());
        System.out.println("\n--- Top " + count + " Performers ---");
        for(int i = 0; i < count && i < students.size(); i++) {
            Student s = students.get(i);
            System.out.printf("%d. %s (ID: %s, Class: %s) - Average: %.2f%%\n", i+1, s.studentName, s.studentId, s.className, s.getAveragePercentage());
        }
        System.out.println("------------------------\n");
    }
    
    public static void generateSchoolReport(List<Student> students) {
        System.out.println("\n#############################################");
        System.out.println("         " + schoolName + " - Annual Report");
        System.out.println("#############################################");
        System.out.println("Total Students: " + totalStudents);
        calculateClassAverage(students, "10A");
        calculateClassAverage(students, "10B");
        getTopPerformers(students, 3);
        System.out.println("#############################################\n");
    }
}


public class StudentGradeManagementSystem {
    public static void main(String[] args) {
        // Setup static data
        Student.setGradingScale();
        
        // Create subjects
        Subject math = new Subject("M101", "Mathematics", 4);
        Subject science = new Subject("S101", "Science", 4);
        Subject english = new Subject("E101", "English", 3);
        Subject history = new Subject("H101", "History", 3);
        
        // Create students
        List<Student> studentList = new ArrayList<>();
        Student s1 = new Student("Alice", "10A");
        s1.addMarks(math, 92);
        s1.addMarks(science, 88);
        s1.addMarks(english, 95);
        s1.addMarks(history, 85);
        studentList.add(s1);

        Student s2 = new Student("Bob", "10A");
        s2.addMarks(math, 75);
        s2.addMarks(science, 65);
        s2.addMarks(english, 80);
        s2.addMarks(history, 70);
        studentList.add(s2);

        Student s3 = new Student("Charlie", "10B");
        s3.addMarks(math, 98);
        s3.addMarks(science, 94);
        s3.addMarks(english, 89);
        s3.addMarks(history, 91);
        studentList.add(s3);
        
        Student s4 = new Student("Diana", "10B");
        s4.addMarks(math, 55);
        s4.addMarks(science, 62);
        s4.addMarks(english, 71);
        s4.addMarks(history, 35); // This will cause promotion to fail
        studentList.add(s4);

        // Generate individual report cards
        System.out.println("--- Individual Student Reports ---");
        for (Student s : studentList) {
            s.generateReportCard();
        }
        
        // Generate school-wide reports using static methods
        Student.generateSchoolReport(studentList);
    }
}