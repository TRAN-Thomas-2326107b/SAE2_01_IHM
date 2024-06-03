package iut.jeu_echec.Jeu.Pieces;

import javafx.util.Pair;

import java.util.List;

public class Cavalier extends Piece {

    public Cavalier(byte equipe, int x, int y) {
        super(Pieces.CAVALIER,equipe,x,y);
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
        return "C" + (this.getEquipe() == (byte)0 ? "_N" : "_B");
    }
}
