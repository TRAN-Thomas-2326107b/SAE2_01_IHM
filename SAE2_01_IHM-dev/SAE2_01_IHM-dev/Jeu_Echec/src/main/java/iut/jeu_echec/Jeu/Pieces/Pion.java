package iut.jeu_echec.Jeu.Pieces;

import iut.jeu_echec.Jeu.TableauEchec;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe pion héritant de la classe Piece
 * Représente un pion dans le jeu d'échecs.
 * Un pion peut se déplacer d'une ou deux cases en avant, à sa position
 * initiale, puis d'une seule case sur le reste de l'échquier et peut
 * capturer des pièces seulement en diagonale.
 */
public class Pion extends Piece {

    /**
     * Constructeur de la classe Pion
     *
     * @param equipe L'équipe à laquelle appartient la pièce
     * @param x La position sur l'axe des abscisses du Pion sur l'échiquier.
     * @param y La position sur l'axe des ordonnées du Pion sur l'échiquier.
     */
    public Pion(byte equipe, int x, int y) {
        super(Pieces.PION,equipe,x,y);
    }

    /**
     * Méthode calculant les mouvements valides pour un pion.
     *
     * @return Une liste de paires (x,y) représentant les mouvements possibles
     * où le Pion peut se déplacer.
     */
    @Override
    public List<Pair<Integer,Integer>> mouvementValides() {
        final int posX = this.getX();
        final int posY = this.getY();
        List<Pair<Integer, Integer>> mvtValides = new ArrayList<>();

        // Si le Pion est dans l'équipe noir
        if (this.getEquipe() == TableauEchec.eNoir) {
            // pion noir avancant de 2 cases
            if (posX == 1 && TableauEchec.BOARD[posX + 2][posY] == null && TableauEchec.BOARD[posX + 1][posY] == null) {
                mvtValides.add(new Pair<>(posX + 2, posY));
            }
            // pion noir avancant d'1 cases
            if (posX + 1 < 8 && TableauEchec.BOARD[posX + 1][posY] == null) {
                mvtValides.add(new Pair<>(posX + 1, posY));
            }
            // pion noir capturant en diagonale-gauche
            if (posX + 1 < 8 && posY - 1 >= 0) {
                Piece diag1 = TableauEchec.BOARD[posX + 1][posY - 1];
                if (diag1 != null && this.getEquipe() != diag1.getEquipe()) {
                    mvtValides.add(new Pair<>(posX + 1, posY - 1));
                }
            }
            // pion noir capturant en diagonale-droite
            if (posX + 1 < 8 && posY + 1 < 8) {
                Piece diag2 = TableauEchec.BOARD[posX + 1][posY + 1];
                if (diag2 != null && this.getEquipe() != diag2.getEquipe()) {
                    mvtValides.add(new Pair<>(posX + 1, posY + 1));
                }
            }
        }

        // Sinon le Pion est dans l'équipe blanche
        else {
            // pion blanc avancant de 2 cases
            if (posX == 6 && TableauEchec.BOARD[posX - 2][posY] == null && TableauEchec.BOARD[posX - 1][posY] == null) {
                mvtValides.add(new Pair<>(posX - 2, posY));
            }
            // pion blanc avancant d'1 case
            if (posX - 1 >= 0 && TableauEchec.BOARD[posX - 1][posY] == null) {
                mvtValides.add(new Pair<>(posX - 1, posY));
            }
            // pion blanc capturant en diagonale-gauche
            if (posX - 1 >= 0 && posY - 1 >= 0) {
                Piece diag1 = TableauEchec.BOARD[posX - 1][posY - 1];
                if (diag1 != null && this.getEquipe() != diag1.getEquipe()) {
                    mvtValides.add(new Pair<>(posX - 1, posY - 1));
                }
            }
            // pion blanc capturant en diagonale-droite
            if (posX - 1 >= 0 && posY + 1 < 8) {
                Piece diag2 = TableauEchec.BOARD[posX - 1][posY + 1];
                if (diag2 != null && this.getEquipe() != diag2.getEquipe()) {
                    mvtValides.add(new Pair<>(posX - 1, posY + 1));
                }
            }
        }
        return mvtValides;
    }

    /**
     * Méthode renvoyant l'URL de l'image représentant la pièce du Pion
     * en fonction de son équipe.
     *
     * @return Une chaîne de caractères correspondant au chemin vers
     * l'image du Pion.
     */
    public String getImage() {
        return this.getEquipe() == TableauEchec.eNoir ? "/iut/jeu_echec/imgs/pions/bp.png" : "/iut/jeu_echec/imgs/pions/wp.png";
    }

    /**
     * Méthode renvoyant une représentation en quelques caractères de la pièce
     * du Pion.
     *
     * @return Une chaîne de caractères correspondant au Pion noir ou blanc.
     */
    @Override
    public String toString() {
        return "P" + (this.getEquipe() == (byte)0 ? "_N" : "_B");
    }
}
