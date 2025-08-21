// Problem 3: Write a program to analyze and compare the performance of
// String concatenation vs StringBuilder vs StringBuffer for building large
// strings

import java.util.Scanner;

public class program3 {

    private static final String SAMPLE_STRING = "abcdefghijklmnopqrstuvwxyz";

    public static long[] performStringConcatenation(int iterations) {
        long startTime = System.currentTimeMillis();
        String result = "";
        for (int i = 0; i < iterations; i++) {
            result += SAMPLE_STRING;
        }
        long endTime = System.currentTimeMillis();
        return new long[]{endTime - startTime, result.length()};
    }

    public static long[] performStringBuilderOperations(int iterations) {
        long startTime = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            sb.append(SAMPLE_STRING);
        }
        long endTime = System.currentTimeMillis();
        return new long[]{endTime - startTime, sb.length()};
    }

    public static long[] performStringBufferOperations(int iterations) {
        long startTime = System.currentTimeMillis();
        StringBuffer sbuf = new StringBuffer();
        for (int i = 0; i < iterations; i++) {
            sbuf.append(SAMPLE_STRING);
        }
        long endTime = System.currentTimeMillis();
        return new long[]{endTime - startTime, sbuf.length()};
    }

    public static void displayPerformance(long[] stringPerf, long[] stringBuilderPerf, long[] stringBufferPerf) {
        System.out.println("\nPerformance Comparison for String Building:");
        System.out.println("-------------------------------------------------------------------");
        System.out.printf("%-20s | %-20s | %-15s%n", "Method Used", "Time Taken (ms)", "Final String Length");
        System.out.println("-------------------------------------------------------------------");
        System.out.printf("%-20s | %-20d | %-15d%n", "String Concatenation (+)", stringPerf[0], stringPerf[1]);
        System.out.printf("%-20s | %-20d | %-15d%n", "StringBuilder.append()", stringBuilderPerf[0], stringBuilderPerf[1]);
        System.out.printf("%-20s | %-20d | %-15d%n", "StringBuffer.append()", stringBufferPerf[0], stringBufferPerf[1]);
        System.out.println("-------------------------------------------------------------------");
        System.out.println("\nNote: StringBuilder is generally faster due to being non-synchronized,");
        System.out.println("while StringBuffer is thread-safe and thus has more overhead.");
        System.out.println("String concatenation using '+' operator creates many intermediate String objects,");
        System.out.println("leading to poor performance and higher memory usage for large loops.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Problem 3: String Concatenation Performance Analysis ---");
        System.out.print("Enter the number of iterations (e.g., 1000, 10000, 100000): ");
        int iterations = scanner.nextInt();

        if (iterations <= 0) {
            System.out.println("Number of iterations must be positive.");
            scanner.close();
            return;
        }

        long[] stringConcatResults = performStringConcatenation(iterations);
        long[] stringBuilderResults = performStringBuilderOperations(iterations);
        long[] stringBufferResults = performStringBufferOperations(iterations);

        displayPerformance(stringConcatResults, stringBuilderResults, stringBufferResults);

        scanner.close();
    }
}