import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//TextCalculator
public class problem4 {

    // b. Create a method to validate expression format
    public static boolean validateExpressionFormat(String expression) {
        int openParentheses = 0;
        int closeParentheses = 0;
        boolean lastCharWasOperator = true; // Start assuming previous was an operator for initial checks

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            // Check for valid characters (digits, operators, spaces, parentheses)
            if (!((ch >= '0' && ch <= '9') || ch == '+' || ch == '-' || ch == '*' || ch == '/' ||
                  ch == '(' || ch == ')' || ch == ' ')) {
                System.out.println("Validation Error: Invalid character '" + ch + "' found.");
                return false;
            }

            if (ch == '(') {
                openParentheses++;
                lastCharWasOperator = true; // After an opening parenthesis, expect a number or another parenthesis
            } else if (ch == ')') {
                closeParentheses++;
                if (closeParentheses > openParentheses) {
                    System.out.println("Validation Error: Unmatched closing parenthesis.");
                    return false;
                }
                lastCharWasOperator = false; // After a closing parenthesis, expect an operator or end
            } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                if (lastCharWasOperator && ch != '-') { // Allow unary minus, but not other consecutive operators
                    System.out.println("Validation Error: Consecutive operators or operator at start/end.");
                    return false;
                }
                lastCharWasOperator = true;
            } else if (ch >= '0' && ch <= '9') {
                lastCharWasOperator = false;
            }
        }

        // Validate operator placement at the end (should not end with an operator)
        if (expression.length() > 0) {
            char lastChar = expression.charAt(expression.length() - 1);
            if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/') {
                System.out.println("Validation Error: Expression ends with an operator.");
                return false;
            }
        }


        // Validate parentheses matching
        if (openParentheses != closeParentheses) {
            System.out.println("Validation Error: Unmatched parentheses.");
            return false;
        }

        return true;
    }

    // c. Create a method to parse numbers and operators from string
    // Returns a List containing both numbers (as Integer) and operators (as Character) in sequence.
    public static List<Object> parseExpression(String expression) {
        List<Object> parsedList = new ArrayList<>();
        StringBuilder currentNumber = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (ch >= '0' && ch <= '9') {
                currentNumber.append(ch);
            } else {
                if (currentNumber.length() > 0) {
                    parsedList.add(Integer.parseInt(currentNumber.toString()));
                    currentNumber.setLength(0); // Clear StringBuilder
                }

                if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                    parsedList.add(ch);
                }
                // Spaces and parentheses are handled in a higher level (like handleParentheses)
                // or ignored here if not relevant to parsing numbers/operators for a flat list.
            }
        }

        // Add the last number if any
        if (currentNumber.length() > 0) {
            parsedList.add(Integer.parseInt(currentNumber.toString()));
        }

        return parsedList;
    }

    // d. Create a method to evaluate expression using order of operations (without parentheses)
    // This method assumes the input list has no parentheses.
    public static int evaluateSimpleExpression(List<Object> tokens, StringBuilder stepLog) {
        // Create mutable lists to perform operations
        List<Integer> numbers = new ArrayList<>();
        List<Character> operators = new ArrayList<>();

        // Separate numbers and operators
        for (Object token : tokens) {
            if (token instanceof Integer) {
                numbers.add((Integer) token);
            } else if (token instanceof Character) {
                operators.add((Character) token);
            }
        }

        // 1. Handle multiplication and division first
        for (int i = 0; i < operators.size(); i++) {
            char op = operators.get(i);
            if (op == '*' || op == '/') {
                int num1 = numbers.remove(i);
                int num2 = numbers.remove(i); // Note: index i is now the next number
                int result;
                if (op == '*') {
                    result = num1 * num2;
                } else {
                    if (num2 == 0) {
                        stepLog.append("Error: Division by zero.\n");
                        return Integer.MIN_VALUE; // Indicate error
                    }
                    result = num1 / num2;
                }
                numbers.add(i, result); // Add result back at the same position
                operators.remove(i);    // Remove the operator
                i--; // Adjust index because list size decreased
                stepLog.append("Step: ").append(num1).append(" ").append(op).append(" ").append(num2).append(" = ").append(result).append("\n");
            }
        }

        // 2. Then handle addition and subtraction
        int finalResult = numbers.get(0); // Start with the first number
        stepLog.append("Intermediate: ").append(finalResult);

        for (int i = 0; i < operators.size(); i++) {
            char op = operators.get(i);
            int num = numbers.get(i + 1); // Get the next number
            if (op == '+') {
                finalResult += num;
            } else { // '-'
                finalResult -= num;
            }
            stepLog.append(" ").append(op).append(" ").append(num);
            stepLog.append(" (Current: ").append(finalResult).append(")"); // Show intermediate current result
        }
        stepLog.append("\n");
        return finalResult;
    }


    // e. Create a method to handle parentheses
    // This is the main evaluation method that handles the full expression
    public static int evaluateExpressionWithParentheses(String expression, StringBuilder stepLog) {
        String currentExpression = expression;

        while (currentExpression.contains("(") || currentExpression.contains(")")) {
            // Find innermost parentheses using indexOf() and lastIndexOf()
            int lastOpenParen = -1;
            int firstCloseParen = -1;

            // Find the last opening parenthesis
            for (int i = 0; i < currentExpression.length(); i++) {
                if (currentExpression.charAt(i) == '(') {
                    lastOpenParen = i;
                }
            }

            // Find the first closing parenthesis AFTER the last opening parenthesis
            if (lastOpenParen != -1) {
                for (int i = lastOpenParen + 1; i < currentExpression.length(); i++) {
                    if (currentExpression.charAt(i) == ')') {
                        firstCloseParen = i;
                        break;
                    }
                }
            }

            if (lastOpenParen == -1 || firstCloseParen == -1) {
                // Should have been caught by validation, but as a safeguard.
                stepLog.append("Error: Mismatched or malformed parentheses during evaluation.\n");
                return Integer.MIN_VALUE; // Indicate error
            }

            // Extract the sub-expression inside the parentheses
            String subExpression = currentExpression.substring(lastOpenParen + 1, firstCloseParen);
            stepLog.append("Evaluating sub-expression: (").append(subExpression).append(")\n");

            // Evaluate the sub-expression recursively (or using the simple evaluator)
            List<Object> subTokens = parseExpression(subExpression);
            int subResult = evaluateSimpleExpression(subTokens, stepLog);
            if (subResult == Integer.MIN_VALUE) { // Propagate error
                return Integer.MIN_VALUE;
            }

            // Replace parenthetical results in main expression
            // Use StringBuilder for efficient replacement
            StringBuilder newExpressionBuilder = new StringBuilder();
            newExpressionBuilder.append(currentExpression.substring(0, lastOpenParen));
            newExpressionBuilder.append(subResult); // Replace with the result
            newExpressionBuilder.append(currentExpression.substring(firstCloseParen + 1));
            currentExpression = newExpressionBuilder.toString();
            stepLog.append("Expression after evaluation: ").append(currentExpression).append("\n");
        }

        // After all parentheses are handled, evaluate the remaining simple expression
        List<Object> finalTokens = parseExpression(currentExpression);
        return evaluateSimpleExpression(finalTokens, stepLog);
    }

    // f. Create a method to display calculation steps
    public static void displayCalculationSteps(String originalExpression, int result, StringBuilder stepLog) {
        System.out.println("\n--- Calculation Report ---");
        System.out.println("Original Expression: " + originalExpression);
        System.out.println("\nDetailed Steps:");
        System.out.println(stepLog.toString());
        if (result != Integer.MIN_VALUE) { // Check for error indicator
            System.out.println("Final Result: " + result);
        } else {
            System.out.println("Calculation could not be completed due to an error.");
        }
        System.out.println("--------------------------");
    }

    // g. The main function processes multiple expressions
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Text-based Calculator ---");
        System.out.println("Enter mathematical expressions. Type 'exit' to quit.");

        while (true) {
            System.out.print("Enter expression: ");
            String userInput = scanner.nextLine().trim();

            if (userInput.equalsIgnoreCase("exit")) {
                break;
            }

            if (userInput.isEmpty()) {
                System.out.println("Please enter an expression.");
                continue;
            }

            // 1. Validate expression format
            if (!validateExpressionFormat(userInput)) {
                System.out.println("Expression is invalid. Please try again.");
                continue;
            }

            // 2. Evaluate expression with parentheses
            StringBuilder stepLog = new StringBuilder();
            int finalResult = evaluateExpressionWithParentheses(userInput, stepLog);

            // 3. Display calculation steps and final result
            displayCalculationSteps(userInput, finalResult, stepLog);
        }

        System.out.println("Calculator exited. Goodbye!");
        scanner.close();
    }
}