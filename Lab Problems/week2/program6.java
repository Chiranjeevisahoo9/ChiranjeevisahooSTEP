
// Problem 6: Write a program to create a text formatter that justifies text to a
// specified width using StringBuilder for efficient string manipulation

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class program6 {

    public static List<String> splitTextIntoWords(String text) {
        List<String> words = new ArrayList<>();
        StringBuilder currentWord = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (Character.isWhitespace(ch)) { if (currentWord.length() > 0) { words.add(currentWord.toString()); currentWord.setLength(0); } }
            else currentWord.append(ch);
        }
        if (currentWord.length() > 0) words.add(currentWord.toString());
        return words;
    }

    public static List<String> justifyText(List<String> words, int lineWidth) {
        List<String> justifiedLines = new ArrayList<>();
        int wordIndex = 0;

        while (wordIndex < words.size()) {
            StringBuilder currentLine = new StringBuilder();
            int lineStartWordIndex = wordIndex, wordsOnLine = 0, currentLineLength = 0;

            while (wordIndex < words.size() && currentLineLength + words.get(wordIndex).length() + (wordsOnLine > 0 ? 1 : 0) <= lineWidth) {
                currentLineLength += words.get(wordIndex).length() + (wordsOnLine > 0 ? 1 : 0);
                wordsOnLine++; wordIndex++;
            }

            if (wordIndex == words.size() || wordsOnLine == 1) {
                for (int i = 0; i < wordsOnLine; i++) currentLine.append(words.get(lineStartWordIndex + i)).append(i < wordsOnLine - 1 ? " " : "");
                while (currentLine.length() < lineWidth) currentLine.append(" ");
                justifiedLines.add(currentLine.toString());
            } else {
                int totalWordsLength = 0; for (int i = 0; i < wordsOnLine; i++) totalWordsLength += words.get(lineStartWordIndex + i).length();
                int spacesNeeded = lineWidth - totalWordsLength, gaps = wordsOnLine - 1;
                int baseSpaces = spacesNeeded / gaps, extraSpaces = spacesNeeded % gaps;

                for (int i = 0; i < wordsOnLine; i++) {
                    currentLine.append(words.get(lineStartWordIndex + i));
                    if (i < gaps) { for (int j = 0; j < baseSpaces; j++) currentLine.append(" "); if (i < extraSpaces) currentLine.append(" "); }
                }
                justifiedLines.add(currentLine.toString());
            }
        }
        return justifiedLines;
    }

    public static List<String> centerAlignText(List<String> lines, int lineWidth) {
        List<String> centeredLines = new ArrayList<>();
        for (String line : lines) {
            StringBuilder centeredLine = new StringBuilder();
            int padding = (lineWidth - line.length()) / 2;
            for (int i = 0; i < padding; i++) centeredLine.append(" ");
            centeredLine.append(line);
            while (centeredLine.length() < lineWidth) centeredLine.append(" ");
            centeredLines.add(centeredLine.toString());
        }
        return centeredLines;
    }

    public static long[] comparePerformance(List<String> words, int lineWidth) {
        long sbStartTime = System.nanoTime(); justifyText(words, lineWidth); long sbTime = System.nanoTime() - sbStartTime;

        long concatStartTime = System.nanoTime();
        List<String> concatLines = new ArrayList<>();
        StringBuilder currentLineBuilder = new StringBuilder();
        int currentLineLen = 0;
        for (String word : words) {
            if (currentLineLen + word.length() + (currentLineBuilder.length() > 0 ? 1 : 0) <= lineWidth) {
                if (currentLineBuilder.length() > 0) currentLineBuilder.append(" ");
                currentLineBuilder.append(word); currentLineLen = currentLineBuilder.length();
            } else {
                while (currentLineBuilder.length() < lineWidth) currentLineBuilder.append(" "); concatLines.add(currentLineBuilder.toString());
                currentLineBuilder.setLength(0); currentLineBuilder.append(word); currentLineLen = word.length();
            }
        }
        if (currentLineBuilder.length() > 0) { while (currentLineBuilder.length() < lineWidth) currentLineBuilder.append(" "); concatLines.add(currentLineBuilder.toString()); }
        long concatTime = System.nanoTime() - concatStartTime;
        return new long[]{sbTime, concatTime};
    }

    public static void displayFormattedText(String label, List<String> formattedLines) {
        System.out.println("\n--- " + label + " ---\n--------------------------------------------------");
        System.out.printf("%-5s | %-40s | %-10s%n", "Line#", "Content", "Char Count");
        System.out.println("--------------------------------------------------");
        for (int i = 0; i < formattedLines.size(); i++) System.out.printf("%-5d | %-40s | %-10d%n", (i + 1), formattedLines.get(i), formattedLines.get(i).length());
        System.out.println("--------------------------------------------------");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Problem 6: Text Formatter ---");
        System.out.print("Enter the text to format: "); String inputText = scanner.nextLine();
        System.out.print("Enter the desired line width: "); int lineWidth = scanner.nextInt(); scanner.nextLine();
        if (lineWidth <= 0) { System.out.println("Line width must be positive."); scanner.close(); return; }

        List<String> words = splitTextIntoWords(inputText);
        List<String> justifiedText = justifyText(words, lineWidth); displayFormattedText("Left-Justified Text (Width " + lineWidth + ")", justifiedText);
        List<String> centeredText = centerAlignText(justifiedText, lineWidth); displayFormattedText("Center-Aligned Text (Width " + lineWidth + ")", centeredText);

        System.out.println("\n--- Performance Analysis ---");
        long[] performanceResults = comparePerformance(words, lineWidth);
        System.out.println("Time taken by StringBuilder for justification: " + performanceResults[0] + " ns");
        System.out.println("Time taken by (Simulated) String Concatenation for justification: " + performanceResults[1] + " ns");
        System.out.println("Note: StringBuilder is significantly more efficient for string manipulation in loops.");
        scanner.close();
    }
}