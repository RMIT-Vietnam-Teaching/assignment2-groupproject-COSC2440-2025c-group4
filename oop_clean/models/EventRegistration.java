package oop_clean.models;

import java.time.LocalDateTime;

/**
 * @author Group04
 */
public class EventRegistration {
    private final String registrationId; 
    private final String attendeeId;
    private final String eventId;
    private final LocalDateTime timestamp;

    public EventRegistration(String registrationId, String attendeeId, String eventId, LocalDateTime timestamp) {
        this.registrationId = registrationId;
        this.attendeeId = attendeeId;
        this.eventId = eventId;
        this.timestamp = timestamp;
    }

    public String getRegistrationId() { return registrationId; }
    public String getAttendeeId() { return attendeeId; }
    public String getEventId() { return eventId; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
