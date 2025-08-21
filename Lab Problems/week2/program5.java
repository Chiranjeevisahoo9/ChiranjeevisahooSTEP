// Problem 5: Write a program to extract and analyze different parts of an
// email address using substring() and indexOf() methods

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class program5 {

    static class EmailDetails {
        String email;
        String username;
        String domain;
        String domainName;
        String extension;
        boolean isValid;

        public EmailDetails(String email) {
            this.email = email;
        }

        @Override
        public String toString() {
            return String.format("%-30s | %-20s | %-20s | %-15s | %-15s | %-10s",
                    email, username, domain, domainName, extension, isValid ? "Valid ✅" : "Invalid ❌");
        }
    }

    public static boolean validateEmailFormat(String email) {
        int atIndex = email.indexOf('@');
        int lastAtIndex = email.lastIndexOf('@');

        if (atIndex == -1 || atIndex != lastAtIndex) {
            return false;
        }

        int dotIndex = email.indexOf('.', atIndex);
        if (dotIndex == -1 || dotIndex == atIndex + 1) {
            return false;
        }

        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        if (username.isEmpty() || domain.isEmpty()) {
            return false;
        }

        int lastDotInDomain = domain.lastIndexOf('.');
        if (lastDotInDomain == -1 || lastDotInDomain == domain.length() - 1) {
            return false;
        }
        
        if (domain.substring(0, dotIndex - atIndex -1).isEmpty() || domain.substring(lastDotInDomain + 1).isEmpty()) {
             return false;
        }


        return true;
    }

    public static void extractEmailComponents(EmailDetails emailDetails) {
        if (!emailDetails.isValid) {
            emailDetails.username = "N/A";
            emailDetails.domain = "N/A";
            emailDetails.domainName = "N/A";
            emailDetails.extension = "N/A";
            return;
        }

        int atIndex = emailDetails.email.indexOf('@');
        emailDetails.username = emailDetails.email.substring(0, atIndex);
        emailDetails.domain = emailDetails.email.substring(atIndex + 1);

        int lastDotIndex = emailDetails.domain.lastIndexOf('.');
        if (lastDotIndex != -1) {
            emailDetails.domainName = emailDetails.domain.substring(0, lastDotIndex);
            emailDetails.extension = emailDetails.domain.substring(lastDotIndex + 1);
        } else {
            emailDetails.domainName = emailDetails.domain;
            emailDetails.extension = "N/A";
        }
    }

    public static void analyzeEmailStatistics(List<EmailDetails> emailList) {
        int totalValid = 0;
        int totalInvalid = 0;
        long totalUsernameLength = 0;
        Map<String, Integer> domainCounts = new HashMap<>();

        for (EmailDetails details : emailList) {
            if (details.isValid) {
                totalValid++;
                totalUsernameLength += details.username.length();
                domainCounts.put(details.domain, domainCounts.getOrDefault(details.domain, 0) + 1);
            } else {
                totalInvalid++;
            }
        }

        String mostCommonDomain = "N/A";
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : domainCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostCommonDomain = entry.getKey();
            }
        }

        double averageUsernameLength = totalValid > 0 ? (double) totalUsernameLength / totalValid : 0;

        System.out.println("\n--- Email Analysis Statistics ---");
        System.out.println("Total Emails Processed: " + emailList.size());
        System.out.println("Total Valid Emails: " + totalValid);
        System.out.println("Total Invalid Emails: " + totalInvalid);
        System.out.printf("Average Username Length: %.2f%n", averageUsernameLength);
        System.out.println("Most Common Domain: " + mostCommonDomain + " (Occurrences: " + maxCount + ")");
    }

    public static void displayResults(List<EmailDetails> emailList) {
        System.out.println("\n--- Email Analysis Results ---");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-30s | %-20s | %-20s | %-15s | %-15s | %-10s%n",
                "Email", "Username", "Domain", "Domain Name", "Extension", "Valid/Invalid");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
        for (EmailDetails details : emailList) {
            System.out.println(details);
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<EmailDetails> emailList = new ArrayList<>();

        System.out.println("--- Problem 5: Email Address Extractor and Analyzer ---");
        System.out.println("Enter email addresses one by one. Type 'done' to finish.");

        String input;
        while (true) {
            System.out.print("Enter email address: ");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("done")) {
                break;
            }
            EmailDetails details = new EmailDetails(input);
            details.isValid = validateEmailFormat(input);
            extractEmailComponents(details);
            emailList.add(details);
        }

        displayResults(emailList);
        analyzeEmailStatistics(emailList);

        scanner.close();
    }
}