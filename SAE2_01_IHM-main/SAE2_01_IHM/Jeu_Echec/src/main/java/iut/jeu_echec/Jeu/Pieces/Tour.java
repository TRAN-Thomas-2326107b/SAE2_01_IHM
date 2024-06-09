package iut.jeu_echec.Jeu.Pieces;

import iut.jeu_echec.Jeu.TableauEchec;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Tour héritant de la classe Piece.
 * Représente une Tour dans le jeu d'échecs.
 * Une Tour peut se déplacer horizontalement et verticalement.
 */
public class Tour extends Piece {

    /**
     * Constructeur de la classe Tour.
     *
     * @param equipe L'équipe à laquelle appartient la Tour.
     * @param x La position sur l'axe des abscisses de la Tour sur l'échiquier.
     * @param y La position sur l'axe des ordonnées de la Tour sur l'échiquier.
     */
    public Tour(byte equipe, int x, int y) {
        super(Pieces.TOUR,equipe,x,y);
    }

    /**
     * Méthode calculant les mouvements valides de la Tour à sa
     * position actuelle.
     *
     * @return Une liste de paires (x,y) représentant toutes les positions
     * valides où la Tour peut se déplacer.
     */
    @Override
    public List<Pair<Integer, Integer>> mouvementValides() {
        final int posX = this.getX();
        final int posY = this.getY();
        List<Pair<Integer,Integer>> mvtValides = new ArrayList<>();

    // Mouvement valide vers le bas
        int i = 1;
        // Vérifie que la tour reste dans la grille et que la case est vide ou que la case soit occupée par une pièce adverse
        while ((posX + i) < 8 &&
                TableauEchec.BOARD[posX + i][posY] == null
                || (posX + i) < 8 && this.getEquipe() != TableauEchec.BOARD[posX + i][posY].getEquipe()) {
            // On ajoute un mouvement valide
            mvtValides.add(new Pair<>(posX+i,posY));
            // Si la case n'est pas vide et que la pièce présente est de la même équipe, le mouvement n'est pas valide
            if ( TableauEchec.BOARD[posX + i][posY] != null && this.getEquipe() != TableauEchec.BOARD[posX + i][posY].getEquipe())
                break;
            ++i;
        }

    // Mouvement valide vers le haut
        int j = 1;
        // Vérifie que la tour reste dans la grille et que la case est vide ou que la case soit occupée par une pièce adverse
        while ((posX - j) >= 0 &&
                TableauEchec.BOARD[posX - j][posY] == null
                || (posX - j) >= 0 && this.getEquipe() != TableauEchec.BOARD[posX - j][posY].getEquipe()) {
            // On ajoute un mouvement valide
            mvtValides.add(new Pair<>(posX-j,posY));
            // Si la case n'est pas vide et que la pièce présente est de la même équipe, le mouvement n'est pas valide
            if ( TableauEchec.BOARD[posX - j][posY] !=  null && this.getEquipe() != TableauEchec.BOARD[posX - j][posY].getEquipe())
                break;
            ++j;
        }

    // Mouvement valide vers la droite
        int k = 1;
        // Vérifie que la tour reste dans la grille et que la case est vide ou que la case soit occupée par une pièce adverse
        while ((posY + k) < 8 &&
                TableauEchec.BOARD[posX][posY + k] == null ||
                (posY + k) < 8 && this.getEquipe() != TableauEchec.BOARD[posX][posY + k].getEquipe()) {
            // On ajoute un mouvement valide
            mvtValides.add(new Pair<>(posX,posY+k));
            // Si la case n'est pas vide et que la pièce présente est de la même équipe, le mouvement n'est pas valide
            if ( TableauEchec.BOARD[posX][posY + k] !=  null && this.getEquipe() != TableauEchec.BOARD[posX][posY + k].getEquipe())
                break;
            ++k;
        }

    // Mouvement valide vers la gauche
        int l = 1;
        // Vérifie que la tour reste dans la grille et que la case est vide ou que la case soit occupée par une pièce adverse
        while ( (posY - l) >= 0 &&
                TableauEchec.BOARD[posX][posY - l] == null ||
                (posY - l) >= 0 && this.getEquipe() != TableauEchec.BOARD[posX][posY - l].getEquipe()) {
            // On ajoute un mouvement valide
            mvtValides.add(new Pair<>(posX,posY-l));
            // Si la case n'est pas vide et que la pièce présente est de la même équipe, le mouvement n'est pas valide
            if ( TableauEchec.BOARD[posX][posY - l] !=  null && this.getEquipe() != TableauEchec.BOARD[posX][posY - l].getEquipe())
                break;
            ++l;
        }
        return mvtValides;
    }

    /**
     * Méthode renvoyant l'URL de l'image représentant la pièce de la Tour
     * en fonction de son équipe.
     *
     * @return Une chaîne de caractères correspondant au chemin vers
     * l'image de la Tour.
     */
    public String getImage() {
        return this.getEquipe() == TableauEchec.eNoir ? "/iut/jeu_echec/imgs/pions/br.png" : "/iut/jeu_echec/imgs/pions/wr.png";
    }

    /**
     * Méthode renvoyant une représentation en quelques caractères de la pièce
     * de la Tour.
     *
     * @return Une chaîne de caractères correspondant à la Tour noire ou blanche.
     */
    @Override
    public String toString() {
        return "T" + (this.getEquipe() == (byte)0 ? "_N" : "_B");
    }
}
