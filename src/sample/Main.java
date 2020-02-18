package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream("sample.fxml"));
        Controller controller = loader.getController();
        primaryStage.setTitle("Stale Memes");
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(controller::parseInput);
        scene.setOnKeyReleased(controller::endInput);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/fun/icon.png"));
        primaryStage.setResizable(false);
        primaryStage.setOnShown(e -> controller.init());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
