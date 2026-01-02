package oop_clean.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import oop_clean.dao.TicketDAO;
import oop_clean.dao.TicketDAOImpl;
import oop_clean.models.Ticket;
import oop_clean.models.TicketStatus;

/**
 * Service class for ticket management.
 * Handles all business logic for creating, updating, and managing tickets.
 * Tickets are used by attendees to register for events and sessions.
 * 
 * @author Group04
 */
public class TicketService {
    private final TicketDAO ticketDAO = new TicketDAOImpl();

    // Create a new ticket for an attendee to access an event/session
    public Ticket generateTicket(String type, double price, String attendeeId, String eventId, String sessionId) throws Exception {
        Ticket t = new Ticket(UUID.randomUUID().toString(), attendeeId, eventId, sessionId, type, price, TicketStatus.ACTIVE, null);
        ticketDAO.insert(t);
        return t;
    }

    public void updateTicketStatus(String ticketId, TicketStatus status) throws Exception {
        Optional<Ticket> opt = ticketDAO.findById(ticketId);
        if (opt.isPresent()) {
            Ticket t = opt.get();
            t.setStatus(status);
            ticketDAO.update(t);
        }
    }

    public void deleteTicket(String ticketId) throws Exception {
        ticketDAO.delete(ticketId);
    }

    public Optional<Ticket> getTicket(String ticketId) throws Exception {
        return ticketDAO.findById(ticketId);
    }

    public List<Ticket> getTicketsByAttendee(String attendeeId) throws Exception {
        return ticketDAO.findByAttendeeId(attendeeId);
    }

    public List<Ticket> getAllTickets() throws Exception {
        return ticketDAO.findAll();
    }
}
