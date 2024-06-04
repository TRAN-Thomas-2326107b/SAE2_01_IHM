package iut.jeu_echec.Jeu.Pieces;

import iut.jeu_echec.Jeu.TableEchec;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Cavalier extends Piece {

    public Cavalier(byte equipe, int x, int y) {
        super(Pieces.CAVALIER,equipe,x,y);
    }


    @Override
    public List<Pair<Integer, Integer>> mouvementValides() {
        final int posX = this.getX();
        final int posY = this.getY();
        List<Pair<Integer,Integer>> mvtValides = new ArrayList<>();

        int[][] mvtPossibles = {
                {posX + 2, posY + 1}, {posX + 2, posY - 1},
                {posX - 2, posY + 1}, {posX - 2, posY - 1},
                {posX + 1, posY + 2}, {posX + 1, posY - 2},
                {posX - 1, posY + 2}, {posX - 1, posY - 2}
        };

        for (int[] move : mvtPossibles) {
            int newX = move[0];
            int newY = move[1];

            // Check if the move is within bounds
            if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                Piece destinationPiece = TableEchec.BOARD[newX][newY];
                // If the destination is empty or has an opponent's piece, it's a valid move
                if (destinationPiece == null || (destinationPiece != null && destinationPiece.getEquipe() != this.getEquipe())) {
                    mvtValides.add(new Pair<>(newX, newY));
                }
            }
        }


        return mvtValides;
    }


    @Override
    public void graphic() {

    }

    @Override
    public String toString() {
        return "C" + (this.getEquipe() == (byte)0 ? "_N" : "_B");
    }
}
