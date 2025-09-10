import java.util.Scanner;

public class AdvancedStringAnalyzer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== ADVANCED STRING ANALYZER ===");
        System.out.print("Enter the first string: ");
        String str1 = scanner.nextLine();
        System.out.print("Enter the second string: ");
        String str2 = scanner.nextLine();

        System.out.println("\n--- Comprehensive Comparison ---");
        performAllComparisons(str1, str2);

        System.out.println("\n--- String Similarity ---");
        double similarity = calculateSimilarity(str1, str2);
        System.out.printf("The two strings are %.2f%% similar.\n", similarity);

        System.out.println("\n--- String Pool (intern()) Demo ---");
        demonstrateStringIntern();

        scanner.close();
    }

    public static double calculateSimilarity(String str1, String str2) {
        String longer = str1, shorter = str2;
        if (str1.length() < str2.length()) {
            longer = str2;
            shorter = str1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) {
            return 100.0;
        }
        // Levenshtein distance algorithm
        int[] costs = new int[shorter.length() + 1];
        for (int i = 0; i <= shorter.length(); i++) {
            costs[i] = i;
        }
        for (int i = 1; i <= longer.length(); i++) {
            costs[0] = i;
            int newValue = i - 1;
            for (int j = 1; j <= shorter.length(); j++) {
                int match = (longer.charAt(i - 1) == shorter.charAt(j - 1)) ? 0 : 1;
                int cost_replace = newValue + match;
                int cost_insert = costs[j] + 1;
                int cost_delete = costs[j - 1] + 1;
                newValue = costs[j];
                costs[j] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
            }
        }
        int distance = costs[shorter.length()];
        return (1.0 - (double) distance / longerLength) * 100.0;
    }

    public static void performAllComparisons(String str1, String str2) {
        System.out.println("\n1. Reference Equality (==)");
        System.out.println("   Result: " + (str1 == str2) + (str1 == str2 ? "" : " (Different objects in memory)"));

        System.out.println("\n2. Content Equality (.equals)");
        System.out.println("   Result: " + str1.equals(str2));

        System.out.println("\n3. Case-Insensitive Equality (.equalsIgnoreCase)");
        System.out.println("   Result: " + str1.equalsIgnoreCase(str2));
        
        System.out.println("\n4. Lexicographic Comparison (.compareTo)");
        int compareResult = str1.compareTo(str2);
        String relation = (compareResult == 0) ? "are equal" : (compareResult < 0 ? "comes before" : "comes after");
        System.out.println("   Result: '" + str1 + "' " + relation + " '" + str2 + "'");
    }

    public static String optimizedStringProcessing(String[] inputs) {
        if (inputs == null || inputs.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        for(String s : inputs) {
            sb.append(s).append(" ");
        }
        return sb.toString().trim();
    }

    public static void demonstrateStringIntern() {
        String s1 = "pool";
        String s2 = new String("pool");
        System.out.println("s1 == s2: " + (s1 == s2)); // false

        // intern() method places the string from heap into the string pool
        // or returns the reference if it already exists.
        String s3 = s2.intern();
        System.out.println("s1 == s3 (after s2.intern()): " + (s1 == s3)); // true
    }
}
