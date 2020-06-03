package github.mjksabit.akash.app.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AuthenticateC {

    private Request request = null;

    @FXML
    public void initialize() {
        backToLogIn(null);
        request = new Request();
    }

    @FXML
    private Pane pane_login;

    @FXML
    private JFXTextField textLoginMobileNo;

    @FXML
    private JFXPasswordField textLogInPassword;

    @FXML
    private JFXButton buttonLogIn;

    @FXML
    private Pane pane_signup;

    @FXML
    private JFXTextField textSignUpMobileNo;

    @FXML
    private JFXPasswordField textSignUpPassword;

    @FXML
    private JFXTextField textSignUpName;

    @FXML
    void backToLogIn(ActionEvent event) {
        pane_login.setVisible(true);
        pane_login.setDisable(false);

        pane_signup.setVisible(false);
        pane_signup.setDisable(true);
    }

    @FXML
    void logInRequest(ActionEvent event) {
        String mobile, password;

        mobile = textLoginMobileNo.getText();
        password = textLogInPassword.getText();

        request.logInRequest(mobile, password);

        textLoginMobileNo.setText("");
        textLogInPassword.setText("");
    }

    @FXML
    void showSignUpPane(ActionEvent event) {
        pane_signup.setVisible(true);
        pane_signup.setDisable(false);

        pane_login.setVisible(false);
        pane_login.setDisable(true);
    }

    @FXML
    void signUpRequest(ActionEvent event) {

    }

}