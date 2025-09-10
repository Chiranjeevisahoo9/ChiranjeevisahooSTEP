public class StringManipulation {
    public static void main(String[] args) {
        // Create the same string "Java Programming" using 3 different methods:
        // 1. String literal
        String s1 = "Java Programming";

        // 2. new String() constructor
        String s2 = new String("Java Programming");

        // 3. Character array
        char[] charArray = {'J', 'a', 'v', 'a', ' ', 'P', 'r', 'o', 'g', 'r', 'a', 'm', 'm', 'i', 'n', 'g'};
        String s3 = new String(charArray);

        System.out.println("--- String Comparison ---");
        System.out.println("s1: \"" + s1 + "\" (literal)");
        System.out.println("s2: \"" + s2 + "\" (new String())");
        System.out.println("s3: \"" + s3 + "\" (char array)");

        // Compare the strings using == and .equals()
        System.out.println("\nComparison with == (checks if objects are the same in memory):");
        System.out.println("s1 == s2: " + (s1 == s2)); // false
        System.out.println("s1 == s3: " + (s1 == s3)); // false

        System.out.println("\nComparison with .equals() (checks if the content is the same):");
        System.out.println("s1.equals(s2): " + s1.equals(s2)); // true
        System.out.println("s1.equals(s3): " + s1.equals(s3)); // true
        
        System.out.println("\nExplanation: `==` compares memory addresses, which are different for s1, s2, and s3.");
        System.out.println("`.equals()` compares the actual character sequence, which is the same for all three.");

        // Create a string with escape sequences
        String formattedString = "\nProgramming Quote:\n\t\"Code is poetry\" - Unknown\nPath: C:\\Java\\Projects";
        System.out.println(formattedString);
    }
}
