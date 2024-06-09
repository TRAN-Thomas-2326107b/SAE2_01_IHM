package iut.jeu_echec.controllers;

import iut.jeu_echec.ChessApp;
import iut.jeu_echec.Jeu.Pieces.Piece;
import iut.jeu_echec.Jeu.Pieces.Roi;
import iut.jeu_echec.Jeu.TableauEchec;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.util.Pair;
import javafx.collections.FXCollections;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Classe du contrôleur gérant les intéractions du jeu d'échecs.
 */
public class GameController {
    /**
     * Couleur utilisée lorsqu'une pièce peut en capturer une autre.
     */
    private final static String COULEUR_CAPTURE = "#ff6666";

    /**
     * Couleur utlisée lorsque le roi est en état d'échec.
     */
    private final static String COULEUR_ECHEC = "#ff9933";

    /**
     * Dernier emplacement de début de mouvement et sa couleur associée.
     */
    private Pair<Pair<Integer, Integer>, Color> lastMoveStart = null;

    /**
     * Dernier emplacement de fin de mouvement et sa couleur associée.
     */
    private Pair<Pair<Integer, Integer>, Color> lastMoveEnd = null;

    /**
     * Emplacement actuel du début de mouvement et sa couleur associée.
     */
    private Pair<Pair<Integer, Integer>, Color> currentMoveStart = null;

    /**
     * Emplacement actuel de fin de mouvement et sa couleur associée.
     */
    private Pair<Pair<Integer, Integer>, Color> currentMoveEnd = null;

    /**
     * Liste des cercles utlisés pour marquer les cases où la pièce peut
     * se déplacer.
     */
    private List<Circle> circles = new ArrayList<>();

    /**
     * Listes des rectangles utilisés pour marquer les casés où la pièce
     * peut en capturer une autre.
     */
    private List<Rectangle> captureSquares = new ArrayList<>();

    /**
     * Image de la pièce choisie.
     */
    private ImageView pieceImg = null;

    /**
     * Colonne de la case sélectionnée dans l'échiquier.
     */
    private int selectedCol = -1;

    /**
     * Ligne de la case sélectionnées dans l'échiquier.
     */
    private int selectedRow = -1;

    /**
     * Pièces choisie sur le plateau.
     */
    private Piece selectedPiece = null;

    /**
     * Chronomètre pour le temps de la partie.
     */
    private Timeline timers;

    /**
     * Grille représentant l'échiquier.
     */
    private GridPane tableauGrid;

    /**
     * Label indiquant le temps restant du joueur blanc.
     */
    private Label tempsJoueurBlanc;

    /**
     * Label indiquant le temps restant du joueur noir.
     */
    private Label tempsJoueurNoir;

    /**
     * Méthode initialisant un contrôleur du jeu avec l'échiquier, les
     * chronomètres des joueurs.
     *
     * @param tableauGrid La grille représentant l'échiquier.
     * @param tempsJoueurBlanc Le label indiquand le temps restant du
     *                         joueur blanc.
     * @param tempsJoueurNoir Le label indiquant le temps restant du
     *                        joueur noir.
     */
    public GameController(GridPane tableauGrid, Label tempsJoueurBlanc, Label tempsJoueurNoir) {
        this.tableauGrid = tableauGrid;
        this.tempsJoueurBlanc = tempsJoueurBlanc;
        this.tempsJoueurNoir = tempsJoueurNoir;

        for (Node node : tableauGrid.getChildren()) {
            if (node instanceof StackPane) {
                StackPane square = (StackPane)node;
                square.setOnMouseClicked(mouseEvent -> handleSquareClick(square));
            }
        }
        createTimer();
    }

    /**
     * Méthode créant un chronomètre pour la partie.
     */
    public void createTimer() {
        TableauEchec.whiteTimeSeconds = 60 * TableauEchec.baseTimer;
        TableauEchec.blackTimeSeconds = 60 * TableauEchec.baseTimer;
        tempsJoueurBlanc.setText(String.format("%02d:%02d", TableauEchec.whiteTimeSeconds / 60, 0));
        tempsJoueurNoir.setText(String.format("%02d:%02d", TableauEchec.blackTimeSeconds / 60, 0));
        tempsJoueurBlanc.setDisable(true);
        tempsJoueurNoir.setDisable(true);

        if (timers != null) {
            timers.stop();
        }

        PauseTransition pause = new PauseTransition(Duration.seconds(Math.random() * 5 +1));
        timers =  new Timeline(new KeyFrame(Duration.seconds(1), event -> {

            // Si c'est le tour de bot
            System.out.println(TableauEchec.joueurBlanc.isBot());
            System.out.println(TableauEchec.joueurNoir.isBot());
            if ((TableauEchec.joueurBlanc != null && TableauEchec.joueurBlanc.isBot() && TableauEchec.getTourActuel() == TableauEchec.eBlanc) ||
                    (TableauEchec.joueurNoir != null && TableauEchec.joueurNoir.isBot() && TableauEchec.getTourActuel() == TableauEchec.eNoir)) {
                pause.setOnFinished(e -> {
                    makeBotMove();
                    pause.setDuration(Duration.seconds(Math.random() * 5 + 1));
                });
                pause.play();
            }


            if (TableauEchec.getTourActuel() == TableauEchec.eBlanc && TableauEchec.whiteTimeSeconds > 0) {
                TableauEchec.whiteTimeSeconds--;
                int minutes = TableauEchec.whiteTimeSeconds / 60;
                int seconds = TableauEchec.whiteTimeSeconds % 60;
                tempsJoueurBlanc.setText(String.format("%02d:%02d", minutes, seconds));
                tempsJoueurBlanc.setDisable(false);
                tempsJoueurNoir.setDisable(true);
            } else if (TableauEchec.getTourActuel() == TableauEchec.eNoir && TableauEchec.blackTimeSeconds > 0) {
                TableauEchec.blackTimeSeconds--;
                int minutes = TableauEchec.blackTimeSeconds / 60;
                int seconds = TableauEchec.blackTimeSeconds % 60;
                tempsJoueurNoir.setText(String.format("%02d:%02d", minutes, seconds));
                tempsJoueurNoir.setDisable(false);
                tempsJoueurBlanc.setDisable(true);
            } else {
                // fin du jeu
                TableauEchec.setEndGame(true);
                timers.stop();
            }
        }));
        timers.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Méthode démarrant une nouvelle partie d'échecs.
     */
    public void startGame() {
        TableauEchec.joueurBlanc = null;
        TableauEchec.joueurNoir = null;

        // nettoie la grille
        for (Node node : tableauGrid.getChildren()) {
            if (node instanceof StackPane) {
                StackPane square = (StackPane)node;
                square.getChildren().removeIf(child -> child instanceof ImageView);
            }
        }

        TableauEchec.movesPlayed = FXCollections.observableArrayList();

        TableauEchec.createBoard();

        createTimer();
        timers.play();

        // Ajoute les pièces sur l'échiquier.
        for (int i = 0; i < TableauEchec.TAILLE; i++) {
            for (int j = 0; j < TableauEchec.TAILLE; j++) {
                Piece piece = TableauEchec.BOARD[i][j];
                if (piece != null) {
                    System.out.println(ChessApp.class.getResource(piece.getImage()));

                    ImageView img = new ImageView(ChessApp.class.getResource(piece.getImage()).toString());

                    img.setFitHeight(59);
                    img.setFitWidth(59);

                    StackPane square = getSquareAt(i, j);
                    square.getChildren().add(img);
                }
            }
        }
        TableauEchec.setGameStarted(true);
        TableauEchec.setEndGame(false);
    }

    /**
     * Traite les clics sur les cases de l'échiquier.
     * Gere la sélection des différentes pièces, la capture,
     * et le retour visuel des mouvements.
     * @param spane La case cliquée.
     */
    private void handleSquareClick(StackPane spane) {
        Integer rowIndex = GridPane.getRowIndex(spane);
        Integer colIndex = GridPane.getColumnIndex(spane);

        Rectangle rect = null;
        ImageView imView = null;

        for (Node child : spane.getChildren()) {
            if (child instanceof ImageView)
                imView = (ImageView)child;
            if (child instanceof Rectangle && rect == null) {
                rect = (Rectangle)child;
            }
        }

        if (rect == null)
            return;

        if (!TableauEchec.isGameStarted()) {
            return;
        }

        if (TableauEchec.didGameEnd()){
            timers.stop();
            return;
        }

        if (TableauEchec.BOARD[rowIndex][colIndex] == null && selectedPiece == null  )
            return;

        // Si c'est le tour du bot
        if ((TableauEchec.joueurBlanc != null && TableauEchec.joueurBlanc.isBot() && TableauEchec.getTourActuel() == TableauEchec.eBlanc) ||
                (TableauEchec.joueurNoir != null && TableauEchec.joueurNoir.isBot() && TableauEchec.getTourActuel() == TableauEchec.eNoir)) {
            return;
        }

        System.out.println("tour actuel: " + String.valueOf(TableauEchec.getTourActuel()));

        if (selectedPiece == null && TableauEchec.BOARD[rowIndex][colIndex].getEquipe() == TableauEchec.getTourActuel()) {
            selectedCol = colIndex;
            selectedRow = rowIndex;

            selectedPiece = TableauEchec.BOARD[rowIndex][colIndex];
            pieceImg = imView;

            if (currentMoveStart != null) {
                resetColor(currentMoveStart);
                resetSquareColor((StackPane)pieceImg.getParent(), selectedRow, selectedCol);
            }

            resetSquareColor((StackPane)pieceImg.getParent(), selectedRow, selectedCol);
            resetSquareColor(spane, rowIndex, colIndex);

            currentMoveStart = new Pair<>(new Pair<>(rowIndex, colIndex), (Color) rect.getFill());
            rect.setFill(((Color) rect.getFill()).brighter());

            for (int i = 0; i < TableauEchec.TAILLE; i++) {
                for (int j = 0; j < TableauEchec.TAILLE; j++) {
                    if (TableauEchec.BOARD[i][j] instanceof Roi) {
                        StackPane kingSquare = getSquareAt(i, j);
                        if (((Roi)(TableauEchec.BOARD[i][j])).estEnEchec()) {
                            colorSquare(kingSquare, COULEUR_ECHEC);
                        } else {
                            resetSquareColor(kingSquare, i, j);
                        }
                    }
                }
            }
        }

        if (selectedPiece != null) {
            System.out.println("pièce choisie: " + selectedPiece);

            List<Pair<Integer,Integer>> mvtValides;
            // Vérifie si le roi est en situation d'échec
            if (TableauEchec.isKingInCheck(selectedPiece.getEquipe(), false)) {
                mvtValides = TableauEchec.filterMovesToPreventCheck(selectedPiece);
            } else {
                mvtValides = selectedPiece.mouvementValides();
            }

            Pair<Integer,Integer> caseVoulue = new Pair<>(rowIndex,colIndex);
            boolean estValide = mvtValides.contains(caseVoulue);

            if (estValide) {
                StackPane parentPane = (StackPane) pieceImg.getParent();

                animatePiece(mvtValides,parentPane,spane,pieceImg,imView,selectedPiece,TableauEchec.BOARD[rowIndex][colIndex],caseVoulue);

                TableauEchec.changeTour();

                supprimeNoeuds(captureSquares);

                if (currentMoveEnd != null) {
                    resetColor(currentMoveEnd);
                }

                resetSquareColor(parentPane, selectedRow, selectedCol);
                resetSquareColor(spane, rowIndex, colIndex);

                currentMoveEnd = new Pair<>(new Pair<>(rowIndex, colIndex), (Color) rect.getFill());
                rect.setFill(((Color) rect.getFill()).brighter());

                TableauEchec.movesPlayed.add(new Pair<>(currentMoveStart.getKey(),currentMoveEnd.getKey()));


                if (lastMoveStart != null) {
                    resetColor(lastMoveStart);
                    supprimeNoeuds(captureSquares);
                }

                if (lastMoveEnd != null) {
                    resetColor(lastMoveEnd);
                    supprimeNoeuds(captureSquares);
                }

                lastMoveStart = currentMoveStart;
                lastMoveEnd = currentMoveEnd;

                supprimeNoeuds(circles);
                supprimeNoeuds(captureSquares);

                currentMoveStart = null;
                currentMoveEnd = null;

                selectedPiece = null;
                pieceImg = null;

            }
        }

        if (selectedPiece != null && (TableauEchec.BOARD[rowIndex][colIndex] != null && selectedPiece.getEquipe() == TableauEchec.BOARD[rowIndex][colIndex].getEquipe())) {
            selectedCol = colIndex;
            selectedRow = rowIndex;

            selectedPiece = TableauEchec.BOARD[rowIndex][colIndex];
            pieceImg = imView;

            supprimeNoeuds(circles);
            supprimeNoeuds(captureSquares);

            if (currentMoveStart != null) {
                resetColor(currentMoveStart);
            }

            currentMoveStart = new Pair<>(new Pair<>(rowIndex, colIndex), (Color) rect.getFill());
            rect.setFill(((Color) rect.getFill()).brighter());

            for (int i = 0; i < TableauEchec.TAILLE; i++) {
                for (int j = 0; j < TableauEchec.TAILLE; j++) {
                    if (TableauEchec.BOARD[i][j] instanceof Roi) {
                        StackPane kingSquare = getSquareAt(i, j);
                        if (((Roi)(TableauEchec.BOARD[i][j])).estEnEchec()) {
                            colorSquare(kingSquare, COULEUR_ECHEC);
                        } else {
                            resetSquareColor(kingSquare, i, j);
                        }
                    }
                }
            }

            System.out.println("row: " + rowIndex + " col: " + colIndex);

            List<Pair<Integer, Integer>> captureMoves = null;
            List<Pair<Integer, Integer>> availableMoves = null;

            if (TableauEchec.isKingInCheck(selectedPiece.getEquipe(), false)) {
                captureMoves = Piece.canEat(TableauEchec.filterMovesToPreventCheck(selectedPiece));
                availableMoves = TableauEchec.filterMovesToPreventCheck(selectedPiece);
            } else {
                captureMoves = Piece.canEat(selectedPiece.mouvementValides());
                availableMoves = selectedPiece.mouvementValides();
            }

            displayCaptureMoves(captureMoves);
            displayAvailableMoves(availableMoves);

        } else if (TableauEchec.BOARD[rowIndex][colIndex] == null) {
            // si on clic en dehors d'une piece qu'on peut jouer (case vide)
            supprimeNoeuds(circles);
            supprimeNoeuds(captureSquares);

            // réinitialise la couleur du dernier mouvement
            if (currentMoveStart != null) {
                resetColor(currentMoveStart);
            }

            if (currentMoveEnd != null) {
                resetColor(currentMoveEnd);
            }

            // Si on était en train de bouger le roi, on doit vérifier si celui-ci est en situation d'échec
            if (TableauEchec.BOARD[selectedRow][selectedCol] instanceof Roi) {
                if (((Roi)TableauEchec.BOARD[selectedRow][selectedCol]).estEnEchec()) {
                    colorSquare(getSquareAt(selectedRow,selectedCol), COULEUR_ECHEC);
                } else {
                    resetSquareColor(getSquareAt(selectedRow,selectedCol), selectedRow, selectedCol);
                }
            }
            selectedPiece = null;
            pieceImg = null;
        }

    }
    /**
     * Anime tous les déplacements des pièces / Manger les piéces.
     *
     * @param mvtValides Listes des mouvements valides.
     * @param parentPane Le pane parents de la pièce.
     * @param targetPane Le pane ou va aller la piéce.
     * @param pieceImg L'imageView de la pièce.
     * @param targimg   L'imageView de la pièce ciblée.
     * @param selectedPiece La pièce actuellement sélectionnée.
     * @param targetPiece La pièce ciblée lors d'une capture.
     * @param caseVoulue La postion voulue du mouvement.
     */
    private void animatePiece(List<Pair<Integer,Integer>> mvtValides, StackPane parentPane, StackPane targetPane, ImageView pieceImg, ImageView targimg, Piece selectedPiece, Piece targetPiece, Pair<Integer,Integer> caseVoulue) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(250),pieceImg);

        double startX = pieceImg.getTranslateX();
        double startY = pieceImg.getTranslateY();
        double endX = targetPane.getLayoutX() - parentPane.getLayoutX();
        double endY = targetPane.getLayoutY() - parentPane.getLayoutY();

        transition.setFromX(startX);
        transition.setFromY(startY);

        transition.setToX(endX);
        transition.setToY(endY);

        parentPane.toFront();

        transition.setOnFinished(event -> {
            pieceImg.toFront();

            if (targetPiece == null ) {
                selectedPiece.mouvement(mvtValides, caseVoulue);
            } else {
                targetPane.getChildren().remove(targimg);
                selectedPiece.mouvement(mvtValides, caseVoulue);
            }

            // Vérifie si le roi est en situation d'échec.
            for (int i = 0; i < TableauEchec.TAILLE; i++) {
                for (int j = 0; j < TableauEchec.TAILLE; j++) {
                    if (TableauEchec.BOARD[i][j] instanceof Roi) {
                        StackPane kingSquare = getSquareAt(i, j);
                        if (((Roi)(TableauEchec.BOARD[i][j])).estEnEchec()) {
                            colorSquare(kingSquare, COULEUR_ECHEC);
                        } else {
                            resetSquareColor(kingSquare, i, j);
                        }
                    }
                }
            }

            targetPane.getChildren().add(pieceImg);
            parentPane.getChildren().remove(pieceImg);

            pieceImg.setTranslateX(0);
            pieceImg.setTranslateY(0);

            parentPane.toBack();
        });
        transition.play();
    }

    /**
     * Méthode mettant en évidence les cases pouvant étre capturées avec de la couleur.
     *
     * @param captureMoves Liste des positions de capture possibles.
     */
    private void displayCaptureMoves(List<Pair<Integer, Integer>> captureMoves) {

        for (Pair<Integer, Integer> move : captureMoves) {
            StackPane pane = getSquareAt(move.getKey(), move.getValue());

            Rectangle captureSquare = new Rectangle(pane.getWidth(), pane.getHeight() - 4);
            captureSquare.setFill(Color.web(COULEUR_CAPTURE));
            captureSquare.setOpacity(0.75);
            captureSquares.add(captureSquare);
            if (pane != null) {
                pane.getChildren().add(captureSquare);
            }
        }
    }

    /**
     * Mise en évidence des cases qui sont des mouvements valides avec un cercle noir.
     *
     * @param movementsValides Liste des mouvements valides pour la pièce sélectionnée.
     */
    private void displayAvailableMoves(List<Pair<Integer, Integer>> movementsValides) {
        // afficher les differents mouvements valides avec un petit rond sur la case comme sur chesscom
        for (Pair<Integer,Integer> move : movementsValides) {

            StackPane pane = getSquareAt(move.getKey(),move.getValue());

            Circle circle = new Circle(5);
            circle.setFill(Color.BLACK);
            circle.setOpacity(0.75);

            circles.add(circle);
            if (pane != null) {
                pane.getChildren().add(circle);
            }
        }
    }

    /**
     * Méthode nettoyant l'interface lorsqu'une action est finie.
     *
     * @param nodes Liste des noeuds a supprimer.
     */
    private void supprimeNoeuds(List<? extends Node> nodes) {
        for (Node node : nodes) {
            StackPane pane = (StackPane) node.getParent();
            if (pane != null) {
                pane.getChildren().remove(node);
            }
        }
        nodes.clear();
    }

    /**
     * Méthode réinitialisant la couleur si le mouvement envisagé est abandonné.
     *
     * @param colorMove Pair de la couleur et la position du mouvement.
     */
    private void resetColor(Pair<Pair<Integer,Integer>, Color> colorMove) {
        int row = colorMove.getKey().getKey();
        int col = colorMove.getKey().getValue();

        Color color = colorMove.getValue();

        StackPane pane = getSquareAt(row,col);
        if (pane != null) {
            for (Node node : pane.getChildren()) {
                if (node instanceof Rectangle) {
                    ((Rectangle) node).setFill(color);
                }
            }
        }
    }

    /**
     * Méthode changeant la couleur de la case.
     * @param square Case sélectionnée.
     * @param color Couleur pour la case.
     */
    private void colorSquare(StackPane square, String color) {
        for (Node node : square.getChildren()) {
            if (node instanceof Rectangle) {
                ((Rectangle) node).setFill(Color.web(color));
            }
        }
    }

    /**
     * Méthode faisant revenir à l'état initial la case.
     *
     * @param square La case à réinitialiser.
     * @param row Index de la ligne.
     * @param col Index de la colonne.
     */
    private void resetSquareColor(StackPane square, int row, int col) {
        Color color;
        if ((row + col) % 2 == 0) {
            color = Color.web("#ebecd0");
        } else {
            color = Color.web("#739552");
        }
        for (Node node : square.getChildren()) {
            if (node instanceof Rectangle) {
                ((Rectangle) node).setFill(color);
            }
        }
    }

    /**
     * Méthode effectuant un mouvement automatique pour le bot.
     * Les mouvements sont choisis parmis la liste des mouvements valides.
     */
    private void makeBotMove() {
        List<Piece> botPieces = new ArrayList<>();
        // Récupère toutes les pièces du bot
        for (int i = 0; i < TableauEchec.TAILLE; i++) {
            for (int j = 0; j < TableauEchec.TAILLE; j++) {
                Piece piece = TableauEchec.BOARD[i][j];
                if (piece != null && piece.getEquipe() == TableauEchec.getTourActuel()) {
                    botPieces.add(piece);
                }
            }
        }

        Random rand = new Random();
        Piece selectedPiece = null;
        List<Pair<Integer, Integer>> validMoves = null;

        // Tant que la pièce ne possède pas de mouvements valides on choisit une autre pièce
        while (validMoves == null || validMoves.isEmpty()) {
            int randomPieceIndex = rand.nextInt(botPieces.size());
            selectedPiece = botPieces.get(randomPieceIndex);
            validMoves = TableauEchec.isKingInCheck(selectedPiece.getEquipe(), false)
                    ? TableauEchec.filterMovesToPreventCheck(selectedPiece)
                    : selectedPiece.mouvementValides();
        }

        // Choisi un mouvement valide aléatoire
        Pair<Integer, Integer> selectedMove = validMoves.get(rand.nextInt(validMoves.size()));

        int fromRow = selectedPiece.getX();
        int fromCol = selectedPiece.getY();
        int toRow = selectedMove.getKey();
        int toCol = selectedMove.getValue();

        StackPane fromPane = getSquareAt(fromRow, fromCol);
        StackPane toPane = getSquareAt(toRow, toCol);
        ImageView pieceImg = null;
        ImageView targetImg = null;

        for (Node child : fromPane.getChildren()) {
            if (child instanceof ImageView) {
                pieceImg = (ImageView) child;
                break;
            }
        }

        for (Node child : toPane.getChildren()) {
            if (child instanceof ImageView) {
                targetImg = (ImageView) child;
                break;
            }
        }

        animatePiece(validMoves, fromPane, toPane, pieceImg, targetImg, selectedPiece, TableauEchec.BOARD[toRow][toCol], selectedMove);

        TableauEchec.movesPlayed.add(new Pair<>(new Pair<>(fromRow, fromCol), new Pair<>(toRow, toCol)));

        TableauEchec.changeTour();
    }

    /**
     * Méthode récupérant un square.
     *
     * @param row Index de la ligne.
     * @param col Index de la colonne.
     * @return StackPane.
     */
    private StackPane getSquareAt(int row, int col) {
        for (Node node : tableauGrid.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col)
                return (StackPane)node;
        }
        return null;
    }
}
