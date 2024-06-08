package iut.jeu_echec.Jeu.Pieces;

import iut.jeu_echec.Jeu.TableauEchec;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Dame héritant de la classe Piece.
 * Représente une Dame dans le jeu d'échec.
 * La dame peut se déplacer horizontalement, verticalement et en
 * diagonale.
 */
public class Dame extends Piece{

    /**
     * Constructeur de la classe Dame.
     *
     * @param equipe L'équipe à laquelle appartient la Dame.
     * @param x La position sur l'axe des abscisses de la Dame sur l'échiquier.
     * @param y La position sur l'axe des ordonnées de la Dame sur l'échiquier.
     */
    public Dame(byte equipe, int x, int y) {
        super(Pieces.DAME,equipe,x,y);
    }

    /**
     * Méthode calculant la liste des mouvements valides de la Dame à sa 
     * @return
     */
    @Override
    public List<Pair<Integer, Integer>> mouvementValides() {
        final int posX = this.getX();
        final int posY = this.getY();
        List<Pair<Integer,Integer>> mvtValides = new ArrayList<>();

        //mouvement valide vers le bas
        int a = 1;
        while ((posX + a) < 8 && (TableauEchec.BOARD[posX + a][posY] == null ||
                this.getEquipe() != TableauEchec.BOARD[posX + a][posY].getEquipe())) {
            mvtValides.add(new Pair<>(posX + a, posY));
            if (TableauEchec.BOARD[posX + a][posY] != null && this.getEquipe() != TableauEchec.BOARD[posX + a][posY].getEquipe())
                break;
            ++a;
        }

// Mouvement valide vers le haut
        int b = 1;
        while ((posX - b) >= 0 && (TableauEchec.BOARD[posX - b][posY] == null ||
                this.getEquipe() != TableauEchec.BOARD[posX - b][posY].getEquipe())) {
            mvtValides.add(new Pair<>(posX - b, posY));
            if (TableauEchec.BOARD[posX - b][posY] != null && this.getEquipe() != TableauEchec.BOARD[posX - b][posY].getEquipe())
                break;
            ++b;
        }

// Mouvement valide vers la droite
        int c = 1;
        while ((posY + c) < 8 && (TableauEchec.BOARD[posX][posY + c] == null ||
                this.getEquipe() != TableauEchec.BOARD[posX][posY + c].getEquipe())) {
            mvtValides.add(new Pair<>(posX, posY + c));
            if (TableauEchec.BOARD[posX][posY + c] != null && this.getEquipe() != TableauEchec.BOARD[posX][posY + c].getEquipe())
                break;
            ++c;
        }

// Mouvement valide vers la gauche
        int d = 1;
        while ((posY - d) >= 0 && (TableauEchec.BOARD[posX][posY - d] == null ||
                this.getEquipe() != TableauEchec.BOARD[posX][posY - d].getEquipe())) {
            mvtValides.add(new Pair<>(posX, posY - d));
            if (TableauEchec.BOARD[posX][posY - d] != null && this.getEquipe() != TableauEchec.BOARD[posX][posY - d].getEquipe())
                break;
            ++d;
        }

// Mouvement valide vers bas-gauche
        int i = 1;
        int j = 1;
        while ((posX + i) < 8 && (posY - j) >= 0 && (TableauEchec.BOARD[posX + i][posY - j] == null ||
                this.getEquipe() != TableauEchec.BOARD[posX + i][posY - j].getEquipe())) {
            mvtValides.add(new Pair<>(posX + i, posY - j));
            if (TableauEchec.BOARD[posX + i][posY - j] != null && this.getEquipe() != TableauEchec.BOARD[posX + i][posY - j].getEquipe())
                break;
            ++i;
            ++j;
        }

// Mouvement valide vers bas-droit
        int k = 1;
        int l = 1;
        while ((posX + k) < 8 && (posY + l) < 8 && (TableauEchec.BOARD[posX + k][posY + l] == null ||
                this.getEquipe() != TableauEchec.BOARD[posX + k][posY + l].getEquipe())) {
            mvtValides.add(new Pair<>(posX + k, posY + l));
            if (TableauEchec.BOARD[posX + k][posY + l] != null && this.getEquipe() != TableauEchec.BOARD[posX + k][posY + l].getEquipe())
                break;
            ++k;
            ++l;
        }

// Mouvement valide vers haut-gauche
        int m = 1;
        int n = 1;
        while ((posX - m) >= 0 && (posY - n) >= 0 && (TableauEchec.BOARD[posX - m][posY - n] == null ||
                this.getEquipe() != TableauEchec.BOARD[posX - m][posY - n].getEquipe())) {
            mvtValides.add(new Pair<>(posX - m, posY - n));
            if (TableauEchec.BOARD[posX - m][posY - n] != null && this.getEquipe() != TableauEchec.BOARD[posX - m][posY - n].getEquipe())
                break;
            ++m;
            ++n;
        }

// Mouvement valide vers haut-droit
        int o = 1;
        int p = 1;
        while ((posX - o) >= 0 && (posY + p) < 8 && (TableauEchec.BOARD[posX - o][posY + p] == null ||
                this.getEquipe() != TableauEchec.BOARD[posX - o][posY + p].getEquipe())) {
            mvtValides.add(new Pair<>(posX - o, posY + p));
            if (TableauEchec.BOARD[posX - o][posY + p] != null && this.getEquipe() != TableauEchec.BOARD[posX - o][posY + p].getEquipe())
                break;
            ++o;
            ++p;
        }

        return mvtValides;
    }


    public String getImage() {
        return this.getEquipe() == TableauEchec.eNoir ? "/iut/jeu_echec/imgs/pions/bq.png" : "/iut/jeu_echec/imgs/pions/wq.png";
    }



    @Override
    public String toString() {
        return "D" + (this.getEquipe() == (byte)0 ? "_N" : "_B");
    }
};
