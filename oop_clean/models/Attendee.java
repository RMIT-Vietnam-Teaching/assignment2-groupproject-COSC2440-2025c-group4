package oop_clean.models;
import java.time.LocalDate;

/**
     * @author Group04
 */
public class Attendee extends Person {
    public Attendee(String personId, String fullName, LocalDate dob, String contact,
                    String username, String password) {
        super(personId, fullName, dob, contact, username, password, Role.ATTENDEE);
    }
}
