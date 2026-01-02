# Event Management System - Backend

**Author:** Group04

## Overview

A comprehensive event management system built in Java with PostgreSQL backend, featuring role-based access control for Anonymous Visitors, Participants (Attendees/Presenters), and Administrators.

## Architecture

### Core Components

- **Domain Model** (`oop_clean/`): Person, Attendee, Presenter, Event, Session, Ticket, Schedule
- **Data Access Layer** (`oop_clean/`): DAOs with JDBC prepared statements
- **Business Logic** (`oop_clean/`): Service layer (Auth, Event, Session, Ticket, Schedule)
- **Database**: PostgreSQL with auto-initialization
- **Testing**: JUnit 5 unit tests
- **Build**: Maven with shade plugin for runnable JAR

### Project Structure

```
.
├── oop_clean/                    # Main source (all Java files)
│   ├── *.java                   # Domain entities
│   ├── *DAO.java / *DAOImpl.java # Data access layer
│   ├── *Service.java             # Business logic
│   ├── DatabaseConfig.java       # DB connection singleton
│   ├── DBInit.java               # Schema & seed data
│   └── MainApp.java              # Entry point
├── src/test/java/oop_clean/      # JUnit 5 tests
├── pom.xml                       # Maven configuration
├── .gitignore                    # Git ignore rules
└── target/                       # Compiled output & JAR
```

## Database Setup

### Prerequisites

- PostgreSQL installed locally or remote (Supabase/Neon)
- Java 17+
- Maven 3.8+

### Configuration

Set environment variables before running:

```bash
export DB_URL=jdbc:postgresql://<host>:5432/<dbname>
export DB_USER=<username>
export DB_PASSWORD=<password>
```

**Example for local Postgres:**

```bash
export DB_URL=jdbc:postgresql://localhost:5432/eventdb
export DB_USER=postgres
export DB_PASSWORD=your_password
```

### Auto-Initialization

On first run, the application will automatically:

1. Create tables if they don't exist
2. Seed 25 persons (mix of Attendees, Presenters, Admin)
3. Seed 10 events with realistic data
4. Seed 15 sessions across events
5. Seed 20 tickets with various statuses

## Building

```bash
# Clean build with tests
mvn clean package

# Build without tests
mvn clean package -DskipTests
```

Output: `target/event-management-1.0-SNAPSHOT.jar` (9.1 MB)

## Running

### From JAR (with DB env vars set)

```bash
java -jar target/event-management-1.0-SNAPSHOT.jar
```

### From Maven

```bash
mvn exec:java -Dexec.mainClass="oop_clean.MainApp"
```

## Testing

### Run All Tests

```bash
mvn test
```

### Current Test Coverage

- **ScheduleTest**: Session overlap detection, capacity checks
- **ScheduleServiceTest**: Conflict detection, capacity enforcement

Results: 6 tests passing

## Database Schema

### Persons

- Stores users (Attendee, Presenter, Admin)
- Fields: person_id, full_name, dob, contact, username, password, role

### Events

- Event records with type, date, location, status
- Fields: event_id, name, type, start_date, location, status

### Sessions

- Sessions within events with capacity & scheduling
- Fields: session_id, event_id, title, description, scheduled_timestamp, venue, capacity

### Tickets

- Attendee tickets linked to events/sessions
- Fields: ticket_id, attendee_id, event_id, session_id, type, price, status

## Key Features Implemented

### Authentication & Authorization

- `AuthService`: Username/password verification
- Role-based access in backend (ADMIN, PRESENTER, ATTENDEE)

### Event Management

- `EventService`: Create, read, update, delete events
- Status transitions (SCHEDULED → ONGOING → COMPLETED/CANCELLED)

### Session Scheduling

- `SessionService`: Manage sessions within events
- `ScheduleService`: Conflict detection to prevent overlapping assignments

### Ticket Management

- `TicketService`: Auto-generate tickets for registrations
- Ticket lifecycle: ACTIVE → USED → CANCELLED

### Data Persistence

- All DAOs use prepared statements (SQL injection safe)
- Transactional operations via Connection pooling
- Database auto-initialization on startup

## Javadoc

Every Java file includes the required author header:

```java
/**
 * @author Group04
 */
```

## Dependencies

- **PostgreSQL JDBC** (42.6.0): Database driver
- **JavaFX** (20): UI framework (included for future GUI)
- **JUnit 5** (5.9.3): Testing framework
- **Maven Shade** (3.4.1): JAR packaging

## Error Handling

- All DAO/Service methods throw `Exception` for database issues
- Input validation at service layer
- Prepared statements prevent SQL injection

## Future Enhancements

1. **GUI**: JavaFX-based user interface for roles
2. **Reports**: PDF/CSV export for admin analytics
3. **Notifications**: Event registration confirmations
4. **Payment Integration**: Ticket payment processing
5. **QR Code Generation**: Dynamic QR code generation for tickets
6. **Advanced Search**: Full-text search for events/sessions

## Known Limitations

- No payment processing (auto-ticket issuance only)
- QR codes stored as BYTEA (placeholder, not generated)
- Session materials stored in DB only (no file upload UI yet)
- No email notifications

## Development Notes

### Adding a New DAO

1. Create `NewEntityDAO` interface
2. Create `NewEntityDAOImpl` with CRUD methods
3. Use prepared statements with parameter binding
4. Add tests in `src/test/java/oop_clean/`

### Adding a New Service

1. Create `NewService` class in `oop_clean/`
2. Inject relevant DAOs
3. Implement business logic + validation
4. Add corresponding unit tests

## Support

For issues or questions, refer to the assignment specification or contact Group04.

---

**Build Date:** January 2, 2026  
**Gradle Compatible:** No (Maven-based)  
**Java Version:** 17+
