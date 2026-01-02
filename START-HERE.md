# 🚀 How to Run the Event Management System

## The Simplest Way - Just Run This:

```bash
cd /Users/ellypham/Desktop/assignment2-groupproject-COSC2440-2025c-group4
python3 launch.py
```

That's it! The launcher will:
✅ Check PostgreSQL (start it if needed)  
✅ Create the database if needed  
✅ Find JavaFX modules  
✅ Build the project if needed  
✅ Launch the GUI

---

## Or Use One of These Commands:

### **Option 1: Python Launcher (Easiest)** ⭐

```bash
cd ~/Desktop/assignment2-groupproject-COSC2440-2025c-group4
python3 launch.py
```

### **Option 2: Direct Java Command**

```bash
cd ~/Desktop/assignment2-groupproject-COSC2440-2025c-group4

export DB_URL="jdbc:postgresql://localhost:5432/eventdb"
export DB_USER="postgres"
export DB_PASSWORD=""

java \
  --enable-native-access=javafx.graphics \
  --module-path ~/.m2/repository/org/openjfx/javafx-controls/20:~/.m2/repository/org/openjfx/javafx-graphics/20:~/.m2/repository/org/openjfx/javafx-fxml/20:~/.m2/repository/org/openjfx/javafx-base/20 \
  --add-modules javafx.controls,javafx.graphics,javafx.fxml \
  -jar target/event-management-1.0-SNAPSHOT.jar
```

### **Option 3: Maven Plugin**

```bash
cd ~/Desktop/assignment2-groupproject-COSC2440-2025c-group4
mvn javafx:run
```

---

## Login Credentials

When the GUI opens, use:

- **Username:** `admin`
- **Password:** `admin123`

Or try: `alice` / `password1`

---

## What You Can Do

Once logged in, test all features:

| Feature            | What It Tests                            |
| ------------------ | ---------------------------------------- |
| 📅 Manage Events   | Create and view events                   |
| 📍 Manage Sessions | Create sessions with datetime validation |
| 🎫 Manage Tickets  | Generate tickets for events              |
| 👥 View Users      | See all 25 demo users                    |
| ⏰ Check Schedule  | Detect session scheduling conflicts      |

---

## Troubleshooting

### "No GUI appears"

- Make sure PostgreSQL is running: `brew services start postgresql@14`
- Try the Java command directly to see error messages

### "Connection refused"

- PostgreSQL isn't running
- Run: `brew services start postgresql@14`

### "Database does not exist"

- Delete it: `dropdb -U postgres eventdb`
- Re-run, it will be created automatically

### "JavaFX runtime error"

- Use the Python launcher: `python3 launch.py`
- Or use Maven: `mvn javafx:run`

---

## Files Available

| File                                       | Purpose                                 |
| ------------------------------------------ | --------------------------------------- |
| `launch.py`                                | ⭐ **Use this** - Easiest way to launch |
| `run-gui.sh`                               | Shell script alternative                |
| `HOW-TO-RUN.md`                            | Detailed instructions                   |
| `target/event-management-1.0-SNAPSHOT.jar` | The compiled JAR (9.2 MB)               |

---

## Summary

**Just run:**

```bash
python3 launch.py
```

Everything else is handled automatically! 🎉
