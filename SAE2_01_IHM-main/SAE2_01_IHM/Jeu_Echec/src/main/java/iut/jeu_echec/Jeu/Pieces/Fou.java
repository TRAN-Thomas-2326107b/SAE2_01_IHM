package iut.jeu_echec.Jeu.Pieces;

import iut.jeu_echec.Jeu.TableauEchec;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Fou héritant de la classe Piece.
 * Représente un Fou dans le jeu d'échecs.
 * Un Fou peut se déplacer en diagonale.
 */
public class Fou extends Piece {

    /**
     * Constructeur de la classe Fou.
     *
     * @param equipe L'équipe à laquelle appartient le Fou.
     * @param x La position sur l'axe des abscisses du Fou sur l'échiquier.
     * @param y La position sur l'axe des ordonnées du Fou sur l'échiquier.
     */
    public Fou(byte equipe, int x, int y) {
        super(Pieces.FOU,equipe,x,y);
    }

    /**
     * Méthode calculant la liste des mouvements valides du Fou à sa
     * position actuelle.
     *
     * @return Une liste de paires (x,y) représentant toutes les positions
     * valides où le Fou peut se déplacer.
     */
    @Override
    public List<Pair<Integer, Integer>> mouvementValides() {
        final int posX = this.getX();
        final int posY = this.getY();
        List<Pair<Integer,Integer>> mvtValides = new ArrayList<>();

    // Mouvement valide vers bas-gauche
        int i = 1;
        int j = 1;
        // Vérifie que le fou reste dans la grille et que la case est vide ou que la case soit occupée par une pièce adverse
        while ((posX + i) < 8 && (posY - j) >= 0 && TableauEchec.BOARD[posX + i][posY - j] == null
                || (posX + i) < 8 && (posY - j) >= 0 && this.getEquipe() != TableauEchec.BOARD[posX + i][posY - j].getEquipe()){
            // On ajoute un mouvement valide
            mvtValides.add(new Pair<>(posX+i,posY-j));
            // Si la case n'est pas vide et que la pièce présente est de la même équipe, le mouvement n'est pas valide
            if ( TableauEchec.BOARD[posX + i][posY - j] != null && this.getEquipe() != TableauEchec.BOARD[posX + i][posY - j].getEquipe())
                break;
            ++i;
            ++j;
        }

    // Mouvement valide vers bas-droit
        int k = 1;
        int l = 1;
        // Vérifie que le fou reste dans la grille et que la case est vide ou que la case soit occupée par une pièce adverse
        while ((posX + k) < 8 && (posY + l) < 8 && TableauEchec.BOARD[posX + k][posY + l] == null
                || (posX + k) < 8 && (posY + l) < 8 && this.getEquipe() != TableauEchec.BOARD[posX + k][posY + l].getEquipe()){
            // On ajoute un mouvement valide
            mvtValides.add(new Pair<>(posX+k,posY+l));
            // Si la case n'est pas vide et que la pièce présente est de la même équipe, le mouvement n'est pas valide
            if ( TableauEchec.BOARD[posX + k][posY + l] != null && this.getEquipe() != TableauEchec.BOARD[posX + k][posY + l].getEquipe())
                break;
            ++k;
            ++l;
        }

    // Mouvement valide vers haut-gauche
        int m = 1;
        int n = 1;
        // Vérifie que le fou reste dans la grille et que la case est vide ou que la case soit occupée par une pièce adverse
        while ((posX - m) >= 0 && (posY - n) >= 0 && TableauEchec.BOARD[posX - m][posY - n] == null
                || (posX - m) >= 0 && (posY - n) >= 0 && this.getEquipe() != TableauEchec.BOARD[posX - m][posY - n].getEquipe()){
            // On ajoute un mouvement valide
            mvtValides.add(new Pair<>(posX-m,posY-n));
            // Si la case n'est pas vide et que la pièce présente est de la même équipe, le mouvement n'est pas valide
            if ( TableauEchec.BOARD[posX - m][posY - n] !=  null && this.getEquipe() != TableauEchec.BOARD[posX - m][posY - n].getEquipe())
                break;
            ++m;
            ++n;
        }

    // Mouvement valide vers haut-droit
        int o = 1;
        int p = 1;
        // Vérifie que le fou reste dans la grille et que la case est vide ou que la case soit occupée par une pièce adverse
        while ( (posX - o) >= 0 && (posY + p) < 8 && TableauEchec.BOARD[posX - o][posY + p] == null
                || (posX - o) >= 0 && (posY + p) < 8 && this.getEquipe() != TableauEchec.BOARD[posX - o][posY + p].getEquipe()){
            // On ajoute un mouvement valide
            mvtValides.add(new Pair<>(posX-o,posY+p));
            // Si la case n'est pas vide et que la pièce présente est de la même équipe, le mouvement n'est pas valide
            if ( TableauEchec.BOARD[posX - o][posY + p] !=  null && this.getEquipe() != TableauEchec.BOARD[posX - o][posY + p].getEquipe())
                break;
            ++o;
            ++p;
        }
        return mvtValides;
    }

    /**
     * Méthode renvoyant l'URL de l'image représentant la pièce du Fou
     * en fonction de son équipe.
     *
     * @return Une chaîne de caractères correspondant au chemin vers
     * l'image du Fou.
     */
    public String getImage() {
        return this.getEquipe() == TableauEchec.eNoir ? "/iut/jeu_echec/imgs/pions/bb.png" : "/iut/jeu_echec/imgs/pions/wb.png";
    }

    /**
     * Méthode renvoyant une représentation en quelques caractères de la pièce
     * du Fou.
     *
     * @return Une chaîne de caractères correspondant au Fou noir ou blanc.
     */
    @Override
    public String toString() {
        return "F" + (this.getEquipe() == (byte)0 ? "_N" : "_B");
    }
}
