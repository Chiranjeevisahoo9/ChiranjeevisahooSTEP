import java.util.Random;
import java.util.Scanner;
//PasswordStrengthAnalyzer
public class problem2 {

    // b. Create a method to analyze password strength using ASCII values
    public static int[] analyzePasswordChars(String password) {
        int uppercaseCount = 0;
        int lowercaseCount = 0;
        int digitCount = 0;
        int specialCharCount = 0;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            int asciiValue = (int) ch;

            // i. Count uppercase letters (ASCII 65-90)
            if (asciiValue >= 65 && asciiValue <= 90) {
                uppercaseCount++;
            }
            // ii. Count lowercase letters (ASCII 97-122)
            else if (asciiValue >= 97 && asciiValue <= 122) {
                lowercaseCount++;
            }
            // iii. Count digits (ASCII 48-57)
            else if (asciiValue >= 48 && asciiValue <= 57) {
                digitCount++;
            }
            // iv. Count special characters (other printable ASCII)
            // This broadly catches anything else that's commonly printable.
            else if (asciiValue >= 33 && asciiValue <= 126) { // Printable ASCII range
                specialCharCount++;
            }
        }
        return new int[]{uppercaseCount, lowercaseCount, digitCount, specialCharCount};
    }

    // v. Check for common patterns and sequences (simplified)
    public static boolean hasCommonPatterns(String password) {
        String lowerCasePassword = password.toLowerCase(); // For case-insensitive pattern check

        // Common sequences
        if (lowerCasePassword.contains("123") || lowerCasePassword.contains("abc") ||
            lowerCasePassword.contains("qwerty") || lowerCasePassword.contains("password")) {
            return true;
        }

        // Simple ascending/descending sequences (e.g., "abcd", "dcba")
        for (int i = 0; i < password.length() - 3; i++) { // Check for sequences of 4
            char c1 = lowerCasePassword.charAt(i);
            char c2 = lowerCasePassword.charAt(i + 1);
            char c3 = lowerCasePassword.charAt(i + 2);
            char c4 = lowerCasePassword.charAt(i + 3);

            if ((c2 == c1 + 1 && c3 == c2 + 1 && c4 == c3 + 1) || // Ascending
                (c2 == c1 - 1 && c3 == c2 - 1 && c4 == c3 - 1)) {  // Descending
                return true;
            }
        }
        return false;
    }

    // c. Create a method to calculate password strength score
    public static int calculatePasswordStrengthScore(String password) {
        int score = 0;
        int length = password.length();
        int[] charCounts = analyzePasswordChars(password);
        int uppercaseCount = charCounts[0];
        int lowercaseCount = charCounts[1];
        int digitCount = charCounts[2];
        int specialCharCount = charCounts[3];

        // i. Length points: +2 per character above 8
        if (length > 8) {
            score += (length - 8) * 2;
        }

        // ii. Character variety: +10 for each type present
        if (uppercaseCount > 0) score += 10;
        if (lowercaseCount > 0) score += 10;
        if (digitCount > 0) score += 10;
        if (specialCharCount > 0) score += 10;

        // iii. Deduct points for common patterns
        if (hasCommonPatterns(password)) {
            score -= 20; // Significant deduction
        }

        return Math.max(0, score); // Score cannot be negative
    }

    // Return strength level: Weak (0-20), Medium (21-50), Strong (51+)
    public static String getStrengthLevel(int score) {
        if (score > 50) return "Strong";
        if (score > 20) return "Medium";
        return "Weak";
    }

    // d. Create a method using StringBuilder to generate strong passwords
    public static String generateStrongPassword(int length) {
        if (length < 8) { // Minimum recommended length for a strong password
            length = 8;
            System.out.println("Warning: Minimum password length is 8. Generating a password of length 8.");
        }

        String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercase = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String specialChars = "!@#$%^&*()-_=+[]{}|;:,.<>?";
        String allChars = uppercase + lowercase + digits + specialChars;

        Random random = new Random();
        StringBuilder passwordBuilder = new StringBuilder();

        // ii. Ensure at least one character from each category (if length permits)
        if (length >= 4) { // Need at least 4 chars for one of each category
            passwordBuilder.append(uppercase.charAt(random.nextInt(uppercase.length())));
            passwordBuilder.append(lowercase.charAt(random.nextInt(lowercase.length())));
            passwordBuilder.append(digits.charAt(random.nextInt(digits.length())));
            passwordBuilder.append(specialChars.charAt(random.nextInt(specialChars.length())));
        } else {
            // If length is less than 4, we just fill randomly.
            // This case ideally won't happen if we enforce length >= 8
            for (int i = 0; i < length; i++) {
                passwordBuilder.append(allChars.charAt(random.nextInt(allChars.length())));
            }
            return passwordBuilder.toString(); // Exit early for very short passwords
        }


        // iii. Fill remaining positions with random characters
        for (int i = passwordBuilder.length(); i < length; i++) {
            passwordBuilder.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        // iv. Shuffle the password for better randomness
        // Convert to char array, shuffle, then convert back to String
        for (int i = 0; i < passwordBuilder.length(); i++) {
            int randomIndex = random.nextInt(passwordBuilder.length());
            char temp = passwordBuilder.charAt(i);
            passwordBuilder.setCharAt(i, passwordBuilder.charAt(randomIndex));
            passwordBuilder.setCharAt(randomIndex, temp);
        }

        return passwordBuilder.toString();
    }

    // e. Create a method to display analysis results in tabular format
    public static void displayAnalysisResults(String password, int length, int[] charCounts, int score, String strength) {
        System.out.printf("| %-18s | %-6d | %-10d | %-12d | %-6d | %-12d | %-6d | %-8s |%n",
                          password, length, charCounts[0], charCounts[1], charCounts[2], charCounts[3], score, strength);
    }

    // f. The main function analyzes existing passwords and generates new strong passwords
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Part 1: Analyze existing passwords
        System.out.println("--- Password Strength Analyzer ---");
        System.out.print("How many passwords do you want to analyze? ");
        int numPasswords = Integer.parseInt(scanner.nextLine());

        System.out.println("\nPassword Analysis Report:");
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-18s | %-6s | %-10s | %-12s | %-6s | %-12s | %-6s | %-8s |%n",
                          "Password", "Length", "Uppercase", "Lowercase", "Digits", "Special Chars", "Score", "Strength");
        System.out.println("-----------------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < numPasswords; i++) {
            System.out.print("Enter password " + (i + 1) + ": ");
            String password = scanner.nextLine();
            int length = password.length();
            int[] charCounts = analyzePasswordChars(password);
            int score = calculatePasswordStrengthScore(password);
            String strength = getStrengthLevel(score);
            displayAnalysisResults(password, length, charCounts, score, strength);
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------------");

        // Part 2: Generate new strong passwords
        System.out.println("\n--- Password Generator ---");
        System.out.print("Do you want to generate a strong password? (yes/no): ");
        String generateChoice = scanner.nextLine().toLowerCase();

        if (generateChoice.equals("yes")) {
            System.out.print("Enter desired password length (minimum 8 recommended): ");
            int desiredLength = Integer.parseInt(scanner.nextLine());
            String generatedPassword = generateStrongPassword(desiredLength);
            System.out.println("Generated Strong Password: " + generatedPassword);

            // Analyze the generated password for verification
            int[] genCharCounts = analyzePasswordChars(generatedPassword);
            int genScore = calculatePasswordStrengthScore(generatedPassword);
            String genStrength = getStrengthLevel(genScore);

            System.out.println("\nGenerated Password Analysis:");
            System.out.println("-----------------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-18s | %-6s | %-10s | %-12s | %-6s | %-12s | %-6s | %-8s |%n",
                              "Password", "Length", "Uppercase", "Lowercase", "Digits", "Special Chars", "Score", "Strength");
            System.out.println("-----------------------------------------------------------------------------------------------------------------");
            displayAnalysisResults(generatedPassword, generatedPassword.length(), genCharCounts, genScore, genStrength);
            System.out.println("-----------------------------------------------------------------------------------------------------------------");
        }

        scanner.close();
    }
}