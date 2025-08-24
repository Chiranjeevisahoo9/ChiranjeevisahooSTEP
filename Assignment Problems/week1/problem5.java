import java.util.Scanner;
//CharacterFrequencyNestedLoops
public class problem5 {

    // Method to find the frequency of characters using nested loops
    public static String[] getCharacterFrequenciesNestedLoop(String text) {
        // Step i: Create an array to store the characters in the text using toCharArray() method
        char[] charArray = text.toCharArray();
        // Create an array to store the frequency of each character
        int[] frequency = new int[charArray.length];

        // Step ii: Loops to find the frequency of each character.
        // Outer loop iterates through each character in the text.
        for (int i = 0; i < charArray.length; i++) {
            // Initialize the frequency of the current character to 1 (assuming it's unique so far).
            frequency[i] = 1;
            char currentChar = charArray[i];

            // Inner loop checks for duplicate characters after the current index.
            for (int j = i + 1; j < charArray.length; j++) {
                if (currentChar == charArray[j]) {
                    frequency[i]++; // Increment frequency if a duplicate is found.
                    charArray[j] = '0'; // Set the duplicate character to '0' to avoid counting it again.
                }
            }
        }

        // Determine how many characters are actually counted (not '0') for the result array size.
        int distinctCharCount = 0;
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] != '0') {
                distinctCharCount++;
            }
        }

        // Step iii: Create a 1D String array to store the characters and their frequencies.
        String[] resultFrequencies = new String[distinctCharCount];
        int resultIndex = 0;

        // Iterate through the processed charArray and frequency array.
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] != '0') { // Only consider characters that haven't been marked as '0' (duplicates).
                resultFrequencies[resultIndex] = "'" + charArray[i] + "': " + frequency[i];
                resultIndex++;
            }
        }
        return resultFrequencies;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Character Frequency (using Nested Loops) ---");
        System.out.print("Enter a string: ");
        String userInput = scanner.nextLine();

        // Call the user-defined method
        String[] frequencies = getCharacterFrequenciesNestedLoop(userInput);

        // Display the result
        if (frequencies.length == 0) {
            System.out.println("The string is empty.");
        } else {
            System.out.println("Character frequencies:");
            for (String entry : frequencies) {
                System.out.println(entry);
            }
        }

        scanner.close();
    }
}