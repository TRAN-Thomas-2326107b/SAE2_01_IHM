package iut.jeu_echec;

import iut.jeu_echec.Jeu.TableauEchec;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        TableauEchec.createBoard();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ChessMain.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Chess");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}