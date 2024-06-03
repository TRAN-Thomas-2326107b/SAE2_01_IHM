package iut.jeu_echec.Jeu.Pieces;

import javafx.util.Pair;

import java.util.List;

public abstract class Piece {
    private int typePiece;
    private int x;
    private int y;
    private byte equipe;


    Piece(Pieces type, byte equipe, int x, int y) {
        this.x = x;
        this.y = y;
        this.typePiece = type.ordinal();
        this.equipe = equipe;
    }

    public abstract List<Pair<Integer,Integer>> mouvementValides();

    public abstract void mouvement(List<Pair<Integer,Integer>> valides);
    public abstract void graphic();


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public byte getEquipe() {
        return equipe;
    }
}
