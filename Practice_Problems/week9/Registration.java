class ContactInfo implements Cloneable {
    private String email;
    private String phone;
    
    public ContactInfo(String email, String phone) {
        this.email = email;
        this.phone = phone;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    @Override
    public String toString() {
        return "ContactInfo{email='" + email + "', phone='" + phone + "'}";
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

class Student implements Cloneable {
    private String id;
    private String name;
    private ContactInfo contact;
    
    public Student(String id, String name, ContactInfo contact) {
        this.id = id;
        this.name = name;
        this.contact = contact;
    }
    
    // Shallow copy
    public Student shallowCopy() throws CloneNotSupportedException {
        return (Student) super.clone();
    }
    
    // Deep copy
    public Student deepCopy() throws CloneNotSupportedException {
        Student cloned = (Student) super.clone();
        cloned.contact = (ContactInfo) this.contact.clone();
        return cloned;
    }
    
    public ContactInfo getContact() {
        return contact;
    }
    
    @Override
    public String toString() {
        return "Student{id='" + id + "', name='" + name + "', contact=" + contact + "}";
    }
}

public class Registration {
    public static void main(String[] args) throws CloneNotSupportedException {
        // 1. Register student
        ContactInfo contact = new ContactInfo("john@example.com", "1234567890");
        Student original = new Student("S001", "John Doe", contact);
        
        System.out.println("Original Student:");
        System.out.println(original);
        
        // Shallow copy
        System.out.println("\n--- SHALLOW COPY TEST ---");
        Student shallowClone = original.shallowCopy();
        System.out.println("Shallow clone: " + shallowClone);
        
        // Change contact in shallow clone
        shallowClone.getContact().setEmail("modified@example.com");
        System.out.println("\nAfter modifying shallow clone's email:");
        System.out.println("Original: " + original);
        System.out.println("Shallow clone: " + shallowClone);
        System.out.println("Notice: Both have same email (shared ContactInfo object)");
        
        // Reset original
        contact.setEmail("john@example.com");
        
        // Deep copy
        System.out.println("\n--- DEEP COPY TEST ---");
        Student deepClone = original.deepCopy();
        System.out.println("Deep clone: " + deepClone);
        
        // Change contact in deep clone
        deepClone.getContact().setEmail("deepcopy@example.com");
        System.out.println("\nAfter modifying deep clone's email:");
        System.out.println("Original: " + original);
        System.out.println("Deep clone: " + deepClone);
        System.out.println("Notice: Original unaffected (separate ContactInfo objects)");
    }
}