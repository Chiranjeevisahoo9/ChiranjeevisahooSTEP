import java.util.Scanner;
//TextCompression
public class problem3 {

    // b. Create a method to count character frequency without using HashMap
    // Returns an Object array: [0] = char[] (characters), [1] = int[] (frequencies)
    public static Object[] countCharacterFrequency(String text) {
        char[] uniqueChars = new char[text.length()];
        int[] frequencies = new int[text.length()];
        int uniqueCount = 0;

        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            boolean found = false;

            // Check if character is already in uniqueChars array
            for (int j = 0; j < uniqueCount; j++) {
                if (uniqueChars[j] == currentChar) {
                    frequencies[j]++;
                    found = true;
                    break;
                }
            }

            // If not found, add it as a new unique character
            if (!found) {
                uniqueChars[uniqueCount] = currentChar;
                frequencies[uniqueCount] = 1;
                uniqueCount++;
            }
        }

        // Create new arrays of the exact size
        char[] finalUniqueChars = new char[uniqueCount];
        int[] finalFrequencies = new int[uniqueCount];
        for (int i = 0; i < uniqueCount; i++) {
            finalUniqueChars[i] = uniqueChars[i];
            finalFrequencies[i] = frequencies[i];
        }

        // Return an Object array containing the char[] and int[]
        return new Object[]{finalUniqueChars, finalFrequencies};
    }

    // c. Create a method to create compression codes using StringBuilder
    // Assigns simple numeric/symbol codes.
    // Returns a 2D String array: [original_char, code_string]
    public static String[][] createCompressionCodes(char[] chars, int[] frequencies) {
        // Simple sorting of characters by frequency (bubble sort for basic coder)
        for (int i = 0; i < frequencies.length - 1; i++) {
            for (int j = 0; j < frequencies.length - 1 - i; j++) {
                if (frequencies[j] < frequencies[j + 1]) { // Sort in descending order of frequency
                    // Swap frequencies
                    int tempFreq = frequencies[j];
                    frequencies[j] = frequencies[j + 1];
                    frequencies[j + 1] = tempFreq;

                    // Swap characters
                    char tempChar = chars[j];
                    chars[j] = chars[j + 1];
                    chars[j + 1] = tempChar;
                }
            }
        }

        String[][] mappingTable = new String[chars.length][2];
        String[] codes = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                          "@", "#", "$", "%", "&", "*", "+", "-", "=", "^",
                          "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"}; // Some simple codes

        for (int i = 0; i < chars.length; i++) {
            mappingTable[i][0] = String.valueOf(chars[i]);
            // Assign shorter codes to more frequent characters
            if (i < codes.length) {
                mappingTable[i][1] = codes[i];
            } else {
                // If we run out of simple codes, use a longer pattern (e.g., c0, c1, etc.)
                mappingTable[i][1] = "c" + i;
            }
        }
        return mappingTable;
    }

    // d. Create a method to compress text using the generated codes
    public static String compressText(String originalText, String[][] mappingTable) {
        StringBuilder compressedBuilder = new StringBuilder();
        for (int i = 0; i < originalText.length(); i++) {
            char currentChar = originalText.charAt(i);
            String code = null;
            for (int j = 0; j < mappingTable.length; j++) {
                if (mappingTable[j][0].charAt(0) == currentChar) {
                    code = mappingTable[j][1];
                    break;
                }
            }
            if (code != null) {
                compressedBuilder.append(code);
            } else {
                // If a character is not in mapping, append the original character (or handle as error)
                compressedBuilder.append(currentChar);
            }
        }
        return compressedBuilder.toString();
    }

    // e. Create a method to decompress the text
    public static String decompressText(String compressedText, String[][] mappingTable) {
        StringBuilder decompressedBuilder = new StringBuilder();
        // Decompression logic for single-character codes
        // If codes could be multi-character, this logic would need to be more complex (e.g., using a Trie)
        // For this basic example, assume single-character codes or distinct codes easily identifiable.

        int i = 0;
        while (i < compressedText.length()) {
            boolean found = false;
            // Iterate through mapping table to find matching code
            for (int j = 0; j < mappingTable.length; j++) {
                String code = mappingTable[j][1];
                // Check if the current segment of compressed text matches a known code
                if (i + code.length() <= compressedText.length() &&
                    compressedText.substring(i, i + code.length()).equals(code)) {
                    decompressedBuilder.append(mappingTable[j][0]); // Append original character
                    i += code.length(); // Move index past the consumed code
                    found = true;
                    break;
                }
            }
            if (!found) {
                // If no code matches, append the character as is (might indicate error or unhandled char)
                decompressedBuilder.append(compressedText.charAt(i));
                i++;
            }
        }
        return decompressedBuilder.toString();
    }


    // f. Create a method to display compression analysis
    public static void displayCompressionAnalysis(String originalText, String compressedText, String decompressedText,
                                                  Object[] freqData, String[][] mappingTable) {
        // Correctly cast the Object[] back to char[] and int[]
        char[] chars = (char[]) freqData[0];
        int[] frequencies = (int[]) freqData[1];

        System.out.println("\n--- Compression Analysis ---");

        // i. Show character frequency table
        System.out.println("\nCharacter Frequency Table:");
        System.out.println("---------------------");
        System.out.printf("| %-5s | %-10s |%n", "Char", "Frequency");
        System.out.println("---------------------");
        for (int i = 0; i < chars.length; i++) {
            System.out.printf("| %-5s | %-10d |%n", (chars[i] == ' ' ? "[space]" : chars[i]), frequencies[i]);
        }
        System.out.println("---------------------");

        // ii. Display compression mapping
        System.out.println("\nCompression Mapping:");
        System.out.println("---------------------");
        System.out.printf("| %-5s | %-10s |%n", "Char", "Code");
        System.out.println("---------------------");
        for (String[] entry : mappingTable) {
            System.out.printf("| %-5s | %-10s |%n", (entry[0].equals(" ") ? "[space]" : entry[0]), entry[1]);
        }
        System.out.println("---------------------");

        // iii. Show original text, compressed text, decompressed text
        System.out.println("\nOriginal Text:\n" + originalText);
        System.out.println("\nCompressed Text:\n" + compressedText);
        System.out.println("\nDecompressed Text:\n" + decompressedText);

        // iv. Calculate and display compression efficiency percentage
        int originalSize = originalText.length();
        int compressedSize = compressedText.length();
        double compressionRatio = (double) compressedSize / originalSize;
        double efficiency = (1 - compressionRatio) * 100;

        System.out.println("\nCompression Statistics:");
        System.out.println("Original Size: " + originalSize + " characters");
        System.out.println("Compressed Size: " + compressedSize + " characters");
        System.out.printf("Compression Ratio (Compressed/Original): %.2f%n", compressionRatio);
        System.out.printf("Compression Efficiency: %.2f%%%n", efficiency);

        // Validate that decompression returns original text
        if (originalText.equals(decompressedText)) {
            System.out.println("Decompression Validation: SUCCESS (Original text matches decompressed text).");
        } else {
            System.out.println("Decompression Validation: FAILED (Original text does NOT match decompressed text).");
        }
    }

    // g. The main function performs compression, decompression, and displays complete analysis
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Text-based Data Compression ---");
        System.out.print("Enter text to compress: ");
        String userInput = scanner.nextLine();

        // 1. Count character frequency
        Object[] freqData = countCharacterFrequency(userInput); // Changed to Object[]
        char[] chars = (char[]) freqData[0];
        int[] frequencies = (int[]) freqData[1];

        // 2. Create compression codes
        String[][] mappingTable = createCompressionCodes(chars, frequencies);

        // 3. Compress text
        String compressedText = compressText(userInput, mappingTable);

        // 4. Decompress text
        String decompressedText = decompressText(compressedText, mappingTable);

        // 5. Display analysis
        displayCompressionAnalysis(userInput, compressedText, decompressedText, freqData, mappingTable);

        scanner.close();
    }
}