import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//CsvDataAnalyzer
public class problem5 {

    // b. Create a method to parse CSV data without using split()
    public static String[][] parseCsvData(String csvInput) {
        List<String[]> rows = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean inQuote = false;
        List<String> currentRow = new ArrayList<>();

        for (int i = 0; i < csvInput.length(); i++) {
            char ch = csvInput.charAt(i);

            if (ch == '"') {
                inQuote = !inQuote; // Toggle inQuote state
                // Don't append the quote itself unless it's an escaped quote inside a quoted field
                if (inQuote && i + 1 < csvInput.length() && csvInput.charAt(i + 1) == '"') {
                    currentField.append('"'); // Escaped quote "" -> "
                    i++; // Skip the next quote
                }
            } else if (ch == ',') {
                if (inQuote) {
                    currentField.append(ch); // Comma inside a quoted field
                } else {
                    currentRow.add(currentField.toString());
                    currentField.setLength(0); // Reset for next field
                }
            } else if (ch == '\n' || ch == '\r') {
                if (inQuote) {
                    currentField.append(ch); // Newline inside a quoted field
                } else {
                    // Only process if currentField has content or if it's an empty field
                    currentRow.add(currentField.toString());
                    currentField.setLength(0); // Reset for next field
                    if (!currentRow.isEmpty()) {
                        rows.add(currentRow.toArray(new String[0]));
                        currentRow.clear();
                    }
                }
            } else {
                currentField.append(ch);
            }
        }

        // Add the last row/field if input doesn't end with a newline
        if (currentField.length() > 0 || !currentRow.isEmpty()) {
            currentRow.add(currentField.toString());
            rows.add(currentRow.toArray(new String[0]));
        }


        // Convert List<String[]> to String[][]
        String[][] data = new String[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            data[i] = rows.get(i);
        }
        return data;
    }

    // Helper method to check if a string is numeric
    public static boolean isNumeric(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            // Allow '-' at the start for negative numbers, and '.' for decimals
            if (!((ch >= '0' && ch <= '9') || (ch == '-' && i == 0) || ch == '.')) {
                return false;
            }
        }
        return true;
    }

    // c. Create a method to validate and clean data
    // Returns a cleaned 2D array and modifies a list of quality issues.
    public static String[][] validateAndCleanData(String[][] rawData, List<String> dataQualityIssues) {
        if (rawData == null || rawData.length == 0) {
            return new String[0][0];
        }

        int numRows = rawData.length;
        int numCols = 0;
        if (numRows > 0) {
            numCols = rawData[0].length; // Assume first row defines number of columns
        }

        String[][] cleanedData = new String[numRows][numCols];

        for (int r = 0; r < numRows; r++) {
            // Handle rows with fewer columns than header (or first row)
            int currentCols = rawData[r].length;
            if (currentCols != numCols) {
                dataQualityIssues.add("Row " + (r + 1) + " has " + currentCols + " columns, expected " + numCols + ".");
            }

            for (int c = 0; c < numCols; c++) {
                String field = "";
                if (c < currentCols) {
                    field = rawData[r][c];
                } else {
                    dataQualityIssues.add("Missing data in Row " + (r + 1) + ", Column " + (c + 1) + ".");
                }

                // i. Remove leading/trailing spaces from each field
                field = field.trim();

                // ii. Validate numeric fields using ASCII values
                if (isNumeric(field) && field.length() > 0) { // Check if it looks numeric AND is not empty
                    // For now, we'll store as string, but mark for analysis
                } else if (field.length() > 0) { // Non-numeric or text field
                    // Example: check for very long fields
                    if (field.length() > 50) {
                        dataQualityIssues.add("Long text field in Row " + (r + 1) + ", Column " + (c + 1) + ".");
                    }
                } else { // Empty field
                    dataQualityIssues.add("Empty field in Row " + (r + 1) + ", Column " + (c + 1) + ".");
                    field = "[MISSING]"; // Placeholder for missing
                }
                cleanedData[r][c] = field;
            }
        }
        return cleanedData;
    }

    // d. Create a method to perform data analysis
    // Returns a List of String (analysis report lines)
    public static List<String> performDataAnalysis(String[][] data, String[] headers) {
        List<String> analysisReport = new ArrayList<>();
        if (data == null || data.length <= 1) { // Assuming first row is header
            analysisReport.add("No data rows for analysis.");
            return analysisReport;
        }

        int numCols = data[0].length;
        int numDataRows = data.length - 1; // Exclude header row

        analysisReport.add("Data Analysis:");
        analysisReport.add("Total Data Records Processed: " + numDataRows);

        for (int c = 0; c < numCols; c++) {
            String colName = (headers != null && c < headers.length) ? headers[c] : "Column " + (c + 1);
            analysisReport.add("\n--- " + colName + " ---");

            List<Double> numericValues = new ArrayList<>();
            List<String> categoricalValues = new ArrayList<>();
            int missingCount = 0;

            for (int r = 1; r < data.length; r++) { // Start from 1 to skip header
                String field = data[r][c];
                if (field.equals("[MISSING]")) {
                    missingCount++;
                } else if (isNumeric(field)) {
                    try {
                        numericValues.add(Double.parseDouble(field));
                    } catch (NumberFormatException e) {
                        // This should ideally not happen if isNumeric is robust
                        categoricalValues.add(field); // Treat as categorical if parsing fails
                    }
                } else {
                    categoricalValues.add(field);
                }
            }

            if (!numericValues.isEmpty()) {
                double min = numericValues.get(0);
                double max = numericValues.get(0);
                double sum = 0;
                for (Double val : numericValues) {
                    if (val < min) min = val;
                    if (val > max) max = val;
                    sum += val;
                }
                double average = sum / numericValues.size();
                analysisReport.add("  Numeric Stats:");
                analysisReport.add("    Min: " + String.format("%.2f", min));
                analysisReport.add("    Max: " + String.format("%.2f", max));
                analysisReport.add("    Avg: " + String.format("%.2f", average));
            }

            if (!categoricalValues.isEmpty()) {
                // Count unique values in categorical columns (basic approach without HashMap)
                List<String> uniqueCats = new ArrayList<>();
                List<Integer> catCounts = new ArrayList<>();
                for (String cat : categoricalValues) {
                    boolean found = false;
                    for (int i = 0; i < uniqueCats.size(); i++) {
                        if (uniqueCats.get(i).equals(cat)) {
                            catCounts.set(i, catCounts.get(i) + 1);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        uniqueCats.add(cat);
                        catCounts.add(1);
                    }
                }
                analysisReport.add("  Categorical Stats (Unique Values):");
                for (int i = 0; i < uniqueCats.size(); i++) {
                    analysisReport.add("    - " + uniqueCats.get(i) + ": " + catCounts.get(i));
                }
            }
            if (missingCount > 0) {
                analysisReport.add("  Missing Entries: " + missingCount);
            }
        }
        return analysisReport;
    }

    // e. Create a method using StringBuilder to format output
    public static String formatOutput(String[][] data, String[] headers, int[] colWidths, List<String> dataQualityIssues) {
        StringBuilder formattedOutput = new StringBuilder();

        // Calculate total width for borders
        int totalWidth = 0;
        for (int width : colWidths) {
            totalWidth += width;
        }
        totalWidth += (colWidths.length + 1) * 3; // Account for " | " between columns and at ends

        // Add top border
        for (int i = 0; i < totalWidth; i++) formattedOutput.append("-");
        formattedOutput.append("\n");

        // Add headers
        formattedOutput.append("|");
        for (int c = 0; c < headers.length; c++) {
            formattedOutput.append(" ").append(padRight(headers[c], colWidths[c])).append(" |");
        }
        formattedOutput.append("\n");

        // Add header-data separator
        for (int i = 0; i < totalWidth; i++) formattedOutput.append("-");
        formattedOutput.append("\n");

        // Add data rows
        for (int r = 0; r < data.length; r++) { // Starting from first data row
            formattedOutput.append("|");
            for (int c = 0; c < data[r].length; c++) {
                String field = data[r][c];
                // Highlight data quality issues (e.g., [MISSING])
                if (field.equals("[MISSING]")) {
                    field = "!" + padRight(field, colWidths[c] - 1); // Mark with '!'
                }
                formattedOutput.append(" ").append(padRight(field, colWidths[c])).append(" |");
            }
            formattedOutput.append("\n");
        }

        // Add bottom border
        for (int i = 0; i < totalWidth; i++) formattedOutput.append("-");
        formattedOutput.append("\n");

        return formattedOutput.toString();
    }

    // Helper for padding strings
    private static String padRight(String s, int n) {
        if (s.length() >= n) {
            return s.substring(0, n); // Truncate if too long
        }
        return String.format("%-" + n + "s", s);
    }

    // f. Create a method to generate data summary report
    public static void generateDataSummaryReport(String[][] originalData, String[][] cleanedData,
                                                  List<String> dataQualityIssues, List<String> analysisReport) {
        System.out.println("\n--- Data Summary Report ---");
        System.out.println("Total Records Processed (including header): " + originalData.length);
        System.out.println("Total Data Rows (excluding header): " + (originalData.length > 0 ? originalData.length - 1 : 0));

        System.out.println("\n--- Data Quality Findings ---");
        if (dataQualityIssues.isEmpty()) {
            System.out.println("No specific data quality issues found.");
        } else {
            for (String issue : dataQualityIssues) {
                System.out.println("- " + issue);
            }
        }

        // Calculate data completeness percentage
        int totalCells = (originalData.length > 0 && originalData[0].length > 0) ? (originalData.length -1) * originalData[0].length : 0;
        int filledCells = 0;
        for (int r = 1; r < cleanedData.length; r++) { // Skip header for completeness calc
            for (int c = 0; c < cleanedData[r].length; c++) {
                if (!cleanedData[r][c].equals("[MISSING]")) {
                    filledCells++;
                }
            }
        }
        double completenessPercentage = (totalCells > 0) ? ((double) filledCells / totalCells) * 100 : 0.0;
        System.out.printf("\nData Completeness: %.2f%%%n", completenessPercentage);

        System.out.println("\n--- Column-wise Statistics ---");
        for (String line : analysisReport) {
            System.out.println(line);
        }
        System.out.println("---------------------------------");
    }

    // g. The main function processes CSV input and generates formatted output with analysis report
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- CSV-like Data Analyzer ---");
        System.out.println("Enter CSV-like data (comma-separated values, multiple lines).");
        System.out.println("Type 'END' on a new line to finish input.");
        System.out.println("Example:");
        System.out.println("Name,Age,City,Score");
        System.out.println("Alice,30,New York,95.5");
        System.out.println("Bob,24,London,88");
        System.out.println("Charlie,,Paris,\"72.1, (partial)\"");
        System.out.println("David,29,Berlin,100");

        StringBuilder csvInputBuilder = new StringBuilder();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("END")) {
                break;
            }
            csvInputBuilder.append(line).append("\n");
        }
        String rawCsvInput = csvInputBuilder.toString().trim();

        if (rawCsvInput.isEmpty()) {
            System.out.println("No data entered. Exiting.");
            scanner.close();
            return;
        }

        // 1. Parse CSV data
        String[][] rawData = parseCsvData(rawCsvInput);
        if (rawData.length == 0 || rawData[0].length == 0) {
            System.out.println("Parsed data is empty or malformed. Exiting.");
            scanner.close();
            return;
        }

        String[] headers = rawData[0]; // Assume first row is header
        String[][] dataWithoutHeader = new String[rawData.length - 1][];
        for (int i = 1; i < rawData.length; i++) {
            dataWithoutHeader[i - 1] = rawData[i];
        }

        List<String> dataQualityIssues = new ArrayList<>();
        // 2. Validate and clean data (use dataWithoutHeader to pass to cleaning)
        String[][] cleanedData = validateAndCleanData(dataWithoutHeader, dataQualityIssues);


        // Re-construct data with header for formatting (header + cleaned data)
        String[][] dataToFormat = new String[cleanedData.length + 1][];
        dataToFormat[0] = headers;
        for(int i = 0; i < cleanedData.length; i++) {
            dataToFormat[i+1] = cleanedData[i];
        }

        // Determine column widths for formatting
        int[] colWidths = new int[headers.length];
        for (int c = 0; c < headers.length; c++) {
            int maxWidth = headers[c].length();
            for (int r = 0; r < dataToFormat.length; r++) { // Check both header and data rows
                if (c < dataToFormat[r].length && dataToFormat[r][c].length() > maxWidth) {
                    maxWidth = dataToFormat[r][c].length();
                }
            }
            colWidths[c] = maxWidth;
        }


        // 3. Perform data analysis
        List<String> analysisReport = performDataAnalysis(dataToFormat, headers);

        // 4. Format and display output
        System.out.println("\n--- Formatted Data ---");
        System.out.println(formatOutput(dataToFormat, headers, colWidths, dataQualityIssues));

        // 5. Generate and display summary report
        generateDataSummaryReport(rawData, dataToFormat, dataQualityIssues, analysisReport);


        scanner.close();
    }
}