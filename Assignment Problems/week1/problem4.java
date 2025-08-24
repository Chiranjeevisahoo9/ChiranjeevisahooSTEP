import java.util.Scanner;
//CharacterFrequencyUnique
public class problem4 {

    // Helper method to find the length of the text without using String.length()
    public static int getStringLength(String text) {
        int count = 0;
        try {
            while (true) {
                text.charAt(count);
                count++;
            }
        } catch (StringIndexOutOfBoundsException e) {
            return count;
        }
    }

    // Method to find unique characters in a string using charAt() and return them as a 1D char array
    // (Reused logic from Problem 1)
    public static char[] findUniqueCharacters(String text) {
        int length = getStringLength(text);
        char[] uniqueCharsTemp = new char[length];
        int uniqueCount = 0;

        for (int i = 0; i < length; i++) {
            char currentChar = text.charAt(i);
            boolean isUnique = true;

            for (int j = 0; j < uniqueCount; j++) {
                if (uniqueCharsTemp[j] == currentChar) {
                    isUnique = false;
                    break;
                }
            }

            if (isUnique) {
                uniqueCharsTemp[uniqueCount] = currentChar;
                uniqueCount++;
            }
        }

        char[] resultUniqueChars = new char[uniqueCount];
        for (int i = 0; i < uniqueCount; i++) {
            resultUniqueChars[i] = uniqueCharsTemp[i];
        }
        return resultUniqueChars;
    }

    // Method to find the frequency of characters using unique characters
    public static String[][] getCharacterFrequenciesFromUnique(String text) {
        // Step i: Create an array to store the frequency of characters.
        int[] charFrequencies = new int[256]; // Assuming ASCII characters

        // Step ii: Loop through the text to find the frequency of each character.
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            charFrequencies[ch]++;
        }

        // Step iii: Call the uniqueCharacters() method to find the unique characters in the text.
        char[] uniqueChars = findUniqueCharacters(text);

        // Step iv: Create a 2D String array to store the unique characters and their frequencies.
        String[][] frequenciesResult = new String[uniqueChars.length][2];

        // Step v: Loop through the unique characters and store the characters and their frequencies.
        for (int i = 0; i < uniqueChars.length; i++) {
            char character = uniqueChars[i];
            int frequency = charFrequencies[character]; // Get frequency using the char as index.
            frequenciesResult[i][0] = String.valueOf(character);
            frequenciesResult[i][1] = String.valueOf(frequency);
        }
        return frequenciesResult;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Character Frequency (using Unique Characters) ---");
        System.out.print("Enter a string: ");
        String userInput = scanner.nextLine();

        // Call user-defined method to get character frequencies
        String[][] frequencies = getCharacterFrequenciesFromUnique(userInput);

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