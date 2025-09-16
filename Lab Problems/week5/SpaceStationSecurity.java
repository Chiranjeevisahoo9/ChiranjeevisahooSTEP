import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

// --- Immutable SecurityClearance Class ---
final class SecurityClearance {
    private final String clearanceId;
    private final String level;
    private final String[] authorizedSections;
    private final long expirationTimestamp;

    public SecurityClearance(String level, String[] authorizedSections, int validityInDays) {
        if (level == null || level.trim().isEmpty()) {
            throw new IllegalArgumentException("Clearance level cannot be null or empty.");
        }
        this.clearanceId = "SEC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.level = level;
        // Defensive copy to ensure immutability
        this.authorizedSections = authorizedSections == null ? new String[0] : authorizedSections.clone();
        this.expirationTimestamp = new Date().getTime() + TimeUnit.DAYS.toMillis(validityInDays);
    }

    public String getClearanceId() { return clearanceId; }
    public String getLevel() { return level; }
    public long getExpirationTimestamp() { return expirationTimestamp; }
    
    // Getter with defensive copy for the mutable array
    public String[] getAuthorizedSections() {
        return authorizedSections.clone();
    }

    public final boolean canAccess(String section) {
        if (isExpired()) return false;
        for (String authSection : authorizedSections) {
            if (authSection.equalsIgnoreCase(section)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isExpired() {
        return new Date().getTime() > this.expirationTimestamp;
    }

    public final String getAccessHash() {
        return Integer.toHexString(hashCode());
    }

    @Override
    public String toString() {
        return String.format("Clearance[ID=%s, Level=%s, Expired=%b]", clearanceId, level, isExpired());
    }
}

// --- Immutable CrewRank Class ---
final class CrewRank {
    private final String rankName;
    private final int level;
    private final String[] permissions;

    private CrewRank(String rankName, int level, String[] permissions) {
        this.rankName = rankName;
        this.level = level;
        this.permissions = permissions.clone();
    }

    public String getRankName() { return rankName; }
    public int getLevel() { return level; }
    public String[] getPermissions() { return permissions.clone(); }

    public static CrewRank createCadet() {
        return new CrewRank("Cadet", 1, new String[]{"Basic Maintenance", "Mess Hall Duty"});
    }
    public static CrewRank createOfficer() {
        return new CrewRank("Officer", 3, new String[]{"Deck Supervision", "Security Patrol"});
    }
    public static CrewRank createCommander() {
        return new CrewRank("Commander", 5, new String[]{"Bridge Operations", "Command Decisions"});
    }
    
    @Override
    public String toString() { return rankName; }
}

// --- Specialized Crew Data Classes (No Inheritance) ---
class CommandCrew {
    private final SpaceCrew baseCrew;
    private final Set<String> commandCertifications;
    public CommandCrew(SpaceCrew baseCrew, Set<String> certs) {
        this.baseCrew = baseCrew;
        this.commandCertifications = Collections.unmodifiableSet(new HashSet<>(certs));
    }
    public SpaceCrew getBaseCrew() { return baseCrew; }
    public Set<String> getCommandCertifications() { return commandCertifications; }
    public String getCrewId() { return baseCrew.getCrewId(); }
    @Override public String toString() { return "Command Crew: " + baseCrew.getCrewName() + " (" + getCrewId() + ")"; }
}

class PilotCrew {
    private final SpaceCrew baseCrew;
    private final Set<String> flightCertifications;
    public PilotCrew(SpaceCrew baseCrew, Set<String> certs) {
        this.baseCrew = baseCrew;
        this.flightCertifications = Collections.unmodifiableSet(new HashSet<>(certs));
    }
    public SpaceCrew getBaseCrew() { return baseCrew; }
    public Set<String> getFlightCertifications() { return flightCertifications; }
    public String getCrewId() { return baseCrew.getCrewId(); }
    @Override public String toString() { return "Pilot Crew: " + baseCrew.getCrewName() + " (" + getCrewId() + ")"; }
}

// --- Main SpaceCrew Class ---
class SpaceCrew {
    private final String crewId;
    private final String crewName;
    private final String homePlanet;
    private final SecurityClearance clearance; // Immutable
    private final CrewRank initialRank;       // Immutable

    private CrewRank currentRank;
    private int missionCount;
    private double spaceHours;

    public static final String STATION_NAME = "Stellar Odyssey";
    public static final int MAX_CREW_CAPACITY = 50;
    
    // Constructor Chaining
    public SpaceCrew(String crewName) {
        this(crewName, "Unknown", CrewRank.createCadet(), 0, 0, new SecurityClearance("Restricted", new String[]{"Deck 1"}, 30));
    }
    
    public SpaceCrew(String crewName, String homePlanet, CrewRank initialRank) {
        this(crewName, homePlanet, initialRank, 0, 0, new SecurityClearance("Standard", new String[]{"Deck 1", "Deck 2", "Labs"}, 365));
    }

    public SpaceCrew(String crewName, String homePlanet, CrewRank initialRank, int missionCount, double spaceHours, SecurityClearance clearance) {
        this.crewId = "CREW-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.crewName = crewName;
        this.homePlanet = homePlanet;
        this.initialRank = initialRank;
        this.currentRank = initialRank;
        this.missionCount = missionCount;
        this.spaceHours = spaceHours;
        this.clearance = clearance; // Assign the immutable object
        
        if (!validateClearanceLevel()) {
            throw new SecurityException("Crew rank is not compatible with clearance level.");
        }
    }
    
    public String getCrewId() { return crewId; }
    public String getCrewName() { return crewName; }
    public CrewRank getCurrentRank() { return currentRank; }

    private final boolean validateClearanceLevel() {
        if (this.currentRank.getLevel() > 3 && this.clearance.getLevel().equals("Restricted")) {
            return false;
        }
        return true;
    }

    public final String getCrewIdentification() {
        return String.format("ID: %s, Name: %s, Rank: %s, Station: %s", crewId, crewName, currentRank, STATION_NAME);
    }
    
    public final boolean canBePromoted() {
        return missionCount > 5 && spaceHours > 1000 && currentRank.getLevel() < 5;
    }

    public void promote() {
        if(canBePromoted() && currentRank.getLevel() == 3) {
            this.currentRank = CrewRank.createCommander();
            System.out.println(crewName + " has been promoted to Commander!");
        }
    }
    
    public boolean canAccessSection(String section) {
        return this.clearance.canAccess(section);
    }
}

// --- Final Registry Class ---
final class SpaceStationRegistry {
    private static final Map<String, Object> crewRegistry = new HashMap<>();

    private SpaceStationRegistry() {} // Prevent instantiation

    public static boolean registerCrew(Object crew) {
        String id = null;
        if (crew instanceof CommandCrew) id = ((CommandCrew) crew).getCrewId();
        else if (crew instanceof PilotCrew) id = ((PilotCrew) crew).getCrewId();
        
        if (id != null && !crewRegistry.containsKey(id) && crewRegistry.size() < SpaceCrew.MAX_CREW_CAPACITY) {
            crewRegistry.put(id, crew);
            System.out.println("Registered Crew: " + id);
            return true;
        }
        System.out.println("Registration Failed for ID: " + id);
        return false;
    }

    public static List<Object> getCrewByType(Class<?> crewType) {
        return crewRegistry.values().stream()
            .filter(crewType::isInstance)
            .collect(Collectors.toList());
    }
    
    public static void assignTasks() {
        System.out.println("\n--- Assigning Station Tasks ---");
        for(Object crewMember : crewRegistry.values()) {
            if (crewMember instanceof CommandCrew) {
                CommandCrew cmd = (CommandCrew) crewMember;
                System.out.println(cmd.getBaseCrew().getCrewName() + " assigned to Bridge Command.");
            } else if (crewMember instanceof PilotCrew) {
                PilotCrew pilot = (PilotCrew) crewMember;
                System.out.println(pilot.getBaseCrew().getCrewName() + " assigned to flight duty.");
            }
        }
    }
}


public class SpaceStationSecurity {
    public static void main(String[] args) {
        System.out.println("Welcome to Space Station: " + SpaceCrew.STATION_NAME);
        
        // Create base crew members with different constructors and clearances
        SecurityClearance highClearance = new SecurityClearance("Top Secret", new String[]{"Bridge", "Engine Room", "Command Deck"}, 730);
        SpaceCrew commanderRiker = new SpaceCrew("William Riker", "Earth", CrewRank.createCommander(), 10, 5000, highClearance);
        SpaceCrew pilotSolo = new SpaceCrew("Han Solo", "Corellia", CrewRank.createOfficer());
        
        // Create specialized crew types
        CommandCrew cmdCrew = new CommandCrew(commanderRiker, new HashSet<>(Arrays.asList("Starfleet Command", "Tactical Analysis")));
        PilotCrew pilotCrew = new PilotCrew(pilotSolo, new HashSet<>(Arrays.asList("Freighter Piloting", "Kessel Run Certificate")));
        
        // Register crew in the final registry
        SpaceStationRegistry.registerCrew(cmdCrew);
        SpaceStationRegistry.registerCrew(pilotCrew);

        // Retrieve and display crew by type
        System.out.println("\n--- Current Command Crew ---");
        SpaceStationRegistry.getCrewByType(CommandCrew.class).forEach(System.out::println);

        System.out.println("\n--- Current Pilot Crew ---");
        SpaceStationRegistry.getCrewByType(PilotCrew.class).forEach(System.out::println);
        
        // Demonstrate access control
        System.out.println("\n--- Access Control Checks ---");
        System.out.println("Can Commander Riker access the Engine Room? " + commanderRiker.canAccessSection("Engine Room"));
        System.out.println("Can Pilot Solo access the Bridge? " + pilotSolo.canAccessSection("Bridge"));
        
        // Demonstrate task assignment using instanceof
        SpaceStationRegistry.assignTasks();
    }
}
