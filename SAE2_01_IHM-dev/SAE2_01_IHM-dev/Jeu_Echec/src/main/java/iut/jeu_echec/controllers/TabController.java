package iut.jeu_echec.controllers;

import iut.jeu_echec.Jeu.TableauEchec;
import iut.jeu_echec.Jeu.GameManager;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import java.util.List;
import java.util.Map;

/**
 * Classe du contrôleur servant à la gestion des onglets dans l'application.
 * Permet de changer d'onglet et charger le contenu approprié selon le
 * choix des utilisateurs.
 */
public class TabController {
    private static final Map<Integer, List<String>> PIECE_MAP = Map.of(
            1, List.of("newPartie"),
            2, List.of("partie_Toutes"),
            3, List.of("player")
    );

    // Dernier onglet sélectionné
    private String lastPanel = "newPartie";

    // Grille des onglets
    private final GridPane changeTab;
    // Conteneur des onglets
    private final StackPane tabContainer;

    /**
     * Méthode initialisant le contrôleur d'onglets avec la grille et le
     * conteneur d'onglets correspondant.
     *
     * @param changeTab Grille des onglets.
     * @param tabContainer Conteneur des onglets.
     */
    public TabController(GridPane changeTab, StackPane tabContainer) {
        this.changeTab = changeTab;
        this.tabContainer = tabContainer;

        initializeTabClickHandlers();
    }

    /**
     * Méthode chargant l'onglet pour créer une nouvelle partie.
     */
    public void firstInit() {
        loadNewPartieTab();
    }

    /**
     * Méthode initialisant les gestionnaires d'événements pour les onglets
     * cliqués par l'utilisateur.
     */
    private void initializeTabClickHandlers() {
        for (Node node : changeTab.getChildren()) {
            if (node instanceof VBox) {
                VBox tab = (VBox) node;
                tab.setOnMouseClicked(mouseEvent -> handleTabClick(tab));
            }
        }
    }

    /**
     * Méthode gérant le clic sur un onglet.
     *
     * @param tab L'onglet cliqué.
     */
    private void handleTabClick(VBox tab) {
        int index = changeTab.getChildren().indexOf(tab) + 1;

        for (Node node : tabContainer.getChildren()) {
            if (!(node instanceof Pane)) continue;

            Pane pane = (Pane) node;
            String id = pane.getId();

            if (PIECE_MAP.get(index).contains(id)) {
                handlePaneVisibility(pane, id);
                if (lastPanel.equals("player")) {
                    TableauController.playersController.savePlayers();
                }

                switch (id) {
                    case "player":
                        loadPlayerTab();
                        break;
                    case "newPartie":
                        loadNewPartieTab();
                        break;
                    case "partie_Toutes":

                        // display
                        ScrollPane scrollPane = (ScrollPane) TableauController.getScene().lookup("#historyAllPlayers");

                        TableauController.playersController.setPlayersScrollList(scrollPane);

                        TableauController.playersController.displayPlayersHistory();

                        break;
                    case "partie_Archives":
                        break;
                    default:
                        break;
                }

                lastPanel = id;
            } else {
                setPaneInvisible(pane);
            }
        }
    }

    /**
     * Méthode affichant le panneau choisi, change l'opacité de celui-ci
     * et l'active.
     *
     * @param pane Le panneau à rendre visible.
     * @param id L'indentifiant du panneau.
     */
    private void handlePaneVisibility(Pane pane, String id) {
        pane.setVisible(true);
        pane.setDisable(false);
        pane.setOpacity(1.0);
    }

    /**
     * Méthode rendant le panneau spécifié invisible, change l'opacité
     * de celui-ci et le désactive.
     *
     * @param pane Le panneau à rendre invisible.
     */
    private void setPaneInvisible(Pane pane) {
        pane.setVisible(false);
        pane.setDisable(true);
        pane.setOpacity(0.0);
    }

    /**
     * Méthode affichant le contenu du tableau des joueurs.
     */
    private void loadPlayerTab() {
        TableauController.playersController.setInPlayers(false);
        ScrollPane scrollPane = (ScrollPane) TableauController.getScene().lookup("#allPlayersScroll");
        TableauController.playersController.setPlayersScrollList(scrollPane);
        TableauController.playersController.displayPlayers();
    }

    /**
     * Méthode affichant le contenu pour charger une nouvelle partie.
     */
    private void loadNewPartieTab() {
        if (!TableauEchec.isGameStarted() && !TableauEchec.didGameEnd()) {
            prepareNewGame();
        } else {
            handleOngoingGame();
        }
    }

    /**
     * Méthode préparant le contenue pour créer une nouvelle partie.
     */
    private void prepareNewGame() {
        ScrollPane scrollPane = (ScrollPane) TableauController.getScene().lookup("#newPartieScroll");

        MenuButton menuButton = (MenuButton) TableauController.getScene().lookup("#menuButtonTimer");

        // Récupère les éléments du menu
        for (MenuItem item : menuButton.getItems()) {
            item.setOnAction(actionEvent -> {
                String timer = item.getText();

                menuButton.setText(timer);

                String[] split = timer.split(" ");
                int time = Integer.parseInt(split[0]);
                TableauEchec.baseTimer = time;

                TableauController.gameController.createTimer();
            });
        }

        // Selection du chronomètre
        TableauController.playersController.setInPlayers(true);
        TableauController.playersController.setPlayersScrollList(scrollPane);
        TableauController.playersController.displayPlayers();

        Button playButton = (Button) TableauController.getScene().lookup("#buttonPlay");
        playButton.setOnMouseClicked(mouseEvent -> startNewGame(scrollPane));
    }

    /**
     * Méthode démarrant une nouvelle partie avec les joueurs sélectionnés.
     *
     * @param scrollPane Zone de défilement de la liste des joueurs.
     */
    private void startNewGame(ScrollPane scrollPane) {
        if (TableauController.playersController.getSelectedPlayers().size() != 2) {
            System.out.println("You need to select 2 players to start the game");
            return;
        }

        TableauController.gameController.startGame();
        int random = (int) (Math.random() * 2);

        GameManager.Player player1 = PlayersController.getSelectedPlayers().get(random);
        GameManager.Player player2 = PlayersController.getSelectedPlayers().get(random == 0 ? 1 : 0);

        Label player1Name = (Label) TableauController.getScene().lookup("#blackPlayerName");
        Label player2Name = (Label) TableauController.getScene().lookup("#whitePlayerName");

        TableauEchec.joueurBlanc = player2;
        TableauEchec.joueurNoir = player1;

        player1Name.setText(player1.getName());
        player2Name.setText(player2.getName());

        clearScrollPaneContent(scrollPane);

        handleOngoingGame();
    }

    /**
     * Méthode gérant une partie en cours en affichant les mouvements
     * effectués.
     */
    private void handleOngoingGame() {
        ScrollPane scrollPane = (ScrollPane) TableauController.getScene().lookup("#newPartieScroll");

        VBox movesVBox = new VBox();
        movesVBox.setId("movesVBox");

        populateMovesVBox(movesVBox);
        addMovesListener(movesVBox, scrollPane);

        ((VBox) scrollPane.getContent()).getChildren().add(movesVBox);
    }

    /**
     * Méthode effacant le contenu de la zone de défilement spécifiée.
     *
     * @param scrollPane Zone de défilement à effacer.
     */
    private void clearScrollPaneContent(ScrollPane scrollPane) {
        ((VBox) scrollPane.getContent()).getChildren().clear();
    }

    /**
     * Méthode remplissant la VBox avec les mouvements joués.
     *
     * @param movesVBox La VBox dans laquelle se trouve les mouvements.
     */
    private void populateMovesVBox(VBox movesVBox) {
        for (Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> move : TableauEchec.movesPlayed) {
            Label moveLabel = new Label(formatMove(move,TableauEchec.movesPlayed.size()));
            moveLabel.setTextFill(Color.WHITE);
            movesVBox.getChildren().add(moveLabel);
        }
    }

    /**
     * Méthode ajoutant un listener afin de mettre à jour la VBox lorsqu'un
     * nouveau mouvement a été effectué.
     *
     * @param movesVBox La VBox dans laquelle se trouve les mouvements.
     * @param scrollPane Le ScrollPane qui contient la VBox des
     *                   mouvements effectués.
     */
    private void addMovesListener(VBox movesVBox, ScrollPane scrollPane) {
        TableauEchec.movesPlayed.addListener((ListChangeListener<? super Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> move : c.getAddedSubList()) {
                        Label moveLabel = new Label(formatMove(move,TableauEchec.movesPlayed.size()));
                        movesVBox.getChildren().add(moveLabel);
                    }

                    scrollPane.layout();
                    scrollPane.setVvalue(5.0);
                }
            }
        });
    }

    /**
     * Méthode formatant un mouvement en notation d'échecs pour l'affichage.
     *
     * @param move Le mouvement à formater.
     * @param index L'index du mouvement dans la liste des mouvements.
     * @return Une chaîne de caractères représentant le mouvement formaté.
     */
    private String formatMove(Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> move, int index) {
        Pair<Integer, Integer> from = move.getKey();
        Pair<Integer, Integer> to = move.getValue();
        return String.format("%d. %s to %s", index, toChessNotation(from), toChessNotation(to));
    }

    /**
     * Méthode convertissant une position de la grille en notation d'échecs
     * pour l'affichage.
     *
     * @param position La position à convertir.
     * @return Une chaîne de caractères représentant la position à convertir.
     */
    private String toChessNotation(Pair<Integer, Integer> position) {
        int fileIndex = position.getValue();
        int rankIndex = position.getKey();

        char file = (char) ('a' + fileIndex);
        int rank = 8 - rankIndex;

        return String.format("%c%d", file, rank);
    }
}
