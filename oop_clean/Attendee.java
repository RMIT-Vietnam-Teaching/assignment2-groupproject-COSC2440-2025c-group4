package oop_clean;
import java.time.LocalDate;

/**
 * @author <your group number>
 */
public class Attendee extends Person {
    public Attendee(String personId, String fullName, LocalDate dob, String contact,
                    String username, String password) {
        super(personId, fullName, dob, contact, username, password, Role.ATTENDEE);
    }
}
