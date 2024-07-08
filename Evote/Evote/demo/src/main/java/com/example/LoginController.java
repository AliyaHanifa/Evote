package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;

public class LoginController {

    @FXML
    private Label Slogan;

    @FXML
    private Hyperlink createacc;

    @FXML
    private Label errorMessageLabel;

    @FXML
    private Hyperlink forgotpassword;

    @FXML
    private Button loginButton;

    @FXML
    private VBox leftPane;

    @FXML
    private Label loginText;

    @FXML
    private ImageView logo;

    @FXML
    private TextField password;

    @FXML
    private ComboBox<String> pilihanRole;

    @FXML
    private VBox rightPane;

    @FXML
    private TextField username;

    @FXML
    private Label welcomemsg;

    private Stage stage;
    private Scene scene;
    public Parent root;

    public List<Login> logins = new ArrayList<>();

    @FXML
    public void initialize() {
        pilihanRole.getItems().addAll("pemilih", "petugas", "admin");
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        String user = username.getText();
        String pass = password.getText();
        String role = pilihanRole.getValue();

        boolean loginValid = validateLogin(user, pass, role);

        if (loginValid) {
            loadSceneBasedOnRole(role, event);
        } else {
            errorMessageLabel.setText("Invalid username, password, or role.");
            errorMessageLabel.setVisible(true);
        }
    }
    private boolean validateLogin(String user, String pass, String role) {
        logins = CSVLoginUtil.readUsersFromCSV();
        for (Login login : logins) {
            System.out.println("Validating against - " + login);
            if (login.getUsername().equals(user) && login.getPassword().equals(pass) && login.getPilihanRole().equals(role)) {
                return true;
            }
        }
        return false;
    }
    

    private void loadSceneBasedOnRole(String pilihanRole, ActionEvent event) {
        String fxmlFile = "";
        switch (pilihanRole) {
            case "pemilih":
                fxmlFile = "/com/example/melihatKandidat-view.fxml";
                break;
            case "petugas":
                fxmlFile = "/com/example/jadwal-view.fxml";
                break;
            case "admin":
                fxmlFile = "/com/example/pemilih-view.fxml";
                break;
        }

        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleCreate(MouseEvent event) {

    }

    @FXML
    void handleForgotPass(MouseEvent event) {

    }
}

