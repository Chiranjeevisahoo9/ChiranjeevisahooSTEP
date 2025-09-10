public class StringPerformanceComparison {
    public static void main(String[] args) {
        int iterations = 50000;
        System.out.println("=== PERFORMANCE COMPARISON (" + iterations + " iterations) ===");

        long startTime = System.nanoTime();
        concatenateWithString(iterations);
        long endTime = System.nanoTime();
        System.out.println("String concatenation time:      " + (endTime - startTime) + " ns");

        startTime = System.nanoTime();
        concatenateWithStringBuilder(iterations);
        endTime = System.nanoTime();
        System.out.println("StringBuilder concatenation time: " + (endTime - startTime) + " ns");
        
        startTime = System.nanoTime();
        concatenateWithStringBuffer(iterations);
        endTime = System.nanoTime();
        System.out.println("StringBuffer concatenation time:  " + (endTime - startTime) + " ns");

        System.out.println("\n--- StringBuilder Methods Demo ---");
        demonstrateStringBuilderMethods();
        
        System.out.println("\n--- String Comparison Demo ---");
        compareStringComparisonMethods();

        // Note: Thread safety and memory efficiency are complex topics.
        // The demonstration for thread safety is best run to see potential issues.
        // Memory efficiency is explained in the comments.
        System.out.println("\n--- Thread Safety Demo (Conceptual) ---");
        System.out.println("Running multiple threads on a shared StringBuffer is safe.");
        System.out.println("Running multiple threads on a shared StringBuilder can lead to data corruption.");
        
        System.out.println("\n--- Memory Efficiency Demo (Conceptual) ---");
        demonstrateMemoryEfficiency();
    }

    public static String concatenateWithString(int iterations) {
        String result = "";
        for (int i = 0; i < iterations; i++) {
            result += "x";
        }
        return result;
    }

    public static String concatenateWithStringBuilder(int iterations) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            sb.append("x");
        }
        return sb.toString();
    }

    public static String concatenateWithStringBuffer(int iterations) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < iterations; i++) {
            sb.append("x");
        }
        return sb.toString();
    }

    public static void demonstrateStringBuilderMethods() {
        StringBuilder sb = new StringBuilder("Hello World");
        System.out.println("Original: " + sb);
        sb.append("!"); // 1. append
        System.out.println("append:   " + sb);
        sb.insert(6, "Java "); // 2. insert
        System.out.println("insert:   " + sb);
        sb.delete(0, 6); // 3. delete
        System.out.println("delete:   " + sb);
        sb.deleteCharAt(4); // 4. deleteCharAt
        System.out.println("deleteCharAt: " + sb);
        sb.reverse(); // 5. reverse
        System.out.println("reverse:  " + sb);
        sb.reverse(); // reverse back
        sb.replace(0, 4, "Universe"); // 6. replace
        System.out.println("replace:  " + sb);
        sb.setCharAt(0, 'u'); // 7. setCharAt
        System.out.println("setCharAt: " + sb);
    }
    
    public static void compareStringComparisonMethods() {
        String str1 = "Hello";
        String str2 = "Hello";
        String str3 = new String("Hello");

        System.out.println("str1 = \"Hello\";");
        System.out.println("str2 = \"Hello\";");
        System.out.println("str3 = new String(\"Hello\");");
        
        System.out.println("\n1. == (Reference Comparison)");
        System.out.println("str1 == str2: " + (str1 == str2)); // true, from string pool
        System.out.println("str1 == str3: " + (str1 == str3)); // false, different objects
        
        System.out.println("\n2. .equals() (Content Comparison)");
        System.out.println("str1.equals(str3): " + str1.equals(str3)); // true, same content
        
        System.out.println("\n3. .compareTo() (Lexicographic Comparison)");
        System.out.println("str1.compareTo(\"Hello\"): " + str1.compareTo("Hello")); // 0, they are equal
        System.out.println("str1.compareTo(\"Apple\"): " + str1.compareTo("Apple")); // positive, 'H' > 'A'
        System.out.println("str1.compareTo(\"World\"): " + str1.compareTo("World")); // negative, 'H' < 'W'
    }

    public static void demonstrateMemoryEfficiency() {
        System.out.println("String literals like \"abc\" are stored in a common 'string pool'.");
        System.out.println("`String s1 = \"abc\"; String s2 = \"abc\";` -> s1 and s2 point to the SAME memory location.");
        System.out.println("`new String(\"abc\")` creates a NEW object on the heap every time.");
        System.out.println("StringBuilder avoids creating new objects for each modification, resizing an internal array instead, making it memory efficient for string building.");
    }
}
