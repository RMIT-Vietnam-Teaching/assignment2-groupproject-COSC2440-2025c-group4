
package oop_clean.models;
import java.time.LocalDateTime;

/**
 * @author Group04
 */
public class SessionRegistration {
    private final String sessionRegistrationId; 
    private final String attendeeId;
    private final String sessionId;
    private final LocalDateTime timestamp;

    public SessionRegistration(String sessionRegistrationId, String attendeeId, String sessionId, LocalDateTime timestamp) {
        this.sessionRegistrationId = sessionRegistrationId;
        this.attendeeId = attendeeId;
        this.sessionId = sessionId;
        this.timestamp = timestamp;
    }

    public String getSessionRegistrationId() { return sessionRegistrationId; }
    public String getAttendeeId() { return attendeeId; }
    public String getSessionId() { return sessionId; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
