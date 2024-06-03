package iut.jeu_echec.Jeu.Pieces;

import javafx.util.Pair;

import java.util.List;

public class Roi extends Piece{
    public Roi(byte equipe, int x, int y) {
        super(Pieces.ROI,equipe,x,y);
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
        return "R" + (this.getEquipe() == (byte)0 ? "_N" : "_B");
    }
}
