package iut.jeu_echec;

import iut.jeu_echec.Jeu.TableauEchec;
import iut.jeu_echec.controllers.TableauController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;



public class ChessApp extends Application {


    @Override
    public void start(Stage stage) throws IOException {

        TableauEchec.createBoard();
        PlayerManager.getInstance(); // init player manager

        TableauController tableauController = new TableauController();

        FXMLLoader fxmlLoader = new FXMLLoader(ChessApp.class.getResource("ChessMainV5.fxml"));
        tableauController.setScene(new Scene(fxmlLoader.load()));
        stage.setTitle("Chess");
        stage.setScene(tableauController.getScene() );
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}