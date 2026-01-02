package oop_clean.dao;

import java.util.Optional;
import oop_clean.models.Person;

/**
 * Data access interface for Person entities.
 * Handles all database operations related to users (CRUD operations).
 * 
 * @author Group04
 */
public interface PersonDAO {
    // Find a person by their login username
    Optional<Person> findByUsername(String username) throws Exception;
    // Insert a new person into the database
    void insert(Person p) throws Exception;
}
