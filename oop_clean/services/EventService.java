package oop_clean.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import oop_clean.dao.EventDAO;
import oop_clean.dao.EventDAOImpl;
import oop_clean.models.Event;
import oop_clean.models.EventStatus;

/**
 * Service class for event management.
 * Handles all business logic for creating, updating, retrieving, and deleting events.
 * Events are the main entities in the system that attendees can register for.
 * 
 * @author Group04
 */
public class EventService {
    private EventDAO eventDAO = new EventDAOImpl();

    // Create a new event with the provided details
    public Event createEvent(String name, String type, String date, String location) throws Exception {
        Event e = new Event(UUID.randomUUID().toString(), name, type, date, location, 0, EventStatus.SCHEDULED, null);
        eventDAO.insert(e);
        return e;
    }

    public void updateEvent(Event e) throws Exception {
        eventDAO.update(e);
    }

    public void deleteEvent(String eventId) throws Exception {
        eventDAO.delete(eventId);
    }

    public Optional<Event> getEvent(String eventId) throws Exception {
        return eventDAO.findById(eventId);
    }

    public List<Event> getAllEvents() throws Exception {
        return eventDAO.findAll();
    }

    public void cancelEvent(String eventId) throws Exception {
        Optional<Event> opt = eventDAO.findById(eventId);
        if (opt.isPresent()) {
            Event e = opt.get();
            e.setStatus(EventStatus.CANCELLED);
            eventDAO.update(e);
        }
    }
}
