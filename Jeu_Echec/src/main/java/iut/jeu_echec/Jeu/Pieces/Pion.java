package iut.jeu_echec.Jeu.Pieces;

import iut.jeu_echec.Jeu.TableEchec;
import javafx.scene.control.Tab;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Pion extends Piece {


    public Pion(byte equipe, int x, int y) {
        super(Pieces.PION,equipe,x,y);
    }

    @Override
    public List<Pair<Integer,Integer>> mouvementValides() {
        final int posX = this.getX();
        final int posY = this.getY();
        List<Pair<Integer,Integer>> mouvementValides = new ArrayList<>();

        if (this.getEquipe() == TableEchec.eNoir){
            if (this.getX() == 1 && TableEchec.BOARD[posX+2][posY] == null) {
                mouvementValides.add(new Pair<>(posX+2,posY));
            }
            if (TableEchec.BOARD[posX+1][posY] == null){
                //this.setX(this.getX()+1);
                mouvementValides.add(new Pair<>(posX+1,posY));
            }

            try {
                Piece diag1 = TableEchec.BOARD[posX+1][posY-1];
                Piece diag2 = TableEchec.BOARD[posX+1][posY+1];
                if (diag1 != null && this.getEquipe() != diag1.getEquipe() ){
                    mouvementValides.add(new Pair<>(posX+1,posY-1));
                }
                if (diag2 != null && this.getEquipe() != diag2.getEquipe() ){
                    mouvementValides.add(new Pair<>(posX+1,posY+1));
                }
            } catch (ArrayIndexOutOfBoundsException exception) {
                return mouvementValides;
            }

        }
        else {
            if (this.getX() == 6 && TableEchec.BOARD[posX-2][posY] == null) {
                mouvementValides.add(new Pair<>(posX-2,posY));
            }
            if (TableEchec.BOARD[posX-1][this.getY()] == null){
                mouvementValides.add(new Pair<>(posX-1,this.getY()));
            }

            try {
                Piece diag1 = TableEchec.BOARD[posX-1][posY-1];
                Piece diag2 = TableEchec.BOARD[posX-1][posY+1];
                if (diag1 != null && this.getEquipe() != diag1.getEquipe() ){
                    mouvementValides.add(new Pair<>(posX+1,posY-1));
                }
                if (diag2 != null && this.getEquipe() != diag2.getEquipe() ){
                    mouvementValides.add(new Pair<>(posX+1,posY+1));
                }
            } catch (ArrayIndexOutOfBoundsException exception) {
                return mouvementValides;
            }
        }

        return mouvementValides;
    };

    @Override
    public void mouvement(List<Pair<Integer,Integer>> mouvementValides) {
        System.out.println("Mouvements valides: ");
        for (int i = 0; i < mouvementValides.size(); ++i) {
            Pair<Integer, Integer> move = mouvementValides.get(i);
            System.out.println("Move #" + i + ": (" + move.getKey() + ", " + move.getValue() + ")");
        }

        System.out.println("Entrez le numéro que vous voulez jouer: ");
        Scanner in = new Scanner(System.in);

        int key = in.nextInt();

        try {
            Pair<Integer,Integer> move = mouvementValides.get(key);
            final int oldX = this.getX();
            final int oldY = this.getY();
            this.setX(move.getKey());
            this.setY(move.getValue());

            TableEchec.updateBoard(this,oldX,oldY);
        } catch (IndexOutOfBoundsException e) {
            return;
        }

    }

    @Override
    public void graphic() {

    }

    @Override
    public String toString() {
        return "P" + (this.getEquipe() == (byte)0 ? "_N" : "_B");
    }
}
