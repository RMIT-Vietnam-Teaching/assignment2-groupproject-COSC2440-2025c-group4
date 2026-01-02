package oop_clean.ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oop_clean.models.Session;
import oop_clean.models.Person;
import oop_clean.services.SessionService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Session management screen for creating and viewing sessions.
 * 
 * @author Group04
 */
public class SessionManagementScreen {
    private Stage primaryStage;
    private Person currentUser;
    private SessionService sessionService = new SessionService();
    private TableView<Session> sessionTable;

    public SessionManagementScreen(Stage primaryStage, Person currentUser) {
        this.primaryStage = primaryStage;
        this.currentUser = currentUser;
    }

    public void show() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f9f9f9;");

        // Header
        HBox header = createHeader("Session Management");
        root.getChildren().add(header);

        // Create session section
        VBox createSection = createSessionCreationSection();
        root.getChildren().add(createSection);

        // List sessions section
        VBox listSection = createSessionListSection();
        root.getChildren().add(listSection);

        // Scroll pane
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 900, 900);
        primaryStage.setTitle("Session Management - Event Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Create the session creation form
     */
    private VBox createSessionCreationSection() {
        VBox section = new VBox(10);
        section.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5; -fx-padding: 15; -fx-background-color: white;");

        Label title = new Label("Create New Session");
        title.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        TextField titleField = new TextField();
        titleField.setPromptText("Session Title");
        titleField.setPrefWidth(250);

        TextArea descField = new TextArea();
        descField.setPromptText("Session Description");
        descField.setPrefHeight(80);
        descField.setWrapText(true);

        TextField dateTimeField = new TextField();
        dateTimeField.setPromptText("Date & Time (YYYY-MM-DD HH:MM)");
        dateTimeField.setPrefWidth(250);

        TextField venueField = new TextField();
        venueField.setPromptText("Venue");
        venueField.setPrefWidth(250);

        Spinner<Integer> capacitySpinner = new Spinner<>(1, 500, 50);
        Label capacityLabel = new Label("Capacity: " + capacitySpinner.getValue());

        capacitySpinner.valueProperty().addListener((obs, oldVal, newVal) ->
            capacityLabel.setText("Capacity: " + newVal)
        );

        Label statusLabel = new Label();
        statusLabel.setStyle("-fx-text-fill: green;");

        Button createButton = new Button("Create Session");
        createButton.setStyle("-fx-font-size: 14; -fx-padding: 10 30; -fx-background-color: #2196F3; -fx-text-fill: white;");

        createButton.setOnAction(e -> {
            try {
                String sessionTitle = titleField.getText();
                String description = descField.getText();
                String dateTimeStr = dateTimeField.getText();
                String venue = venueField.getText();
                int capacity = capacitySpinner.getValue();

                if (sessionTitle.isEmpty() || dateTimeStr.isEmpty() || venue.isEmpty()) {
                    statusLabel.setText("Please fill in all required fields");
                    statusLabel.setStyle("-fx-text-fill: red;");
                    return;
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);

                Session newSession = sessionService.createSession(sessionTitle, description, dateTime, venue, capacity);
                statusLabel.setText("✓ Session created successfully! ID: " + newSession.getSessionId());
                statusLabel.setStyle("-fx-text-fill: green;");

                // Clear fields
                titleField.clear();
                descField.clear();
                dateTimeField.clear();
                venueField.clear();

                refreshSessionList();
            } catch (Exception ex) {
                statusLabel.setText("Error: " + ex.getMessage());
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        });

        HBox inputBox = new HBox(10);
        inputBox.getChildren().addAll(
            new VBox(3, new Label("Title:"), titleField),
            new VBox(3, new Label("Venue:"), venueField),
            new VBox(3, new Label("Capacity:"), capacitySpinner)
        );

        section.getChildren().addAll(
            title,
            inputBox,
            new Label("Description:"),
            descField,
            new Label("Date & Time:"),
            dateTimeField,
            createButton,
            statusLabel
        );
        return section;
    }

    /**
     * Create the session list section
     */
    private VBox createSessionListSection() {
        VBox section = new VBox(10);
        section.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5; -fx-padding: 15; -fx-background-color: white;");

        Label title = new Label("All Sessions");
        title.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        sessionTable = new TableView<>();
        
        TableColumn<Session, String> idCol = new TableColumn<>("Session ID");
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getSessionId()));
        idCol.setPrefWidth(100);

        TableColumn<Session, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTitle()));
        titleCol.setPrefWidth(150);

        TableColumn<Session, String> dateCol = new TableColumn<>("Date & Time");
        dateCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            cellData.getValue().getScheduledDateTime().toString()
        ));
        dateCol.setPrefWidth(150);

        TableColumn<Session, String> venueCol = new TableColumn<>("Venue");
        venueCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getVenue()));
        venueCol.setPrefWidth(100);

        TableColumn<Session, Integer> capacityCol = new TableColumn<>("Capacity");
        capacityCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(
            cellData.getValue().getCapacity()
        ).asObject());
        capacityCol.setPrefWidth(80);

        sessionTable.getColumns().addAll(idCol, titleCol, dateCol, venueCol, capacityCol);
        sessionTable.setPrefHeight(300);

        Button refreshButton = new Button("Refresh List");
        refreshButton.setOnAction(e -> refreshSessionList());

        section.getChildren().addAll(title, sessionTable, refreshButton);
        return section;
    }

    /**
     * Refresh the session list
     */
    private void refreshSessionList() {
        try {
            List<Session> sessions = sessionService.getAllSessions();
            sessionTable.getItems().setAll(sessions);
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to refresh: " + ex.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Create header with back button
     */
    private HBox createHeader(String title) {
        HBox header = new HBox();
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");

        Button backButton = new Button("← Back");
        backButton.setStyle("-fx-padding: 8 15;");
        backButton.setOnAction(e -> {
            MainDashboard dashboard = new MainDashboard(primaryStage, currentUser);
            dashboard.show();
        });

        header.getChildren().addAll(titleLabel, backButton);
        header.setSpacing(20);
        return header;
    }
}
