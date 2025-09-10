import java.util.Arrays;
import java.util.Scanner;

public class TextProcessor {
    // Method to clean and validate input
    public static String cleanInput(String input) {
        if (input == null) return "";
        // Remove extra spaces and trim
        return input.trim().replaceAll("\\s+", " ");
    }

    // Method to analyze text
    public static void analyzeText(String text) {
        if (text.isEmpty()) {
            System.out.println("No text to analyze.");
            return;
        }
        // Count words, sentences, characters
        String[] words = text.split("\\s+");
        String[] sentences = text.split("[.!?]+");
        int charCount = text.length();

        // Find longest word
        String longestWord = "";
        for (String word : words) {
            if (word.length() > longestWord.length()) {
                longestWord = word;
            }
        }
        
        System.out.println("\n--- TEXT ANALYSIS ---");
        System.out.println("Word Count: " + words.length);
        System.out.println("Sentence Count: " + sentences.length);
        System.out.println("Character Count (including spaces): " + charCount);
        System.out.println("Longest Word: '" + longestWord + "'");
    }

    // Method to create word array and sort alphabetically
    public static String[] getWordsSorted(String text) {
        if (text.isEmpty()) return new String[0];
        // Split text into words, remove punctuation, convert to lower case, sort
        String[] words = text.toLowerCase().replaceAll("[^a-zA-Z\\s]", "").split("\\s+");
        Arrays.sort(words);
        return words;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== TEXT PROCESSOR ===");
        
        // 1. Ask user for a paragraph of text
        System.out.println("Please enter a paragraph of text:");
        String userInput = scanner.nextLine();
        
        // 2. Clean the input
        String cleanedText = cleanInput(userInput);
        System.out.println("\nCleaned Text: \n\"" + cleanedText + "\"");

        // 3. Analyze the text
        analyzeText(cleanedText);
        
        // 4. Show the words in alphabetical order
        String[] sortedWords = getWordsSorted(cleanedText);
        System.out.println("\n--- WORDS IN ALPHABETICAL ORDER ---");
        System.out.println(Arrays.toString(sortedWords));

        // 5. Allow user to search for specific words
        System.out.println("\n--- WORD SEARCH ---");
        while (true) {
            System.out.print("Enter a word to search for (or type 'exit' to quit): ");
            String searchTerm = scanner.nextLine();
            if (searchTerm.equalsIgnoreCase("exit")) {
                break;
            }
            if (cleanedText.toLowerCase().contains(searchTerm.toLowerCase())) {
                System.out.println(" -> Found '" + searchTerm + "' in the text.");
            } else {
                System.out.println(" -> '" + searchTerm + "' was NOT found.");
            }
        }
        
        System.out.println("\nExiting Text Processor. Goodbye!");
        scanner.close();
    }
}
