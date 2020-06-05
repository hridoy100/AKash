package github.mjksabit.akash.app.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import github.mjksabit.akash.app.Main;
import github.mjksabit.akash.app.Model.Controller;
import github.mjksabit.akash.app.Model.User;
import github.mjksabit.akash.app.Network.SendMoneyRequest;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import static java.lang.Double.parseDouble;

public class Donate extends Controller {
    User user = null;
    SendMoneyRequest request = null;

    public void setUser(User user) {
        this.user = user;
        textSenderNumber.setText(user.getMobile());
        textReference.setText(user.getName());
    }

    @FXML
    public void initialize() {
        sendProgress.setVisible(false);
        request = new SendMoneyRequest(this);
        setRootNode(root);
    }

    @FXML
    AnchorPane root;

    @FXML
    private Label textSenderNumber;

    @FXML
    private JFXTextField textSendTo;

    @FXML
    private JFXTextField textReference;

    @FXML
    private JFXPasswordField textPassword;

    @FXML
    private JFXButton buttonSend;

    @FXML
    private ProgressBar sendProgress;

    @FXML
    private JFXTextField textAmount;

    final int totalTime = 1000;
    final int perStep = 50;
    boolean tap;

    boolean notEmpty(JFXTextField textField) {
        if(textField.getText().isEmpty()) {
            textField.requestFocus();
            textField.setFocusColor(Color.RED);
            return false;
        }
        return true;
    }

    boolean isInteger(JFXTextField textField) {
        try{Integer.parseInt(textField.getText());}
        catch (NumberFormatException e) {
            textField.requestFocus();
            textField.setFocusColor(Color.RED);
            return false;
        }
        return true;
    }

    boolean matchPassword(JFXPasswordField passwordField) {
        if(passwordField.getText().isEmpty()) {
            passwordField.requestFocus();
            passwordField.setFocusColor(Color.RED);
            return false;
        }

        if(!passwordField.getText().equals(user.getPassword())) {
            Main.showError((Pane) getRoot(), "Password Mismatch!", 2000);
            return false;
        }
        return true;
    }

    boolean validateInput() {
        return
                notEmpty(textSendTo) &&
                        isInteger(textSendTo) &&
                        notEmpty(textAmount) &&
                        isInteger(textAmount) &&
                        matchPassword(textPassword);
    }

    @FXML
    void startSendMoneyProgress(Event event) {
        if(!validateInput()) return;

        tap = true;
        new Thread(() -> {
            int time;
            sendProgress.setVisible(true);
            for (time=perStep; time<=totalTime && tap; time += perStep) {
                double progress = ((double)time)/totalTime;
                try {
                    Thread.sleep(perStep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> sendProgress.setProgress(progress));
            }
            sendProgress.setVisible(false);
            if (time>totalTime) sendMoneyRequest();
        }).start();
    }

    @FXML
    void stopSendMoneyProgress(Event event) {
        tap = false;
    }

    public void sendMoneyRequest() {
        request.sendMoney(user, textSendTo.getText(), parseDouble(textAmount.getText()), textReference.getText(), "Donation");
    }
}
