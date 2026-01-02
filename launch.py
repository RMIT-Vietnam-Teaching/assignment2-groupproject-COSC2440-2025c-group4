#!/usr/bin/env python3
"""
Event Management System Launcher
Simple Python wrapper to run the JavaFX GUI with proper JavaFX modules
"""

import subprocess
import sys
import os
import pathlib
from pathlib import Path

def find_javafx_jars():
    """Find JavaFX JARs in Maven repository"""
    home = Path.home()
    javafx_repo = home / ".m2/repository/org/openjfx"
    
    if not javafx_repo.exists():
        print("❌ JavaFX not found in Maven cache")
        return None
    
    jars = {
        'controls': None,
        'graphics': None,
        'fxml': None,
        'base': None
    }
    
    for jar_file in javafx_repo.glob("javafx-*/20/javafx-*-20-mac-aarch64.jar"):
        name = jar_file.parent.parent.name
        if 'controls' in name:
            jars['controls'] = str(jar_file)
        elif 'graphics' in name:
            jars['graphics'] = str(jar_file)
        elif 'fxml' in name:
            jars['fxml'] = str(jar_file)
        elif 'base' in name:
            jars['base'] = str(jar_file)
    
    if all(jars.values()):
        return jars
    return None

def check_postgres():
    """Check if PostgreSQL is running"""
    result = subprocess.run(
        ["brew", "services", "list"],
        capture_output=True,
        text=True
    )
    return "postgresql@14" in result.stdout and "started" in result.stdout

def create_database():
    """Create eventdb if it doesn't exist"""
    try:
        subprocess.run(
            ["psql", "-U", "postgres", "-c", "CREATE DATABASE eventdb;"],
            capture_output=True,
            timeout=5
        )
    except:
        pass  # Database might already exist

def main():
    print("\n" + "="*50)
    print("  Event Management System Launcher")
    print("="*50 + "\n")
    
    # Check PostgreSQL
    print("📍 Checking PostgreSQL...")
    if not check_postgres():
        print("❌ PostgreSQL not running. Starting...")
        subprocess.run(["brew", "services", "start", "postgresql@14"])
    print("✓ PostgreSQL ready\n")
    
    # Create database
    print("📍 Checking database...")
    create_database()
    print("✓ Database ready\n")
    
    # Find JavaFX JARs
    print("📍 Finding JavaFX modules...")
    jars = find_javafx_jars()
    if not jars:
        print("❌ Could not find JavaFX JARs")
        print("\nTry running manually:")
        print("  mvn javafx:run")
        return 1
    print("✓ JavaFX modules found\n")
    
    # Check JAR file
    project_dir = Path(__file__).parent
    jar_file = project_dir / "target/event-management-1.0-SNAPSHOT.jar"
    
    if not jar_file.exists():
        print("📍 Building project...")
        result = subprocess.run(
            ["mvn", "clean", "package", "-DskipTests", "-q"],
            cwd=project_dir
        )
        if result.returncode != 0:
            print("❌ Build failed")
            return 1
        print("✓ Build complete\n")
    
    # Set environment variables
    env = os.environ.copy()
    env["DB_URL"] = "jdbc:postgresql://localhost:5432/eventdb"
    env["DB_USER"] = "postgres"
    env["DB_PASSWORD"] = ""
    
    # Build module path
    module_path = ":".join([jars['controls'], jars['graphics'], jars['fxml'], jars['base']])
    
    print("🚀 Launching Event Management System...\n")
    
    # Run the application
    cmd = [
        "java",
        "--enable-native-access=javafx.graphics",
        "--module-path", module_path,
        "--add-modules", "javafx.controls,javafx.graphics,javafx.fxml",
        "-jar", str(jar_file)
    ]
    
    try:
        subprocess.run(cmd, env=env, check=True)
    except KeyboardInterrupt:
        print("\n\n👋 Application closed")
        return 0
    except Exception as e:
        print(f"\n❌ Error: {e}")
        return 1

if __name__ == "__main__":
    sys.exit(main())
