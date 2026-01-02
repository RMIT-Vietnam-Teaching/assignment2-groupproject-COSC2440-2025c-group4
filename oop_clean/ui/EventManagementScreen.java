package oop_clean.ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oop_clean.models.Event;
import oop_clean.models.Person;
import oop_clean.services.EventService;
import java.util.List;

/**
 * Event management screen for creating, viewing, and managing events.
 * 
 * @author Group04
 */
public class EventManagementScreen {
    private Stage primaryStage;
    private Person currentUser;
    private EventService eventService = new EventService();

    public EventManagementScreen(Stage primaryStage, Person currentUser) {
        this.primaryStage = primaryStage;
        this.currentUser = currentUser;
    }

    public void show() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f9f9f9;");

        // Header
        HBox header = createHeader("Event Management");
        root.getChildren().add(header);

        // Create event section
        VBox createSection = createEventCreationSection();
        root.getChildren().add(createSection);

        // List events section
        VBox listSection = createEventListSection();
        root.getChildren().add(listSection);

        // Scroll pane
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 800, 900);
        primaryStage.setTitle("Event Management - Event Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Create the event creation form
     */
    private VBox createEventCreationSection() {
        VBox section = new VBox(10);
        section.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5; -fx-padding: 15; -fx-background-color: white;");

        Label title = new Label("Create New Event");
        title.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        TextField nameField = new TextField();
        nameField.setPromptText("Event Name");
        nameField.setPrefWidth(300);

        ComboBox<String> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("CONFERENCE", "WORKSHOP", "SEMINAR", "FORUM", "EXPO", "NETWORKING");
        typeCombo.setPromptText("Select Event Type");
        typeCombo.setPrefWidth(300);

        TextField dateField = new TextField();
        dateField.setPromptText("Start Date (YYYY-MM-DD)");
        dateField.setPrefWidth(300);

        TextField locationField = new TextField();
        locationField.setPromptText("Location");
        locationField.setPrefWidth(300);

        Label statusLabel = new Label();
        statusLabel.setStyle("-fx-text-fill: green;");

        Button createButton = new Button("Create Event");
        createButton.setStyle("-fx-font-size: 14; -fx-padding: 10 30; -fx-background-color: #4CAF50; -fx-text-fill: white;");

        createButton.setOnAction(e -> {
            try {
                String name = nameField.getText();
                String type = typeCombo.getValue();
                String date = dateField.getText();
                String location = locationField.getText();

                if (name.isEmpty() || type == null || date.isEmpty() || location.isEmpty()) {
                    statusLabel.setText("Please fill in all fields");
                    statusLabel.setStyle("-fx-text-fill: red;");
                    return;
                }

                Event newEvent = eventService.createEvent(name, type, date, location);
                statusLabel.setText("✓ Event created successfully! ID: " + newEvent.getEventId());
                statusLabel.setStyle("-fx-text-fill: green;");

                // Clear fields
                nameField.clear();
                typeCombo.setValue(null);
                dateField.clear();
                locationField.clear();

                // Refresh list
                refreshEventList();
            } catch (Exception ex) {
                statusLabel.setText("Error: " + ex.getMessage());
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        });

        HBox inputBox = new HBox(10);
        inputBox.getChildren().addAll(
            new VBox(3, new Label("Name:"), nameField),
            new VBox(3, new Label("Type:"), typeCombo),
            new VBox(3, new Label("Date:"), dateField),
            new VBox(3, new Label("Location:"), locationField)
        );

        section.getChildren().addAll(title, inputBox, createButton, statusLabel);
        return section;
    }

    /**
     * Create the event list section
     */
    private VBox createEventListSection() {
        VBox section = new VBox(10);
        section.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5; -fx-padding: 15; -fx-background-color: white;");

        Label title = new Label("All Events");
        title.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        TableView<Event> table = new TableView<>();
        
        TableColumn<Event, String> idCol = new TableColumn<>("Event ID");
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEventId()));
        idCol.setPrefWidth(100);

        TableColumn<Event, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        nameCol.setPrefWidth(120);

        TableColumn<Event, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getType()));
        typeCol.setPrefWidth(100);

        TableColumn<Event, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDate()));
        dateCol.setPrefWidth(120);

        TableColumn<Event, String> locationCol = new TableColumn<>("Location");
        locationCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getLocation()));
        locationCol.setPrefWidth(150);

        table.getColumns().addAll(idCol, nameCol, typeCol, dateCol, locationCol);
        table.setPrefHeight(300);

        Button refreshButton = new Button("Refresh List");
        refreshButton.setOnAction(e -> refreshEventList());

        section.getChildren().addAll(title, table, refreshButton);
        this.eventTable = table;
        return section;
    }

    private TableView<Event> eventTable;

    /**
     * Refresh the event list
     */
    private void refreshEventList() {
        try {
            List<Event> events = eventService.getAllEvents();
            eventTable.getItems().setAll(events);
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
