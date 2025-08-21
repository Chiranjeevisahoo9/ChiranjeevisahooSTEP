// Problem 2: Write a program to convert text between different cases
// (uppercase, lowercase, title case) using ASCII values without using built-in
// case conversion methods

import java.util.Scanner;

public class program2 {

    public static char toUpperCaseASCII(char ch) {
        if (ch >= 97 && ch <= 122) {
            return (char) (ch - 32);
        }
        return ch;
    }

    public static char toLowerCaseASCII(char ch) {
        if (ch >= 65 && ch <= 90) {
            return (char) (ch + 32);
        }
        return ch;
    }

    public static String convertToUpperCase(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            result.append(toUpperCaseASCII(text.charAt(i)));
        }
        return result.toString();
    }

    public static String convertToLowerCase(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            result.append(toLowerCaseASCII(text.charAt(i)));
        }
        return result.toString();
    }

    public static String convertToTitleCase(String text) {
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = true;

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            if (Character.isWhitespace(ch)) {
                result.append(ch);
                capitalizeNext = true;
            } else if (capitalizeNext) {
                result.append(toUpperCaseASCII(ch));
                capitalizeNext = false;
            } else {
                result.append(toLowerCaseASCII(ch));
            }
        }
        return result.toString();
    }

    public static boolean[] compareWithBuiltIn(String originalText, String manualUpperCase, String manualLowerCase) {
        String builtInUpperCase = originalText.toUpperCase();
        String builtInLowerCase = originalText.toLowerCase();

        boolean upperCaseMatch = manualUpperCase.equals(builtInUpperCase);
        boolean lowerCaseMatch = manualLowerCase.equals(builtInLowerCase);

        return new boolean[]{upperCaseMatch, lowerCaseMatch};
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Problem 2: Text Case Conversion (ASCII) ---");
        System.out.print("Enter text to convert: ");
        String inputText = scanner.nextLine();

        String upperCaseText = convertToUpperCase(inputText);
        String lowerCaseText = convertToLowerCase(inputText);
        String titleCaseText = convertToTitleCase(inputText);

        boolean[] comparisonResults = compareWithBuiltIn(inputText, upperCaseText, lowerCaseText);

        System.out.println("\nCase Conversion Results:");
        System.out.println("----------------------------------------------------------------------------------");
        System.out.printf("%-20s | %-25s | %-10s%n", "Case Type", "Converted Text", "Matches Built-in?");
        System.out.println("----------------------------------------------------------------------------------");
        System.out.printf("%-20s | %-25s | %-10s%n", "Original", inputText, "N/A");
        System.out.printf("%-20s | %-25s | %-10s%n", "Manual Uppercase", upperCaseText, comparisonResults[0] ? "✅ Yes" : "❌ No");
        System.out.printf("%-20s | %-25s | %-10s%n", "Manual Lowercase", lowerCaseText, comparisonResults[1] ? "✅ Yes" : "❌ No");
        System.out.printf("%-20s | %-25s | %-10s%n", "Manual Title Case", titleCaseText, "N/A");

        scanner.close();
    }
}