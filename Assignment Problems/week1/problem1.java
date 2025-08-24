import java.util.Scanner;
//UniqueCharacters
public class problem1 {

    // Method to find the length of the text without using the String method length()
    public static int getStringLength(String text) {
        int count = 0;
        try {
            while (true) {
                // Try to access character at current index. If it fails, it means we've reached the end.
                text.charAt(count);
                count++;
            }
        } catch (StringIndexOutOfBoundsException e) {
            // This exception indicates the end of the string.
            return count;
        }
    }

    // Method to find unique characters in a string using charAt() and return them as a 1D char array
    public static char[] findUniqueCharacters(String text) {
        int length = getStringLength(text);
        // Step i: Create an array to store potential unique characters. Its size is the length of the text.
        char[] uniqueCharsTemp = new char[length];
        int uniqueCount = 0;

        // Step ii: Loops to find unique characters in the text.
        // Outer loop iterates through each character of the input string.
        for (int i = 0; i < length; i++) {
            char currentChar = text.charAt(i);
            boolean isUnique = true;

            // Inner loop checks if the currentChar has appeared before in the uniqueCharsTemp array.
            for (int j = 0; j < uniqueCount; j++) {
                if (uniqueCharsTemp[j] == currentChar) {
                    isUnique = false; // Character is not unique, it has been seen before.
                    break;
                }
            }

            // If the character is unique, store it in the uniqueCharsTemp array.
            if (isUnique) {
                uniqueCharsTemp[uniqueCount] = currentChar;
                uniqueCount++;
            }
        }

        // Step iii: Create a new array to store only the actual unique characters.
        char[] resultUniqueChars = new char[uniqueCount];
        for (int i = 0; i < uniqueCount; i++) {
            resultUniqueChars[i] = uniqueCharsTemp[i];
        }
        return resultUniqueChars;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Find Unique Characters ---");
        System.out.print("Enter a string: ");
        String userInput = scanner.nextLine();

        // Call the user-defined method to find unique characters
        char[] uniqueCharacters = findUniqueCharacters(userInput);

        // Display the result
        if (uniqueCharacters.length == 0) {
            System.out.println("The string has no characters.");
        } else {
            System.out.print("Unique characters in the string are: ");
            for (char c : uniqueCharacters) {
                System.out.print(c + " ");
            }
            System.out.println();
        }

        scanner.close();
    }
}