package oop_clean.models;

/**
 * Represents an event in the system (conference, workshop, seminar, etc.).
 * Events contain multiple sessions and can be registered for by attendees.
 * 
 * @author Group04
 */
public class Event {
    private final String eventId; 
    private String name;
    private String type;
    private String date;      
    private String location;
    private int duration;
    private EventStatus status;
    private byte[] image;     

    public Event(String eventId, String name, String type, String date,
                 String location, int duration, EventStatus status, byte[] image) {
        this.eventId = eventId;
        this.name = name;
        this.type = type;
        this.date = date;
        this.location = location;
        this.duration = duration;
        this.status = status;
        this.image = image;
    }

    public String getEventId() { return eventId; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getDate() { return date; }
    public String getLocation() { return location; }
    public int getDuration() { return duration; }
    public EventStatus getStatus() { return status; }
    public byte[] getImage() { return image; }

    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setDate(String date) { this.date = date; }
    public void setLocation(String location) { this.location = location; }
    public void setDuration(int duration) { this.duration = duration; }
    public void setStatus(EventStatus status) { this.status = status; }
    public void setImage(byte[] image) { this.image = image; }
}
