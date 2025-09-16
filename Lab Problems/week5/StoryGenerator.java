import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

// --- Immutable CharacterDNA Class ---
final class CharacterDNA implements Serializable {
    private final String geneticId;
    private final String personalityType;
    private final String[] innateTalents;
    private final String characterArchetype;
    private final Map<String, Integer> baseAttributes; // e.g., STR, INT, AGI

    private CharacterDNA(String personality, String archetype, String[] talents, Map<String, Integer> attributes) {
        this.geneticId = "DNA-" + UUID.randomUUID().toString().toUpperCase().substring(0, 8);
        this.personalityType = personality;
        this.characterArchetype = archetype;
        // Defensive copying for immutability
        this.innateTalents = talents.clone();
        this.baseAttributes = Collections.unmodifiableMap(new HashMap<>(attributes));
    }

    public String getGeneticId() { return geneticId; }
    public String getPersonalityType() { return personalityType; }
    public String getCharacterArchetype() { return characterArchetype; }
    public String[] getInnateTalents() { return innateTalents.clone(); }
    public Map<String, Integer> getBaseAttributes() { return baseAttributes; } // Already unmodifiable

    public final boolean isCompatibleWith(CharacterDNA other) {
        return !this.characterArchetype.equals(other.characterArchetype);
    }
    
    public static CharacterDNA createFromTemplate(String template) {
        Map<String, Integer> attributes = new HashMap<>();
        if ("Hero".equals(template)) {
            attributes.put("Courage", 10); attributes.put("Strength", 8);
            return new CharacterDNA("Brave", "The Chosen One", new String[]{"Swordsmanship", "Leadership"}, attributes);
        } else if ("Villain".equals(template)) {
            attributes.put("Cunning", 10); attributes.put("Power", 9);
            return new CharacterDNA("Ruthless", "The Tyrant", new String[]{"Dark Magic", "Deception"}, attributes);
        }
        return null;
    }
    @Override public String toString() { return String.format("DNA[%s, %s]", geneticId, characterArchetype); }
}

// --- Base StoryCharacter Class ---
class StoryCharacter implements Serializable {
    private final String characterId;
    private final CharacterDNA dna;
    private final long birthTimestamp;

    private String currentName;
    private String currentLocation;
    private String emotionalState;
    private int experiencePoints;
    private final Set<String> learnedSkills = new HashSet<>();

    public static final String CHARACTER_SYSTEM_VERSION = "4.0";
    static final String STORY_ENGINE_VERSION = "4.0"; // Package-private
    
    // Constructor Chaining
    public StoryCharacter(CharacterDNA dna, String name) {
        this(dna, name, "The Void", "Neutral");
    }

    public StoryCharacter(CharacterDNA dna, String name, String startLocation, String mood) {
        this.characterId = "CHAR-" + UUID.randomUUID().toString().toUpperCase().substring(0, 8);
        this.dna = dna;
        this.birthTimestamp = System.currentTimeMillis();
        this.currentName = name;
        this.currentLocation = startLocation;
        this.emotionalState = mood;
        this.experiencePoints = 0;
    }

    // Getters for final fields, Getters/Setters for mutable fields
    public String getCharacterId() { return characterId; }
    public CharacterDNA getDna() { return dna; }
    public String getCurrentName() { return currentName; }
    public void setCurrentName(String name) { this.currentName = name; }
    public String getCurrentLocation() { return currentLocation; }
    public void setCurrentLocation(String loc) { this.currentLocation = loc; }
    public String getEmotionalState() { return emotionalState; }
    public void setEmotionalState(String state) { this.emotionalState = state; }

    @Override public String toString() { return String.format("%s the %s", currentName, dna.getCharacterArchetype()); }
    @Override public int hashCode() { return Objects.hash(characterId); }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return characterId.equals(((StoryCharacter) o).characterId);
    }
}

// --- Specialized Character Classes ---
class HeroCharacter extends StoryCharacter {
    private final String destinyQuest;
    public HeroCharacter(CharacterDNA dna, String name, String quest) {
        super(dna, name, "Homestead", "Hopeful");
        this.destinyQuest = quest;
    }
    public String getDestinyQuest() { return destinyQuest; }
}

class VillainCharacter extends StoryCharacter {
    private final String evilScheme;
    public VillainCharacter(CharacterDNA dna, String name, String scheme) {
        super(dna, name, "Dark Fortress", "Ambitious");
        this.evilScheme = scheme;
    }
    public String getEvilScheme() { return evilScheme; }
}

class SelfAwareCharacter extends StoryCharacter {
    public SelfAwareCharacter(CharacterDNA dna, String name) { super(dna, name); }
    public void commentOnLimitations() {
        System.out.println("'" + getCurrentName() + "' sighs. 'My very essence, my DNA (" + getDna().getGeneticId() + "), is `final`. " + 
        "No matter how much I develop, I can't change who I fundamentally am. It's the curse of immutability!'");
    }
}

// --- Final StoryEngine (Singleton) ---
final class StoryEngine {
    private static final StoryEngine INSTANCE = new StoryEngine();
    private final Map<String, StoryCharacter> activeCharacters = new HashMap<>();
    private final String[] narrativeRules = {"A hero must face a villain.", "Conflict drives the plot."};

    private StoryEngine() {}

    public static StoryEngine getInstance() { return INSTANCE; }

    void registerCharacter(StoryCharacter character) {
        activeCharacters.put(character.getCharacterId(), character);
    }

    public final String generateNarrative() {
        StringBuilder narrative = new StringBuilder("A new story begins...\n");
        List<StoryCharacter> chars = new ArrayList<>(activeCharacters.values());
        if (chars.size() < 2) return "Not enough characters to tell a story.";

        for (int i = 0; i < chars.size(); i++) {
            for (int j = i + 1; j < chars.size(); j++) {
                narrative.append(resolveConflict(chars.get(i), chars.get(j)));
            }
        }
        return narrative.toString();
    }
    
    private String resolveConflict(StoryCharacter c1, StoryCharacter c2) {
        // Complex interaction logic using instanceof
        if (c1 instanceof HeroCharacter && c2 instanceof VillainCharacter) {
            HeroCharacter hero = (HeroCharacter) c1;
            VillainCharacter villain = (VillainCharacter) c2;
            return String.format("The valiant hero, %s, on a quest to '%s', confronts the evil %s, who plots to '%s'. A legendary battle is imminent!\n",
                hero.getCurrentName(), hero.getDestinyQuest(), villain.getCurrentName(), villain.getEvilScheme());
        } else if (c1 instanceof VillainCharacter && c2 instanceof HeroCharacter) {
            return resolveConflict(c2, c1); // Reuse logic
        } else {
            return String.format("%s and %s cross paths, their destinies yet unwritten.\n", c1.getCurrentName(), c2.getCurrentName());
        }
    }
}

public class StoryGenerator {
    public static void main(String[] args) {
        System.out.println("Story Engine Version: " + StoryCharacter.STORY_ENGINE_VERSION);
        System.out.println("Character System Version: " + StoryCharacter.CHARACTER_SYSTEM_VERSION);
        
        // Create DNA templates
        CharacterDNA heroDNA = CharacterDNA.createFromTemplate("Hero");
        CharacterDNA villainDNA = CharacterDNA.createFromTemplate("Villain");

        // Create specialized characters
        HeroCharacter arthur = new HeroCharacter(heroDNA, "Arthur", "Find the Holy Grail");
        VillainCharacter mordred = new VillainCharacter(villainDNA, "Mordred", "Usurp the Throne");
        SelfAwareCharacter glados = new SelfAwareCharacter(CharacterDNA.createFromTemplate("Villain"), "GLaDOS");

        // Get the StoryEngine instance and register characters
        StoryEngine engine = StoryEngine.getInstance();
        engine.registerCharacter(arthur);
        engine.registerCharacter(mordred);
        engine.registerCharacter(glados);
        
        // Generate and print the story
        System.out.println("\n--- GENERATED NARRATIVE ---");
        String story = engine.generateNarrative();
        System.out.println(story);
        
        // Demonstrate meta-commentary from self-aware character
        System.out.println("--- A Character's Thoughts ---");
        glados.commentOnLimitations();
    }
}
