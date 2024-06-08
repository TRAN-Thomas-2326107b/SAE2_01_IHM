package iut.jeu_echec.controllers;

import iut.jeu_echec.ChessApp;
import iut.jeu_echec.Jeu.Pieces.Piece;
import iut.jeu_echec.Jeu.Pieces.Roi;
import iut.jeu_echec.Jeu.TableauEchec;
import iut.jeu_echec.PlayerManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


    private static Scene scene;

    @FXML
    /**
     * Initialise l'échiquier et configure la logique, le chronometre pour chaque joeurs.
     * @return void
     */
    public void initialize() {
        // create the board


        playersController = new PlayersController(playersScrollList, btnAddNewPlayer);

        gameController = new GameController(tableauGrid, tempsJoueurBlanc, tempsJoueurNoir);

        TabController tabController = new TabController(changeTab, tabContainer);

        //TableauEchec.setGameStarted(true);
    }



    public static Scene getScene() {
        return scene;
    }

    public static void setScene(Scene sce) {
        scene = sce;
    }
}