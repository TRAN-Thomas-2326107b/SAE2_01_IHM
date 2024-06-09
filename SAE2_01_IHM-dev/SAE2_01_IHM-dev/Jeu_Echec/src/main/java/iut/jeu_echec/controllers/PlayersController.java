package iut.jeu_echec.controllers;

import iut.jeu_echec.Jeu.GameManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

/**
 * Gere interface des joueurs, l'ajout de nouveau joueurs,l'affichage et met a jours les informations
 */
public class PlayersController {

    private ScrollPane playersScrollList;
    private ImageView btnAddNewPlayer;

    private boolean isInPlayers = false;

    public static ArrayList<GameManager.Player> selectedPlayers = new ArrayList<>();

    GameManager playerManager = GameManager.getInstance();

    PlayersController(ScrollPane playersScrollList, ImageView btnAddNewPlayer) {
        this.playersScrollList = playersScrollList;
        this.btnAddNewPlayer = btnAddNewPlayer;

        this.btnAddNewPlayer.setOnMouseClicked(event -> addNewPlayerButton());
    }

    /**
     * Ajoute un nouveau player lorsque le bouton est appuié
     */
    public void addNewPlayerButton() {
        int id = GameManager.getInstance().addPlayer("Joueur " + playerManager.getPlayers().size() + 1);

        GameManager.Player player = playerManager.getPlayers().get(id);
        if (player == null) return;

        VBox newPlayer = new VBox();
        VBox.setMargin(newPlayer, new Insets(5, 0, 0, 0));
        newPlayer.setSpacing(10);
        HBox playerInfo = new HBox();

        // Utilise un textfield pour changer le nom du joueur
        TextField pseudoField = new TextField(player.getName());

        Label finalPseudo = new Label();

        finalPseudo.setTextFill(Color.WHITE);

        URL url = getClass().getResource("/iut/jeu_echec/imgs/icons/black_400.png");
        System.out.println("url: " + url);

        ImageView img = new ImageView(new Image(url.toString()));
        img.setFitHeight(36);
        img.setFitWidth(36);

        playerInfo.getChildren().add(img);

        pseudoField.setPrefWidth(200);
        pseudoField.setPrefHeight(20);
        pseudoField.setStyle("-fx-font-size: 16px;");

        pseudoField.textProperty().addListener((observable, oldValue, newValue) -> {
            player.setName(newValue);
            finalPseudo.setText(newValue);
        });

        // lorsqu'on finit de modifer le textfield on modifie le label et on supprime le textfield
        pseudoField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                playerInfo.getChildren().remove(pseudoField);
                playerInfo.getChildren().add(finalPseudo);
            }
        });

        HBox.setMargin(finalPseudo, new Insets(0, 10, 0, 10));
        HBox.setMargin(pseudoField, new Insets(0, 10, 0, 10));
        finalPseudo.setStyle("-fx-font-size: 16px;");

        playerInfo.getChildren().add(pseudoField);
        newPlayer.getChildren().add(playerInfo);

        // ajout le joueur à la liste
        VBox content = this.playersScrollList.getContent() instanceof VBox ? (VBox) this.playersScrollList.getContent() : new VBox();
        content.getChildren().add(newPlayer);
    }

    /**
     * Permet d'afficher les différents joueurs actuels, passe le label en rouge lorsque le joue
     */
    public void displayPlayers() {
        final int MAX_SELECTION = 2;
        Label feedbackLabel = new Label();
        feedbackLabel.setStyle("-fx-text-fill: red;");
        VBox content = this.playersScrollList.getContent() instanceof VBox ? (VBox) this.playersScrollList.getContent() : new VBox();
        content.getChildren().clear();
        content.getChildren().add(feedbackLabel);

        for (Map.Entry<Integer, GameManager.Player> entry : GameManager.getInstance().getPlayers().entrySet()) {
            GameManager.Player player = entry.getValue();
            VBox newPlayer = createPlayerVBox(player, feedbackLabel, MAX_SELECTION);
            content.getChildren().add(newPlayer);
        }
    }

    /**
     * Permet d'afficher l'historique des différentes parties jouées par un joueurs
     */
    public void displayPlayersHistory() {
        VBox content = this.playersScrollList.getContent() instanceof VBox ? (VBox) this.playersScrollList.getContent() : new VBox();
        content.getChildren().clear();

        // boucle chaque jeu GameManager.Game
        for (Map.Entry<Integer, GameManager.Game> entry : GameManager.getInstance().getGames().entrySet()) {

            GameManager.Game game = entry.getValue();

            VBox newGame = new VBox();
            VBox.setMargin(newGame, new Insets(0, 0, 10, 0));
            newGame.setAlignment(Pos.CENTER);

            HBox gameInfo = new HBox();
            gameInfo.setAlignment(Pos.CENTER);
            gameInfo.setSpacing(10);

            URL timerImage = getClass().getResource("/iut/jeu_echec/imgs/icons/timer.png");

            System.out.println("timerImage: " + timerImage);

            if (timerImage == null) return;

            ImageView img = new ImageView(new Image(timerImage.toString()));

            img.setFitWidth(18);
            img.setFitHeight(18);

            GameManager.Player winner = game.getWinner();


            Label whitePlayer = new Label(game.getPlayers().get(0).getName());
            Label blackPlayer = new Label(game.getPlayers().get(1).getName());



            if (winner != null && winner.getName().equals(whitePlayer.getText())) {
                whitePlayer.setStyle("-fx-font-weight: bold;");
                whitePlayer.setTextFill(Color.GREEN);
                blackPlayer.setStyle("-fx-font-weight: normal;");
                blackPlayer.setTextFill(Color.RED);
            } else if (winner != null) {
                whitePlayer.setStyle("-fx-font-weight: normal;");
                whitePlayer.setTextFill(Color.RED);
                blackPlayer.setStyle("-fx-font-weight: bold;");
                blackPlayer.setTextFill(Color.GREEN);
            }

            whitePlayer.setStyle("-fx-font-size: 16px;");
            blackPlayer.setStyle("-fx-font-size: 16px;");

            Label _0_1 = new Label();
            _0_1.setTextFill(Color.WHITE);

            if (winner != null && winner.getName().equals(whitePlayer.getText())) {
                _0_1.setText(" 1 - 0 ");
            } else if (winner != null) {
                _0_1.setText(" 0 - 1 ");
            } else {
                _0_1.setText(" 0 - 0 ");
            }

            _0_1.setStyle("-fx-font-size: 16px;");

            Label timerText = new Label(String.valueOf(game.getTimer()) + " min");

            timerText.setTextFill(Color.WHITE);


            timerText.setStyle("-fx-font-weight: bold;");

            timerText.setStyle("-fx-font-size: 16px;");

            gameInfo.getChildren().addAll(img, whitePlayer, blackPlayer,_0_1, timerText);


            newGame.getChildren().add(gameInfo);
            content.getChildren().add(newGame);
        }
    }

    /**
     * Cette fonction permet de créer la VBox d'un nouveau joueur
     * @param player Le joueur a qui ont crée un VBox
     * @param feedbackLabel Le label qui afficher le message
     * @param maxSelection Le nombre max de joueurs possible
     * @return La fonction retourne une VBox configurée pour un joueur
     */
    private VBox createPlayerVBox(GameManager.Player player, Label feedbackLabel, int maxSelection) {
        VBox newPlayer = new VBox();
        VBox.setMargin(newPlayer, new Insets(0, 0, 5, 0));
        newPlayer.setSpacing(10);
        HBox playerInfo = new HBox();

        Label finalPseudo = new Label(player.getName() + " (" + player.getGamesPlayed() + " jouées, " + player.getGamesWon() + " gagnées)");

        URL url = getClass().getResource("/iut/jeu_echec/imgs/icons/black_400.png");
        if (url == null) return newPlayer;

        ImageView img = new ImageView(new Image(url.toString()));
        img.setFitHeight(36);
        img.setFitWidth(36);

        finalPseudo.setStyle("-fx-font-size: 16px;");
        finalPseudo.setTextFill(Color.WHITE);

        playerInfo.getChildren().addAll(img, finalPseudo);
        HBox.setMargin(finalPseudo, new Insets(0, 10, 0, 10));
        newPlayer.getChildren().add(playerInfo);


        // Event handlers avec des fonctions lambda
        if (!this.isInPlayers)
            return newPlayer;

        newPlayer.setOnMouseClicked(event -> {
            if (selectedPlayers.contains(player)) {
                newPlayer.setStyle("-fx-background-color: transparent;");
                newPlayer.setStyle("-fx-text-fill: white;");
                selectedPlayers.remove(player);
                feedbackLabel.setText("");
            } else {
                if (selectedPlayers.size() >= maxSelection) {
                    feedbackLabel.setText("Vous pouvez pas sélectionner plus de " + maxSelection + " joueurs");
                } else {
                    newPlayer.setStyle("-fx-background-color: lightblue;");
                    newPlayer.setStyle("-fx-text-fill: black;");
                    selectedPlayers.add(player);
                    feedbackLabel.setText("");
                }
            }
        });

        // Change le curseur
        newPlayer.setOnMouseEntered(event -> {
            newPlayer.setCursor(Cursor.HAND);
            if (!selectedPlayers.contains(player)) {
                newPlayer.setStyle("-fx-background-color: lightgrey;");
            } else {
                newPlayer.setStyle("-fx-background-color: lightblue;");
            }
        });
        newPlayer.setOnMouseExited(event -> {
            newPlayer.setCursor(Cursor.DEFAULT);
            if (selectedPlayers.contains(player)) {
                newPlayer.setStyle("-fx-background-color: lightblue;");
            } else {
                newPlayer.setStyle("-fx-background-color: transparent;");
            }
        });

        return newPlayer;
    }

    /**
     * setJoueurs dans la liste
     * @param inPlayers True s'il est dans la liste, false si non
     */
    public void setInPlayers(boolean inPlayers) {
        isInPlayers = inPlayers;
    }

    /**
     * Get la liste des joueurs sélectionnées
     * @return La listes des joueurs
     */
    public static ArrayList<GameManager.Player> getSelectedPlayers() {
        return selectedPlayers;
    }

    /**
     * Set la liste des joueurs
     */
    public void setPlayersScrollList(ScrollPane playersScrollList) {
        this.playersScrollList = playersScrollList;
    }

    /**
     * Enregistre les joueurs
     */
    public void savePlayers() {
        playerManager.savePlayers();
    }
}
