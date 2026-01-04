package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Database initialization class that sets up the schema and seed data.
 * Creates all tables on first run if they don't exist.
 * Inserts sample data for testing (users, events, sessions, tickets).
 * 
 * @author Group04
 */
public class DBInit {
    public static void init() throws SQLException {
        // Get the database connection from the singleton
        Connection conn = DatabaseConfig.getInstance().getConnection();
        // Create a minimal set of tables for core functionality.
        String createPersons = "CREATE TABLE IF NOT EXISTS persons (person_id VARCHAR PRIMARY KEY, full_name VARCHAR, dob DATE, contact VARCHAR, username VARCHAR UNIQUE, password VARCHAR, role VARCHAR);";
        String createEvents = "CREATE TABLE IF NOT EXISTS events (event_id VARCHAR PRIMARY KEY, name VARCHAR, type VARCHAR, start_date VARCHAR, end_date VARCHAR, location VARCHAR, status VARCHAR);";
        String createSessions = "CREATE TABLE IF NOT EXISTS sessions (session_id VARCHAR PRIMARY KEY, event_id VARCHAR REFERENCES events(event_id), title VARCHAR, description TEXT, scheduled_timestamp TIMESTAMP, venue VARCHAR, capacity INTEGER);";
        String createTickets = "CREATE TABLE IF NOT EXISTS tickets (ticket_id VARCHAR PRIMARY KEY, attendee_id VARCHAR REFERENCES persons(person_id), event_id VARCHAR REFERENCES events(event_id), session_id VARCHAR REFERENCES sessions(session_id), type VARCHAR, price NUMERIC, status VARCHAR, qr BYTEA);";

        try (PreparedStatement p1 = conn.prepareStatement(createPersons);
             PreparedStatement p2 = conn.prepareStatement(createEvents);
             PreparedStatement p3 = conn.prepareStatement(createSessions);
             PreparedStatement p4 = conn.prepareStatement(createTickets)) {
            p1.execute(); p2.execute(); p3.execute(); p4.execute();
        }

        // Seed 30+ persons (mix of attendees, presenters, admins)
        String[] seedPersons = {
            "INSERT INTO persons(person_id, full_name, dob, contact, username, password, role) VALUES ('P001','Alice Johnson','1990-01-15','alice@example.com','alice','password1','ATTENDEE') ON CONFLICT DO NOTHING;",
            "INSERT INTO persons(person_id, full_name, dob, contact, username, password, role) VALUES ('P002','Bob Smith','1985-05-22','bob@example.com','bob','password2','ATTENDEE') ON CONFLICT DO NOTHING;",
            "INSERT INTO persons(person_id, full_name, dob, contact, username, password, role) VALUES ('P003','Carol White','1992-09-10','carol@example.com','carol','password3','PRESENTER') ON CONFLICT DO NOTHING;",
            "INSERT INTO persons(person_id, full_name, dob, contact, username, password, role) VALUES ('P004','Diana Brown','1988-03-18','diana@example.com','diana','password4','PRESENTER') ON CONFLICT DO NOTHING;",
            "INSERT INTO persons(person_id, full_name, dob, contact, username, password, role) VALUES ('P005','Evan Davis','1995-07-25','evan@example.com','evan','password5','ATTENDEE') ON CONFLICT DO NOTHING;",
            "INSERT INTO persons(person_id, full_name, dob, contact, username, password, role) VALUES ('P006','Fiona Miller','1991-11-30','fiona@example.com','fiona','password6','ATTENDEE') ON CONFLICT DO NOTHING;",
            "INSERT INTO persons(person_id, full_name, dob, contact, username, password, role) VALUES ('P007','George Wilson','1987-02-14','george@example.com','george','password7','PRESENTER') ON CONFLICT DO NOTHING;",
            "INSERT INTO persons(person_id, full_name, dob, contact, username, password, role) VALUES ('P008','Hannah Moore','1993-06-08','hannah@example.com','hannah','password8','ATTENDEE') ON CONFLICT DO NOTHING;",
            "INSERT INTO persons(person_id, full_name, dob, contact, username, password, role) VALUES ('P009','Isaac Taylor','1989-10-20','isaac@example.com','isaac','password9','PRESENTER') ON CONFLICT DO NOTHING;",
            "INSERT INTO persons(person_id, full_name, dob, contact, username, password, role) VALUES ('P010','Julie Anderson','1994-04-12','julie@example.com','julie','password10','ATTENDEE') ON CONFLICT DO NOTHING;",
            "INSERT INTO persons(person_id, full_name, dob, contact, username, password, role) VALUES ('P011','Kevin Thomas','1986-08-19','kevin@example.com','kevin','password11','ATTENDEE') ON CONFLICT DO NOTHING;",
            "INSERT INTO persons(person_id, full_name, dob, contact, username, password, role) VALUES ('P012','Laura Jackson','1991-12-05','laura@example.com','laura','password12','PRESENTER') ON CONFLICT DO NOTHING;",
            "INSERT INTO persons(person_id, full_name, dob, contact, username, password, role) VALUES ('P013','Mike White','1990-01-27','mike@example.com','mike','password13','ATTENDEE') ON CONFLICT DO NOTHING;",
            "INSERT INTO persons(person_id, full_name, dob, contact, username, password, role) VALUES ('P014','Nancy Harris','1993-05-15','nancy@example.com','nancy','password14','PRESENTER') ON CONFLICT DO NOTHING;",
            "INSERT INTO persons(person_id, full_name, dob, contact, username, password, role) VALUES ('P015','Oscar Martin','1987-09-22','oscar@example.com','oscar','password15','ATTENDEE') ON CONFLICT DO NOTHING;",
            "INSERT INTO persons(person_id, full_name, dob, contact, username, password, role) VALUES ('P016','Patricia Garcia','1992-03-10','patricia@example.com','patricia','password16','ATTENDEE') ON CONFLICT DO NOTHING;",
            "INSERT INTO persons(person_id, full_name, dob, contact, username, password, role) VALUES ('P017','Quentin Lee','1988-07-17','quentin@example.com','quentin','password17','PRESENTER') ON CONFLICT DO NOTHING;",
            "INSERT INTO persons(person_id, full_name, dob, contact, username, password, role) VALUES ('P018','Rachel Clark','1995-11-02','rachel@example.com','rachel','password18','ATTENDEE') ON CONFLICT DO NOTHING;",
            "INSERT INTO persons(person_id, full_name, dob, contact, username, password, role) VALUES ('P019','Samuel Rodriguez','1989-06-28','samuel@example.com','samuel','password19','PRESENTER') ON CONFLICT DO NOTHING;",
            "INSERT INTO persons(person_id, full_name, dob, contact, username, password, role) VALUES ('P020','Tina Lewis','1994-10-09','tina@example.com','tina','password20','ATTENDEE') ON CONFLICT DO NOTHING;",
            "INSERT INTO persons(person_id, full_name, dob, contact, username, password, role) VALUES ('P021','Admin User','1980-01-01','admin@example.com','admin','admin123','ADMIN') ON CONFLICT DO NOTHING;",
            "INSERT INTO persons(person_id, full_name, dob, contact, username, password, role) VALUES ('P022','Vincent Walker','1991-04-14','vincent@example.com','vincent','password22','ATTENDEE') ON CONFLICT DO NOTHING;",
            "INSERT INTO persons(person_id, full_name, dob, contact, username, password, role) VALUES ('P023','Wendy Hall','1990-08-20','wendy@example.com','wendy','password23','PRESENTER') ON CONFLICT DO NOTHING;",
            "INSERT INTO persons(person_id, full_name, dob, contact, username, password, role) VALUES ('P024','Xavier Young','1993-02-11','xavier@example.com','xavier','password24','ATTENDEE') ON CONFLICT DO NOTHING;",
            "INSERT INTO persons(person_id, full_name, dob, contact, username, password, role) VALUES ('P025','Yara Hernandez','1987-12-07','yara@example.com','yara','password25','PRESENTER') ON CONFLICT DO NOTHING;",
        };
        for (String sql : seedPersons) {
            try (PreparedStatement p = conn.prepareStatement(sql)) {
                p.execute();
            }
        }

        // Seed 10 events
        String[] seedEvents = {
            "INSERT INTO events(event_id, name, type, start_date, end_date, location, status) VALUES ('E001','Tech Conference 2025','CONFERENCE','2025-03-01','2025-03-03','Convention Center A','SCHEDULED') ON CONFLICT DO NOTHING;",
            "INSERT INTO events(event_id, name, type, start_date, end_date, location, status) VALUES ('E002','Web Development Workshop','WORKSHOP','2025-02-15','2025-02-15','Room 101','SCHEDULED') ON CONFLICT DO NOTHING;",
            "INSERT INTO events(event_id, name, type, start_date, end_date, location, status) VALUES ('E003','AI & ML Summit','CONFERENCE','2025-04-10','2025-04-12','Grand Hall','SCHEDULED') ON CONFLICT DO NOTHING;",
            "INSERT INTO events(event_id, name, type, start_date, end_date, location, status) VALUES ('E004','Cloud Computing Seminar','SEMINAR','2025-02-20','2025-02-20','Room 202','SCHEDULED') ON CONFLICT DO NOTHING;",
            "INSERT INTO events(event_id, name, type, start_date, end_date, location, status) VALUES ('E005','DevOps Best Practices','WORKSHOP','2025-03-15','2025-03-15','Room 303','SCHEDULED') ON CONFLICT DO NOTHING;",
            "INSERT INTO events(event_id, name, type, start_date, end_date, location, status) VALUES ('E006','Cybersecurity Forum','FORUM','2025-05-01','2025-05-02','Auditorium B','SCHEDULED') ON CONFLICT DO NOTHING;",
            "INSERT INTO events(event_id, name, type, start_date, end_date, location, status) VALUES ('E007','Data Science Expo','EXPO','2025-06-10','2025-06-11','Exhibition Hall','SCHEDULED') ON CONFLICT DO NOTHING;",
            "INSERT INTO events(event_id, name, type, start_date, end_date, location, status) VALUES ('E008','Mobile App Development','WORKSHOP','2025-02-25','2025-02-25','Lab A','SCHEDULED') ON CONFLICT DO NOTHING;",
            "INSERT INTO events(event_id, name, type, start_date, end_date, location, status) VALUES ('E009','Product Management Masterclass','WORKSHOP','2025-03-20','2025-03-20','Room 404','SCHEDULED') ON CONFLICT DO NOTHING;",
            "INSERT INTO events(event_id, name, type, start_date, end_date, location, status) VALUES ('E010','Startup Pitching Event','NETWORKING','2025-04-15','2025-04-15','Innovation Hub','SCHEDULED') ON CONFLICT DO NOTHING;",
        };
        for (String sql : seedEvents) {
            try (PreparedStatement p = conn.prepareStatement(sql)) {
                p.execute();
            }
        }

        // Seed 15 sessions
        String[] seedSessions = {
            "INSERT INTO sessions(session_id, event_id, title, description, scheduled_timestamp, venue, capacity) VALUES ('S001','E001','Opening Keynote','Industry leaders discuss future trends','2025-03-01 09:00:00','Main Stage',500) ON CONFLICT DO NOTHING;",
            "INSERT INTO sessions(session_id, event_id, title, description, scheduled_timestamp, venue, capacity) VALUES ('S002','E001','Web APIs Panel','Best practices for API design','2025-03-01 11:00:00','Room A',100) ON CONFLICT DO NOTHING;",
            "INSERT INTO sessions(session_id, event_id, title, description, scheduled_timestamp, venue, capacity) VALUES ('S003','E002','React Advanced Patterns','Deep dive into React optimization','2025-02-15 10:00:00','Lab 101',50) ON CONFLICT DO NOTHING;",
            "INSERT INTO sessions(session_id, event_id, title, description, scheduled_timestamp, venue, capacity) VALUES ('S004','E002','Node.js Performance','Optimizing Node.js applications','2025-02-15 14:00:00','Lab 102',50) ON CONFLICT DO NOTHING;",
            "INSERT INTO sessions(session_id, event_id, title, description, scheduled_timestamp, venue, capacity) VALUES ('S005','E003','AI Ethics','Responsible AI development','2025-04-10 09:00:00','Hall A',200) ON CONFLICT DO NOTHING;",
            "INSERT INTO sessions(session_id, event_id, title, description, scheduled_timestamp, venue, capacity) VALUES ('S006','E003','ML Deployment','Deploying ML models to production','2025-04-10 14:00:00','Hall B',200) ON CONFLICT DO NOTHING;",
            "INSERT INTO sessions(session_id, event_id, title, description, scheduled_timestamp, venue, capacity) VALUES ('S007','E004','Cloud Architecture','Designing scalable cloud systems','2025-02-20 10:00:00','Room 202',75) ON CONFLICT DO NOTHING;",
            "INSERT INTO sessions(session_id, event_id, title, description, scheduled_timestamp, venue, capacity) VALUES ('S008','E005','CI/CD Pipelines','Automating deployment pipelines','2025-03-15 10:00:00','Room 303',60) ON CONFLICT DO NOTHING;",
            "INSERT INTO sessions(session_id, event_id, title, description, scheduled_timestamp, venue, capacity) VALUES ('S009','E006','Threat Detection','Advanced threat detection techniques','2025-05-01 09:00:00','Auditorium B',150) ON CONFLICT DO NOTHING;",
            "INSERT INTO sessions(session_id, event_id, title, description, scheduled_timestamp, venue, capacity) VALUES ('S010','E007','Data Visualization','Interactive dashboards and viz','2025-06-10 10:00:00','Hall C',180) ON CONFLICT DO NOTHING;",
            "INSERT INTO sessions(session_id, event_id, title, description, scheduled_timestamp, venue, capacity) VALUES ('S011','E008','Flutter Development','Cross-platform mobile with Flutter','2025-02-25 10:00:00','Lab A',40) ON CONFLICT DO NOTHING;",
            "INSERT INTO sessions(session_id, event_id, title, description, scheduled_timestamp, venue, capacity) VALUES ('S012','E009','OKR Frameworks','Setting and tracking OKRs','2025-03-20 10:00:00','Room 404',80) ON CONFLICT DO NOTHING;",
            "INSERT INTO sessions(session_id, event_id, title, description, scheduled_timestamp, venue, capacity) VALUES ('S013','E010','Pitch Coaching','Preparing an elevator pitch','2025-04-15 09:00:00','Hub A',100) ON CONFLICT DO NOTHING;",
            "INSERT INTO sessions(session_id, event_id, title, description, scheduled_timestamp, venue, capacity) VALUES ('S014','E001','Closing Remarks','Summary of key takeaways','2025-03-03 16:00:00','Main Stage',500) ON CONFLICT DO NOTHING;",
            "INSERT INTO sessions(session_id, event_id, title, description, scheduled_timestamp, venue, capacity) VALUES ('S015','E003','Networking Dinner','Informal networking session','2025-04-11 18:00:00','Ballroom',300) ON CONFLICT DO NOTHING;",
        };
        for (String sql : seedSessions) {
            try (PreparedStatement p = conn.prepareStatement(sql)) {
                p.execute();
            }
        }

        // Seed 20 tickets
        String[] seedTickets = {
            "INSERT INTO tickets(ticket_id, attendee_id, event_id, session_id, type, price, status, qr) VALUES ('T001','P001','E001','S001','STANDARD',50.00,'ACTIVE',NULL) ON CONFLICT DO NOTHING;",
            "INSERT INTO tickets(ticket_id, attendee_id, event_id, session_id, type, price, status, qr) VALUES ('T002','P002','E001','S002','PREMIUM',100.00,'ACTIVE',NULL) ON CONFLICT DO NOTHING;",
            "INSERT INTO tickets(ticket_id, attendee_id, event_id, session_id, type, price, status, qr) VALUES ('T003','P005','E002','S003','STANDARD',30.00,'ACTIVE',NULL) ON CONFLICT DO NOTHING;",
            "INSERT INTO tickets(ticket_id, attendee_id, event_id, session_id, type, price, status, qr) VALUES ('T004','P006','E002','S004','STANDARD',30.00,'ACTIVE',NULL) ON CONFLICT DO NOTHING;",
            "INSERT INTO tickets(ticket_id, attendee_id, event_id, session_id, type, price, status, qr) VALUES ('T005','P008','E003','S005','PREMIUM',75.00,'ACTIVE',NULL) ON CONFLICT DO NOTHING;",
            "INSERT INTO tickets(ticket_id, attendee_id, event_id, session_id, type, price, status, qr) VALUES ('T006','P010','E003','S006','STANDARD',75.00,'ACTIVE',NULL) ON CONFLICT DO NOTHING;",
            "INSERT INTO tickets(ticket_id, attendee_id, event_id, session_id, type, price, status, qr) VALUES ('T007','P011','E004','S007','STANDARD',40.00,'ACTIVE',NULL) ON CONFLICT DO NOTHING;",
            "INSERT INTO tickets(ticket_id, attendee_id, event_id, session_id, type, price, status, qr) VALUES ('T008','P013','E005','S008','STANDARD',45.00,'ACTIVE',NULL) ON CONFLICT DO NOTHING;",
            "INSERT INTO tickets(ticket_id, attendee_id, event_id, session_id, type, price, status, qr) VALUES ('T009','P015','E006','S009','PREMIUM',80.00,'USED',NULL) ON CONFLICT DO NOTHING;",
            "INSERT INTO tickets(ticket_id, attendee_id, event_id, session_id, type, price, status, qr) VALUES ('T010','P016','E007','S010','STANDARD',60.00,'ACTIVE',NULL) ON CONFLICT DO NOTHING;",
            "INSERT INTO tickets(ticket_id, attendee_id, event_id, session_id, type, price, status, qr) VALUES ('T011','P018','E008','S011','STANDARD',35.00,'ACTIVE',NULL) ON CONFLICT DO NOTHING;",
            "INSERT INTO tickets(ticket_id, attendee_id, event_id, session_id, type, price, status, qr) VALUES ('T012','P020','E009','S012','STANDARD',55.00,'ACTIVE',NULL) ON CONFLICT DO NOTHING;",
            "INSERT INTO tickets(ticket_id, attendee_id, event_id, session_id, type, price, status, qr) VALUES ('T013','P022','E010','S013','STANDARD',25.00,'ACTIVE',NULL) ON CONFLICT DO NOTHING;",
            "INSERT INTO tickets(ticket_id, attendee_id, event_id, session_id, type, price, status, qr) VALUES ('T014','P001','E003','S005','STANDARD',75.00,'CANCELLED',NULL) ON CONFLICT DO NOTHING;",
            "INSERT INTO tickets(ticket_id, attendee_id, event_id, session_id, type, price, status, qr) VALUES ('T015','P002','E002','S003','PREMIUM',45.00,'ACTIVE',NULL) ON CONFLICT DO NOTHING;",
            "INSERT INTO tickets(ticket_id, attendee_id, event_id, session_id, type, price, status, qr) VALUES ('T016','P024','E001','S001','STANDARD',50.00,'ACTIVE',NULL) ON CONFLICT DO NOTHING;",
            "INSERT INTO tickets(ticket_id, attendee_id, event_id, session_id, type, price, status, qr) VALUES ('T017','P005','E004','S007','PREMIUM',60.00,'ACTIVE',NULL) ON CONFLICT DO NOTHING;",
            "INSERT INTO tickets(ticket_id, attendee_id, event_id, session_id, type, price, status, qr) VALUES ('T018','P008','E006','S009','STANDARD',80.00,'USED',NULL) ON CONFLICT DO NOTHING;",
            "INSERT INTO tickets(ticket_id, attendee_id, event_id, session_id, type, price, status, qr) VALUES ('T019','P010','E010','S013','STANDARD',25.00,'ACTIVE',NULL) ON CONFLICT DO NOTHING;",
            "INSERT INTO tickets(ticket_id, attendee_id, event_id, session_id, type, price, status, qr) VALUES ('T020','P011','E001','S002','PREMIUM',100.00,'ACTIVE',NULL) ON CONFLICT DO NOTHING;",
        };
        for (String sql : seedTickets) {
            try (PreparedStatement p = conn.prepareStatement(sql)) {
                p.execute();
            }
        }

        System.out.println("[DBInit] Database initialized with schema and seed data.");
    }
}

