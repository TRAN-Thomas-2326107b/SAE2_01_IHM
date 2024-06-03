package iut.jeu_echec.Jeu.Pieces;

import iut.jeu_echec.Jeu.TableEchec;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Tour extends Piece {

    public Tour(byte equipe, int x, int y) {
        super(Pieces.TOUR,equipe,x,y);
    }


    @Override
    public List<Pair<Integer, Integer>> mouvementValides() {
        return null;
    };

    @Override
    public void mouvement(List<Pair<Integer, Integer>> valides)

    @Override
    public void mouvement() {
        final int posX = this.getX();
        final int posY = this.getY();
        List<Pair<Integer,Integer>> mouvementValides = new ArrayList<>();

        int i = 1;
        while (TableEchec.BOARD[posX + i][posY] == null || this.getEquipe() != TableEchec.BOARD[posX + i][posY].getEquipe()) {
            mouvementValides.add(new Pair<>(posX+i,posY));
            ++i;
        }

        int j = 1;
        while (TableEchec.BOARD[posX - j][posY] == null || this.getEquipe() != TableEchec.BOARD[posX - j][posY].getEquipe()) {
            mouvementValides.add(new Pair<>(posX-j,posY));
            ++j;
        }

        int k = 1;
        while (TableEchec.BOARD[posX][posY + k] == null || this.getEquipe() != TableEchec.BOARD[posX][posY + k].getEquipe()) {
            mouvementValides.add(new Pair<>(posX,posY+k));
            ++k;
        }


        int l = 1;
        while (TableEchec.BOARD[posX][posY - l] == null || this.getEquipe() != TableEchec.BOARD[posX][posY - l].getEquipe()) {
            mouvementValides.add(new Pair<>(posX,posY-l));
            ++l;
        }

        System.out.println("Valid Moves:");
        for (Pair<Integer, Integer> move : mouvementValides) {
            System.out.println("Move: (" + move.getKey() + ", " + move.getValue() + ")");
        }

        //TableEchec.updateBoard(this,oldX,oldY);
    }

    @Override
    public void graphic() {

    }

    @Override
    public String toString() {
        return "T" + (this.getEquipe() == (byte)0 ? "_N" : "_B");
    }
}
