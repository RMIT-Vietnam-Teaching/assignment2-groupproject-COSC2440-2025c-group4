package oop_clean.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Optional;
import oop_clean.core.DatabaseConfig;
import oop_clean.models.Person;

/**
 * Implementation of PersonDAO.
 * Provides database access for user (person) data using SQL queries.
 * 
 * @author Group04
 */
public class PersonDAOImpl implements PersonDAO {
    @Override
    // Search for a person by username (used during login)
    public Optional<Person> findByUsername(String username) throws Exception {
        String sql = "SELECT person_id, full_name, dob, contact, username, password, role FROM persons WHERE username = ?";
        Connection c = DatabaseConfig.getInstance().getConnection();
        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, username);
            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) {
                    String id = rs.getString(1);
                    String fullName = rs.getString(2);
                    LocalDate dob = rs.getDate(3) != null ? rs.getDate(3).toLocalDate() : null;
                    String contact = rs.getString(4);
                    String user = rs.getString(5);
                    String pass = rs.getString(6);
                    // role parsing is left simple for now
                    Person pObj = new Person(id, fullName, dob, contact, user, pass, null);
                    return Optional.of(pObj);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void insert(Person p) throws Exception {
        String sql = "INSERT INTO persons(person_id, full_name, dob, contact, username, password, role) VALUES (?,?,?,?,?,?,?)";
        Connection c = DatabaseConfig.getInstance().getConnection();
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getPersonId());
            ps.setString(2, p.getFullName());
            if (p.getDob() != null) ps.setDate(3, java.sql.Date.valueOf(p.getDob())); else ps.setDate(3, null);
            ps.setString(4, p.getContact());
            ps.setString(5, p.getUsername());
            ps.setString(6, p.getPassword());
            ps.setString(7, p.getRole() != null ? p.getRole().name() : null);
            ps.executeUpdate();
        }
    }
}
