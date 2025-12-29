package oop_clean;
import java.time.LocalDate;

/**
 * @author 
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
