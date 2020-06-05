package github.mjksabit.akash.app.Network;

import com.jfoenix.controls.JFXAlert;
import github.mjksabit.akash.app.Controller.Application;
import github.mjksabit.akash.app.Controller.SendMoney;
import github.mjksabit.akash.app.Main;
import github.mjksabit.akash.app.Model.Request;
import github.mjksabit.akash.app.Model.Transaction;
import github.mjksabit.akash.app.Model.User;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApplicationRequest extends Request<Application> {

    private User user = null;

    public ApplicationRequest(Application requester) {
        super(requester);
    }

    public void balanceRequest() {
        JSONObject reqest = new JSONObject();
        try {
            reqest.put(REQUEST_TYPE, REQUEST_BALANCE);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ServerConnect.getInstance().sendRequest(reqest);
        ServerConnect.getInstance().waitForResponse(REQUEST_BALANCE, (response) -> {
            if (response.getBoolean(RESPONSE_SUCCESS)) {
                double balance = response.getDouble("balance");
                Platform.runLater(() -> {
                    JFXAlert<Label> jfxAlert = new JFXAlert<>(Main.stage);
                    jfxAlert.setHideOnEscape(true);
                    Label label = new Label("Your Account Balance: "+ balance);
                    label.setPadding(new Insets(10));
                    jfxAlert.setContent(label);
                    jfxAlert.show();
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Your Account Balance: "+ balance);
//                    alert.showAndWait();
                });
            }
        });
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void loadTransaction(int index, int loadEverytime, int filter) {
        JSONObject request = new JSONObject();

        try {
            request.put(REQUEST_TYPE, REQUEST_GET_TRANSACTION);
            request.put("index", index);
            request.put("limit", loadEverytime);
            request.put("filter", filter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ServerConnect.getInstance().sendRequest(request);
        ServerConnect.getInstance().waitForResponse(REQUEST_GET_TRANSACTION, (response) -> {
            if(!response.getBoolean(RESPONSE_SUCCESS)) return;
            JSONArray transactions = response.getJSONArray("transactions");
            
            for (int i=0; i<transactions.length(); i++) {
                JSONObject transaction = transactions.getJSONObject(i);
                Platform.runLater(() -> {
                    try {
                        requester.addTransaction(new Transaction(
                                transaction.getString("id"),
                                transaction.getString("name"),
                                transaction.getString("type"),
                                transaction.getString("reference"),
                                transaction.getBoolean("isCashOut"),
                                transaction.getDouble("amount")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }
}
