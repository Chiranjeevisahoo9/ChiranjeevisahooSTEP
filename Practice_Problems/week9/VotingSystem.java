// File: VotingSystem.java

public class VotingSystem {
    
    public void processVote(String voterId, String candidate) {
        // Local Inner Class: VoteValidator
        class VoteValidator {
            public boolean validate() {
                // Check if voterId is valid (simple validation: not null and proper format)
                if (voterId == null || voterId.isEmpty()) {
                    System.out.println("Invalid: Voter ID cannot be empty");
                    return false;
                }
                
                if (voterId.length() < 5) {
                    System.out.println("Invalid: Voter ID '" + voterId + "' is too short");
                    return false;
                }
                
                if (!voterId.matches("[A-Z0-9]+")) {
                    System.out.println("Invalid: Voter ID '" + voterId + "' contains invalid characters");
                    return false;
                }
                
                System.out.println("Valid: Voter ID '" + voterId + "' is valid");
                return true;
            }
        }
        
        // Create instance of local inner class and validate
        VoteValidator validator = new VoteValidator();
        
        if (validator.validate()) {
            System.out.println("Vote cast for candidate: " + candidate);
        } else {
            System.out.println("Vote rejected for candidate: " + candidate);
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        VotingSystem system = new VotingSystem();
        
        // Call processVote with various voterIds
        system.processVote("V12345", "Alice Johnson");
        system.processVote("ABC", "Bob Smith");
        system.processVote("", "Charlie Brown");
        system.processVote("VOTER001", "Diana Prince");
        system.processVote("voter@123", "Eve Adams");
    }
}