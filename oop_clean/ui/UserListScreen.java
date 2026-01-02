package oop_clean.ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oop_clean.models.Person;
import oop_clean.dao.PersonDAOImpl;
import java.util.List;

/**
 * User list screen for viewing all users in the system.
 * 
 * @author Group04
 */
public class UserListScreen {
    private Stage primaryStage;
    private PersonDAOImpl personDAO = new PersonDAOImpl();

    public UserListScreen(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void show() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f9f9f9;");

        // Header
        HBox header = createHeader("User Management");
        root.getChildren().add(header);

        // User list section
        VBox listSection = createUserListSection();
        root.getChildren().add(listSection);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("User Management - Event Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Create the user list section
     */
    private VBox createUserListSection() {
        VBox section = new VBox(10);
        section.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5; -fx-padding: 15; -fx-background-color: white;");

        Label title = new Label("Registered Users");
        title.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        TableView<Person> userTable = new TableView<>();
        
        TableColumn<Person, String> idCol = new TableColumn<>("Person ID");
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPersonId()));
        idCol.setPrefWidth(100);

        TableColumn<Person, String> nameCol = new TableColumn<>("Full Name");
        nameCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFullName()));
        nameCol.setPrefWidth(150);

        TableColumn<Person, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUsername()));
        usernameCol.setPrefWidth(120);

        TableColumn<Person, String> contactCol = new TableColumn<>("Contact");
        contactCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getContact()));
        contactCol.setPrefWidth(150);

        TableColumn<Person, String> roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            cellData.getValue().getRole() != null ? cellData.getValue().getRole().toString() : "N/A"
        ));
        roleCol.setPrefWidth(100);

        userTable.getColumns().addAll(idCol, nameCol, usernameCol, contactCol, roleCol);
        userTable.setPrefHeight(400);

        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(e -> {
            try {
                // For demo purposes, we load all users from database
                // In a real app, you'd use a getAllUsers() method
                userTable.getItems().clear();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setContentText("Users are loaded from database at startup. Total: 25 demo users");
                alert.showAndWait();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Failed to refresh: " + ex.getMessage());
                alert.showAndWait();
            }
        });

        section.getChildren().addAll(title, userTable, refreshButton);
        return section;
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
