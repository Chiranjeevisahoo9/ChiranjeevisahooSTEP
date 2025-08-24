import java.util.Scanner;
//SimpleSpellChecker
public class problem1 {

    // A simple dictionary of correct words
    private static final String[] DICTIONARY = {
        "hello", "world", "java", "programming", "example", "apple", "banana", "orange", "computer",
        "science", "developer", "keyboard", "mouse", "monitor", "application", "algorithm",
        "data", "structure", "variable", "function", "method", "class", "object", "string", "integer",
        "boolean", "array", "loop", "condition", "statement", "system", "output", "input", "scanner",
        "console", "terminal", "editor", "compiler", "runtime", "error", "debug", "test", "solution"
    };

    // a. Takes user input for a sentence and uses a predefined dictionary.

    // b. Create a method to split the sentence into words without using split()
    public static String[] splitSentenceIntoWords(String sentence) {
        // We'll use a temporary array, then resize it at the end.
        String[] tempWords = new String[sentence.length()]; // Max possible words is sentence length
        int wordCount = 0;
        int wordStart = 0;

        for (int i = 0; i < sentence.length(); i++) {
            char currentChar = sentence.charAt(i);

            // Check if the current character is a word boundary (space or punctuation)
            if (currentChar == ' ' || currentChar == '.' || currentChar == ',' ||
                currentChar == '!' || currentChar == '?' || currentChar == ';' ||
                currentChar == ':' || currentChar == '(' || currentChar == ')') {

                // If a word was formed before this boundary
                if (i > wordStart) {
                    // ii. Extract each word using substring() method
                    String word = sentence.substring(wordStart, i);
                    tempWords[wordCount++] = word.toLowerCase(); // Store in lowercase for case-insensitive check
                }
                wordStart = i + 1; // Move wordStart past the current boundary
            }
        }

        // Add the last word if the sentence doesn't end with a boundary character
        if (sentence.length() > wordStart) {
            String lastWord = sentence.substring(wordStart, sentence.length());
            tempWords[wordCount++] = lastWord.toLowerCase();
        }

        // iii. Store words in a new array of the correct size
        String[] words = new String[wordCount];
        for (int i = 0; i < wordCount; i++) {
            words[i] = tempWords[i];
        }
        return words;
    }

    // c. Create a method to calculate string distance between two words (simple version)
    public static int calculateStringDistance(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();

        // i. Count character differences between words of same length
        if (len1 == len2) {
            int diffCount = 0;
            for (int i = 0; i < len1; i++) {
                if (word1.charAt(i) != word2.charAt(i)) {
                    diffCount++;
                }
            }
            return diffCount;
        } else {
            // ii. For different lengths, calculate insertion/deletion distance (absolute difference in length)
            // A more sophisticated algorithm like Levenshtein distance would be used in a real scenario,
            // but for a "basic coder" as per hint, absolute length difference is a simple proxy for insertion/deletion.
            return Math.abs(len1 - len2);
        }
    }

    // d. Create a method to find the closest matching word from dictionary
    public static String findClosestMatch(String inputWord, String[] dictionary) {
        String suggestion = null;
        int minDistance = Integer.MAX_VALUE;
        final int ACCEPTABLE_DISTANCE = 2; // As per hint (<= 2)

        for (String dictWord : dictionary) {
            int distance = calculateStringDistance(inputWord, dictWord);
            if (distance < minDistance) {
                minDistance = distance;
                suggestion = dictWord;
            }
        }

        // iii. Return the suggestion if distance is within acceptable range
        if (minDistance <= ACCEPTABLE_DISTANCE) {
            return suggestion;
        }
        return null; // No good suggestion found
    }

    // e. Create a method to display spell check results in tabular format
    public static void displaySpellCheckResults(String originalWord, String suggestedWord, int distance) {
        String status;
        if (suggestedWord != null && distance == 0) {
            status = "Correct";
        } else if (suggestedWord != null) {
            status = "Misspelled (Suggestion: " + suggestedWord + ", Distance: " + distance + ")";
        } else {
            status = "Misspelled (No close suggestion)";
        }

        System.out.printf("| %-15s | %-45s | %n", originalWord, status);
    }

    // f. The main function processes the sentence and displays comprehensive spell check report
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Simple Spell Checker ---");
        System.out.println("Dictionary Size: " + DICTIONARY.length + " words.");
        System.out.print("Enter a sentence to spell check: ");
        String sentence = scanner.nextLine();

        System.out.println("\nSpell Check Report:");
        System.out.println("------------------------------------------------------------------");
        System.out.printf("| %-15s | %-45s | %n", "Original Word", "Status / Suggestion");
        System.out.println("------------------------------------------------------------------");

        String[] wordsInSentence = splitSentenceIntoWords(sentence);

        if (wordsInSentence.length == 0) {
            System.out.println("| No words found in the sentence.                          |");
        } else {
            for (String word : wordsInSentence) {
                if (word.isEmpty()) {
                    continue; // Skip empty strings if any
                }
                String suggestedCorrection = findClosestMatch(word, DICTIONARY);
                int distance = (suggestedCorrection != null) ? calculateStringDistance(word, suggestedCorrection) : -1;
                displaySpellCheckResults(word, suggestedCorrection, distance);
            }
        }
        System.out.println("------------------------------------------------------------------");

        scanner.close();
    }
}