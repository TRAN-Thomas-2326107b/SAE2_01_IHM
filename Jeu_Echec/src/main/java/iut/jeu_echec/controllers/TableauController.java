package iut.jeu_echec.controllers;

import iut.jeu_echec.Jeu.Pieces.Piece;
import iut.jeu_echec.Jeu.TableauEchec;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class TableauController {
    @FXML
    private GridPane tableauGrid;

    private int selectedCol = -1;
    private int selectedRow = -1;
    private Piece selectedPiece = null;
    private ImageView pieceImg = null;

    private static final String COULEUR_CLICK_BEIGE = "#f5f681";
    private static final String COULEUR_CLICK_VERT = "#b9cb43";

    private Pair<Pair<Integer, Integer>, Color> lastMoveStart = null;
    private Pair<Pair<Integer, Integer>, Color> lastMoveEnd = null;
    private Pair<Pair<Integer, Integer>, Color> currentMoveStart = null;
    private Pair<Pair<Integer, Integer>, Color> currentMoveEnd = null;

    private List<Circle> circles = new ArrayList<>();

    @FXML
    public void initialize() {
        for (Node node : tableauGrid.getChildren()) {
            if (node instanceof StackPane) {
                StackPane square = (StackPane) node;
                square.setOnMouseClicked(mouseEvent -> handleClick(square));
            }
        }
    }

        private void handleClick(StackPane spane) {
            Integer rowIndex = GridPane.getRowIndex(spane);
            Integer colIndex = GridPane.getColumnIndex(spane);

            Rectangle rect = null;
            ImageView imView = null;

            for (Node child : spane.getChildren()) {
                if (child instanceof Rectangle) {
                    rect = (Rectangle) child;
                }
                if (child instanceof ImageView) {
                    imView = (ImageView) child;
                }
            }

            if (rect == null) {
                return;
            }

            if (TableauEchec.BOARD[rowIndex][colIndex] == null && selectedPiece == null) {
                return;
            }

            if (selectedPiece == null && TableauEchec.BOARD[rowIndex][colIndex].getEquipe() == TableauEchec.getTourActuel()) {
                selectedCol = colIndex;
                selectedRow = rowIndex;
                selectedPiece = TableauEchec.BOARD[rowIndex][colIndex];
                pieceImg = imView;

                clearCircles();

                if (currentMoveStart != null) {
                    resetColor(currentMoveStart);
                }
                currentMoveStart = new Pair<>(new Pair<>(rowIndex, colIndex), (Color) rect.getFill());
                rect.setFill(Paint.valueOf(COULEUR_CLICK_VERT));
            }




            if (selectedPiece != null) {
                List<Pair<Integer, Integer>> mvtValides = selectedPiece.mouvementValides();


                for (Pair<Integer,Integer> mouvements : mvtValides) {
                    // display different possible movements

                    StackPane pane = getSquareAt(mouvements.getKey(), mouvements.getValue());

                    Circle circle = new Circle(10);
                    circle.setFill(Color.GREEN);
                    circle.setOpacity(0.5);


                    circles.add(circle);
                    pane.getChildren().add(circle);

                }

                Pair<Integer, Integer> caseVoulue = new Pair<>(rowIndex, colIndex);

                boolean estValide = mvtValides.contains(caseVoulue);

                if (estValide) {
                    StackPane parentPane = (StackPane) pieceImg.getParent();

                    // move simply to the correct stackpane
                    parentPane.getChildren().remove(pieceImg);
                    spane.getChildren().add(pieceImg);

                    /**
                    TranslateTransition transition = new TranslateTransition(Duration.millis(250), pieceImg);

                    double startX = pieceImg.getTranslateX();
                    double startY = pieceImg.getTranslateY();
                    double endX = spane.getLayoutX() - parentPane.getLayoutX(); // Adjusting for the layout positions
                    double endY = spane.getLayoutY() - parentPane.getLayoutY() - 2; // Adjusting for the layout positions

                    transition.setFromX(startX);
                    transition.setFromY(startY);
                    transition.setToX(endX);
                    transition.setToY(endY);

                    ImageView finalImView = imView;

                    transition.setOnFinished(event -> {



                    });

                    transition.play();**/

                    Piece targetPiece = TableauEchec.BOARD[rowIndex][colIndex];

                    if (targetPiece == null) {
                        selectedPiece.mouvement(mvtValides, caseVoulue);
                    } else {
                        spane.getChildren().remove(imView);
                        selectedPiece.mouvement(mvtValides, caseVoulue);
                    }

                    TableauEchec.changeTour();


                    if (currentMoveEnd != null) {
                        resetColor(currentMoveEnd);
                    }
                    currentMoveEnd = new Pair<>(new Pair<>(rowIndex, colIndex), (Color) rect.getFill());
                    rect.setFill(Paint.valueOf(COULEUR_CLICK_BEIGE));

                    if (lastMoveStart != null) {
                        resetColor(lastMoveStart);
                    }
                    if (lastMoveEnd != null) {
                        resetColor(lastMoveEnd);
                    }



                    lastMoveStart = currentMoveStart;
                    lastMoveEnd = currentMoveEnd;

                    currentMoveStart = null;
                    currentMoveEnd = null;

                    selectedPiece = null;
                    pieceImg = null;

                } else if (selectedPiece != null) {
                    System.out.println("caca");
                }
                TableauEchec.afficheBoard(TableauEchec.BOARD);
            }


            if ( selectedPiece != null && (TableauEchec.BOARD[rowIndex][colIndex] == null && selectedPiece.getEquipe() == TableauEchec.BOARD[rowIndex][colIndex].getEquipe())) {
                selectedCol = colIndex;
                selectedRow = rowIndex;
                selectedPiece = TableauEchec.BOARD[rowIndex][colIndex];
                pieceImg = imView;

                clearCircles();

                if (currentMoveStart != null) {
                    resetColor(currentMoveStart);
                }
                currentMoveStart = new Pair<>(new Pair<>(rowIndex, colIndex), (Color) rect.getFill());
                rect.setFill(Paint.valueOf(COULEUR_CLICK_VERT));
                return;
            }
        }

    private void resetColor(Pair<Pair<Integer, Integer>, Color> move) {
        int row = move.getKey().getKey();
        int col = move.getKey().getValue();
        Color color = move.getValue();

        StackPane pane = getSquareAt(row, col);
        if (pane != null) {
            for (Node child : pane.getChildren()) {
                if (child instanceof Rectangle) {
                    ((Rectangle) child).setFill(color);
                }
            }
        }
    }

    private void clearCircles() {
        for (Circle circle : circles) {
            StackPane parentPane = (StackPane) circle.getParent();
            if (parentPane != null) {
                parentPane.getChildren().remove(circle);
            }
        }
        circles.clear();
    }

    private StackPane getSquareAt(int row, int col) {
        for (Node node : tableauGrid.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return (StackPane) node;
            }
        }
        return null;
    }
}
