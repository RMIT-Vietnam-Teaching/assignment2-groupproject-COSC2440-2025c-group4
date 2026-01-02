package oop_clean.core;

/**
 * Entry point of the backend application.
 * Initializes the database schema and seed data on startup.
 * 
 * @author Group04
 */
public class MainApp {
    public static void main(String[] args) {
        try {
            // Initialize the database (create tables, insert seed data)
            DBInit.init();
            System.out.println("Database initialized. Backend ready.");
        } catch (Exception e) {
            System.err.println("Failed to initialize DB: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
