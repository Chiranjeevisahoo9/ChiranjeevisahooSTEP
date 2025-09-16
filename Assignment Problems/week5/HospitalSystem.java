import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

final class MedicalRecord {
    private final String recordId;
    private final String bloodType;
    private final String[] allergies;

    public MedicalRecord(String bloodType, String[] allergies) {
        this.recordId = "MR-" + UUID.randomUUID().toString().substring(0, 4);
        this.bloodType = bloodType;
        this.allergies = allergies.clone();
    }

    public String getRecordId() { return recordId; }
    public String getBloodType() { return bloodType; }
    public String[] getAllergies() { return allergies.clone(); }

    @Override
    public String toString() {
        return String.format("Record ID: %s, Blood Type: %s, Allergies: %s",
            recordId, bloodType, Arrays.toString(allergies));
    }
}

class Patient {
    private final String patientId;
    private final MedicalRecord medicalRecord;

    private String name;
    private int roomNumber;

    public Patient(String name, int roomNumber, MedicalRecord record) {
        this.patientId = "PAT-" + UUID.randomUUID().toString().substring(0, 4);
        this.name = name;
        this.roomNumber = roomNumber;
        this.medicalRecord = record;
    }

    public String getPatientId() { return patientId; }
    public String getName() { return name; }
    public int getRoomNumber() { return roomNumber; }
    public MedicalRecord getMedicalRecord() { return medicalRecord; }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
        System.out.println(this.name + "'s room has been updated to " + this.roomNumber);
    }
}

public class HospitalSystem {
    private final Map<String, Patient> patientRegistry = new HashMap<>();

    public void admitPatient(Patient patient) {
        patientRegistry.put(patient.getPatientId(), patient);
        System.out.println("Patient " + patient.getName() + " has been admitted to room " + patient.getRoomNumber() + ".");
    }

    public void displayPatientSummary(String patientId) {
        Patient patient = patientRegistry.get(patientId);
        if (patient != null) {
            System.out.println("\n--- Patient Summary ---");
            System.out.println("Name: " + patient.getName());
            System.out.println("Room: " + patient.getRoomNumber());
            System.out.println("Medical Info: " + patient.getMedicalRecord());
            System.out.println("-----------------------");
        } else {
            System.out.println("Patient with ID " + patientId + " not found.");
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Welcome to Simple Hospital System ---");

        MedicalRecord johnsRecord = new MedicalRecord("O+", new String[]{"Peanuts"});
        
        Patient johnDoe = new Patient("John Doe", 101, johnsRecord);

        HospitalSystem hospital = new HospitalSystem();
        hospital.admitPatient(johnDoe);

        hospital.displayPatientSummary(johnDoe.getPatientId());

        johnDoe.setRoomNumber(105);
        hospital.displayPatientSummary(johnDoe.getPatientId());
        
        String[] allergies = johnDoe.getMedicalRecord().getAllergies();
        allergies[0] = "Shellfish"; 
        
        System.out.println("\nAttempted to change allergies externally.");
        System.out.println("Original record is unchanged: " + johnDoe.getMedicalRecord());
    }
}

