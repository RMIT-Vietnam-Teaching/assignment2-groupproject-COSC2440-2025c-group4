package oop_clean.services;

import java.util.Optional;
import oop_clean.dao.PersonDAO;
import oop_clean.dao.PersonDAOImpl;
import oop_clean.models.Person;

/**
 * Service class for user authentication.
 * Handles login verification by checking username and password against the database.
 * 
 * @author Group04
 */
public class AuthService {
    private final PersonDAO personDAO = new PersonDAOImpl();

    // Authenticate a user - verify username and password match
    public Optional<Person> authenticate(String username, String password) throws Exception {
        Optional<Person> person = personDAO.findByUsername(username);
        if (person.isPresent() && person.get().getPassword().equals(password)) {
            return person;
        }
        return Optional.empty();
    }
}
