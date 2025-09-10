import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StringManipulation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Ask user to enter a sentence with mixed formatting
        System.out.println("Enter a sentence with mixed case, extra spaces, and some numbers/punctuation (e.g., '  Hello World! 123 Java...  '):");
        String input = scanner.nextLine();

        System.out.println("\n--- Initial Processing ---");
        // 1. trim() - Remove extra spaces
        String trimmed = input.trim();
        System.out.println("1. Trimmed: '" + trimmed + "'");

        // 2. replace() - Replace all spaces with underscores
        String replacedSpaces = trimmed.replace(' ', '_');
        System.out.println("2. Spaces replaced with underscores: '" + replacedSpaces + "'");
        
        // 3. replaceAll() - Remove all digits using regex
        String noDigits = trimmed.replaceAll("\\d", "");
        System.out.println("3. Digits removed: '" + noDigits + "'");

        // 4. split() - Split sentence into words array
        String[] words = trimmed.split("\\s+"); // Split by one or more spaces
        System.out.println("4. Split into words: " + Arrays.toString(words));

        // 5. join() - Rejoin words with " | " separator
        String joined = String.join(" | ", words);
        System.out.println("5. Rejoined with separator: '" + joined + "'");

        System.out.println("\n--- Advanced Processing ---");
        // Remove all punctuation
        String noPunctuation = removePunctuation(trimmed);
        System.out.println("Punctuation removed: '" + noPunctuation + "'");
        
        // Capitalize first letter of each word
        String capitalized = capitalizeWords(noPunctuation);
        System.out.println("Capitalized words: '" + capitalized + "'");
        
        // Reverse the order of words
        String reversed = reverseWordOrder(capitalized);
        System.out.println("Reversed word order: '" + reversed + "'");

        // Count word frequency
        System.out.println("\n--- Word Frequency ---");
        countWordFrequency(capitalized);
        
        scanner.close();
    }

    // Method to remove punctuation
    public static String removePunctuation(String text) {
        return text.replaceAll("[\\p{Punct}]", "");
    }

    // Method to capitalize each word
    public static String capitalizeWords(String text) {
        String[] words = text.trim().split("\\s+");
        StringBuilder capitalizedText = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                capitalizedText.append(Character.toUpperCase(word.charAt(0)))
                               .append(word.substring(1).toLowerCase())
                               .append(" ");
            }
        }
        return capitalizedText.toString().trim();
    }

    // Method to reverse word order
    public static String reverseWordOrder(String text) {
        String[] words = text.trim().split("\\s+");
        Collections.reverse(Arrays.asList(words));
        return String.join(" ", words);
    }

    // Method to count word frequency
    public static void countWordFrequency(String text) {
        String[] words = text.toLowerCase().trim().split("\\s+");
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String word : words) {
            if (!word.isEmpty()) {
                frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
            }
        }
        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            System.out.println("'" + entry.getKey() + "': " + entry.getValue());
        }
    }
}