package iut.jeu_echec.Jeu.Pieces;

import iut.jeu_echec.Jeu.TableauEchec;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Fou extends Piece {

    public Fou(byte equipe, int x, int y) {
        super(Pieces.FOU,equipe,x,y);
    }

    @Override
    public List<Pair<Integer, Integer>> mouvementValides() {
        final int posX = this.getX();
        final int posY = this.getY();
        List<Pair<Integer,Integer>> mvtValides = new ArrayList<>();

        //mouvement valide vers bas-gauche
        int i = 1;
        int j = 1;
        while ((posX + i) < 8 && (posY - j) >= 0 && TableauEchec.BOARD[posX + i][posY - j] == null
                || (posX + i) < 8 && (posY - j) >= 0 && this.getEquipe() != TableauEchec.BOARD[posX + i][posY - j].getEquipe()){
            mvtValides.add(new Pair<>(posX+i,posY-j));
            if ( TableauEchec.BOARD[posX + i][posY - j] != null && this.getEquipe() != TableauEchec.BOARD[posX + i][posY - j].getEquipe())
                break;
            ++i;
            ++j;
        }

        //mouvement valide vers bas-droit
        int k = 1;
        int l = 1;
        while ((posX + k) < 8 && (posY + l) < 8 && TableauEchec.BOARD[posX + k][posY + l] == null
                || (posX + k) < 8 && (posY + l) < 8 && this.getEquipe() != TableauEchec.BOARD[posX + k][posY + l].getEquipe()){
            mvtValides.add(new Pair<>(posX+k,posY+l));
            if ( TableauEchec.BOARD[posX + k][posY + l] != null && this.getEquipe() != TableauEchec.BOARD[posX + k][posY + l].getEquipe())
                break;
            ++k;
            ++l;
        }

        //mouvement valide vers haut-gauche
        int m = 1;
        int n = 1;
        while ((posX - m) >= 0 && (posY - n) >= 0 && TableauEchec.BOARD[posX - m][posY - n] == null
                || (posX - m) >= 0 && (posY - n) >= 0 && this.getEquipe() != TableauEchec.BOARD[posX - m][posY - n].getEquipe()){
            mvtValides.add(new Pair<>(posX-m,posY-n));
            if ( TableauEchec.BOARD[posX - m][posY - n] !=  null && this.getEquipe() != TableauEchec.BOARD[posX - m][posY - n].getEquipe())
                break;
            ++m;
            ++n;
        }

        //mouvement valide vers haut-droit
        int o = 1;
        int p = 1;
        while ( (posX - o) >= 0 && (posY + p) < 8 && TableauEchec.BOARD[posX - o][posY + p] == null
                || (posX - o) >= 0 && (posY + p) < 8 && this.getEquipe() != TableauEchec.BOARD[posX - o][posY + p].getEquipe()){
            mvtValides.add(new Pair<>(posX-o,posY+p));
            if ( TableauEchec.BOARD[posX - o][posY + p] !=  null && this.getEquipe() != TableauEchec.BOARD[posX - o][posY + p].getEquipe())
                break;
            ++o;
            ++p;
        }

        return mvtValides;
    }

    public String getImage() {
        return this.getEquipe() == TableauEchec.eNoir ? "/iut/jeu_echec/imgs/pions/bb.png" : "/iut/jeu_echec/imgs/pions/wb.png";
    }

    @Override
    public String toString() {
        return "F" + (this.getEquipe() == (byte)0 ? "_N" : "_B");
    }
}
