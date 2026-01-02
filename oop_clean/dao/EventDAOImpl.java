package oop_clean.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import oop_clean.core.DatabaseConfig;
import oop_clean.models.Event;
import oop_clean.models.EventStatus;

/**
 * Implementation of EventDAO.
 * Provides database access for event data using SQL queries.
 * 
 * @author Group04
 */
public class EventDAOImpl implements EventDAO {
    @Override
    // Retrieve a specific event by ID
    public Optional<Event> findById(String eventId) throws Exception {
        String sql = "SELECT event_id, name, type, start_date, location, status FROM events WHERE event_id = ?";
        try (Connection c = DatabaseConfig.getInstance().getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, eventId);
            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) {
                    Event e = new Event(rs.getString(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5), 0, EventStatus.valueOf(rs.getString(6)), null);
                    return Optional.of(e);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Event> findAll() throws Exception {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT event_id, name, type, start_date, location, status FROM events";
        try (Connection c = DatabaseConfig.getInstance().getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    Event e = new Event(rs.getString(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5), 0, EventStatus.valueOf(rs.getString(6)), null);
                    list.add(e);
                }
            }
        }
        return list;
    }

    @Override
    public void insert(Event e) throws Exception {
        String sql = "INSERT INTO events(event_id, name, type, start_date, location, status) VALUES (?,?,?,?,?,?)";
        try (Connection c = DatabaseConfig.getInstance().getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, e.getEventId());
            p.setString(2, e.getName());
            p.setString(3, e.getType());
            p.setString(4, e.getDate());
            p.setString(5, e.getLocation());
            p.setString(6, e.getStatus().name());
            p.executeUpdate();
        }
    }

    @Override
    public void update(Event e) throws Exception {
        String sql = "UPDATE events SET name=?, type=?, start_date=?, location=?, status=? WHERE event_id=?";
        try (Connection c = DatabaseConfig.getInstance().getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, e.getName());
            p.setString(2, e.getType());
            p.setString(3, e.getDate());
            p.setString(4, e.getLocation());
            p.setString(5, e.getStatus().name());
            p.setString(6, e.getEventId());
            p.executeUpdate();
        }
    }

    @Override
    public void delete(String eventId) throws Exception {
        String sql = "DELETE FROM events WHERE event_id = ?";
        try (Connection c = DatabaseConfig.getInstance().getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, eventId);
            p.executeUpdate();
        }
    }
}
