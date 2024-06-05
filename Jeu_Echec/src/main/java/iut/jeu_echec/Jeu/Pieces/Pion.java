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
        List<Pair<Integer, Integer>> mvtValides = new ArrayList<>();

        if (this.getEquipe() == TableEchec.eNoir) {
            // pion noir avance de 2 cases
            if (posX == 1 && TableEchec.BOARD[posX + 2][posY] == null && TableEchec.BOARD[posX + 1][posY] == null) {
                mvtValides.add(new Pair<>(posX + 2, posY));
            }
            // pion noir avant d'1 cases
            if (posX + 1 < 8 && TableEchec.BOARD[posX + 1][posY] == null) {
                mvtValides.add(new Pair<>(posX + 1, posY));
            }
            // pion noir capture en diagonale-gauche
            if (posX + 1 < 8 && posY - 1 >= 0) {
                Piece diag1 = TableEchec.BOARD[posX + 1][posY - 1];
                if (diag1 != null && this.getEquipe() != diag1.getEquipe()) {
                    mvtValides.add(new Pair<>(posX + 1, posY - 1));
                }
            }
            // pion noir capture en diagonale-droite
            if (posX + 1 < 8 && posY + 1 < 8) {
                Piece diag2 = TableEchec.BOARD[posX + 1][posY + 1];
                if (diag2 != null && this.getEquipe() != diag2.getEquipe()) {
                    mvtValides.add(new Pair<>(posX + 1, posY + 1));
                }
            }
        } else {
            // pion blanc avance de 2 cases
            if (posX == 6 && TableEchec.BOARD[posX - 2][posY] == null && TableEchec.BOARD[posX - 1][posY] == null) {
                mvtValides.add(new Pair<>(posX - 2, posY));
            }
            // pion blanc avance d'1 case
            if (posX - 1 >= 0 && TableEchec.BOARD[posX - 1][posY] == null) {
                mvtValides.add(new Pair<>(posX - 1, posY));
            }
            // pion blanc capture en diagonale-gauche
            if (posX - 1 >= 0 && posY - 1 >= 0) {
                Piece diag1 = TableEchec.BOARD[posX - 1][posY - 1];
                if (diag1 != null && this.getEquipe() != diag1.getEquipe()) {
                    mvtValides.add(new Pair<>(posX - 1, posY - 1));
                }
            }
            // pion blanc capture en diagonale-droite
            if (posX - 1 >= 0 && posY + 1 < 8) {
                Piece diag2 = TableEchec.BOARD[posX - 1][posY + 1];
                if (diag2 != null && this.getEquipe() != diag2.getEquipe()) {
                    mvtValides.add(new Pair<>(posX - 1, posY + 1));
                }
            }
        }

        return mvtValides;
    };


    @Override
    public void graphic() {

    }

    @Override
    public String toString() {
        return "P" + (this.getEquipe() == (byte)0 ? "_N" : "_B");
    }
}