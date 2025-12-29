package oop_clean;
import java.time.LocalDate;

/**
 * @author 
 */
public class Person {
    private final String personId;  
    private String fullName;
    private LocalDate dob;
    private String contact;         
    private String username;
    private String password;
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
