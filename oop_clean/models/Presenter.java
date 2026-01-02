
package oop_clean.models;
import java.time.LocalDate;

/**
 * @author Group04
 */
public class Presenter extends Person {
    private String presenterRole; 

    public Presenter(String personId, String fullName, LocalDate dob, String contact,
                     String username, String password, String presenterRole) {
        super(personId, fullName, dob, contact, username, password, Role.PRESENTER);
        this.presenterRole = presenterRole;
    }

    public String getPresenterRole() { return presenterRole; }
    public void setPresenterRole(String presenterRole) { this.presenterRole = presenterRole; }
}
