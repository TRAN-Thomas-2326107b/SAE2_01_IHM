package iut.jeu_echec.Jeu.Pieces;

import javafx.util.Pair;

import java.util.List;

public class Fou extends Piece {

    public Fou(byte equipe, int x, int y) {
        super(Pieces.FOU,equipe,x,y);
    }


    @Override
    public List<Pair<Integer, Integer>> mouvementValides() {
        return null;
    }

    @Override
    public void mouvement(List<Pair<Integer, Integer>> valides) {

    }

    @Override
    public void graphic() {

    }

    @Override
    public String toString() {
        return "F" + (this.getEquipe() == (byte)0 ? "_N" : "_B");
    }
}
