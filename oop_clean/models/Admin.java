package oop_clean.models;
import java.time.LocalDate;

/**
 * @author Group04
 */
public class Admin extends Person {
    private String adminRole; 

    public Admin(String personId, String fullName, LocalDate dob, String contact,
                 String username, String password, String adminRole) {
        super(personId, fullName, dob, contact, username, password, Role.ADMIN);
        this.adminRole = adminRole;
    }

    public String getAdminRole() { return adminRole; }
    public void setAdminRole(String adminRole) { this.adminRole = adminRole; }
}
