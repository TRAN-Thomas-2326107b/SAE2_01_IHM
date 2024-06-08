package iut.jeu_echec.controllers;

import iut.jeu_echec.Jeu.TableauEchec;
import iut.jeu_echec.PlayerManager;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Map;

public class TabController {
    Map<Integer, List<String>> pieceMap = Map.of(
            1, List.of("newPartie"),
            2, List.of("partie_Archives", "partie_Toutes", "partie_Tournois"),
            3, List.of("player"));

    private String lastPanel = "newPartie";

    private GridPane changeTab;
    private StackPane tabContainer;

    public TabController(GridPane changeTab, StackPane tabContainer) {
        this.changeTab = changeTab;
        this.tabContainer = tabContainer;

        for (Node node : changeTab.getChildren()) {
            if (node instanceof VBox) {
                VBox tab = (VBox)node;
                tab.setOnMouseClicked(mouseEvent -> handleTabClick(tab));
            }
        }
    }

    /**
     * Gére les clicks sur les élements présent dans l'app, tel que changer de Vbox, démarer nouvelle partie, voir les archives.
     * @param tab L'élement clickée
     */
    private void handleTabClick(VBox tab) {
        // get the index of the tab clicked
        int index = changeTab.getChildren().indexOf(tab) +1;

        // based on the index, we can determine the piece to change : {1: ['newPartie'], 2: ['partie_Archies','partie_tournoi']}

        for (Node node : tabContainer.getChildren()) {
            if (!(node instanceof Pane))
                continue;

            Pane pane = (Pane)node;


            // get the id
            String id = pane.getId();

            if (pieceMap.get(index).contains(id)) {
                if (lastPanel.equals("player")) {
                    // save the players
                    TableauController.playersController.savePlayers();
                }
                // if we are in the player tab, load the players
                if (id.equals("player")) {
                    // display the players
                    TableauController.playersController.setInPlayers(false);
                    ScrollPane scrollPane = (ScrollPane) TableauController.getScene().lookup("#allPlayersScroll");
                    TableauController.playersController.setPlayersScrollList(scrollPane);
                    TableauController.playersController.displayPlayers();
                }

                if (id.equals("newPartie")) {

                    if (!TableauEchec.isGameStarted() && !TableauEchec.didGameEnd()) {
                        // lookup in the scene for a specific id
                        ScrollPane scrollPane = (ScrollPane) TableauController.getScene().lookup("#newPartieScroll");
                        TableauController.playersController.setInPlayers(true);

                        TableauController.playersController.setPlayersScrollList(scrollPane);
                        TableauController.playersController.displayPlayers();
                        System.out.println("scrollPane: " + scrollPane);


                        Button playButton = (Button)TableauController.getScene().lookup("#buttonPlay");

                        playButton.setOnMouseClicked(mouseEvent -> {
                            // chec if we have 2 players
                            if (TableauController.playersController.getSelectedPlayers().size() != 2) {
                                System.out.println("You need to select 2 players to start the game");
                                return;
                            }

                            TableauController.gameController.startGame();

                            // make them random
                            int random = (int)(Math.random() * 2);

                            PlayerManager.Player player1 = PlayersController.getSelectedPlayers().get(random);

                            PlayerManager.Player player2 = PlayersController.getSelectedPlayers().get(random == 0 ? 1 : 0);
                            Label player1Name = (Label)TableauController.getScene().lookup("#blackPlayerName");
                            Label player2Name = (Label)TableauController.getScene().lookup("#whitePlayerName");


                            TableauEchec.joueurBlanc = player2;
                            TableauEchec.joueurNoir = player1;

                            player1Name.setText(player1.getName());
                            player2Name.setText(player2.getName());

                        });
                    } else {
                        // if the game has started, add a button to forfeit the game

                    }


                }

                lastPanel = id;
                pane.setVisible(true);
                pane.setDisable(false);
                pane.setOpacity(1.0);
            } else {
                pane.setVisible(false);
                pane.setDisable(true);
                pane.setOpacity(0.0);
            }



        }
    }


}
