package iut.jeu_echec.Jeu.Pieces;

import iut.jeu_echec.Jeu.TableauEchec;
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


    public void afficheMouvement(List<Pair<Integer,Integer>> mvtValides) {
        System.out.println("Mouvements valides: ");
        for (int i = 0; i < mvtValides.size(); ++i) {
            Pair<Integer, Integer> move = mvtValides.get(i);
            System.out.println("Move #" + i + ": (" + move.getKey() + ", " + move.getValue() + ")");
        }
    }

    public void mouvement(List<Pair<Integer,Integer>> mvtValides, Pair<Integer,Integer> caseVoulue) {
        afficheMouvement(mvtValides);

        boolean estValide = mvtValides.contains(caseVoulue);

        try {
            if (estValide) {
                final int oldX = this.getX();
                final int oldY = this.getY();
                this.setX(caseVoulue.getKey());
                this.setY(caseVoulue.getValue());
                TableauEchec.updateBoard(this,oldX,oldY);
            }
        } catch (IndexOutOfBoundsException e) {
            return;
        }
    }

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
