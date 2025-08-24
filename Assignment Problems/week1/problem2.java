import java.util.Scanner;
//FirstNonRepeatingCharacter
public class problem2 {

    // Method to find the first non-repeating character in a string
    public static char findFirstNonRepeatingChar(String text) {
        // Step i: Create an array to store the frequency of characters.
        // ASCII values (0-255) are used as indexes.
        int[] charFrequencies = new int[256]; // Assuming ASCII characters

        // Step ii: Loop through the text to find the frequency of each character.
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            charFrequencies[ch]++; // Increment frequency for the character at its ASCII index.
        }

        // Step iii: Loop through the text again to find the first non-repeating character.
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            // If the frequency of the current character is 1, it's the first non-repeating.
            if (charFrequencies[ch] == 1) {
                return ch; // Return the character.
            }
        }

        return '\0'; // Return null character if no non-repeating character is found.
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Find First Non-Repeating Character ---");
        System.out.print("Enter a string: ");
        String userInput = scanner.nextLine();

        // Call the user-defined method to find the first non-repeating character
        char firstNonRepeating = findFirstNonRepeatingChar(userInput);

        // Display the result
        if (firstNonRepeating == '\0') {
            System.out.println("No non-repeating character found or the string is empty.");
        } else {
            System.out.println("The first non-repeating character is: " + firstNonRepeating);
        }

        scanner.close();
    }
}