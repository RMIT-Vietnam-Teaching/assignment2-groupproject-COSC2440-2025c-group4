package oop_clean.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import oop_clean.core.DatabaseConfig;
import oop_clean.models.Ticket;
import oop_clean.models.TicketStatus;

/**
 * Implementation of TicketDAO.
 * Provides database access for ticket data using SQL queries.
 * 
 * @author Group04
 */
public class TicketDAOImpl implements TicketDAO {
    @Override
    // Retrieve a specific ticket by ID
    public Optional<Ticket> findById(String ticketId) throws Exception {
        String sql = "SELECT ticket_id, attendee_id, event_id, session_id, type, price, status, qr FROM tickets WHERE ticket_id = ?";
        Connection c = DatabaseConfig.getInstance().getConnection();
        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, ticketId);
            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) {
                    Ticket t = new Ticket(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                            rs.getString(5), rs.getDouble(6), TicketStatus.valueOf(rs.getString(7)), rs.getBytes(8));
                    return Optional.of(t);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Ticket> findByAttendeeId(String attendeeId) throws Exception {
        List<Ticket> list = new ArrayList<>();
        String sql = "SELECT ticket_id, attendee_id, event_id, session_id, type, price, status, qr FROM tickets WHERE attendee_id = ?";
        Connection c = DatabaseConfig.getInstance().getConnection();
        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, attendeeId);
            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    Ticket t = new Ticket(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                            rs.getString(5), rs.getDouble(6), TicketStatus.valueOf(rs.getString(7)), rs.getBytes(8));
                    list.add(t);
                }
            }
        }
        return list;
    }

    @Override
    public List<Ticket> findAll() throws Exception {
        List<Ticket> list = new ArrayList<>();
        String sql = "SELECT ticket_id, attendee_id, event_id, session_id, type, price, status, qr FROM tickets";
        Connection c = DatabaseConfig.getInstance().getConnection();
        try (PreparedStatement p = c.prepareStatement(sql)) {
            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    Ticket t = new Ticket(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                            rs.getString(5), rs.getDouble(6), TicketStatus.valueOf(rs.getString(7)), rs.getBytes(8));
                    list.add(t);
                }
            }
        }
        return list;
    }

    @Override
    public void insert(Ticket t) throws Exception {
        String sql = "INSERT INTO tickets(ticket_id, attendee_id, event_id, session_id, type, price, status, qr) VALUES (?,?,?,?,?,?,?,?)";
        Connection c = DatabaseConfig.getInstance().getConnection();
        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, t.getTicketId());
            p.setString(2, t.getAttendeeId());
            p.setString(3, t.getEventId());
            p.setString(4, t.getSessionId());
            p.setString(5, t.getType());
            p.setDouble(6, t.getPrice());
            p.setString(7, t.getStatus().name());
            p.setBytes(8, t.getQrCode());
            p.executeUpdate();
        }
    }

    @Override
    public void update(Ticket t) throws Exception {
        String sql = "UPDATE tickets SET attendee_id=?, event_id=?, session_id=?, type=?, price=?, status=?, qr=? WHERE ticket_id=?";
        Connection c = DatabaseConfig.getInstance().getConnection();
        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, t.getAttendeeId());
            p.setString(2, t.getEventId());
            p.setString(3, t.getSessionId());
            p.setString(4, t.getType());
            p.setDouble(5, t.getPrice());
            p.setString(6, t.getStatus().name());
            p.setBytes(7, t.getQrCode());
            p.setString(8, t.getTicketId());
            p.executeUpdate();
        }
    }

    @Override
    public void delete(String ticketId) throws Exception {
        String sql = "DELETE FROM tickets WHERE ticket_id = ?";
        Connection c = DatabaseConfig.getInstance().getConnection();
        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, ticketId);
            p.executeUpdate();
        }
    }
}
