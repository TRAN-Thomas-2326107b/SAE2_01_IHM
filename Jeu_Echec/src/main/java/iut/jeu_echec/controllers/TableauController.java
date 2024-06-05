package iut.jeu_echec.controllers;

import iut.jeu_echec.Jeu.Pieces.Piece;
import iut.jeu_echec.Jeu.TableEchec;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.List;

public class TableauController {
    @FXML
    private GridPane tableauGrid;


    private int selectedCol = -1;
    private int selectedRow = -1;

    private Piece selectedPiece = null;
    private ImageView pieceImg = null;

    private int numClick = 0;

    private byte currentTurn = TableEchec.eBlanc;

    @FXML
    public void initialize() {
        for (Node node : tableauGrid.getChildren()) {
            if (node instanceof StackPane) {
                StackPane square = (StackPane)node;
                square.setOnMouseClicked(mouseEvent -> handleClick(square));
            }
        }
    }

    private void handleClick(StackPane spane) {;

        Integer rowIndex = GridPane.getRowIndex(spane);
        Integer colIndex = GridPane.getColumnIndex(spane);



        Rectangle rect = null;
        ImageView imView = null;

        for (Node child : spane.getChildren()) {
            if (child instanceof Rectangle)
                rect = (Rectangle)child;
            if (child instanceof ImageView)
                imView = (ImageView)child;

        }

        if (rect == null)
            return;

        //rect.setFill(Paint.valueOf("red"));
        if (TableEchec.BOARD[rowIndex][colIndex] == null && selectedPiece == null  )
            return;

        System.out.println("tour actuel: " + String.valueOf(TableEchec.getTourActuel()));


        if (selectedPiece == null && TableEchec.BOARD[rowIndex][colIndex].getEquipe() == TableEchec.getTourActuel()) {
            selectedCol = colIndex;
            selectedRow = rowIndex;

            selectedPiece = TableEchec.BOARD[rowIndex][colIndex];
            pieceImg = imView;

            selectedPiece.afficheMouvement(selectedPiece.mouvementValides());
        }



        if (selectedPiece != null) {

            System.out.println("caca: " + selectedPiece);

            List<Pair<Integer,Integer>> mvtValides = selectedPiece.mouvementValides();
            Pair<Integer,Integer> caseVoulue = new Pair<>(rowIndex,colIndex);

            boolean estValide = mvtValides.contains(caseVoulue);
            System.out.println(estValide);


            if (estValide) {
                StackPane parentPane = (StackPane) pieceImg.getParent();
                parentPane.getChildren().remove(pieceImg);

                // Add the pieceImg to the new GridPane cell (spane)
                spane.getChildren().add(pieceImg);

                Piece targetPiece = TableEchec.BOARD[rowIndex][colIndex];

                if (targetPiece == null)
                    selectedPiece.mouvement(mvtValides, caseVoulue);
                else {
                    spane.getChildren().remove(imView);
                    selectedPiece.mouvement(mvtValides,caseVoulue);
                    // TODO l'ajouter en bas du pseudo
                }
                selectedPiece = null;
                pieceImg = null;
                TableEchec.changeTour();
            }

            TableEchec.afficheBoard(TableEchec.BOARD);

        }

        if (selectedPiece != null && (TableEchec.BOARD[rowIndex][colIndex] != null && selectedPiece.getEquipe() == TableEchec.BOARD[rowIndex][colIndex].getEquipe())) {
            selectedCol = colIndex;
            selectedRow = rowIndex;

            selectedPiece = TableEchec.BOARD[rowIndex][colIndex];
            pieceImg = imView;
            selectedPiece.afficheMouvement(selectedPiece.mouvementValides());
            return;
        }

        System.out.println("row: " + rowIndex + " col: " + colIndex);

    }


}