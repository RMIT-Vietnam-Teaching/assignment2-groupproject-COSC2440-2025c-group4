package oop_clean.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import oop_clean.core.DBInit;

/**
 * GUI Application launcher for the Event Management System.
 * Initializes the database and starts the JavaFX application.
 * 
 * @author Group04
 */
public class GUIApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Initialize database
            DBInit.init();
            System.out.println("[GUIApp] Database initialized successfully");
            
            // Show login screen
            LoginScreen loginScreen = new LoginScreen(primaryStage);
            loginScreen.show();
        } catch (Exception e) {
            System.err.println("Failed to start application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
