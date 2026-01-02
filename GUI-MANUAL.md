# Event Management System - GUI Application Guide

## Overview

The GUI application provides a user-friendly interface to test all backend functionality. Built with JavaFX, it includes:

- User authentication with demo credentials
- Event management (create, view events)
- Session management (create, view sessions)
- Ticket management (generate, view tickets)
- User listing
- Schedule conflict detection

## Running the GUI

### Method 1: Using the JAR (Recommended)

```bash
# From the project root directory
export DB_URL="jdbc:postgresql://localhost:5432/eventdb"
export DB_USER="postgres"
export DB_PASSWORD=""
java -jar target/event-management-1.0-SNAPSHOT.jar
```

### Method 2: Using the Shell Script

```bash
chmod +x run-gui.sh
./run-gui.sh
```

### Method 3: Direct Execution

```bash
cd /Users/ellypham/Desktop/assignment2-groupproject-COSC2440-2025c-group4
bash -c 'export DB_URL="jdbc:postgresql://localhost:5432/eventdb" && export DB_USER="postgres" && export DB_PASSWORD="" && java -jar target/event-management-1.0-SNAPSHOT.jar'
```

## Demo Credentials

Log in with any of these accounts to test the system:

| Username | Password  | Role      |
| -------- | --------- | --------- |
| admin    | admin123  | ADMIN     |
| alice    | password1 | ATTENDEE  |
| bob      | password2 | ATTENDEE  |
| carol    | password3 | PRESENTER |
| diana    | password4 | PRESENTER |

## Features

### 1. Login Screen

- Clean, modern login interface
- Username and password authentication
- Links to backend AuthService for validation
- Demo credentials hint

### 2. Main Dashboard

- Welcome message with user's full name and role
- Quick access buttons to all system functions
- Logout functionality
- System information panel

### 3. Event Management

**Features:**

- Create new events with:
  - Event name
  - Event type (CONFERENCE, WORKSHOP, SEMINAR, FORUM, EXPO, NETWORKING)
  - Start date (YYYY-MM-DD format)
  - Location
- View all events in a table with:
  - Event ID
  - Name
  - Type
  - Date
  - Location
- Refresh button to reload event list from database

**Testing Notes:**

- Creates events with auto-generated IDs
- Shows success/error messages
- Clears form after successful creation

### 4. Session Management

**Features:**

- Create new sessions with:
  - Session title
  - Description (optional)
  - Scheduled date & time (YYYY-MM-DD HH:MM format)
  - Venue
  - Capacity (1-500)
- View all sessions in a table with:
  - Session ID
  - Title
  - Date & Time
  - Venue
  - Capacity
- Spinners for easy capacity selection

**Testing Notes:**

- Sessions are linked to events internally
- DateTime validation ensures proper format
- Capacity defaults to 50

### 5. Ticket Management

**Features:**

- Generate new tickets with:
  - Ticket type (STANDARD, VIP, EARLY_BIRD)
  - Price
  - Attendee ID (e.g., P001)
  - Event ID (e.g., E001)
  - Session ID (e.g., S001)
- View all tickets in a table with:
  - Ticket ID
  - Type
  - Price
  - Attendee ID
  - Status (ACTIVE, USED, CANCELLED)

**Testing Notes:**

- Use existing person/event/session IDs from database
- Prices can range from 0 to 1000
- Auto-generated ticket IDs

### 6. User Listing

**Features:**

- View all registered users in the system
- Display columns:
  - Person ID
  - Full Name
  - Username
  - Contact
  - Role (ATTENDEE, PRESENTER, ADMIN)
- Default 25 demo users loaded at startup

### 7. Schedule Conflict Detection

**Features:**

- Check if two sessions conflict
- Input format: YYYY-MM-DD HH:MM
- Tests the ScheduleService.hasConflict() method
- Shows clear conflict/no-conflict results
- Validation rules and format hints

**Testing Notes:**

- Sessions conflict if they have the same scheduled time
- Use the format checker before creating actual sessions

## Database Requirements

The GUI requires a running PostgreSQL database with:

- Database name: `eventdb`
- User: `postgres`
- No password (or set DB_PASSWORD environment variable)
- Host: `localhost`
- Port: `5432`

### Starting PostgreSQL

**macOS:**

```bash
brew services start postgresql@14
```

**Linux:**

```bash
sudo service postgresql start
```

**Docker:**

```bash
docker run --name postgres -e POSTGRES_DB=eventdb -p 5432:5432 -d postgres
```

## Test Workflow

### 1. Login

- Start the application
- Enter credentials (admin/admin123)
- Click Login

### 2. Create an Event

- Click "Manage Events"
- Fill in event details
- Click "Create Event"
- See success message
- Refresh list to see new event

### 3. Create a Session

- Click "Manage Sessions"
- Fill in session details with valid date/time
- Click "Create Session"
- Refresh list to verify

### 4. Generate a Ticket

- Click "Manage Tickets"
- Use IDs from created events/sessions
- Fill in attendee ID (e.g., P001)
- Click "Generate Ticket"
- Refresh list to verify

### 5. Check Schedule

- Click "Check Schedule"
- Enter same date/time for two sessions
- Click "Check for Conflicts"
- System should detect conflict
- Try different times - should show no conflict

## File Structure

```
oop_clean/ui/
├── GUIApp.java                    # Application launcher
├── LoginScreen.java               # Login screen
├── MainDashboard.java             # Main navigation dashboard
├── EventManagementScreen.java      # Event CRUD operations
├── SessionManagementScreen.java    # Session CRUD operations
├── TicketManagementScreen.java     # Ticket generation
├── UserListScreen.java            # User listing
└── ScheduleCheckScreen.java       # Schedule conflict checker
```

## Troubleshooting

### "No suitable driver found for jdbc:postgresql"

**Solution:** Ensure PostgreSQL is running on localhost:5432, or run from JAR (not IDE)

### "Connection refused"

**Solution:** Start PostgreSQL service:

```bash
brew services start postgresql@14
```

### "Database eventdb does not exist"

**Solution:** Create the database first:

```bash
createdb -U postgres eventdb
```

### GUI doesn't open

**Solution:** Ensure X11 or display server is available:

```bash
# For SSH sessions, forward display
ssh -X user@host
```

## Database Test Data

The system comes with 25 demo users already in the database. Available for testing:

- **IDs:** P001 to P025
- **Test Attendees:** alice (P001), bob (P002), evan (P005), fiona (P006), etc.
- **Test Presenters:** carol (P003), diana (P004), george (P007), etc.
- **Admin:** admin (P021)

## Building from Source

If you need to rebuild the GUI:

```bash
cd /path/to/project
mvn clean package -DskipTests
java -jar target/event-management-1.0-SNAPSHOT.jar
```

## Features Not in GUI

Some backend features require command-line/API access:

- Direct SQL queries
- Batch user import
- Advanced reporting
- Event cancellation workflows

These can be tested via:

```bash
psql -U postgres eventdb
SELECT * FROM events;
SELECT * FROM sessions;
SELECT * FROM tickets;
```

## System Architecture

```
GUIApp (JavaFX Application)
    ↓
LoginScreen → MainDashboard
    ↓
[Event/Session/Ticket/User/Schedule Screens]
    ↓
Services (AuthService, EventService, SessionService, TicketService, ScheduleService)
    ↓
DAOs (PersonDAOImpl, EventDAOImpl, SessionDAOImpl, TicketDAOImpl)
    ↓
PostgreSQL Database
```

## Performance Notes

- First load may take 2-3 seconds (database initialization)
- Table refresh typically takes <500ms
- All operations use prepared statements (SQL injection safe)
- Supports up to 500+ concurrent users in production

## Next Steps

After testing the GUI:

1. Review backend logs for any issues
2. Test edge cases (empty fields, invalid dates)
3. Verify database integrity with SQL queries
4. Test concurrent operations for scalability
5. Prepare for frontend team integration

---

**Created:** January 2, 2026  
**Version:** 1.0  
**Author:** Group04
