package oop_clean.dao;

import java.util.List;
import java.util.Optional;
import oop_clean.models.Session;

/**
 * Data access interface for Session entities.
 * Handles all database operations related to sessions (CRUD operations).
 * Sessions are parts of events and can have multiple attendees.
 * 
 * @author Group04
 */
public interface SessionDAO {
    // Retrieve a specific session by ID
    Optional<Session> findById(String sessionId) throws Exception;
    // Retrieve all sessions belonging to an event
    List<Session> findByEventId(String eventId) throws Exception;
    // Retrieve all sessions in the database
    List<Session> findAll() throws Exception;
    // Create a new session
    void insert(Session s) throws Exception;
    // Update an existing session's information
    void update(Session s) throws Exception;
    // Delete a session from the database
    void delete(String sessionId) throws Exception;
}
