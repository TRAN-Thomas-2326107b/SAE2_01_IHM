package iut.jeu_echec.controllers;

import iut.jeu_echec.PlayerManager;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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



    static ArrayList<PlayerManager.Player> selectedPlayers = new ArrayList<>();

    PlayerManager playerManager = PlayerManager.getInstance();

    PlayersController(ScrollPane playersScrollList, ImageView btnAddNewPlayer) {
        this.playersScrollList = playersScrollList;
        this.btnAddNewPlayer = btnAddNewPlayer;

        this.btnAddNewPlayer.setOnMouseClicked(event -> addNewPlayerButton());
    }

    /**
     * Ajoute un nouveau player lorsque le bouton est appuié
     */
    public void addNewPlayerButton() {
        int id = PlayerManager.getInstance().addPlayer("Joueur " + playerManager.getPlayers().size() + 1);

        PlayerManager.Player player = playerManager.getPlayers().get(id);
        if (player == null) return;

        VBox newPlayer = new VBox();
        VBox.setMargin(newPlayer, new Insets(5, 0, 0, 0));
        newPlayer.setSpacing(10);
        HBox playerInfo = new HBox();

        // use a textfield to change the name of the player
        TextField pseudoField = new TextField(player.getName());
        Label finalPseudo = new Label();

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

        // when we finish editing the textfield we set the label and remove the textfield
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

        // add the player to the list
        VBox content = this.playersScrollList.getContent() instanceof VBox ? (VBox) this.playersScrollList.getContent() : new VBox();
        content.getChildren().add(newPlayer);
    }

    public void displayPlayers() {
        final int MAX_SELECTION = 2;
        Label feedbackLabel = new Label();
        feedbackLabel.setStyle("-fx-text-fill: red;");
        VBox content = this.playersScrollList.getContent() instanceof VBox ? (VBox) this.playersScrollList.getContent() : new VBox();
        content.getChildren().clear();
        content.getChildren().add(feedbackLabel);

        for (Map.Entry<Integer, PlayerManager.Player> entry : PlayerManager.getInstance().getPlayers().entrySet()) {
            PlayerManager.Player player = entry.getValue();
            VBox newPlayer = createPlayerVBox(player, feedbackLabel, MAX_SELECTION);
            content.getChildren().add(newPlayer);
        }
    }

    private VBox createPlayerVBox(PlayerManager.Player player, Label feedbackLabel, int maxSelection) {
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

        playerInfo.getChildren().addAll(img, finalPseudo);
        HBox.setMargin(finalPseudo, new Insets(0, 10, 0, 10));
        finalPseudo.setStyle("-fx-font-size: 16px;");
        newPlayer.getChildren().add(playerInfo);

        // Event handlers with lambda functions

        if (!this.isInPlayers)
            return newPlayer;

        newPlayer.setOnMouseClicked(event -> {
            if (selectedPlayers.contains(player)) {
                newPlayer.setStyle("-fx-background-color: white;");
                selectedPlayers.remove(player);
                feedbackLabel.setText("");
            } else {
                if (selectedPlayers.size() >= maxSelection) {
                    feedbackLabel.setText("You can't select more than " + maxSelection + " players");
                } else {
                    newPlayer.setStyle("-fx-background-color: lightblue;");
                    selectedPlayers.add(player);
                    feedbackLabel.setText("");
                }
            }
        });

        // Change cursor on hover
        newPlayer.setOnMouseEntered(event -> {
            newPlayer.setCursor(Cursor.HAND);
            if (!selectedPlayers.contains(player)) {
                newPlayer.setStyle("-fx-background-color: lightgrey;");
            }
        });
        newPlayer.setOnMouseExited(event -> {
            newPlayer.setCursor(Cursor.DEFAULT);
            if (selectedPlayers.contains(player)) {
                newPlayer.setStyle("-fx-background-color: lightblue;");
            } else {
                newPlayer.setStyle("-fx-background-color: white;");
            }
        });

        return newPlayer;
    }

    public void setInPlayers(boolean inPlayers) {
        isInPlayers = inPlayers;
    }

    public boolean isInPlayers() {
        return isInPlayers;
    }

    public static ArrayList<PlayerManager.Player> getSelectedPlayers() {
        return selectedPlayers;
    }

    public void setPlayersScrollList(ScrollPane playersScrollList) {
        this.playersScrollList = playersScrollList;
    }

    public ScrollPane getPlayersScrollList() {
        return playersScrollList;
    }

    public void setBtnAddNewPlayer(ImageView btnAddNewPlayer) {
        this.btnAddNewPlayer = btnAddNewPlayer;
    }

    public ImageView getBtnAddNewPlayer() {
        return btnAddNewPlayer;
    }

    public void savePlayers() {
        playerManager.savePlayers();
    }
}
