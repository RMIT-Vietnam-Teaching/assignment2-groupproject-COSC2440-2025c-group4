# GUI Quick Start Guide

## 🚀 Start the GUI in 30 Seconds

### Step 1: Make sure PostgreSQL is running

```bash
brew services start postgresql@14
```

### Step 2: Run the GUI

```bash
cd /Users/ellypham/Desktop/assignment2-groupproject-COSC2440-2025c-group4

export DB_URL="jdbc:postgresql://localhost:5432/eventdb" && \
export DB_USER="postgres" && \
export DB_PASSWORD="" && \
java -jar target/event-management-1.0-SNAPSHOT.jar
```

### Step 3: Login with demo credentials

- **Username:** admin
- **Password:** admin123

That's it! The GUI window should open immediately.

---

## 📱 What You'll See

### Screen 1: Login

- Simple login form
- Demo credentials hint shown
- "Invalid username or password" error if wrong

### Screen 2: Dashboard

- Welcome message
- 5 big buttons for different features
- Logout button (top right)

### Screen 3-7: Feature Screens

- **Events:** Create events, see all events
- **Sessions:** Create sessions with date/time
- **Tickets:** Generate tickets for attendees
- **Users:** View all registered users (25 demo users)
- **Schedule:** Check if two sessions conflict

---

## 🧪 Quick Test

### Test 1: Create an Event

1. Click "📅 Manage Events"
2. Fill in:
   - Name: "My Event"
   - Type: "CONFERENCE"
   - Date: "2025-03-15"
   - Location: "Room 101"
3. Click "Create Event"
4. See "✓ Event created successfully!"
5. Click "Refresh List" to see it in the table

### Test 2: Create a Session

1. Click "📍 Manage Sessions"
2. Fill in:
   - Title: "Opening Keynote"
   - Description: "Main presentation"
   - Date & Time: "2025-03-15 09:00"
   - Venue: "Main Hall"
   - Capacity: 100
3. Click "Create Session"
4. See success message

### Test 3: Generate a Ticket

1. Click "🎫 Manage Tickets"
2. Fill in:
   - Type: "STANDARD"
   - Price: 50.0
   - Attendee ID: "P001"
   - Event ID: "E001" (from created event)
   - Session ID: "S001" (from created session)
3. Click "Generate Ticket"
4. See success message with ticket ID

### Test 4: Check Schedule

1. Click "⏰ Check Schedule"
2. Session 1:
   - Title: "Keynote"
   - Date & Time: "2025-03-15 09:00"
3. Session 2:
   - Title: "Panel"
   - Date & Time: "2025-03-15 09:00"
4. Click "Check for Conflicts"
5. See "⚠ CONFLICT DETECTED"

---

## 👤 Demo Users

| Username | Password  | Role      |
| -------- | --------- | --------- |
| admin    | admin123  | ADMIN     |
| alice    | password1 | ATTENDEE  |
| bob      | password2 | ATTENDEE  |
| carol    | password3 | PRESENTER |
| diana    | password4 | PRESENTER |

Try logging in as different users to see role-based functionality.

---

## ⚠️ Common Issues

### Issue: "Connection refused"

**Solution:** Start PostgreSQL

```bash
brew services start postgresql@14
```

### Issue: "No suitable driver found"

**Solution:** Use the JAR file (not IDE), database must be running

### Issue: GUI doesn't open

**Solution:** Try running from terminal, check for error messages

### Issue: "Database does not exist"

**Solution:** Create database

```bash
createdb -U postgres eventdb
```

---

## 📊 What Gets Tested

✅ **Authentication** - Login with username/password  
✅ **Event CRUD** - Create and list events  
✅ **Session CRUD** - Create and list sessions  
✅ **Ticket Generation** - Create and list tickets  
✅ **Data Validation** - Date/time format checking  
✅ **Schedule Conflicts** - Detect overlapping sessions  
✅ **Database Integration** - All operations persist  
✅ **Error Handling** - User-friendly error messages

---

## 🎯 Testing Workflow

1. **Login** → Test AuthService
2. **Create Event** → Test EventService
3. **Create Session** → Test SessionService
4. **Generate Ticket** → Test TicketService
5. **Check Schedule** → Test ScheduleService
6. **View Users** → Test database connectivity
7. **Logout & Re-login** → Test persistence

---

## 📁 Files Created

```
oop_clean/ui/
├── GUIApp.java                    # Main application (8 lines)
├── LoginScreen.java               # Login screen (95 lines)
├── MainDashboard.java             # Dashboard (140 lines)
├── EventManagementScreen.java      # Events CRUD (200 lines)
├── SessionManagementScreen.java    # Sessions CRUD (240 lines)
├── TicketManagementScreen.java     # Tickets CRUD (230 lines)
├── UserListScreen.java            # User listing (100 lines)
└── ScheduleCheckScreen.java       # Schedule checker (190 lines)

Documentation/
├── GUI-SUMMARY.md                 # This guide
├── GUI-MANUAL.md                  # Detailed manual
└── run-gui.sh                      # Launch script
```

**Total GUI Code:** ~1,200 lines of JavaFX

---

## 🔄 How It Works

```
You → GUI Window
    ↓
LoginScreen (validates username/password)
    ↓
MainDashboard (shows 5 feature buttons)
    ↓
Selected Feature Screen (Event/Session/Ticket/etc)
    ↓
Service Layer (EventService, SessionService, etc)
    ↓
DAO Layer (database access)
    ↓
PostgreSQL Database
    ↓
Data returned and shown in GUI
```

---

## 🎓 Learning Resources

- **JavaFX Docs:** https://openjfx.io/
- **Your Backend:** See `BACKEND-README.md`
- **Database Schema:** Defined in `DBInit.java`
- **Services:** See `oop_clean/services/`

---

## ✨ Summary

You now have:

- ✅ Complete backend with 31 Java classes
- ✅ Full GUI with 8 JavaFX screens
- ✅ Database with 25 demo users
- ✅ Comprehensive testing interface
- ✅ Clean, organized code (models→dao→services→ui)
- ✅ Professional documentation

**Status:** Ready to use! 🚀

Run it now:

```bash
export DB_URL="jdbc:postgresql://localhost:5432/eventdb" && \
export DB_USER="postgres" && \
export DB_PASSWORD="" && \
java -jar target/event-management-1.0-SNAPSHOT.jar
```
