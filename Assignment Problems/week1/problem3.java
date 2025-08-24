import java.util.Scanner;
//CharacterFrequencyCharAt
public class problem3 {

    // Method to find the frequency of characters and return them as a 2D array
    public static String[][] getCharacterFrequencies(String text) {
        // Step i: Create an array to store the frequency of characters.
        // ASCII values (0-255) are used as indexes.
        int[] charFrequencies = new int[256]; // Assuming ASCII characters

        // Step ii: Loop through the text to find the frequency of each character.
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            charFrequencies[ch]++; // Increment frequency for the character at its ASCII index.
        }

        // Determine how many unique characters actually appeared to size the 2D array correctly.
        int uniqueCharCount = 0;
        for (int i = 0; i < charFrequencies.length; i++) {
            if (charFrequencies[i] > 0) {
                uniqueCharCount++;
            }
        }

        // Step iii: Create an array to store the characters and their frequencies.
        // Each inner array will have [character, frequency].
        String[][] frequenciesResult = new String[uniqueCharCount][2];
        int resultIndex = 0;

        // Step iv: Loop through the characters in the text (or ASCII range) and store their frequencies.
        // To avoid duplicates in the result, we iterate through the ASCII frequency array.
        for (int i = 0; i < charFrequencies.length; i++) {
            if (charFrequencies[i] > 0) { // If a character appeared at least once.
                char character = (char) i; // Convert ASCII index back to character.
                int frequency = charFrequencies[i];
                frequenciesResult[resultIndex][0] = String.valueOf(character);
                frequenciesResult[resultIndex][1] = String.valueOf(frequency);
                resultIndex++;
            }
        }
        return frequenciesResult;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Character Frequency (using charAt()) ---");
        System.out.print("Enter a string: ");
        String userInput = scanner.nextLine();

        // Call user-defined method to get character frequencies
        String[][] frequencies = getCharacterFrequencies(userInput);

        // Display the result
        if (frequencies.length == 0) {
            System.out.println("The string is empty.");
        } else {
            System.out.println("Character frequencies:");
            for (String[] entry : frequencies) {
                System.out.println("'" + entry[0] + "': " + entry[1]);
            }
        }

        scanner.close();
    }
}