
package oop_clean.models;
import java.time.LocalDateTime;

/**
 * Represents a session within an event.
 * A session has a specific time, venue, and capacity.
 * Multiple attendees can register for a session.
 * 
 * @author Group04
 */
public class Session {
    private final String sessionId; 
    private String title;
    private String description;
    private LocalDateTime scheduledDateTime;
    private String venue;
    private int capacity;
    private byte[] materials; 

    public Session(String sessionId, String title, String description,
                   LocalDateTime scheduledDateTime, String venue, int capacity, byte[] materials) {
        this.sessionId = sessionId;
        this.title = title;
        this.description = description;
        this.scheduledDateTime = scheduledDateTime;
        this.venue = venue;
        this.capacity = capacity;
        this.materials = materials;
    }

    public String getSessionId() { return sessionId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getScheduledDateTime() { return scheduledDateTime; }
    public String getVenue() { return venue; }
    public int getCapacity() { return capacity; }
    public byte[] getMaterials() { return materials; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setScheduledDateTime(LocalDateTime scheduledDateTime) { this.scheduledDateTime = scheduledDateTime; }
    public void setVenue(String venue) { this.venue = venue; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setMaterials(byte[] materials) { this.materials = materials; }
}
