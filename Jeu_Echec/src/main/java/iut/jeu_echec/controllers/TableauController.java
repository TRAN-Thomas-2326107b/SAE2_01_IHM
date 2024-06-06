package iut.jeu_echec.controllers;

import iut.jeu_echec.Jeu.Pieces.Piece;
import iut.jeu_echec.Jeu.TableauEchec;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.effect.BlendMode;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class TableauController {
    @FXML
    private GridPane tableauGrid;

    @FXML
    private Label tempsJoueurBlanc;

    @FXML
    private Label tempsJOueurNoir;

    private int selectedCol = -1;
    private int selectedRow = -1;

    private Piece selectedPiece = null;


    private final static String COULEUR_CLICK_BEIGE = "#f5f681";
    private final static String COULEUR_CLICK_VERT = "#b9cb43";
    private final static String COULEUR_CAPTURE = "#ff6666";

    private Pair<Pair<Integer, Integer>, Color> lastMoveStart = null;
    private Pair<Pair<Integer, Integer>, Color> lastMoveEnd = null;
    private Pair<Pair<Integer, Integer>, Color> currentMoveStart = null;
    private Pair<Pair<Integer, Integer>, Color> currentMoveEnd = null;

    private List<Circle> circles = new ArrayList<>();
    private List<Rectangle> captureSquares = new ArrayList<>();

    private ImageView pieceImg = null;


    private Timeline timers;


    // create a list of all the moves already played :



    @FXML
    public void initialize() {
        for (Node node : tableauGrid.getChildren()) {
            if (node instanceof StackPane) {
                StackPane square = (StackPane)node;
                square.setOnMouseClicked(mouseEvent -> handleClick(square));
            }
        }


        //timeline
        timers =  new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (TableauEchec.getTourActuel() == TableauEchec.eBlanc && TableauEchec.whiteTimeSeconds > 0) {
                TableauEchec.whiteTimeSeconds--;
                int minutes = TableauEchec.whiteTimeSeconds / 60;
                int seconds = TableauEchec.whiteTimeSeconds % 60;
                tempsJoueurBlanc.setText(String.format("%02d:%02d", minutes, seconds));
            } else if (TableauEchec.getTourActuel() == TableauEchec.eNoir && TableauEchec.blackTimeSeconds > 0) {
                TableauEchec.blackTimeSeconds--;
                int minutes = TableauEchec.blackTimeSeconds / 60;
                int seconds = TableauEchec.blackTimeSeconds % 60;
                tempsJOueurNoir.setText(String.format("%02d:%02d", minutes, seconds));
            } else {
                timers.stop();
            }
        }));

        timers.setCycleCount(Timeline.INDEFINITE);
        timers.play();
    }




    private void handleClick(StackPane spane) {

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

        //rect.setFill(Paint.valueOf("red"));
        if (TableauEchec.BOARD[rowIndex][colIndex] == null && selectedPiece == null  )
            return;

        System.out.println("tour actuel: " + String.valueOf(TableauEchec.getTourActuel()));


        if (selectedPiece == null && TableauEchec.BOARD[rowIndex][colIndex].getEquipe() == TableauEchec.getTourActuel()) {
            selectedCol = colIndex;
            selectedRow = rowIndex;

            selectedPiece = TableauEchec.BOARD[rowIndex][colIndex];
            pieceImg = imView;


            if (currentMoveStart != null) {
                resetColor(currentMoveStart);
            }


            currentMoveStart = new Pair<>(new Pair<>(rowIndex, colIndex), (Color) rect.getFill());
            rect.setFill(((Color) rect.getFill()).brighter());

            selectedPiece.afficheMouvement(selectedPiece.mouvementValides());
        }



        if (selectedPiece != null) {

            System.out.println("caca: " + selectedPiece);

            List<Pair<Integer,Integer>> mvtValides = selectedPiece.mouvementValides();

            selectedPiece.afficheMouvement(mvtValides);


            Pair<Integer,Integer> caseVoulue = new Pair<>(rowIndex,colIndex);

            boolean estValide = mvtValides.contains(caseVoulue);
            System.out.println(estValide);


            if (estValide) {

                StackPane parentPane = (StackPane) pieceImg.getParent();
                //rect.setFill(Paint.valueOf(COULEUR_CLICK));
                /**parentPane.getChildren().remove(pieceImg);

                 // Add the pieceImg to the new GridPane cell (spane)
                 spane.getChildren().add(pieceImg);

                 Piece targetPiece = TableauEchec.BOARD[rowIndex][colIndex];

                 if (targetPiece == null)
                 selectedPiece.mouvement(mvtValides, caseVoulue);
                 else {
                 spane.getChildren().remove(imView);
                 selectedPiece.mouvement(mvtValides,caseVoulue);
                 // TODO l'ajouter en bas du pseudo
                 }**/



                animatePiece(parentPane,spane,pieceImg,imView,selectedPiece,TableauEchec.BOARD[rowIndex][colIndex],caseVoulue);

                TableauEchec.changeTour();

                supprimeNoeuds(captureSquares);
                if (currentMoveEnd != null) {
                    resetColor(currentMoveEnd);
                }

                currentMoveEnd = new Pair<>(new Pair<>(rowIndex, colIndex), (Color) rect.getFill());
                rect.setFill(((Color) rect.getFill()).brighter());

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


            TableauEchec.afficheBoard(TableauEchec.BOARD);

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

            System.out.println("row: " + rowIndex + " col: " + colIndex);

            displayAvailableMoves(selectedPiece.mouvementValides());
            List<Pair<Integer, Integer>> captureMoves = Piece.canEat(selectedPiece.mouvementValides());
            displayCaptureMoves(captureMoves);
            //selectedPiece.afficheMouvement(selectedPiece.mouvementValides());

        } else if (TableauEchec.BOARD[rowIndex][colIndex] == null) {
            // si on click en dehors d'une piece qu'on peut jouer (case vide)
            supprimeNoeuds(circles);
            supprimeNoeuds(captureSquares);

            if (currentMoveStart != null) {
                resetColor(currentMoveStart);
            }

            if (lastMoveEnd != null) {
                resetColor(lastMoveEnd);
            }

            selectedPiece = null;
            pieceImg = null;

            System.out.println("row: " + rowIndex + " col: " + colIndex);
        }

    }



    private void animatePiece(StackPane parentPane, StackPane targetPane, ImageView pieceImg, ImageView targimg, Piece selectedPiece, Piece targetPiece, Pair<Integer,Integer> caseVoulue) {


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
                selectedPiece.mouvement(selectedPiece.mouvementValides(), caseVoulue);
            } else {
                targetPane.getChildren().remove(targimg);
                selectedPiece.mouvement(selectedPiece.mouvementValides(), caseVoulue);
            }

            targetPane.getChildren().add(pieceImg);
            parentPane.getChildren().remove(pieceImg);

            pieceImg.setTranslateX(0);
            pieceImg.setTranslateY(0);

            parentPane.toBack();
        });

        transition.play();
    }


    private void displayCaptureMoves(List<Pair<Integer, Integer>> captureMoves) {

        for (Pair<Integer, Integer> move : captureMoves) {
            StackPane pane = getSquareAt(move.getKey(), move.getValue());

            Rectangle captureSquare = new Rectangle(pane.getWidth(), pane.getHeight() - 4);
            captureSquare.setFill(Color.web(COULEUR_CAPTURE));
            captureSquare.setOpacity(0.75);
            //captureSquare.setBlendMode(BlendMode.MULTIPLY);

            System.out.println("ajout: ");
            System.out.println(pane.getChildren());

            captureSquares.add(captureSquare);
            if (pane != null) {
                pane.getChildren().add(captureSquare);
            }
        }
    }


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

    private void supprimeNoeuds(List<? extends Node> nodes) {
        for (Node node : nodes) {
            StackPane pane = (StackPane) node.getParent();
            if (pane != null) {
                System.out.println(pane.getChildren());
                System.out.println("removing: " + node);
                pane.getChildren().remove(node);
            }
        }
        nodes.clear();
    }

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


    private StackPane getSquareAt(int row, int col) {
        for (Node node : tableauGrid.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col)
                return (StackPane)node;
        }

        return null;
    }


}