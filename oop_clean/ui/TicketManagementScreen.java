package oop_clean.ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oop_clean.models.Ticket;
import oop_clean.services.TicketService;
import oop_clean.models.Person;
import java.util.List;

/**
 * Ticket management screen for generating and managing tickets.
 * 
 * @author Group04
 */
public class TicketManagementScreen {
    private Stage primaryStage;
    private Person currentUser;
    private TicketService ticketService = new TicketService();
    private TableView<Ticket> ticketTable;

    public TicketManagementScreen(Stage primaryStage, Person currentUser) {
        this.primaryStage = primaryStage;
        this.currentUser = currentUser;
    }

    public void show() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f9f9f9;");

        // Header
        HBox header = createHeader("Ticket Management");
        root.getChildren().add(header);

        // Generate ticket section
        VBox generateSection = createTicketGenerationSection();
        root.getChildren().add(generateSection);

        // List tickets section
        VBox listSection = createTicketListSection();
        root.getChildren().add(listSection);

        // Scroll pane
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 900, 900);
        primaryStage.setTitle("Ticket Management - Event Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Create the ticket generation form
     */
    private VBox createTicketGenerationSection() {
        VBox section = new VBox(10);
        section.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5; -fx-padding: 15; -fx-background-color: white;");

        Label title = new Label("Generate New Ticket");
        title.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        ComboBox<String> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("STANDARD", "VIP", "EARLY_BIRD");
        typeCombo.setPromptText("Select Ticket Type");
        typeCombo.setPrefWidth(200);

        Spinner<Double> priceSpinner = new Spinner<>(0.0, 1000.0, 50.0, 10.0);
        priceSpinner.setPrefWidth(150);

        TextField attendeeIdField = new TextField();
        attendeeIdField.setPromptText("Attendee ID (e.g., P001)");
        attendeeIdField.setPrefWidth(200);

        TextField eventIdField = new TextField();
        eventIdField.setPromptText("Event ID (e.g., E001)");
        eventIdField.setPrefWidth(200);

        TextField sessionIdField = new TextField();
        sessionIdField.setPromptText("Session ID (e.g., S001)");
        sessionIdField.setPrefWidth(200);

        Label statusLabel = new Label();
        statusLabel.setStyle("-fx-text-fill: green;");

        Button generateButton = new Button("Generate Ticket");
        generateButton.setStyle("-fx-font-size: 14; -fx-padding: 10 30; -fx-background-color: #FF9800; -fx-text-fill: white;");

        generateButton.setOnAction(e -> {
            try {
                String type = typeCombo.getValue();
                Double price = priceSpinner.getValue();
                String attendeeId = attendeeIdField.getText();
                String eventId = eventIdField.getText();
                String sessionId = sessionIdField.getText();

                if (type == null || attendeeId.isEmpty() || eventId.isEmpty() || sessionId.isEmpty()) {
                    statusLabel.setText("Please fill in all required fields");
                    statusLabel.setStyle("-fx-text-fill: red;");
                    return;
                }

                Ticket newTicket = ticketService.generateTicket(type, price, attendeeId, eventId, sessionId);
                statusLabel.setText("✓ Ticket generated successfully! ID: " + newTicket.getTicketId());
                statusLabel.setStyle("-fx-text-fill: green;");

                // Clear fields
                typeCombo.setValue(null);
                priceSpinner.getValueFactory().setValue(50.0);
                attendeeIdField.clear();
                eventIdField.clear();
                sessionIdField.clear();

                refreshTicketList();
            } catch (Exception ex) {
                statusLabel.setText("Error: " + ex.getMessage());
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        });

        HBox inputBox = new HBox(15);
        inputBox.getChildren().addAll(
            new VBox(3, new Label("Type:"), typeCombo),
            new VBox(3, new Label("Price:"), priceSpinner),
            new VBox(3, new Label("Attendee ID:"), attendeeIdField),
            new VBox(3, new Label("Event ID:"), eventIdField),
            new VBox(3, new Label("Session ID:"), sessionIdField)
        );

        section.getChildren().addAll(title, inputBox, generateButton, statusLabel);
        return section;
    }

    /**
     * Create the ticket list section
     */
    private VBox createTicketListSection() {
        VBox section = new VBox(10);
        section.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5; -fx-padding: 15; -fx-background-color: white;");

        Label title = new Label("All Tickets");
        title.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        ticketTable = new TableView<>();
        
        TableColumn<Ticket, String> idCol = new TableColumn<>("Ticket ID");
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTicketId()));
        idCol.setPrefWidth(120);

        TableColumn<Ticket, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getType()));
        typeCol.setPrefWidth(100);

        TableColumn<Ticket, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(
            cellData.getValue().getPrice()
        ).asObject());
        priceCol.setPrefWidth(80);

        TableColumn<Ticket, String> attendeeCol = new TableColumn<>("Attendee ID");
        attendeeCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAttendeeId()));
        attendeeCol.setPrefWidth(120);

        TableColumn<Ticket, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            cellData.getValue().getStatus().toString()
        ));
        statusCol.setPrefWidth(100);

        ticketTable.getColumns().addAll(idCol, typeCol, priceCol, attendeeCol, statusCol);
        ticketTable.setPrefHeight(300);

        Button refreshButton = new Button("Refresh List");
        refreshButton.setOnAction(e -> refreshTicketList());

        section.getChildren().addAll(title, ticketTable, refreshButton);
        return section;
    }

    /**
     * Refresh the ticket list
     */
    private void refreshTicketList() {
        try {
            List<Ticket> tickets = ticketService.getAllTickets();
            ticketTable.getItems().setAll(tickets);
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
