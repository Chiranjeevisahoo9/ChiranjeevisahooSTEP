
abstract class MagicalStructure {
    protected String structureName;
    protected int magicPower;
    protected String location;
    protected boolean isActive;

    public MagicalStructure(String structureName, int magicPower, String location, boolean isActive) {
        this.structureName = structureName;
        this.magicPower = magicPower;
        this.location = location;
        this.isActive = isActive;
    }

    public MagicalStructure(String structureName, int magicPower) {
        this(structureName, magicPower, "Unassigned", true);
    }
    
    public abstract void castMagicSpell();
    
    public void displayInfo() {
        System.out.printf("  - %-20s (Power: %-4d) at %s. Status: %s\n", 
            structureName, magicPower, location, isActive ? "Active" : "Inactive");
    }
}

class WizardTower extends MagicalStructure {
    private int spellCapacity;
    private String[] knownSpells;

    public WizardTower(String name, String location, int spellCapacity, String[] spells) {
        super(name, 150, location, true);
        this.spellCapacity = spellCapacity;
        this.knownSpells = spells;
    }
    
    public WizardTower(String name, String location) {
        this(name, location, 10, new String[]{"Fireball", "Magic Missile"});
    }

    @Override
    public void castMagicSpell() {
        if(knownSpells.length > 0)
            System.out.println(structureName + " casts " + knownSpells[0] + "!");
    }
    
    public void setSpellCapacity(int capacity){ this.spellCapacity = capacity; }
    public int getSpellCapacity() { return this.spellCapacity; } // Getter added
}

class EnchantedCastle extends MagicalStructure {
    private int defenseRating;
    private boolean hasDrawbridge;

    public EnchantedCastle(String name, String location, int defense, boolean drawbridge) {
        super(name, 100, location, true);
        this.defenseRating = defense;
        this.hasDrawbridge = drawbridge;
    }
    
    public EnchantedCastle(String name) {
        this(name, "Royal Hilltop", 500, true);
    }
    
    @Override
    public void castMagicSpell() {
        System.out.println(structureName + " activates its defensive magical barrier!");
    }
    
    public void setDefenseRating(int rating) { this.defenseRating = rating; }
    public int getDefenseRating() { return this.defenseRating; } // Getter added
}

class MysticLibrary extends MagicalStructure {
    private int bookCount;
    private String ancientLanguage;

    public MysticLibrary(String name, String location, int books) {
        super(name, 80, location, true);
        this.bookCount = books;
        this.ancientLanguage = "Elvish";
    }

    @Override
    public void castMagicSpell() {
        System.out.println(structureName + " reveals a forgotten prophecy!");
    }
}

class DragonLair extends MagicalStructure {
    private String dragonType;
    private int treasureValue;

    public DragonLair(String name, String location, String dragonType) {
        super(name, 250, location, true);
        this.dragonType = dragonType;
        this.treasureValue = 10000;
    }

    @Override
    public void castMagicSpell() {
        System.out.println("The " + dragonType + " dragon from " + structureName + " breathes fire!");
    }
}

class KingdomManager {

    public static int calculateKingdomMagicPower(MagicalStructure[] structures) {
        int totalPower = 0;
        for (MagicalStructure s : structures) {
            if (s.isActive) {
                totalPower += s.magicPower;
            }
        }
        return totalPower;
    }

    public static void analyzeKingdom(MagicalStructure[] structures) {
        int towerCount = 0;
        int castleCount = 0;
        int libraryCount = 0;
        int lairCount = 0;

        for (MagicalStructure s : structures) {
            if (s instanceof WizardTower) towerCount++;
            else if (s instanceof EnchantedCastle) castleCount++;
            else if (s instanceof MysticLibrary) libraryCount++;
            else if (s instanceof DragonLair) lairCount++;
        }
        
        System.out.println("\n--- Kingdom Composition ---");
        System.out.println("Wizard Towers: " + towerCount + " | Castles: " + castleCount + " | Libraries: " + libraryCount + " | Lairs: " + lairCount);

        if (towerCount > castleCount && towerCount > libraryCount) {
            System.out.println("Kingdom Specialization: Magic-focused");
        } else if (castleCount > towerCount) {
            System.out.println("Kingdom Specialization: Defense-focused");
        } else {
            System.out.println("Kingdom Specialization: Balanced");
        }
    }
    
    public static void applySynergyEffects(MagicalStructure[] structures) {
        System.out.println("\n--- Checking for Synergy Effects ---");
        WizardTower tower = null;
        MysticLibrary library = null;
        EnchantedCastle castle = null;
        DragonLair lair = null;

        for(MagicalStructure s : structures) {
            if (s instanceof WizardTower) tower = (WizardTower) s;
            if (s instanceof MysticLibrary) library = (MysticLibrary) s;
            if (s instanceof EnchantedCastle) castle = (EnchantedCastle) s;
            if (s instanceof DragonLair) lair = (DragonLair) s;
        }

        if (tower != null && library != null) {
            tower.setSpellCapacity(tower.getSpellCapacity() * 2); // Fixed
            System.out.println("Synergy Found! Wizard Tower + Library boosted spell capacity.");
        }
        if (castle != null && lair != null) {
            castle.setDefenseRating(castle.getDefenseRating() * 3); // Fixed
            System.out.println("Synergy Found! Castle + Dragon Lair tripled kingdom defense.");
        }
    }
}

public class KingdomBuilder {
    public static void main(String[] args) {
        System.out.println("--- Building a Magical Kingdom ---");

        MagicalStructure[] kingdomStructures = {
            new WizardTower("Tower of Eldoria", "North Mountains"),
            new EnchantedCastle("Castle Greystone"),
            new MysticLibrary("Grand Scriptorium", "City Center", 5000),
            new DragonLair("Black Peak Cavern", "Volcanic Plains", "Ruby")
        };

        System.out.println("\n--- Initial Kingdom Structures ---");
        for (MagicalStructure s : kingdomStructures) {
            s.displayInfo();
        }

        int initialPower = KingdomManager.calculateKingdomMagicPower(kingdomStructures);
        System.out.println("\nInitial Total Kingdom Magic Power: " + initialPower);
        
        KingdomManager.analyzeKingdom(kingdomStructures);
        
        KingdomManager.applySynergyEffects(kingdomStructures);

        System.out.println("\n--- Casting Spells from Each Structure ---");
        for (MagicalStructure s : kingdomStructures) {
            s.castMagicSpell();
        }
    }
}