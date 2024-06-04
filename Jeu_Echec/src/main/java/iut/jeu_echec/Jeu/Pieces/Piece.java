package iut.jeu_echec.Jeu.Pieces;

import iut.jeu_echec.Jeu.TableEchec;
import javafx.util.Pair;

import java.util.List;
import java.util.Scanner;

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

    public void mouvement(List<Pair<Integer,Integer>> mvtValides) {
        System.out.println("Mouvements valides: ");
        for (int i = 0; i < mvtValides.size(); ++i) {
            Pair<Integer, Integer> move = mvtValides.get(i);
            System.out.println("Move #" + i + ": (" + move.getKey() + ", " + move.getValue() + ")");
        }

        System.out.println("Entrez le numéro que vous voulez jouer: ");
        Scanner in = new Scanner(System.in);

        int key = in.nextInt();

        try {
            Pair<Integer,Integer> move = mvtValides.get(key);
            final int oldX = this.getX();
            final int oldY = this.getY();
            this.setX(move.getKey());
            this.setY(move.getValue());

            TableEchec.updateBoard(this,oldX,oldY);
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
