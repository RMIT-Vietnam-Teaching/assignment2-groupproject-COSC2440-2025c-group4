package oop_clean.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oop_clean.models.Person;
import oop_clean.services.AuthService;
import java.util.Optional;

/**
 * Login screen for user authentication.
 * Allows users to enter their credentials and access the system.
 * 
 * @author Group04
 */
public class LoginScreen {
    private final AuthService authService = new AuthService();
    private final Stage primaryStage;

    public LoginScreen(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Display the login screen
     */
    public void show() {
        // Create main container
        VBox root = new VBox(15);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: #f5f5f5;");
        root.setAlignment(Pos.CENTER);

        // Title
        Label title = new Label("Event Management System");
        title.setStyle("-fx-font-size: 28; -fx-font-weight: bold; -fx-text-fill: #333;");

        // Username field
        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-font-size: 14;");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setStyle("-fx-font-size: 14; -fx-padding: 10;");
        usernameField.setPrefWidth(300);

        // Password field
        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-size: 14;");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setStyle("-fx-font-size: 14; -fx-padding: 10;");
        passwordField.setPrefWidth(300);

        // Status label
        Label statusLabel = new Label();
        statusLabel.setStyle("-fx-text-fill: red;");

        // Login button
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-font-size: 14; -fx-padding: 10 30; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        loginButton.setPrefWidth(150);

        // Demo credentials info
        Label demoLabel = new Label("Demo: admin/admin123 or alice/password1");
        demoLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #666;");

        // Login action
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                statusLabel.setText("Please enter username and password");
                return;
            }

            try {
                Optional<Person> user = authService.authenticate(username, password);
                if (user.isPresent()) {
                    // Login successful - go to main dashboard
                    MainDashboard dashboard = new MainDashboard(primaryStage, user.get());
                    dashboard.show();
                } else {
                    statusLabel.setText("Invalid username or password");
                }
            } catch (Exception ex) {
                statusLabel.setText("Login failed: " + ex.getMessage());
            }
        });

        // Add to layout
        root.getChildren().addAll(
            title,
            new Separator(),
            usernameLabel,
            usernameField,
            passwordLabel,
            passwordField,
            loginButton,
            statusLabel,
            new Separator(),
            demoLabel
        );

        // Create scene
        Scene scene = new Scene(root, 500, 500);
        primaryStage.setTitle("Login - Event Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
