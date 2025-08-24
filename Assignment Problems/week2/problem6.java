import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//FileOrganizer
public class problem6 {

    // Simple class to hold file information
    static class FileInfo {
        String originalName;
        String fileName;
        String extension;
        String category;
        String subCategory; // From content analysis
        String suggestedNewName;
        int priority; // Higher means more important/needs attention

        FileInfo(String originalName, String fileName, String extension) {
            this.originalName = originalName;
            this.fileName = fileName;
            this.extension = extension;
            this.category = "Unknown"; // Default
            this.subCategory = "N/A";
            this.suggestedNewName = originalName; // Default
            this.priority = 0; // Default
        }
    }

    // b. Create a method to extract file components without using split()
    public static FileInfo extractFileComponents(String fullFileName) {
        int lastDotIndex = -1;
        // i. Use lastIndexOf() to find the last dot for extension
        for (int i = fullFileName.length() - 1; i >= 0; i--) {
            if (fullFileName.charAt(i) == '.') {
                lastDotIndex = i;
                break;
            }
        }

        String fileName;
        String extension;

        if (lastDotIndex != -1 && lastDotIndex < fullFileName.length() - 1) { // Check if dot is not the last char
            // ii. Extract filename and extension using substring()
            fileName = fullFileName.substring(0, lastDotIndex);
            extension = fullFileName.substring(lastDotIndex + 1);
        } else {
            fileName = fullFileName;
            extension = ""; // No extension
        }

        // iii. Validate file name format and characters (basic check using ASCII)
        if (!isValidFileName(fileName)) {
            System.err.println("Warning: Invalid characters in filename part: " + fileName);
        }
        if (!isValidExtension(extension)) {
            System.err.println("Warning: Invalid characters in extension part: " + extension);
        }

        return new FileInfo(fullFileName, fileName, extension);
    }

    // Helper for validating file name characters (basic alphanumeric and common symbols)
    private static boolean isValidFileName(String name) {
        if (name.length() == 0) return false;
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            // Allow alphanumeric, underscore, hyphen, space
            if (!((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') ||
                  (ch >= '0' && ch <= '9') || ch == '_' || ch == '-' || ch == ' ')) {
                return false;
            }
        }
        return true;
    }

    // Helper for validating extension characters (basic alphanumeric)
    private static boolean isValidExtension(String ext) {
        for (int i = 0; i < ext.length(); i++) {
            char ch = ext.charAt(i);
            if (!((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9'))) {
                return false;
            }
        }
        return true;
    }

    // c. Create a method to categorize files by extension
    public static void categorizeFiles(List<FileInfo> files) {
        for (FileInfo file : files) {
            String ext = file.extension.toLowerCase(); // Case-insensitive
            if (ext.equals("txt") || ext.equals("doc") || ext.equals("docx") || ext.equals("pdf")) {
                file.category = "Documents";
            } else if (ext.equals("jpg") || ext.equals("jpeg") || ext.equals("png") || ext.equals("gif")) {
                file.category = "Images";
            } else if (ext.equals("mp3") || ext.equals("wav") || ext.equals("flac")) {
                file.category = "Audio";
            } else if (ext.equals("mp4") || ext.equals("mov") || ext.equals("avi")) {
                file.category = "Videos";
            } else if (ext.equals("zip") || ext.equals("rar") || ext.equals("7z")) {
                file.category = "Archives";
            } else if (ext.equals("java") || ext.equals("py") || ext.equals("js") || ext.equals("html") || ext.equals("css")) {
                file.category = "Code";
            } else if (ext.isEmpty()) {
                file.category = "No Extension";
            } else {
                file.category = "Other"; // iv. Identify unknown file types
            }
        }
    }

    // d. Create a method using StringBuilder to generate new file names
    public static void generateNewFileNames(List<FileInfo> files) {
        // Simple "date" simulation for uniqueness
        String dateSuffix = "20250824_"; // Example: YYYYMMDD_

        // Track base names to handle duplicates
        List<String> generatedBaseNames = new ArrayList<>();

        for (FileInfo file : files) {
            StringBuilder newNameBuilder = new StringBuilder();
            newNameBuilder.append(file.category.replace(" ", "")).append("_"); // Category_
            newNameBuilder.append(dateSuffix); // Date_
            newNameBuilder.append(file.fileName.replace(" ", "_")); // OriginalName

            // Ensure generated names follow proper file naming rules (basic cleanup)
            for(int i = 0; i < newNameBuilder.length(); i++) {
                char ch = newNameBuilder.charAt(i);
                if (!((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') ||
                      (ch >= '0' && ch <= '9') || ch == '_')) {
                    newNameBuilder.setCharAt(i, '_'); // Replace invalid chars with underscore
                }
            }


            String baseNewName = newNameBuilder.toString();
            String finalNewName = baseNewName;
            int counter = 1;

            // Handle duplicate names by adding numbers
            while (generatedBaseNames.contains(finalNewName)) {
                finalNewName = baseNewName + "_" + counter++;
            }
            generatedBaseNames.add(finalNewName); // Add the chosen name to track

            if (!file.extension.isEmpty()) {
                finalNewName += "." + file.extension;
            }
            file.suggestedNewName = finalNewName;
        }
    }

    // e. Create a method to simulate content-based analysis
    // This is a simulation since we're not actually reading file contents.
    public static void simulateContentBasedAnalysis(List<FileInfo> files) {
        for (FileInfo file : files) {
            String lowerCaseFileName = file.fileName.toLowerCase();

            // Suggest subcategories based on content keywords in filename
            if (file.category.equals("Documents")) {
                if (lowerCaseFileName.contains("report")) {
                    file.subCategory = "Report";
                    file.priority += 2;
                } else if (lowerCaseFileName.contains("resume") || lowerCaseFileName.contains("cv")) {
                    file.subCategory = "Resume/CV";
                    file.priority += 3;
                } else if (lowerCaseFileName.contains("meeting")) {
                    file.subCategory = "MeetingNotes";
                    file.priority += 1;
                }
            } else if (file.category.equals("Code")) {
                if (lowerCaseFileName.contains("main") || lowerCaseFileName.contains("app")) {
                    file.subCategory = "MainApp";
                    file.priority += 2;
                } else if (lowerCaseFileName.contains("test")) {
                    file.subCategory = "Tests";
                    file.priority += 1;
                }
            }

            // Calculate file priority based on name patterns and content keywords
            // Files with "important" or "final" in their name get higher priority
            if (lowerCaseFileName.contains("important") || lowerCaseFileName.contains("final")) {
                file.priority += 5;
            }
            // Files with "backup" or "old" get lower priority
            if (lowerCaseFileName.contains("backup") || lowerCaseFileName.contains("old")) {
                file.priority -= 1;
            }

            // Validate content characters using ASCII values - simulated for now
            // In a real scenario, this would involve reading the actual file content.
            // For text files, check for non-ASCII or problematic characters.
            // This is just a placeholder to acknowledge the hint.
            // No actual file content analysis is done here.
        }
    }


    // f. Create a method to display file organization report
    public static void displayOrganizationReport(List<FileInfo> files) {
        System.out.println("\n--- File Organization Report ---");

        // Show original filename, category, new suggested name
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-25s | %-15s | %-15s | %-15s | %-30s |%n",
                          "Original Filename", "Category", "Subcategory", "Priority", "Suggested New Name");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        for (FileInfo file : files) {
            System.out.printf("| %-25s | %-15s | %-15s | %-15d | %-30s |%n",
                              file.originalName, file.category, file.subCategory, file.priority, file.suggestedNewName);
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------");

        // Display category-wise file counts
        System.out.println("\nCategory-wise File Counts:");
        List<String> categories = new ArrayList<>();
        List<Integer> counts = new ArrayList<>();
        for (FileInfo file : files) {
            boolean found = false;
            for (int i = 0; i < categories.size(); i++) {
                if (categories.get(i).equals(file.category)) {
                    counts.set(i, counts.get(i) + 1);
                    found = true;
                    break;
                }
            }
            if (!found) {
                categories.add(file.category);
                counts.add(1);
            }
        }
        System.out.println("---------------------");
        System.out.printf("| %-15s | %-5s |%n", "Category", "Count");
        System.out.println("---------------------");
        for (int i = 0; i < categories.size(); i++) {
            System.out.printf("| %-15s | %-5d |%n", categories.get(i), counts.get(i));
        }
        System.out.println("---------------------");

        // List files that need attention (invalid names, unknown types, high priority)
        System.out.println("\nFiles Needing Attention:");
        boolean attentionNeeded = false;
        for (FileInfo file : files) {
            if (!isValidFileName(file.fileName) || !isValidExtension(file.extension) ||
                file.category.equals("Unknown") || file.priority >= 3) {
                System.out.println("- " + file.originalName + " (Category: " + file.category + ", Priority: " + file.priority + ")");
                attentionNeeded = true;
            }
        }
        if (!attentionNeeded) {
            System.out.println("No files identified as needing immediate attention.");
        }
    }

    // g. Create a method to generate batch rename commands
    public static void generateBatchRenameCommands(List<FileInfo> files) {
        System.out.println("\n--- Batch Rename Commands (Simulation) ---");
        System.out.println("Use these commands in your terminal to rename files:");

        for (FileInfo file : files) {
            if (!file.originalName.equals(file.suggestedNewName)) {
                System.out.println("mv \"" + file.originalName + "\" \"" + file.suggestedNewName + "\"");
            }
        }

        // Calculate storage organization improvement (a simulated metric)
        int renamedCount = 0;
        for (FileInfo file : files) {
            if (!file.originalName.equals(file.suggestedNewName)) {
                renamedCount++;
            }
        }
        double improvementPercentage = (files.size() > 0) ? ((double) renamedCount / files.size()) * 100 : 0.0;
        System.out.printf("\nSimulated Storage Organization Improvement: %.2f%%%n", improvementPercentage);
    }

    // h. The main function processes file list and generates comprehensive organization plan
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Text-based File Organizer ---");
        System.out.println("Enter file names with extensions, one per line.");
        System.out.println("Type 'END' on a new line when finished.");
        System.out.println("Example:");
        System.out.println("My_Document_Final_V1.docx");
        System.out.println("vacation_pic.jpg");
        System.out.println("important_report_2024.pdf");
        System.out.println("main.java");
        System.out.println("untitled file"); // No extension

        List<FileInfo> files = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.equalsIgnoreCase("END")) {
                break;
            }
            if (!line.isEmpty()) {
                files.add(extractFileComponents(line));
            }
        }

        if (files.isEmpty()) {
            System.out.println("No files entered. Exiting.");
            scanner.close();
            return;
        }

        // 1. Categorize files by extension
        categorizeFiles(files);

        // 2. Simulate content-based analysis (updates subcategory and priority)
        simulateContentBasedAnalysis(files);

        // 3. Generate new file names
        generateNewFileNames(files);

        // 4. Display file organization report
        displayOrganizationReport(files);

        // 5. Generate batch rename commands
        generateBatchRenameCommands(files);

        scanner.close();
    }
}