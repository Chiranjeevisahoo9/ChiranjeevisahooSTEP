import java.util.Arrays;
import java.util.Scanner;

public class ASCIIProcessor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string (e.g., 'Hello 123!'): ");
        String input = scanner.nextLine();

        System.out.println("\n--- Character Analysis ---");
        for (char ch : input.toCharArray()) {
            int asciiValue = (int) ch;
            System.out.println("\nCharacter: '" + ch + "', ASCII: " + asciiValue);
            System.out.println("Type: " + classifyCharacter(ch));
            if (Character.isLetter(ch)) {
                char toggled = toggleCase(ch);
                System.out.println("Toggled Case: '" + toggled + "', ASCII: " + (int) toggled);
                System.out.println("ASCII difference between cases: " + Math.abs((int)ch - (int)toggled));
            }
        }
        
        System.out.println("\n--- ASCII Table (A-Z) ---");
        displayASCIITable('A', 'Z');
        
        System.out.println("\n--- Caesar Cipher ---");
        String encrypted = caesarCipher(input, 3);
        System.out.println("Original: '" + input + "'");
        System.out.println("Encrypted (shift 3): '" + encrypted + "'");
        String decrypted = caesarCipher(encrypted, -3);
        System.out.println("Decrypted (shift -3): '" + decrypted + "'");
        
        System.out.println("\n--- String <-> ASCII Array ---");
        int[] asciiArray = stringToASCII(input);
        System.out.println("String to ASCII Array: " + Arrays.toString(asciiArray));
        String fromAscii = asciiToString(asciiArray);
        System.out.println("ASCII Array to String: '" + fromAscii + "'");
        
        scanner.close();
    }

    public static String classifyCharacter(char ch) {
        if (Character.isUpperCase(ch)) return "Uppercase Letter";
        if (Character.isLowerCase(ch)) return "Lowercase Letter";
        if (Character.isDigit(ch)) return "Digit";
        return "Special Character";
    }

    public static char toggleCase(char ch) {
        if (Character.isUpperCase(ch)) {
            return (char) (ch + 32);
        } else if (Character.isLowerCase(ch)) {
            return (char) (ch - 32);
        }
        return ch;
    }

    public static String caesarCipher(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isLowerCase(ch) ? 'a' : 'A';
                result.append((char) (((ch - base + shift) % 26 + 26) % 26 + base));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static void displayASCIITable(int start, int end) {
        System.out.println("Code | Char");
        System.out.println("-----|------");
        for (int i = start; i <= end; i++) {
            System.out.printf("%4d | %c\n", i, (char) i);
        }
    }

    public static int[] stringToASCII(String text) {
        int[] asciiValues = new int[text.length()];
        for (int i = 0; i < text.length(); i++) {
            asciiValues[i] = (int) text.charAt(i);
        }
        return asciiValues;
    }

    public static String asciiToString(int[] asciiValues) {
        StringBuilder sb = new StringBuilder();
        for (int val : asciiValues) {
            sb.append((char) val);
        }
        return sb.toString();
    }
}