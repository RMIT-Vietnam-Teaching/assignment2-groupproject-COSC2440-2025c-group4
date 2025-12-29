
package oop_clean;
/**
 * @author 
 */
public class Ticket {
    private final String ticketId; 
    private final String attendeeId;
    private final String eventId;
    private final String sessionId;

    private String type;
    private double price;
    private TicketStatus status;
    private byte[] qrCode;

    public Ticket(String ticketId, String attendeeId, String eventId, String sessionId,
                  String type, double price, TicketStatus status, byte[] qrCode) {
        this.ticketId = ticketId;
        this.attendeeId = attendeeId;
        this.eventId = eventId;
        this.sessionId = sessionId;
        this.type = type;
        this.price = price;
        this.status = status;
        this.qrCode = qrCode;
    }

    public String getTicketId() { return ticketId; }
    public String getAttendeeId() { return attendeeId; }
    public String getEventId() { return eventId; }
    public String getSessionId() { return sessionId; }
    public String getType() { return type; }
    public double getPrice() { return price; }
    public TicketStatus getStatus() { return status; }
    public byte[] getQrCode() { return qrCode; }

    public void setType(String type) { this.type = type; }
    public void setPrice(double price) { this.price = price; }
    public void setStatus(TicketStatus status) { this.status = status; }
    public void setQrCode(byte[] qrCode) { this.qrCode = qrCode; }
}
