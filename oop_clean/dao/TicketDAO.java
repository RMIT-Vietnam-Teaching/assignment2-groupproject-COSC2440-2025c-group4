package oop_clean.dao;

import java.util.List;
import java.util.Optional;
import oop_clean.models.Ticket;

/**
 * Data access interface for Ticket entities.
 * Handles all database operations related to tickets (CRUD operations).
 * Tickets are used by attendees to register for events or sessions.
 * 
 * @author Group04
 */
public interface TicketDAO {
    // Retrieve a specific ticket by ID
    Optional<Ticket> findById(String ticketId) throws Exception;
    // Retrieve all tickets for a specific attendee
    List<Ticket> findByAttendeeId(String attendeeId) throws Exception;
    // Retrieve all tickets in the database
    List<Ticket> findAll() throws Exception;
    // Create a new ticket
    void insert(Ticket t) throws Exception;
    // Update an existing ticket (e.g., change status)
    void update(Ticket t) throws Exception;
    // Delete a ticket from the database
    void delete(String ticketId) throws Exception;
}
