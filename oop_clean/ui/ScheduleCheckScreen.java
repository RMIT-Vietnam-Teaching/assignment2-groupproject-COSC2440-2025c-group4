package oop_clean.ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oop_clean.services.ScheduleService;
import oop_clean.models.Session;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Schedule checking screen for verifying session conflicts.
 * 
 * @author Group04
 */
public class ScheduleCheckScreen {
    private Stage primaryStage;
    private ScheduleService scheduleService = new ScheduleService();

    public ScheduleCheckScreen(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void show() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f9f9f9;");

        // Header
        HBox header = createHeader("Schedule Conflict Checker");
        root.getChildren().add(header);

        // Conflict check section
        VBox checkSection = createConflictCheckSection();
        root.getChildren().add(checkSection);

        // Scroll pane
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 800, 700);
        primaryStage.setTitle("Schedule Checker - Event Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Create the conflict check section
     */
    private VBox createConflictCheckSection() {
        VBox section = new VBox(10);
        section.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5; -fx-padding: 15; -fx-background-color: white;");

        Label title = new Label("Check for Session Conflicts");
        title.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        Label info = new Label(
            "Enter details for two sessions to check if they conflict\n" +
            "(Sessions conflict if they are scheduled at the same time)"
        );
        info.setStyle("-fx-text-fill: #666; -fx-font-size: 12;");

        // Session 1 inputs
        VBox session1Box = createSessionInputGroup("Session 1");
        TextField session1Title = (TextField) session1Box.getChildren().get(1);
        TextField session1DateTime = (TextField) session1Box.getChildren().get(3);

        // Session 2 inputs
        VBox session2Box = createSessionInputGroup("Session 2");
        TextField session2Title = (TextField) session2Box.getChildren().get(1);
        TextField session2DateTime = (TextField) session2Box.getChildren().get(3);

        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

        Button checkButton = new Button("Check for Conflicts");
        checkButton.setStyle("-fx-font-size: 14; -fx-padding: 10 30; -fx-background-color: #FF5722; -fx-text-fill: white;");

        checkButton.setOnAction(e -> {
            try {
                String title1 = session1Title.getText();
                String dateTime1Str = session1DateTime.getText();
                String title2 = session2Title.getText();
                String dateTime2Str = session2DateTime.getText();

                if (title1.isEmpty() || dateTime1Str.isEmpty() || title2.isEmpty() || dateTime2Str.isEmpty()) {
                    resultLabel.setText("Please fill in all fields");
                    resultLabel.setStyle("-fx-text-fill: red;");
                    return;
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime dateTime1 = LocalDateTime.parse(dateTime1Str, formatter);
                LocalDateTime dateTime2 = LocalDateTime.parse(dateTime2Str, formatter);

                // Create dummy sessions for testing
                Session s1 = new Session("S_TEST1", title1, "Test session 1", dateTime1, "Room A", 50, null);
                Session s2 = new Session("S_TEST2", title2, "Test session 2", dateTime2, "Room B", 50, null);

                // Check for conflicts
                boolean hasConflict = scheduleService.hasConflict(s1, s2);

                if (hasConflict) {
                    resultLabel.setText("⚠ CONFLICT DETECTED: Sessions are scheduled at the same time!");
                    resultLabel.setStyle("-fx-text-fill: red;");
                } else {
                    resultLabel.setText("✓ NO CONFLICT: Sessions can be scheduled without overlap");
                    resultLabel.setStyle("-fx-text-fill: green;");
                }
            } catch (Exception ex) {
                resultLabel.setText("Error: " + ex.getMessage());
                resultLabel.setStyle("-fx-text-fill: red;");
            }
        });

        // Info section
        VBox infoSection = new VBox(10);
        infoSection.setStyle("-fx-border-color: #90CAF9; -fx-border-radius: 5; -fx-padding: 15; -fx-background-color: #E3F2FD;");
        Label infoTitle = new Label("Schedule Validation Rules");
        infoTitle.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        Label infoText = new Label(
            "• Two sessions conflict if they have the same scheduled time\n" +
            "• Format for Date & Time: YYYY-MM-DD HH:MM (e.g., 2025-03-01 10:00)\n" +
            "• System prevents double-booking of sessions\n" +
            "• Use this tool to verify session scheduling before creation"
        );
        infoText.setStyle("-fx-font-size: 12; -fx-wrap-text: true;");
        infoSection.getChildren().addAll(infoTitle, infoText);

        section.getChildren().addAll(
            title,
            info,
            new Separator(),
            session1Box,
            session2Box,
            checkButton,
            resultLabel,
            new Separator(),
            infoSection
        );
        return section;
    }

    /**
     * Create a session input group
     */
    private VBox createSessionInputGroup(String sessionName) {
        VBox group = new VBox(5);
        group.setStyle("-fx-border-color: #ccc; -fx-border-radius: 3; -fx-padding: 10; -fx-background-color: #fafafa;");

        Label nameLabel = new Label(sessionName);
        nameLabel.setStyle("-fx-font-size: 13; -fx-font-weight: bold;");

        TextField titleField = new TextField();
        titleField.setPromptText("Session Title");
        titleField.setPrefWidth(300);

        TextField dateTimeField = new TextField();
        dateTimeField.setPromptText("Date & Time (YYYY-MM-DD HH:MM)");
        dateTimeField.setPrefWidth(300);

        HBox inputBox = new HBox(10);
        inputBox.getChildren().addAll(
            new VBox(3, new Label("Title:"), titleField),
            new VBox(3, new Label("Date & Time:"), dateTimeField)
        );

        group.getChildren().addAll(nameLabel, inputBox);
        return group;
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
            MainDashboard dashboard = new MainDashboard(primaryStage, null);
            dashboard.show();
        });

        header.getChildren().addAll(titleLabel, backButton);
        header.setSpacing(20);
        return header;
    }
}
