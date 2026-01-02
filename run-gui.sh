#!/bin/bash

# Event Management System - Launcher Script
# This script sets up the environment and runs the GUI application

set -e

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${BLUE}╔════════════════════════════════════════════╗${NC}"
echo -e "${BLUE}║   Event Management System - Launcher       ║${NC}"
echo -e "${BLUE}╚════════════════════════════════════════════╝${NC}"

# 1. Check PostgreSQL
echo -e "${BLUE}[1/4]${NC} Checking PostgreSQL..."
if ! brew services list | grep -q "postgresql@14.*started"; then
    echo -e "${RED}✗ PostgreSQL not running${NC}"
    echo "Starting PostgreSQL..."
    brew services start postgresql@14
    sleep 2
fi
echo -e "${GREEN}✓ PostgreSQL is running${NC}"

# 2. Check database
echo -e "${BLUE}[2/4]${NC} Checking database..."
if ! psql -U postgres -tc "SELECT 1 FROM pg_database WHERE datname = 'eventdb'" | grep -q 1; then
    echo "Creating eventdb..."
    createdb -U postgres eventdb
    echo -e "${GREEN}✓ Database created${NC}"
else
    echo -e "${GREEN}✓ Database exists${NC}"
fi

# 3. Set environment variables
echo -e "${BLUE}[3/4]${NC} Setting environment variables..."
export DB_URL="jdbc:postgresql://localhost:5432/eventdb"
export DB_USER="postgres"
export DB_PASSWORD=""
echo -e "${GREEN}✓ Environment configured${NC}"

# 4. Check JAR file
echo -e "${BLUE}[4/4]${NC} Checking application JAR..."
JAR_FILE="target/event-management-1.0-SNAPSHOT.jar"

if [ ! -f "$JAR_FILE" ]; then
    echo -e "${RED}✗ JAR file not found. Building project...${NC}"
    mvn clean package -DskipTests -q
    echo -e "${GREEN}✓ Project built${NC}"
else
    echo -e "${GREEN}✓ JAR file found${NC}"
fi

# 5. Find JavaFX modules path
echo -e "${BLUE}[5/5]${NC} Configuring JavaFX runtime..."

JAVAFX_PATH="$HOME/.m2/repository/org/openjfx"

if [ ! -d "$JAVAFX_PATH" ]; then
    echo -e "${RED}✗ JavaFX not found in Maven cache${NC}"
    echo "Installing JavaFX dependencies..."
    mvn dependency:copy-dependencies -DoutputDirectory=target/lib -q
fi

# Get all JavaFX JARs
JAVAFX_LIBS=$(find "$JAVAFX_PATH" -name "javafx-*.jar" -not -name "*-sources.jar" | grep -E "20/javafx-(controls|graphics|fxml)" | tr '\n' ':' | sed 's/:$//')

if [ -z "$JAVAFX_LIBS" ]; then
    echo -e "${RED}✗ Could not find JavaFX JARs${NC}"
    exit 1
fi

echo -e "${GREEN}✓ JavaFX configured${NC}"

# 6. Run the application
echo ""
echo -e "${GREEN}════════════════════════════════════════════${NC}"
echo -e "${GREEN}   Launching Event Management System...${NC}"
echo -e "${GREEN}════════════════════════════════════════════${NC}"
echo ""

# Run with JavaFX modules
java \
    --module-path "$JAVAFX_LIBS" \
    --add-modules javafx.controls,javafx.graphics,javafx.fxml \
    -jar "$JAR_FILE"

# If JAR path approach fails, try running with full classpath
if [ $? -ne 0 ]; then
    echo -e "${BLUE}Trying alternative launch method...${NC}"
    java \
        -cp "$JAR_FILE" \
        --module-path "$JAVAFX_PATH" \
        --add-modules javafx.controls,javafx.graphics,javafx.fxml \
        oop_clean.ui.GUIApp
fi
