package github.mjksabit.akash.app;

import com.jfoenix.controls.JFXSnackbar;
import github.mjksabit.akash.app.Controller.ServerConnect;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class Main extends Application {

    public static Stage stage;

    private static Main instance;

    public Main() {
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;

        primaryStage.setTitle("AKash");
//        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setOnCloseRequest((event) -> {
            System.out.println("Bye");
            Platform.exit();
            System.exit(0);
        });

        showAuthorizationStage();
    }

    public boolean showAuthorizationStage () {
        try {
            replaceSceneContent("authentication");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        stage.show();
        return true;
    }

    public static Parent replaceSceneContent(String fxml) throws Exception {
        stage.setResizable(true);
        Parent page = (Parent) FXMLLoader.load(Main.class.getResource("View/"+fxml+".fxml"), null, new JavaFXBuilderFactory());
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(page);
//            scene.getStylesheets().add(Main.class.getResource("demo.css").toExternalForm());
            stage.setScene(scene);
        } else {
            stage.getScene().setRoot(page);
        }
        stage.sizeToScene();
        //stage.setResizable(false);
        return page;
    }

    public static void showError(Pane rootPane, String text, long durationInMSec) {
        Label toast = new Label(text);
        toast.setPrefWidth(rootPane.getWidth());
        toast.setWrapText(true);
        toast.setStyle("-fx-background-color: #ff0e4d; -fx-text-fill: #f0f8ff; -fx-padding: 20px; -fx-alignment: center; ");
        showNotification(rootPane, toast, durationInMSec);
    }

    public static void showSuccess(Pane rootPane, String text, long duration) {
        Label toast = new Label(text);
        toast.setPrefWidth(rootPane.getWidth());
        toast.setWrapText(true);
        toast.setStyle("-fx-background-color: #00ba35; -fx-text-fill: #f0f8ff; -fx-padding: 20px; -fx-alignment: center; ");
        showNotification(rootPane, toast, duration);
    }

    private static void showNotification(Pane rootPane, Node node, long duration) {
        Platform.runLater( () -> {
            JFXSnackbar snackbar = new JFXSnackbar(rootPane);
            JFXSnackbar.SnackbarEvent eventToast = new JFXSnackbar.SnackbarEvent(node, new Duration(duration), null);
            snackbar.enqueue(eventToast);
        } );
    }

    public static void main(String[] args) {
        launch(args);
    }
}
