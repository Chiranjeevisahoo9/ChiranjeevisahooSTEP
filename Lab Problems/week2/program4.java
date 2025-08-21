// Problem 4: Write a program to create a simple encryption and decryption
// system using ASCII character shifting (Caesar Cipher implementation)

import java.util.Scanner;

public class program4 {

    public static String encrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            if (ch >= 'A' && ch <= 'Z') {
                ch = (char) ('A' + (ch - 'A' + shift) % 26);
            } else if (ch >= 'a' && ch <= 'z') {
                ch = (char) ('a' + (ch - 'a' + shift) % 26);
            }
            result.append(ch);
        }
        return result.toString();
    }

    public static String decrypt(String encryptedText, int shift) {
        return encrypt(encryptedText, 26 - (shift % 26 + 26) % 26);
    }

    public static void displayAsciiValues(String label, String text) {
        System.out.println("\n" + label + ": " + text);
        System.out.print("ASCII Values: ");
        for (int i = 0; i < text.length(); i++) {
            System.out.print((int) text.charAt(i) + " ");
        }
        System.out.println();
    }

    public static boolean validateDecryption(String originalText, String decryptedText) {
        return originalText.equals(decryptedText);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Problem 4: Caesar Cipher Encryption/Decryption ---");
        System.out.print("Enter the text to encrypt: ");
        String inputText = scanner.nextLine();

        System.out.print("Enter the shift value (e.g., 3 for Caesar): ");
        int shift = scanner.nextInt();

        String encryptedText = encrypt(inputText, shift);

        String decryptedText = decrypt(encryptedText, shift);

        displayAsciiValues("Original Text", inputText);
        displayAsciiValues("Encrypted Text", encryptedText);
        displayAsciiValues("Decrypted Text", decryptedText);

        boolean validationResult = validateDecryption(inputText, decryptedText);
        System.out.println("\nDecryption Validation: " + (validationResult ? "Successful! Original text restored. ✅" : "Failed! Mismatch. ❌"));

        scanner.close();
    }
}