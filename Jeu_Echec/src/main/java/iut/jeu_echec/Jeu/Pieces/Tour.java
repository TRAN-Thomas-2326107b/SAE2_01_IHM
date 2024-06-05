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
        final int posX = this.getX();
        final int posY = this.getY();
        List<Pair<Integer,Integer>> mvtValides = new ArrayList<>();

        //mouvement valide vers le bas
        int i = 1;
        while ((posX + i) < 8 &&
                TableEchec.BOARD[posX + i][posY] == null
                || (posX + i) < 8 && this.getEquipe() != TableEchec.BOARD[posX + i][posY].getEquipe()) {
            mvtValides.add(new Pair<>(posX+i,posY));
            if ( TableEchec.BOARD[posX + i][posY] != null && this.getEquipe() != TableEchec.BOARD[posX + i][posY].getEquipe())
                break;
            ++i;
        }

        //mouvement valide vers le haut
        int j = 1;
        while ((posX - j) >= 0 &&
                TableEchec.BOARD[posX - j][posY] == null
                || (posX - j) >= 0 && this.getEquipe() != TableEchec.BOARD[posX - j][posY].getEquipe()) {

            mvtValides.add(new Pair<>(posX-j,posY));
            if ( TableEchec.BOARD[posX - j][posY] !=  null && this.getEquipe() != TableEchec.BOARD[posX - j][posY].getEquipe())
                break;
            ++j;
        }

        //mouvement valide vers la droite
        int k = 1;
        while ((posY + k) < 8 &&
                TableEchec.BOARD[posX][posY + k] == null ||
                (posY + k) < 8 && this.getEquipe() != TableEchec.BOARD[posX][posY + k].getEquipe()) {
            mvtValides.add(new Pair<>(posX,posY+k));
            if ( TableEchec.BOARD[posX][posY + k] !=  null && this.getEquipe() != TableEchec.BOARD[posX][posY + k].getEquipe())
                break;
            ++k;
        }

        //mouvement valide vers la gauche
        int l = 1;
        while ( (posY - l) >= 0 &&
                TableEchec.BOARD[posX][posY - l] == null ||
                (posY - l) >= 0 && this.getEquipe() != TableEchec.BOARD[posX][posY - l].getEquipe()) {
            mvtValides.add(new Pair<>(posX,posY-l));
            if ( TableEchec.BOARD[posX][posY - l] !=  null && this.getEquipe() != TableEchec.BOARD[posX][posY - l].getEquipe())
                break;
            ++l;
        }

        return mvtValides;

    }


    @Override
    public void graphic() {

    }

    @Override
    public String toString() {
        return "T" + (this.getEquipe() == (byte)0 ? "_N" : "_B");
    }
}
