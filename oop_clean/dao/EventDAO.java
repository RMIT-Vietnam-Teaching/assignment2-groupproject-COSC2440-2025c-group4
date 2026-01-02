package oop_clean.dao;

import java.util.List;
import java.util.Optional;
import oop_clean.models.Event;

/**
 * Data access interface for Event entities.
 * Handles all database operations related to events (CRUD: Create, Read, Update, Delete).
 * 
 * @author Group04
 */
public interface EventDAO {
    // Retrieve an event by its ID
    Optional<Event> findById(String eventId) throws Exception;
    // Retrieve all events from the database
    List<Event> findAll() throws Exception;
    // Create a new event
    void insert(Event e) throws Exception;
    // Update an existing event's information
    void update(Event e) throws Exception;
    // Delete an event from the database
    void delete(String eventId) throws Exception;
}
