package oop_clean.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import oop_clean.core.DatabaseConfig;
import oop_clean.models.Session;

/**
 * Implementation of SessionDAO.
 * Provides database access for session data using SQL queries.
 * 
 * @author Group04
 */
public class SessionDAOImpl implements SessionDAO {
    @Override
    // Retrieve a specific session by its ID
    public Optional<Session> findById(String sessionId) throws Exception {
        String sql = "SELECT session_id, event_id, title, description, scheduled_timestamp, venue, capacity FROM sessions WHERE session_id = ?";
        try (Connection c = DatabaseConfig.getInstance().getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, sessionId);
            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) {
                    Session s = new Session(rs.getString(1), rs.getString(3), rs.getString(4),
                            rs.getTimestamp(5) != null ? rs.getTimestamp(5).toLocalDateTime() : null,
                            rs.getString(6), rs.getInt(7), null);
                    return Optional.of(s);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Session> findByEventId(String eventId) throws Exception {
        List<Session> list = new ArrayList<>();
        String sql = "SELECT session_id, event_id, title, description, scheduled_timestamp, venue, capacity FROM sessions WHERE event_id = ?";
        try (Connection c = DatabaseConfig.getInstance().getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, eventId);
            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    Session s = new Session(rs.getString(1), rs.getString(3), rs.getString(4),
                            rs.getTimestamp(5) != null ? rs.getTimestamp(5).toLocalDateTime() : null,
                            rs.getString(6), rs.getInt(7), null);
                    list.add(s);
                }
            }
        }
        return list;
    }

    @Override
    public List<Session> findAll() throws Exception {
        List<Session> list = new ArrayList<>();
        String sql = "SELECT session_id, event_id, title, description, scheduled_timestamp, venue, capacity FROM sessions";
        try (Connection c = DatabaseConfig.getInstance().getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    Session s = new Session(rs.getString(1), rs.getString(3), rs.getString(4),
                            rs.getTimestamp(5) != null ? rs.getTimestamp(5).toLocalDateTime() : null,
                            rs.getString(6), rs.getInt(7), null);
                    list.add(s);
                }
            }
        }
        return list;
    }

    @Override
    public void insert(Session s) throws Exception {
        String sql = "INSERT INTO sessions(session_id, event_id, title, description, scheduled_timestamp, venue, capacity) VALUES (?,?,?,?,?,?,?)";
        try (Connection c = DatabaseConfig.getInstance().getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, s.getSessionId());
            p.setString(2, ""); // eventId would come from a related entity
            p.setString(3, s.getTitle());
            p.setString(4, s.getDescription());
            if (s.getScheduledDateTime() != null) p.setTimestamp(5, Timestamp.valueOf(s.getScheduledDateTime())); else p.setTimestamp(5, null);
            p.setString(6, s.getVenue());
            p.setInt(7, s.getCapacity());
            p.executeUpdate();
        }
    }

    @Override
    public void update(Session s) throws Exception {
        String sql = "UPDATE sessions SET title=?, description=?, scheduled_timestamp=?, venue=?, capacity=? WHERE session_id=?";
        try (Connection c = DatabaseConfig.getInstance().getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, s.getTitle());
            p.setString(2, s.getDescription());
            if (s.getScheduledDateTime() != null) p.setTimestamp(3, Timestamp.valueOf(s.getScheduledDateTime())); else p.setTimestamp(3, null);
            p.setString(4, s.getVenue());
            p.setInt(5, s.getCapacity());
            p.setString(6, s.getSessionId());
            p.executeUpdate();
        }
    }

    @Override
    public void delete(String sessionId) throws Exception {
        String sql = "DELETE FROM sessions WHERE session_id = ?";
        try (Connection c = DatabaseConfig.getInstance().getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, sessionId);
            p.executeUpdate();
        }
    }
}
