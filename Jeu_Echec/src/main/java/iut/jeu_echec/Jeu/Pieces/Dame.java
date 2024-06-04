package iut.jeu_echec.Jeu.Pieces;

import iut.jeu_echec.Jeu.TableEchec;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Dame extends Piece{

    public Dame(byte equipe, int x, int y) {
        super(Pieces.DAME,equipe,x,y);
    }

    @Override
    public List<Pair<Integer, Integer>> mouvementValides() {
        final int posX = this.getX();
        final int posY = this.getY();
        List<Pair<Integer,Integer>> mvtValides = new ArrayList<>();

        //mouvement vers le bas
        int a = 1;
        while ((posX + a) < 8 &&
                TableEchec.BOARD[posX + a][posY] == null
                || (posX + a) < 8 && this.getEquipe() != TableEchec.BOARD[posX + a][posY].getEquipe()) {
            mvtValides.add(new Pair<>(posX+a,posY));
            if ( TableEchec.BOARD[posX + a][posY] != null && this.getEquipe() != TableEchec.BOARD[posX + a][posY].getEquipe())
                break;
            ++a;
        }

        //mouvement vers le haut
        int b = 1;
        while ((posX - b) >= 0 &&
                TableEchec.BOARD[posX - b][posY] == null
                || (posX - b) >= 0 && this.getEquipe() != TableEchec.BOARD[posX - b][posY].getEquipe()) {

            mvtValides.add(new Pair<>(posX-b,posY));
            if ( TableEchec.BOARD[posX - b][posY] !=  null && this.getEquipe() != TableEchec.BOARD[posX - b][posY].getEquipe())
                break;
            ++b;
        }

        //mouvement vers la droite
        int c = 1;
        while ((posY + c) < 8 &&
                TableEchec.BOARD[posX][posY + c] == null ||
                (posY + c) < 8 && this.getEquipe() != TableEchec.BOARD[posX][posY + c].getEquipe()) {
            mvtValides.add(new Pair<>(posX,posY+c));
            if ( TableEchec.BOARD[posX][posY + c] !=  null && this.getEquipe() != TableEchec.BOARD[posX][posY + c].getEquipe())
                break;
            ++c;
        }

        //mouvement vers la gauche
        int d = 1;
        while ( (posY - d) >= 0 &&
                TableEchec.BOARD[posX][posY - d] == null ||
                (posY - d) >= 0 && this.getEquipe() != TableEchec.BOARD[posX][posY - d].getEquipe()) {
            mvtValides.add(new Pair<>(posX,posY-d));
            if ( TableEchec.BOARD[posX][posY - d] !=  null && this.getEquipe() != TableEchec.BOARD[posX][posY - d].getEquipe())
                break;
            ++d;
        }


        //mouvement vers bas-gauche
        int i = 1;
        int j = 1;
        while ((posX + i) < 8 && (posY - j) >= 0 && TableEchec.BOARD[posX + i][posY - j] == null
                || (posX + i) < 8 && (posY - j) >= 0 && this.getEquipe() != TableEchec.BOARD[posX + i][posY - j].getEquipe()){
            mvtValides.add(new Pair<>(posX+i,posY-j));
            if ( TableEchec.BOARD[posX + i][posY - j] != null && this.getEquipe() != TableEchec.BOARD[posX + i][posY - j].getEquipe())
                break;
            ++i;
            ++j;
        }

        //mouvement vers bas-droit
        int k = 1;
        int l = 1;
        while ((posX + k) < 8 && (posY + l) < 8 && TableEchec.BOARD[posX + k][posY + l] == null
                || (posX + k) < 8 && (posY + l) < 8 && this.getEquipe() != TableEchec.BOARD[posX + k][posY + l].getEquipe()){
            mvtValides.add(new Pair<>(posX+k,posY+l));
            if ( TableEchec.BOARD[posX + k][posY + l] != null && this.getEquipe() != TableEchec.BOARD[posX + k][posY + l].getEquipe())
                break;
            ++k;
            ++l;
        }

        //mouvement vers haut-gauche
        int m = 1;
        int n = 1;
        while ((posX - m) >= 0 && (posY - n) >= 0 && TableEchec.BOARD[posX - m][posY - n] == null
                || (posX - m) >= 0 && (posY - n) >= 0 && this.getEquipe() != TableEchec.BOARD[posX - m][posY - n].getEquipe()){
            mvtValides.add(new Pair<>(posX-m,posY-n));
            if ( TableEchec.BOARD[posX - m][posY - n] !=  null && this.getEquipe() != TableEchec.BOARD[posX - m][posY - n].getEquipe())
                break;
            ++m;
            ++n;
        }

        //mouvement vers haut-droit
        int o = 1;
        int p = 1;
        while ( (posX - o) >= 0 && (posY + p) < 8 && TableEchec.BOARD[posX - o][posY + p] == null
                || (posX - o) >= 0 && (posY + p) < 8 && this.getEquipe() != TableEchec.BOARD[posX - o][posY + p].getEquipe()){
            mvtValides.add(new Pair<>(posX-o,posY-p));
            if ( TableEchec.BOARD[posX - o][posY + p] !=  null && this.getEquipe() != TableEchec.BOARD[posX - o][posY + p].getEquipe())
                break;
            ++o;
            ++p;
        }


        return mvtValides;


    }


    @Override
    public void graphic() {

    }

    @Override
    public String toString() {
        return "D" + (this.getEquipe() == (byte)0 ? "_N" : "_B");
    }
};
