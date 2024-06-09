package iut.jeu_echec;

import iut.jeu_echec.Jeu.GameManager;
import iut.jeu_echec.Jeu.TableauEchec;
import iut.jeu_echec.controllers.TabController;
import iut.jeu_echec.controllers.TableauController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Classe principale de l'application d'échecs.
 * Lance l'interface utilisateur et initialise les composants nécessaires
 * du jeu.
 */
public class ChessApp extends Application {

    /**
     * Point d'entrée principal de l'application.
     *
     * @param stage La fenêtre princiaple de l'application.
     * @throws IOException Dans le cas où le fichier FXML ne peut se charger.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Crée l'échiquier et initialise le gestionnaire de joueurs
        TableauEchec.createBoard();
        GameManager.getInstance();

        // Initialise le contrôleur de l'échiquier
        TableauController tableauController = new TableauController();

        // Charge le fichier FXML de l'interface
        FXMLLoader fxmlLoader = new FXMLLoader(ChessApp.class.getResource("ChessMainV5.fxml"));
        tableauController.setScene(new Scene(fxmlLoader.load()));


        tableauController.getTabController().firstInit();
        // Affichage de la scène principale
        stage.setTitle("Chess");
        stage.setScene(tableauController.getScene());
        stage.show();
    }

    /**
     * Méthode principale lancant l'application.
     *
     * @param args Arguments de ligne de commande.
     */
    public static void main(String[] args) {
        launch();
    }
}