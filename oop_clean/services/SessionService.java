package oop_clean.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import oop_clean.dao.SessionDAO;
import oop_clean.dao.SessionDAOImpl;
import oop_clean.models.Session;

/**
 * Service class for session management.
 * Handles all business logic for creating, updating, retrieving, and managing sessions.
 * Sessions are time-specific parts of events with limited capacity.
 * 
 * @author Group04
 */
public class SessionService {
    private final SessionDAO sessionDAO = new SessionDAOImpl();

    // Create a new session with the provided details
    public Session createSession(String title, String description, LocalDateTime scheduledDateTime, 
                                  String venue, int capacity) throws Exception {
        Session s = new Session(UUID.randomUUID().toString(), title, description, scheduledDateTime, venue, capacity, null);
        sessionDAO.insert(s);
        return s;
    }

    public void updateSession(Session s) throws Exception {
        sessionDAO.update(s);
    }

    public void deleteSession(String sessionId) throws Exception {
        sessionDAO.delete(sessionId);
    }

    public Optional<Session> getSession(String sessionId) throws Exception {
        return sessionDAO.findById(sessionId);
    }

    public List<Session> getSessionsByEvent(String eventId) throws Exception {
        return sessionDAO.findByEventId(eventId);
    }

    public List<Session> getAllSessions() throws Exception {
        return sessionDAO.findAll();
    }

    public boolean canAddSession(List<Session> existing, Session newSession) {
        if (existing == null) return true;
        for (Session s : existing) {
            if (s.getScheduledDateTime().equals(newSession.getScheduledDateTime())) {
                return false;
            }
        }
        return true;
    }
}
