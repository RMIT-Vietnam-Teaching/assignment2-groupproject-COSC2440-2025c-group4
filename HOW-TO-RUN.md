# How to Run the Event Management System

## Quick Start (Copy & Paste)

### Step 1: Start PostgreSQL

```bash
brew services start postgresql@14
```

### Step 2: Run the Application

Use **ONE** of these commands:

---

### **OPTION A: Easiest - Use Maven** ✅ RECOMMENDED

```bash
cd /Users/ellypham/Desktop/assignment2-groupproject-COSC2440-2025c-group4
mvn clean javafx:run
```

This will automatically handle all JavaFX configuration!

---

### **OPTION B: Use the provided shell script**

```bash
cd /Users/ellypham/Desktop/assignment2-groupproject-COSC2440-2025c-group4
chmod +x run-gui.sh
./run-gui.sh
```

---

### **OPTION C: Manual command with JavaFX modules**

```bash
cd /Users/ellypham/Desktop/assignment2-groupproject-COSC2440-2025c-group4

export DB_URL="jdbc:postgresql://localhost:5432/eventdb"
export DB_USER="postgres"
export DB_PASSWORD=""

java \
  --module-path /Users/ellypham/.m2/repository/org/openjfx/javafx-controls/20:/Users/ellypham/.m2/repository/org/openjfx/javafx-graphics/20:/Users/ellypham/.m2/repository/org/openjfx/javafx-fxml/20:/Users/ellypham/.m2/repository/org/openjfx/javafx-base/20 \
  --add-modules javafx.controls,javafx.graphics,javafx.fxml \
  -jar target/event-management-1.0-SNAPSHOT.jar
```

---

## Default Login Credentials

When the GUI opens, login with:

- **Username:** `admin`
- **Password:** `admin123`

Or try: `alice` / `password1`

---

## What to Expect

1. Database initialization happens automatically on first run
2. Login screen appears
3. Click menu buttons to test features:
   - 📅 Manage Events
   - 📍 Manage Sessions
   - 🎫 Manage Tickets
   - 👥 View Users
   - ⏰ Check Schedule

---

## Troubleshooting

| Problem                   | Solution                                                                  |
| ------------------------- | ------------------------------------------------------------------------- |
| `PostgreSQL not running`  | Run: `brew services start postgresql@14`                                  |
| `JavaFX runtime missing`  | Use OPTION A (Maven) - it handles this automatically                      |
| `Connection refused`      | Make sure PostgreSQL is running                                           |
| `Database does not exist` | Delete the database and restart: `dropdb -U postgres eventdb` then re-run |

---

## Recommended: Use Maven (Simplest)

```bash
cd /Users/ellypham/Desktop/assignment2-groupproject-COSC2440-2025c-group4
mvn clean javafx:run
```

This single command:
✅ Ensures PostgreSQL is configured  
✅ Builds the project  
✅ Handles all JavaFX module loading  
✅ Launches the GUI  
✅ Initializes the database automatically
