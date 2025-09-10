import java.util.Arrays;

public class StringArrays {
    // Method that takes a string array of names and returns the longest name
    public static String findLongestName(String[] names) {
        if (names == null || names.length == 0) {
            return "No names provided.";
        }
        String longestName = names[0];
        for (String name : names) {
            if (name.length() > longestName.length()) {
                longestName = name;
            }
        }
        return longestName;
    }

    // Method that counts how many names start with a given letter (case-insensitive)
    public static int countNamesStartingWith(String[] names, char letter) {
        if (names == null) return 0;
        int count = 0;
        char upperCaseLetter = Character.toUpperCase(letter);
        for (String name : names) {
            if (!name.isEmpty() && Character.toUpperCase(name.charAt(0)) == upperCaseLetter) {
                count++;
            }
        }
        return count;
    }

    // Method that formats all names to "Last, First" format
    public static String[] formatNames(String[] names) {
        if (names == null) return new String[0];
        String[] formattedNames = new String[names.length];
        for (int i = 0; i < names.length; i++) {
            String[] parts = names[i].split(" ");
            if (parts.length >= 2) {
                formattedNames[i] = parts[1] + ", " + parts[0];
            } else {
                formattedNames[i] = names[i]; // Keep as is if format is not "First Last"
            }
        }
        return formattedNames;
    }

    public static void main(String[] args) {
        String[] students = {"John Smith", "Alice Johnson", "Bob Brown", "Carol Davis", "David Wilson"};

        System.out.println("--- Student Name Analysis ---");
        System.out.println("Original list: " + Arrays.toString(students));

        // Test finding the longest name
        String longest = findLongestName(students);
        System.out.println("\nLongest name: " + longest);

        // Test counting names starting with a letter
        char letterToCount = 'D';
        int count = countNamesStartingWith(students, letterToCount);
        System.out.println("Number of names starting with '" + letterToCount + "': " + count);

        // Test formatting names
        String[] formatted = formatNames(students);
        System.out.println("Formatted names (Last, First): " + Arrays.toString(formatted));
    }
}