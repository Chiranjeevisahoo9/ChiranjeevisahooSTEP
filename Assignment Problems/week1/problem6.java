import java.util.Scanner;
//PalindromeChecker
public class problem6 {

    // Logic 1: Iterative method to compare characters from start and end
    public static boolean isPalindromeLogic1(String text) {
        // Step i: Set the start and end indexes of the text
        int start = 0;
        int end = text.length() - 1;

        // Step ii: Loop through the text and compare characters
        while (start < end) {
            if (text.charAt(start) != text.charAt(end)) {
                return false; // Characters are not equal, not a palindrome.
            }
            start++; // Move start index forward.
            end--;   // Move end index backward.
        }
        return true; // All characters matched, it's a palindrome.
    }

    // Logic 2: Recursive method to compare characters from start and end
    public static boolean isPalindromeLogic2(String text, int start, int end) {
        // Step i: Base case: if start index is greater than or equal to end index, it's a palindrome.
        if (start >= end) {
            return true;
        }

        // Step ii: If characters at start and end are not equal, it's not a palindrome.
        if (text.charAt(start) != text.charAt(end)) {
            return false;
        }

        // Step iii: Otherwise, call the method recursively with incremented start and decremented end.
        return isPalindromeLogic2(text, start + 1, end - 1);
    }

    // Helper method to reverse a string using charAt() and return as a char array
    private static char[] reverseStringToArray(String text) {
        int length = text.length();
        char[] reversedArray = new char[length];
        for (int i = 0; i < length; i++) {
            reversedArray[i] = text.charAt(length - 1 - i);
        }
        return reversedArray;
    }

    // Logic 3: Method using character arrays to compare original and reversed
    public static boolean isPalindromeLogic3(String text) {
        // Step i: Call the helper method to reverse the string and get a char array.
        char[] reversedChars = reverseStringToArray(text);

        // Step ii: Create a character array from the original string.
        char[] originalChars = text.toCharArray();

        // Compare the characters in the original and reverse arrays
        for (int i = 0; i < originalChars.length; i++) {
            if (originalChars[i] != reversedChars[i]) {
                return false; // Mismatch found, not a palindrome.
            }
        }
        return true; // All characters matched, it's a palindrome.
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Palindrome Checker ---");
        System.out.print("Enter a string: ");
        String userInput = scanner.nextLine();

        // Perform palindrome check using all three logics and display results
        System.out.println("\nChecking with Logic 1 (Iterative Comparison):");
        if (isPalindromeLogic1(userInput)) {
            System.out.println("'" + userInput + "' is a palindrome.");
        } else {
            System.out.println("'" + userInput + "' is NOT a palindrome.");
        }

        System.out.println("\nChecking with Logic 2 (Recursive Comparison):");
        if (isPalindromeLogic2(userInput, 0, userInput.length() - 1)) {
            System.out.println("'" + userInput + "' is a palindrome.");
        } else {
            System.out.println("'" + userInput + "' is NOT a palindrome.");
        }

        System.out.println("\nChecking with Logic 3 (Reverse Array Comparison):");
        if (isPalindromeLogic3(userInput)) {
            System.out.println("'" + userInput + "' is a palindrome.");
        } else {
            System.out.println("'" + userInput + "' is NOT a palindrome.");
        }

        scanner.close();
    }
}