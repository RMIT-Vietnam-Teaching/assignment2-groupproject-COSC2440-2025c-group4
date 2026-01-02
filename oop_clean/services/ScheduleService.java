package oop_clean.services;

import java.util.List;
import oop_clean.models.Session;

/**
 * Service class for schedule management and conflict detection.
 * Handles validation to prevent scheduling conflicts (e.g., same time/venue).
 * Ensures sessions can be properly scheduled without overlaps.
 * 
 * @author Group04
 */
public class ScheduleService {
    // Check if two sessions have a scheduling conflict (same time)
    public boolean hasConflict(Session session1, Session session2) {
        if (session1 == null || session2 == null) return false;
        return session1.getScheduledDateTime().equals(session2.getScheduledDateTime());
    }

    public boolean canAddSession(List<Session> existingSessions, Session newSession) {
        if (existingSessions == null) return true;
        for (Session s : existingSessions) {
            if (hasConflict(s, newSession)) {
                return false;
            }
        }
        return true;
    }

    public boolean canAddAttendee(Session session, int currentAttendeeCount) {
        if (session == null) return false;
        return currentAttendeeCount < session.getCapacity();
    }
}
