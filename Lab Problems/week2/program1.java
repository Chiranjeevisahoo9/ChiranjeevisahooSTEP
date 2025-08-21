// Problem 1: Write a program to find and replace all occurrences of a
// substring in a text without using the replace() method

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class program1 {

    public static List<Integer> findOccurrences(String mainText, String subString) {
        List<Integer> occurrences = new ArrayList<>();
        if (subString.isEmpty()) {
            return occurrences;
        }

        int index = 0;
        while ((index = mainText.indexOf(subString, index)) != -1) {
            occurrences.add(index);
            index += subString.length();
        }
        return occurrences;
    }

    public static String manualReplace(String mainText, String subString, String replacement) {
        if (subString.isEmpty()) {
            return mainText;
        }

        StringBuilder newText = new StringBuilder();
        int lastIndex = 0;
        List<Integer> occurrences = findOccurrences(mainText, subString);

        if (occurrences.isEmpty()) {
            return mainText;
        }

        for (int startIndex : occurrences) {
            newText.append(mainText.substring(lastIndex, startIndex));
            newText.append(replacement);
            lastIndex = startIndex + subString.length();
        }
        newText.append(mainText.substring(lastIndex));

        return newText.toString();
    }

    public static boolean compareWithBuiltIn(String manualResult, String mainText, String subString, String replacement) {
        String builtInResult = mainText.replace(subString, replacement);
        return manualResult.equals(builtInResult);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Problem 1: Substring Find and Replace ---");
        System.out.print("Enter the main text: ");
        String mainText = scanner.nextLine();

        System.out.print("Enter the substring to find: ");
        String subString = scanner.nextLine();

        System.out.print("Enter the replacement substring: ");
        String replacement = scanner.nextLine();

        String replacedTextManually = manualReplace(mainText, subString, replacement);

        String replacedTextBuiltIn = mainText.replace(subString, replacement);

        boolean comparisonResult = compareWithBuiltIn(replacedTextManually, mainText, subString, replacement);

        System.out.println("\nOriginal Text: " + mainText);
        System.out.println("Substring to Find: " + subString);
        System.out.println("Replacement Substring: " + replacement);
        System.out.println("\nManually Replaced Text: " + replacedTextManually);
        System.out.println("Built-in Replaced Text: " + replacedTextBuiltIn);
        System.out.println("Comparison Result (Manual vs. Built-in): " + (comparisonResult ? "Match ✅" : "Mismatch ❌"));

        scanner.close();
    }
}