package github.mjksabit.akash.app.Network;

import github.mjksabit.akash.app.Main;
import github.mjksabit.akash.app.Model.Controller;
import github.mjksabit.akash.app.Model.Request;
import github.mjksabit.akash.app.Model.User;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import org.json.JSONException;
import org.json.JSONObject;

public class SendMoneyRequest extends Request<Controller> {

    public SendMoneyRequest(Controller requester) {
        super(requester);
    }

    public void sendMoney(User sender, String receiver, double amount, String reference, String type) {
        JSONObject request = new JSONObject();

        try {
            request.put(REQUEST_TYPE, REQUEST_SEND_MONEY);
            request.put("sender", sender.getMobile());
            request.put("receiver", receiver);
            request.put("amount", amount);
            request.put("reference", reference);
            request.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ServerConnect.getInstance().sendRequest(request);
        ServerConnect.getInstance().waitForResponse(REQUEST_SEND_MONEY, (response) -> {
            if(response.getBoolean(RESPONSE_SUCCESS)) {
                Platform.runLater(() -> {
                    Main.showSuccess("Transaction Successful!", 2000);
                    requester.getStage().close();
                });
            }
            else {
                String error = "Transaction Failed! ", errorMessage;
                try {
                    error += response.getString("info");
                } catch (JSONException e) {

                }
                errorMessage = error;

                Platform.runLater(() -> {
                    Main.showError((Pane) requester.getRoot(), errorMessage, 2000);
                });
            }
        });
    }
}
