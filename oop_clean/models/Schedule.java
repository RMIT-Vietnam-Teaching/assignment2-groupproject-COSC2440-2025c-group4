
package oop_clean.models;
import java.util.List;

/**
 * @author Group04
 */
public class Schedule {

    public boolean overlapSessions(Session a, Session b) {
        if (a == null || b == null) return false;
        return a.getScheduledDateTime().equals(b.getScheduledDateTime());
    }

    public boolean canAddSession(List<Session> existing, Session newSession) {
        if (existing == null) return true;
        for (Session s : existing) {
            if (overlapSessions(s, newSession)) return false;
        }
        return true;
    }

    public boolean canAddAttendee(Session session, int currentAttendeeCount) {
        if (session == null) return false;
        return currentAttendeeCount < session.getCapacity();
    }

    public boolean canAddPresenter(List<Session> presenterSessions, Session newSession) {
        if (presenterSessions == null) return true;
        for (Session s : presenterSessions) {
            if (overlapSessions(s, newSession)) return false;
        }
        return true;
    }
}
