package oop_clean.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oop_clean.models.Person;

/**
 * Main dashboard after successful login.
 * Provides navigation to different management screens (Events, Sessions, Tickets).
 * 
 * @author Group04
 */
public class MainDashboard {
    private Stage primaryStage;
    private Person currentUser;

    public MainDashboard(Stage primaryStage, Person currentUser) {
        this.primaryStage = primaryStage;
        this.currentUser = currentUser;
    }

    /**
     * Display the main dashboard
     */
    public void show() {
        // Create main container
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #f9f9f9;");

        // Header with welcome message
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);
        Label welcomeLabel = new Label("Welcome, " + currentUser.getFullName() + "!");
        welcomeLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
        Label roleLabel = new Label("Role: " + currentUser.getRole());
        roleLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #666;");
        VBox headerLeft = new VBox(5);
        headerLeft.getChildren().addAll(welcomeLabel, roleLabel);
        
        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-font-size: 12; -fx-padding: 8 15;");
        logoutButton.setOnAction(e -> {
            LoginScreen login = new LoginScreen(primaryStage);
            login.show();
        });
        
        header.getChildren().addAll(headerLeft, logoutButton);

        // Navigation section
        VBox navSection = new VBox(15);
        navSection.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5; -fx-padding: 20; -fx-background-color: white;");

        Label navTitle = new Label("System Management");
        navTitle.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        // Create buttons for different modules
        Button eventsButton = createNavButton("📅 Manage Events", "Create, view, update, delete events");
        Button sessionsButton = createNavButton("📍 Manage Sessions", "Create sessions within events");
        Button ticketsButton = createNavButton("🎫 Manage Tickets", "Generate and manage tickets");
        Button usersButton = createNavButton("👥 View Users", "List all users in the system");
        Button scheduleButton = createNavButton("⏰ Check Schedule", "Verify session schedules and conflicts");

        // Button actions
        eventsButton.setOnAction(e -> {
            EventManagementScreen screen = new EventManagementScreen(primaryStage, currentUser);
            screen.show();
        });

        sessionsButton.setOnAction(e -> {
            SessionManagementScreen screen = new SessionManagementScreen(primaryStage, currentUser);
            screen.show();
        });

        ticketsButton.setOnAction(e -> {
            TicketManagementScreen screen = new TicketManagementScreen(primaryStage, currentUser);
            screen.show();
        });

        usersButton.setOnAction(e -> {
            UserListScreen screen = new UserListScreen(primaryStage);
            screen.show();
        });

        scheduleButton.setOnAction(e -> {
            ScheduleCheckScreen screen = new ScheduleCheckScreen(primaryStage);
            screen.show();
        });

        navSection.getChildren().addAll(
            navTitle,
            new Separator(),
            eventsButton,
            sessionsButton,
            ticketsButton,
            usersButton,
            scheduleButton
        );

        // System info section
        VBox infoSection = new VBox(10);
        infoSection.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5; -fx-padding: 15; -fx-background-color: #f0f8ff;");
        Label infoTitle = new Label("System Information");
        infoTitle.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        Label infoText = new Label(
            "This GUI allows you to test all backend functions:\n" +
            "• Create and manage events\n" +
            "• Schedule sessions with conflict detection\n" +
            "• Generate and track tickets\n" +
            "• Authenticate users\n" +
            "• View and validate data"
        );
        infoText.setStyle("-fx-font-size: 12;");
        infoSection.getChildren().addAll(infoTitle, infoText);

        root.getChildren().addAll(header, new Separator(), navSection, infoSection);

        // Create scene
        Scene scene = new Scene(root, 700, 800);
        primaryStage.setTitle("Event Management System - Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Create a styled navigation button
     */
    private Button createNavButton(String title, String description) {
        VBox buttonBox = new VBox(3);
        buttonBox.setPadding(new Insets(10));

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

        Label descLabel = new Label(description);
        descLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #666;");

        buttonBox.getChildren().addAll(titleLabel, descLabel);

        Button button = new Button();
        button.setGraphic(buttonBox);
        button.setStyle("-fx-padding: 15; -fx-background-color: #e3f2fd; -fx-border-color: #90caf9; -fx-border-radius: 5;");
        button.setPrefHeight(80);
        button.setPrefWidth(Double.MAX_VALUE);

        return button;
    }
}
