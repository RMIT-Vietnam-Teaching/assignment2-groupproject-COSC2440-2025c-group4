package oop_clean.models;
import java.time.LocalDate;

/**
 * Represents a user in the event management system.
 * Can be an attendee, presenter, or admin.
 * 
 * @author Group04
 */
public class Person {
    // Unique identifier for this person
    private final String personId;  
    // Full name of the person
    private String fullName;
    // Date of birth
    private LocalDate dob;
    // Contact information (email or phone)
    private String contact;         
    // Login username
    private String username;
    // Login password (hashed in production)
    private String password;
    // Role in the system (ATTENDEE, PRESENTER, or ADMIN)
    private Role role;

    public Person(String personId, String fullName, LocalDate dob, String contact,
                  String username, String password, Role role) {
        this.personId = personId;
        this.fullName = fullName;
        this.dob = dob;
        this.contact = contact;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getPersonId() { return personId; }
    public String getFullName() { return fullName; }
    public LocalDate getDob() { return dob; }
    public String getContact() { return contact; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }

    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setDob(LocalDate dob) { this.dob = dob; }
    public void setContact(String contact) { this.contact = contact; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(Role role) { this.role = role; }
}
