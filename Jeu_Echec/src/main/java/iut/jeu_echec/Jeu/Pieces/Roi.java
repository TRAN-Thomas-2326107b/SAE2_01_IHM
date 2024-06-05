package iut.jeu_echec.Jeu.Pieces;

import iut.jeu_echec.Jeu.TableEchec;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Roi extends Piece{
    public Roi(byte equipe, int x, int y) {
        super(Pieces.ROI,equipe,x,y);
    }

    @Override
    public List<Pair<Integer, Integer>> mouvementValides() {
        final int posX = this.getX();
        final int posY = this.getY();
        List<Pair<Integer,Integer>> mvtValides = new ArrayList<>();

        // toutes les positions possibles du cavalier autour de lui
        int[][] mvtPossibles = {
                {posX - 1, posY}, {posX - 1, posY + 1},
                {posX, posY + 1}, {posX + 1, posY + 1},
                {posX + 1, posY}, {posX + 1, posY - 1},
                {posX, posY - 1}, {posX - 1, posY - 1}
        };
        for (int[] move : mvtPossibles) {
            int newX = move[0];
            int newY = move[1];

            // Vérifie que le roi reste dans la grille
            if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                Piece destinationPiece = TableEchec.BOARD[newX][newY];
                // Si la case de destination est vide ou contient un ennemi, le mouvement est valide
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
        return "R" + (this.getEquipe() == (byte)0 ? "_N" : "_B");
    }
}
