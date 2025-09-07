import java.util.Random;
import java.util.UUID;

class VirtualPet {
    private final String petId;
    private String petName;
    private String species;
    private int age;
    private int happiness;
    private int health;
    private int evolutionIndex;

    public static final String[] EVOLUTION_STAGES = {"Egg", "Baby", "Child", "Teen", "Adult", "Elder", "Ghost"};
    private static int totalPetsCreated = 0;
    private static final Random random = new Random();

    public VirtualPet(String petName, String species, int age, int happiness, int health, int evolutionIndex) {
        this.petId = generatePetId();
        this.petName = petName;
        this.species = species;
        this.age = age;
        this.happiness = happiness;
        this.health = health;
        this.evolutionIndex = evolutionIndex;
        totalPetsCreated++;
    }

    public VirtualPet(String petName, String species) {
        this(petName, species, 3, 70, 80, 2); 
    }

    public VirtualPet(String petName) {
        this(petName, "Dragomon", 1, 80, 90, 1);
    }

    public VirtualPet() {
        this("Mysterious Egg", "Unknown", 0, 50, 100, 0);
        String[] randomSpecies = {"Fuzzling", "Sparklephant", "Gryphon"};
        this.species = randomSpecies[random.nextInt(randomSpecies.length)];
    }

    private static String generatePetId() {
        return "PET-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public void feedPet() {
        if (isGhost()) return;
        this.health = Math.min(100, this.health + 10);
        this.happiness = Math.min(100, this.happiness + 5);
        System.out.println(this.petName + " enjoyed the meal! Health and happiness increased.");
    }

    public void playWithPet() {
        if (isGhost()) return;
        this.happiness = Math.min(100, this.happiness + 15);
        this.health = Math.min(100, this.health - 5);
        System.out.println(this.petName + " had a great time playing! Happiness increased.");
    }
    
    public void healPet() {
        if (isGhost()) return;
        this.health = Math.min(100, this.health + 20);
        System.out.println(this.petName + " feels much better after some medicine!");
    }

    public void simulateDay() {
        if (isGhost()) {
            System.out.println(this.petName + " floats around menacingly...");
            return;
        }
        this.age++;
        this.happiness -= random.nextInt(10) + 5;
        this.health -= random.nextInt(10) + 5;
        
        System.out.println("A day has passed for " + this.petName + ".");
        
        if (this.health <= 0) {
            this.health = 0;
            this.happiness = 0;
            this.evolutionIndex = 6; 
            System.out.println("Oh no! " + this.petName + " has become a Ghost!");
        } else {
             if (this.happiness < 20) System.out.println(this.petName + " is feeling sad!");
             if (this.health < 30) System.out.println(this.petName + " is not feeling well!");
             evolvePet();
        }
    }

    private void evolvePet() {
        if (isGhost()) return;
        int previousStage = this.evolutionIndex;
        if (age > 15 && happiness > 60 && health > 60 && evolutionIndex < 4) {
            this.evolutionIndex = 4; // Adult
        } else if (age > 8 && happiness > 50 && health > 50 && evolutionIndex < 3) {
            this.evolutionIndex = 3; // Teen
        } else if (age > 3 && happiness > 40 && health > 40 && evolutionIndex < 2) {
            this.evolutionIndex = 2; // Child
        } else if (age > 0 && evolutionIndex < 1) {
             this.evolutionIndex = 1; // Baby
        }
        
        if(previousStage != this.evolutionIndex) {
            System.out.println("Congratulations! " + this.petName + " has evolved into a " + getPetStatus() + "!");
        }
    }
    
    public String getPetStatus() {
        return EVOLUTION_STAGES[this.evolutionIndex];
    }
    
    private boolean isGhost(){
        return this.evolutionIndex == 6;
    }

    public void displayInfo() {
        System.out.printf("  ID: %s | Name: %-15s | Species: %-12s | Age: %-2d | Stage: %-7s | Health: %-3d | Happiness: %-3d\n",
                petId, petName, species, age, getPetStatus(), health, happiness);
    }
    
    public static int getTotalPetsCreated() {
        return totalPetsCreated;
    }
}

public class VirtualPetSimulator {
    public static void main(String[] args) {
        System.out.println("--- Welcome to the Virtual Pet Daycare ---\n");

        VirtualPet[] daycare = {
            new VirtualPet(),
            new VirtualPet("Sparky"),
            new VirtualPet("Fluffy", "Cattus"),
            new VirtualPet("Rocky", "Rocklem", 5, 90, 90, 3)
        };

        System.out.println("--- Initial Pet Roster ---");
        for (VirtualPet pet : daycare) {
            pet.displayInfo();
        }
        System.out.println("\nTotal pets created: " + VirtualPet.getTotalPetsCreated());

        for (int day = 1; day <= 5; day++) {
            System.out.println("\n--- SIMULATING DAY " + day + " ---");
            for (VirtualPet pet : daycare) {
                pet.simulateDay();
            }

            daycare[1].feedPet();
            daycare[2].playWithPet();
            daycare[3].healPet();
        }

        System.out.println("\n--- Final Pet Roster After 5 Days ---");
        for (VirtualPet pet : daycare) {
            pet.displayInfo();
        }
    }
}