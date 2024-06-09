package iut.jeu_echec.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class TableauController {
    @FXML
    private GridPane tableauGrid;

    @FXML
    private GridPane changeTab;

    @FXML
    private StackPane tabContainer;

    @FXML
    Label tempsJoueurBlanc;

    @FXML
    Label tempsJoueurNoir;

    @FXML
    private ScrollPane playersScrollList;

    @FXML
    private ImageView btnAddNewPlayer;

    static PlayersController playersController;
    static GameController gameController;
    static TabController tabController;
    private static Scene scene;

    /**
     * Initialise l'échiquier, configure la logique et le chronomètre pour
     * chaque joueurs.
     */
    @FXML
    public void initialize() {
        // Crée l'échiquier
        playersController = new PlayersController(playersScrollList, btnAddNewPlayer);
        gameController = new GameController(tableauGrid, tempsJoueurBlanc, tempsJoueurNoir);

        tabController = new TabController(changeTab, tabContainer);
    }

    /**
     * Méthode qui obtient la scène de l'application.
     *
     * @return La scène actuelle.
     */
    public static Scene getScene() {
        return scene;
    }

    public static TabController getTabController() {
        return tabController;
    }

    /**
     * Méthode définissant la scène de l'application.
     *
     * @param sce La nouvelle scène à définir.
     */
    public static void setScene(Scene sce) {
        scene = sce;
    }
}