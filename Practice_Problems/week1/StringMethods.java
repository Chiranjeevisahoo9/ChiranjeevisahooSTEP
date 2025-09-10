import java.util.Scanner;

public class StringMethods {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask user for their full name (first and last name)
        System.out.print("Enter your full name (e.g., John Doe): ");
        String fullName = scanner.nextLine();

        // Ask user for their favorite programming language
        System.out.print("Enter your favorite programming language: ");
        String language = scanner.nextLine();

        // Ask user for a sentence about their programming experience
        System.out.print("Enter a sentence about your programming experience: ");
        String sentence = scanner.nextLine();

        // Process the input:
        // 1. Extract first and last name separately
        String firstName = "";
        String lastName = "";
        int spaceIndex = fullName.indexOf(' ');
        if (spaceIndex != -1) {
            firstName = fullName.substring(0, spaceIndex);
            lastName = fullName.substring(spaceIndex + 1);
        } else {
            firstName = fullName; // Handle single-name input
        }

        // 2. Count total characters in the sentence (excluding spaces)
        int charCount = sentence.replace(" ", "").length();

        // 3. Convert programming language to uppercase
        String upperCaseLanguage = language.toUpperCase();

        // 4. Display a formatted summary
        System.out.println("\n--- PROCESSING COMPLETE ---");
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + (lastName.isEmpty() ? "N/A" : lastName));
        System.out.println("Favorite Language (in uppercase): " + upperCaseLanguage);
        System.out.println("Your experience sentence has " + charCount + " characters (excluding spaces).");
        System.out.println("---------------------------");

        scanner.close();
    }
}
